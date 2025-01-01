window.addEventListener("DOMContentLoaded", () => {
  // Funzione per aggiornare l'altezza dell'elemento #hero
  function updateHeroHeight() {
    const header = document.querySelector("header");
    const hero = document.getElementById("hero");
    const heroBg = document.querySelector("#hero-bg");
    const heroDarkness = document.querySelector("#hero-bg-darkness");
    const heroContent = document.querySelector("#hero-content");

    // Sottrai l'altezza dell'header dall'altezza totale della finestra
    const heroHeight = window.innerHeight - header.offsetHeight;

    // Imposta l'altezza di #hero
    hero.style.height = `${heroHeight}px`;
    heroBg.style.height = `${heroHeight}px`;
    heroDarkness.style.height = `${heroHeight}px`;
    heroContent.style.height = `${(heroHeight / 100) * 90}px`;
  }

  function openAccordion() {
    const faqItems = document.querySelectorAll(".faq-container");

    faqItems.forEach((faq) => {
      faq.addEventListener("click", () => {
        // Aggiungi/rimuovi la classe active
        faq.classList.toggle("active");
        // Chiudi gli altri accordion (opzionale)
        faqItems.forEach((item) => {
          if (item !== faq && item.classList.contains("active")) {
            item.classList.remove("active");
          }
        });
      });
    });
  }

  function ctaScroll() {
    const ctas = document.querySelectorAll(".discover-more");
    const heroHeight = document.getElementById("hero").offsetHeight;

    ctas.forEach((cta) => {
      cta.addEventListener("click", () => {
        window.scrollTo({ top: heroHeight, behavior: "smooth" });
      });
    });
  }

  const dots = document.querySelectorAll(".dot");
  const items = document.querySelectorAll(".carosello-item");
  const container = document.querySelector(".carosello-container");

  let currentIndex = 0;

  // Funzione per aggiornare i pallini
  function updateDots() {
    dots.forEach((dot) => dot.classList.remove("active")); // Rimuove l'attivo da tutti i pallini
    dots[currentIndex].classList.add("active"); // Aggiunge l'attivo al pallino corrente
  }

  // Funzione per sincronizzare l'indice corrente con lo scroll
  function updateCurrentIndex() {
    const scrollPosition = container.scrollLeft;
    const itemWidth = items[0].offsetWidth; // Larghezza di un singolo item
    currentIndex = Math.round(scrollPosition / itemWidth);
    updateDots();
  }

  // Aggiungere l'evento di scroll per sincronizzare i pallini
  container.addEventListener("scroll", updateCurrentIndex);

  // Gestire il clic sui pallini
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      container.scrollTo({
        left: items[index].offsetLeft, // Scorrere fino alla posizione dell'immagine
        behavior: "smooth", // Abilita lo scroll morbido
      });
    });
  });

  // Inizializzare lo stato dei pallini
  updateCurrentIndex();

  // Esegui la funzione all'inizio per impostare l'altezza corretta
  updateHeroHeight();
  ctaScroll();
  openAccordion();

  /* ----------------- gestione progress-bar ----------------*/

  // Aggiunge un listener per l'evento "scroll" alla finestra
  // Quando l'utente scorre la pagina, viene eseguita la funzione handleScroll
  window.addEventListener("scroll", handleScroll);

  function handleScroll() {
    // Ottiene la posizione verticale dello scroll nella pagina
    // 'document.body.scrollTop' per alcuni browser
    // 'document.documentElement.scrollTop' per altri browser
    let winScroll =
      document.body.scrollTop || document.documentElement.scrollTop;

    // Calcola l'altezza totale della pagina disponibile per lo scroll
    // Sottrae l'altezza visibile della finestra dall'altezza totale del documento
    let height =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;

    // Calcola la percentuale di scroll in base alla posizione corrente e all'altezza totale
    let scrolled = (winScroll / height) * 100;

    // Modifica la larghezza della barra di progresso in base alla percentuale di scroll
    // Il valore "scrolled + '%'" viene applicato alla proprietà "width" di un elemento con ID "progressBar"
    document.getElementById("progressBar").style.width = scrolled + "%";
  }

  /*----- Apertura e chiusura hamburger ------------*/

  // Aggiunge un listener per l'evento "click" sull'elemento con ID "hamburger".
  // Quando l'elemento viene cliccato, esegue la funzione `handleClick`.
  document.getElementById("hamburger").addEventListener("click", handleClick);

  /**
   * Funzione `handleClick`:
   * Gestisce l'apertura e la chiusura del menu hamburger.
   * Cambia l'immagine e il testo alternativo (alt) in base allo stato del checkbox.
   */
  function handleClick() {
    // Ottiene l'elemento dell'immagine hamburger
    let hamburger = document.getElementById("hamburger");

    // Ottiene il checkbox associato con ID "check"
    let hamburgerCheck = document.getElementById("check");

    // Controlla se il checkbox non è selezionato
    if (!hamburgerCheck.checked) {
      // Modifica l'immagine e il testo alternativo per indicare che il menu è aperto
      hamburger.src = "./assets/svg/closure.svg";
      hamburger.alt = "Chiusura menu di navigazione";
    } else {
      // Modifica l'immagine e il testo alternativo per indicare che il menu è chiuso
      hamburger.src = "./assets/svg/coloredHamburger.svg";
      hamburger.alt = "Menu di navigazione";
    }
  }
});
