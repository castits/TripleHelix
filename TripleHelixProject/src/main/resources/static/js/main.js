window.addEventListener("DOMContentLoaded", () => {
  // Funzione per aggiornare l'altezza dell'elemento #hero
  function updateHeroHeight() {
    const header = document.querySelector("header");
    const hero = document.getElementById("hero");
    const heroBg = document.querySelector("#hero-bg");
    const heroDarkness = window.getComputedStyle(heroBg, "::after");
    const heroContent = document.querySelector("#hero-content");

    // Sottrai l'altezza dell'header dall'altezza totale della finestra
    const heroHeight = window.offsetHeight - header.offsetHeight;

    // Imposta l'altezza di #hero
    hero.style.height = `${heroHeight}px`;
    heroBg.style.height = `${heroHeight}px`;
    heroDarkness.style.height = `${heroHeight}px`;
    heroContent.style.height = `${(heroHeight / 100) * 90}px`;
  }

  // Esegui la funzione all'inizio per impostare l'altezza corretta
  updateHeroHeight();

  window.addEventListener("resize", updateHeroHeight);
});
