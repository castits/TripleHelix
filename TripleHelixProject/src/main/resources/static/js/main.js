/* ----------------- gestione progress-bar ----------------*/

// Aggiunge un listener per l'evento "scroll" alla finestra
// Quando l'utente scorre la pagina, viene eseguita la funzione handleScroll
window.addEventListener("scroll", handleScroll);

function handleScroll() {
  // Ottiene la posizione verticale dello scroll nella pagina
  // 'document.body.scrollTop' per alcuni browser
  // 'document.documentElement.scrollTop' per altri browser
  let winScroll = document.body.scrollTop || document.documentElement.scrollTop;

  // Calcola l'altezza totale della pagina disponibile per lo scroll
  // Sottrae l'altezza visibile della finestra dall'altezza totale del documento
  let height = document.documentElement.scrollHeight - document.documentElement.clientHeight;

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
    hamburger.src = "./assets/img/closure.svg";
    hamburger.alt = "Chiusura menu di navigazione";
  } else {
    // Modifica l'immagine e il testo alternativo per indicare che il menu è chiuso
    hamburger.src = "./assets/img/coloredHamburger.svg";
    hamburger.alt = "Menu di navigazione";
  }
}
