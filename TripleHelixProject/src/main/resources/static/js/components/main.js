window.addEventListener("DOMContentLoaded", () => {
  /**
   * Funzione che aggiorna l'altezza della sezione #hero in base all'altezza
   * della finestra e dell'header, adattando gli altri elementi correlati come
   * il background, il filtro oscurante e il contenuto della sezione.
   */
  function updateHeroHeight() {
    const header = document.querySelector("header");
    const hero = document.getElementById("hero");
    const heroBg = document.querySelector("#hero-bg");
    const heroDarkness = document.querySelector("#hero-bg-darkness");
    const heroContent = document.querySelector("#hero-content");

    // Calcola l'altezza disponibile per la sezione hero, sottraendo l'altezza dell'header
    const heroHeight = window.innerHeight - header.offsetHeight;

    // Imposta l'altezza della sezione hero e degli altri elementi correlati
    hero.style.height = `${heroHeight}px`;
    heroBg.style.height = `${heroHeight}px`;
    heroDarkness.style.height = `${heroHeight}px`;

    // Imposta l'altezza del contenuto della sezione hero al 90% dell'altezza della sezione
    heroContent.style.height = `${(heroHeight / 100) * 90}px`;
  }

  /**
   * Inizializza la funzionalità dell'accordion delle FAQ, che permette di aprire e chiudere
   * le risposte delle domande cliccate.
   */
  function openAccordion() {
    const faqItems = document.querySelectorAll(".faq-container");

    faqItems.forEach((faq) => {
      faq.addEventListener("click", () => {
        faq.classList.toggle("active");

        // Se un altro item è aperto, lo chiude
        faqItems.forEach((item) => {
          if (item !== faq && item.classList.contains("active")) {
            item.classList.remove("active");
          }
        });
      });
    });
  }

  /**
   * Aggiunge un comportamento di scroll morbido ai bottoni "discover more",
   * che porta l'utente alla sezione #hero con un'animazione di scorrimento.
   */
  function ctaScroll() {
    const ctas = document.querySelectorAll(".discover-more");
    const heroHeight = document.getElementById("hero").offsetHeight;

    ctas.forEach((cta) => {
      cta.addEventListener("click", () => {
        window.scrollTo({ top: heroHeight, behavior: "smooth" });
      });
    });
  }

  /**
   * Imposta il comportamento dei link della navbar, facendo uno scroll morbido
   * verso le diverse sezioni della pagina, tenendo conto dell'altezza dell'header.
   */
  function setupNavbarLinks() {
    const navLinks = document.querySelectorAll(".nav-mobile .scroll");
    const check = document.getElementById("check"); // Il checkbox (l'hamburger) del menu mobile
    const headerHeight = document.querySelector("header").offsetHeight;

    navLinks.forEach((link) => {
      link.addEventListener("click", (event) => {
        event.preventDefault();

        const href = link.getAttribute("href");

        if (href.startsWith("#")) {
          targetElement = document.querySelector(href);

          if (targetElement) {
            if (targetElement.id === "contact-form") {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight,
                behavior: "smooth",
              });
            } else {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight - 40,
                behavior: "smooth",
              });
            }
          }
        } else {
          window.location.href = href;
        }

        // Chiude il menu mobile dopo aver cliccato
        handleClick();
        if (check.checked) {
          check.checked = false;
        }
      });
    });
  }

  // Inizializzazione del carousel
  const dots = document.querySelectorAll(".dot");
  const items = document.querySelectorAll(".carousel-item");
  const container = document.querySelector(".carousel-container");

  let currentIndex = 0; // Tracks the current carousel item index

  // Imposta il primo punto come attivo all'inizio
  dots[currentIndex].classList.add("active");

  /**
   * Funzione che aggiorna lo stato del dot attivo in base all'indice corrente
   * del carousel, cambiando il punto evidenziato.
   */
  function updateDots() {
    dots.forEach((dot) => dot.classList.remove("active"));
    dots[currentIndex].classList.add("active");
  }

  /**
   * Funzione che calcola l'indice corrente del carousel in base alla posizione
   * di scroll del contenitore, aggiornando di conseguenza il punto attivo.
   */
  function updateCurrentIndex() {
    const scrollPosition = container.scrollLeft;
    const itemWidth = items[0].offsetWidth;
    currentIndex = Math.round(scrollPosition / itemWidth);
    updateDots();
  }

  // Listener per l'evento di scroll del carousel
  container.addEventListener("scroll", updateCurrentIndex);

  // Comportamento di navigazione tramite i punti, per scorrere tra gli item del carousel
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      // Scorrimento al corrispettivo item del carousel
      container.scrollTo({
        left: items[index].offsetLeft,
        behavior: "smooth",
      });
    });
  });

  /**
   * Gestisce la barra di progresso che si aggiorna in base alla posizione di scroll
   * della pagina, mostrando un'idea del progresso del visitatore nella lettura della pagina.
   */
  window.addEventListener("scroll", handleScroll);

  function handleScroll() {
    const winScroll =
      document.body.scrollTop || document.documentElement.scrollTop;

    const height =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;

    const scrolled = (winScroll / height) * 100;

    document.getElementById("progressBar").style.width = scrolled + "%";
  }

  /**
   * Gestisce l'apertura e la chiusura del menu mobile, cambiando l'icona dell'hamburger
   * (da apertura a chiusura) a seconda dello stato del menu.
   */

  /**
   * Verifica se l'utente è loggato e, in base alla risposta, reindirizza
   * alla pagina appropriata: dashboard se loggato, login altrimenti.
   */
  function isLogged() {
    const profile = document.getElementById("profile");
    let isUserLogged = false;

    profile.addEventListener("click", async () => {
      try {
        const response = await fetch("/pub/auth/is-logged");

        if (response.ok) {
          const text = await response.text();
          if (text === "true") {
            isUserLogged = true;
            console.log(isUserLogged);
          }
          if (isUserLogged) {
            location.href = "./dashboardUtente.html";
          } else {
            location.href = "./login.html";
          }
        }
      } catch (error) {
        console.error(error);
      }
    });
  }

  /**
   * Gestisce il logout dell'utente, inviando una richiesta POST al server per
   * disconnettere l'utente e reindirizzarlo alla homepage.
   */
  function logout() {
    const logout = document.getElementById("logout");

    logout.addEventListener("click", async () => {
      try {
        const response = await fetch("/pub/auth/logout", {
          method: "POST",
        });

        if (response.ok) {
          location.href = "/index.html";
          console.log("user logged out");
        }
      } catch (error) {
        console.error(error);
      }
    });
  }

  /**
   * Gestisce l'invio del modulo di contatto e la validazione dei dati inseriti.
   * Mostra errori in caso di campi non validi, o invia i dati via fetch se tutto è corretto.
   */
  function contact() {
    const form = document.querySelector("#contact-form");
    const messageContainer = document.createElement("div");
    messageContainer.id = "message-container";
    messageContainer.style.marginTop = "20px";
    form.appendChild(messageContainer);

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      // Pulisce i messaggi precedenti
      messageContainer.textContent = "";

      // Validazione dei campi: se ci sono errori, vengono mostrati nella pagina
      const errors = [];
      const name = form.name.value.trim();
      const surname = form.surname.value.trim();
      const email = form.email.value.trim();
      const institute = form.ente.value.trim();
      const participantQuantity = form.partecipanti.value.trim();
      const appointmentDate = form.data.value.trim();
      const timeSlot = form.durata.value.trim();
      const activity = form.attivita.value.trim();
      const bookingInfoReq = form.messaggio.value.trim();

      // Regex per validare l'email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      // Validazione specifica per ogni campo
      if (!name) errors.push("Il campo 'Nome' è obbligatorio.");
      if (!surname) errors.push("Il campo 'Cognome' è obbligatorio.");
      if (!email || !emailRegex.test(email)) {
        errors.push("Inserisci un'email valida.");
      }
      if (!institute) errors.push("Il campo 'Ente' è obbligatorio.");
      if (
        !participantQuantity ||
        isNaN(participantQuantity) ||
        participantQuantity <= 0
      ) {
        errors.push("Inserisci un numero di partecipanti valido.");
      }
      if (!appointmentDate) errors.push("Il campo 'Data' è obbligatorio.");
      if (!timeSlot) errors.push("Seleziona una durata valida.");
      if (!activity) errors.push("Seleziona un'attività valida.");
      if (bookingInfoReq.length > 500) {
        errors.push("Il messaggio non può superare i 500 caratteri.");
      }

      // Mostra errori se presenti
      if (errors.length > 0) {
        messageContainer.style.color = "red";
        errors.forEach((error) => {
          const errorMessage = document.createElement("p");
          errorMessage.textContent = error;
          messageContainer.appendChild(errorMessage);
        });
        return;
      }

      // Se i dati sono validi, invia la richiesta
      try {
        const response = await fetch("api/bookings/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            name,
            surname,
            email,
            institute,
            participantQuantity,
            appointmentDate,
            timeSlot,
            activity,
            bookingInfoReq,
          }),
        });

        if (response.ok) {
          messageContainer.style.color = "green";
          const successMessage = document.createElement("p");
          successMessage.textContent = "Prenotazione effettuata con successo!";
          messageContainer.appendChild(successMessage);
          setTimeout(() => {
            location.href = "./index.html";
          }, 2000);
        } else {
          const errorData = await response.json();
          messageContainer.style.color = "red";
          const errorMessage = document.createElement("p");
          errorMessage.textContent =
            errorData.message || "Errore durante la prenotazione.";
          messageContainer.appendChild(errorMessage);
        }
      } catch (error) {
        console.error("Errore durante la prenotazione:", error);
        messageContainer.style.color = "red";
        const errorMessage = document.createElement("p");
        errorMessage.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        messageContainer.appendChild(errorMessage);
      }
    });
  }

  updateHeroHeight();
  openAccordion();
  ctaScroll();
  setupNavbarLinks();
  isLogged();
  logout();
  contact();
});
