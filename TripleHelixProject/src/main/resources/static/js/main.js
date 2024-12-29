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

  // Esegui la funzione all'inizio per impostare l'altezza corretta
  updateHeroHeight();
  openAccordion();
});
