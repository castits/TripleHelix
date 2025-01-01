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
});
