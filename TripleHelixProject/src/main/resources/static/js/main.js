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

  function carouselScroll() {
    const track = document.getElementById("carouselTrack");
    const slides = Array.from(track.children);
    const indicators = document.querySelectorAll("#carouselIndicators button");

    // Configura la larghezza del carosello
    const slideWidth = slides[0].getBoundingClientRect().width;
    const gap = 16; // Spazio visibile tra immagini

    indicators.forEach((indicator, index) => {
      indicator.addEventListener("click", () => {
        // Aggiorna lo stato degli indicatori
        indicators.forEach((ind) => ind.classList.remove("active"));
        indicator.classList.add("active");

        // Calcola la posizione del carosello
        const translateX = index * (slideWidth + gap) - gap / 2;
        track.style.transform = `translateX(-${translateX}px)`;
      });
    });

    // Aggiungi evento di resize per garantire che il carosello sia responsivo
    window.addEventListener("resize", () => {
      const slideWidth = slides[0].getBoundingClientRect().width;
      const activeIndex = Array.from(indicators).findIndex((indicator) =>
        indicator.classList.contains("active")
      );
      const translateX = activeIndex * (slideWidth + gap) - gap / 2;
      track.style.transform = `translateX(-${translateX}px)`;
    });
  }

  // Esegui la funzione all'inizio per impostare l'altezza corretta
  updateHeroHeight();
  ctaScroll();
  openAccordion();
  carouselScroll();
});
