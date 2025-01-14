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

  // Inizializzazione del carosello
  const dots = document.querySelectorAll(".dot");
  const items = document.querySelectorAll(".carosello-item");
  const container = document.querySelector(".carosello-container");

  let currentIndex = 0; // Tracks the current carousel item index

  /**
   * Funzione che aggiorna lo stato del dot attivo in base all'indice corrente
   * del carosello, cambiando il punto evidenziato.
   */
  function updateDots() {
    dots.forEach((dot) => dot.classList.remove("active"));
    dots[currentIndex].classList.add("active");
  }

  /**
   * Funzione che calcola l'indice corrente del carosello in base alla posizione
   * di scroll del contenitore, aggiornando di conseguenza il punto attivo.
   */
  function updateCurrentIndex() {
    const scrollPosition = container.scrollLeft;
    const itemWidth = items[0].offsetWidth;
    currentIndex = Math.round(scrollPosition / itemWidth);
    updateDots();
  }

  // Listener per l'evento di scroll del carosello
  container.addEventListener("scroll", updateCurrentIndex);

  // Comportamento di navigazione tramite i punti, per scorrere tra gli item del carosello
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      // Scorrimento al corrispettivo item del carosello
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
  document.getElementById("hamburger").addEventListener("click", handleClick);

  function handleClick() {
    const hamburger = document.getElementById("hamburger");
    const hamburgerCheck = document.getElementById("check");

    // Se il menu è chiuso, cambia l'icona a "chiudi", altrimenti a "apri"
    if (!hamburgerCheck.checked) {
      hamburger.src = "./assets/svg/closure.svg";
      hamburger.alt = "Close navigation menu";
    } else {
      hamburger.src = "./assets/svg/hamburger.svg";
      hamburger.alt = "Open navigation menu";
    }
  }

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
      const phone = form.phone.value.trim();
      const message = form.message.value.trim();

      // Regex per validare l'email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      // Validazione specifica per ogni campo
      if (!name) errors.push("Il campo 'Nome' è obbligatorio.");
      if (!surname) errors.push("Il campo 'Cognome' è obbligatorio.");
      if (!email || !emailRegex.test(email)) {
        errors.push("Inserisci un'email valida.");
      }
      if (phone && isNaN(phone)) {
        errors.push("Il numero di telefono deve contenere solo numeri.");
      }
      if (!message) errors.push("Il campo 'Messaggio' è obbligatorio.");
      if (message.length < 10)
        errors.push("Il messaggio deve contenere almeno 10 caratteri.");

      // Mostra errori se presenti
      if (errors.length > 0) {
        messageContainer.style.color = "red";
        errors.forEach((error) => {
          const errorElement = document.createElement("p");
          errorElement.textContent = error;
          messageContainer.appendChild(errorElement);
        });
        return;
      }

      // Se i dati sono validi, invia l'email
      try {
        const response = await fetch("api/information-requests/send", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userName: form.name.value,
            userSurname: form.surname.value,
            userEmail: form.email.value,
            userPhone: form.phone.value,
            informationRequestText: form.message.value,
          }),
        });

        if (response.ok) {
          messageContainer.style.color = "green";
          const successMessage = document.createElement("p");
          successMessage.textContent = "Email inviata con successo!";
          messageContainer.appendChild(successMessage);
          setTimeout(() => {
            location.href = "./index.html";
          }, 2000);
        } else {
          const errorData = await response.json();
          messageContainer.style.color = "red";
          const errorMessage = document.createElement("p");
          errorMessage.textContent =
            errorData.message ||
            "Errore nell'invio dell'email. Riprova più tardi.";
          messageContainer.appendChild(errorMessage);
        }
      } catch (error) {
        console.error("Errore durante l'invio dell'email:", error);
        messageContainer.style.color = "red";
        const errorMessage = document.createElement("p");
        errorMessage.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        messageContainer.appendChild(errorMessage);
      }
    });
  }

  updateCurrentIndex(); // Inizializza lo stato iniziale del carosello
  updateHeroHeight(); // Imposta la sezione hero all'altezza corretta
  ctaScroll(); // Inizializza il comportamento di scrolling delle CTA
  openAccordion(); // Inizializza le funzionalità degli accordion
  setupNavbarLinks(); // Inizializza il comportamento dei link della navbar
  isLogged(); // Verifica se l'utente è loggato
  logout(); // Effettua il logout dell'utente corrente
  contact(); // Invia la mail di contatto
});
