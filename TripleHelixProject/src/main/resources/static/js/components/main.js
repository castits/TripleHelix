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

  window.addEventListener("resize", updateHeroHeight);

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
  function discoverMoreScroll() {
    const ctas = document.querySelectorAll(".discover-more");

    ctas.forEach((cta) => {
      cta.addEventListener("click", (event) => {
        event.preventDefault();
        document.getElementById("about-us").scrollIntoView();
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
              });
            } else {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight - 40,
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
   * Gestisce l'invio del modulo di contatto e la validazione dei dati inseriti.
   * Mostra errori in caso di campi non validi, o invia i dati via fetch se tutto è corretto.
   */
  function contact() {
    const form = document.querySelector("#contact");

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      // Pulisce i messaggi di errore precedenti
      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const successMessage = form.querySelector(".success-message");
      if (successMessage) successMessage.remove();

      const name = form.name.value.trim();
      const surname = form.surname.value.trim();
      const email = form.email.value.trim();
      const phone = form.phone.value.trim();
      const message = form.message.value.trim();

      // Regex per validazione
      const nameRegex = /^[a-zA-ZàèéìòùÀÈÉÌÒÙ\s'-]{2,}$/; // Nome e cognome: minimo 2 caratteri, solo lettere, spazi, apostrofi e trattini
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/; // Email valida con dominio ed estensione
      const phoneRegex = /^\d{8,15}$/; // Solo cifre, lunghezza da 8 a 15

      let hasErrors = false;

      // Funzione per mostrare un messaggio di errore sotto un campo
      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      // Validazione specifica per ogni campo
      if (!name || !nameRegex.test(name)) {
        showError(
          form.name,
          "Il campo 'Nome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!surname || !nameRegex.test(surname)) {
        showError(
          form.surname,
          "Il campo 'Cognome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!email || !emailRegex.test(email)) {
        showError(form.email, "Inserisci un'email valida.");
      }

      if (phone && !phoneRegex.test(phone)) {
        showError(
          form.phone,
          "Il numero di telefono deve contenere solo cifre e avere una lunghezza compresa tra 8 e 15 caratteri."
        );
      }

      if (message.length > 500) {
        showError(
          form.message,
          "Il messaggio non può superare i 500 caratteri."
        );
      }

      if (hasErrors) {
        return;
      }

      // Se i dati sono validi, invia la richiesta
      try {
        const response = await fetch("/api/information-requests/send", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userName: name,
            userSurname: surname,
            userEmail: email,
            userPhone: phone,
            informationRequestText: message,
          }),
        });

        if (response.ok) {
          const successElement = document.createElement("div");
          successElement.className = "success-message";
          successElement.textContent = "Messaggio inviato con successo!";
          form.appendChild(successElement);
          form.reset(); // Resetta il form dopo l'invio
        } else {
          const errorElement = document.createElement("div");
          errorElement.className = "error-message";
          errorElement.textContent =
            "Errore durante l'invio del messaggio. Riprova più tardi.";
          form.appendChild(errorElement);
        }
      } catch (error) {
        console.error("Errore:", error);
        const errorElement = document.createElement("div");
        errorElement.className = "error-message";
        errorElement.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        form.appendChild(errorElement);
      }
    });
  }

  updateHeroHeight(); // Imposta la sezione hero all'altezza corretta
  discoverMoreScroll(); // Inizializza il comportamento di scrolling delle CTA
  openAccordion(); // Inizializza le funzionalità degli accordion
  setupNavbarLinks(); // Inizializza il comportamento dei link della navbar
  isLogged(); // Verifica se l'utente è loggato
  contact(); // Invia la mail di contatto
});
