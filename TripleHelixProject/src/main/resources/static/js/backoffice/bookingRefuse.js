let endpointPrenotazioni = "/api/bookings/status?status=REFUSED";
let prenotazioni = [];
let x = 0;

// Mappatura dei giorni e delle fasce orarie in italiano
const giorniItaliano = {
  MONDAY: "Lunedì",
  TUESDAY: "Martedì",
  WEDNESDAY: "Mercoledì",
  THURSDAY: "Giovedì",
  FRIDAY: "Venerdì",
  SATURDAY: "Sabato",
  SUNDAY: "Domenica",
};

const fasceOrarieItaliano = {
  MORNING: "Mattina",
  AFTERNOON: "Pomeriggio",
  FULL_DAY: "Tutto il giorno",
};

// Fetch per ottenere il JSON
fetch(endpointPrenotazioni)
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error("Network response was not ok.");
    }
  })
  .then((prenotazioneJSON) => {
    prenotazioni = prenotazioneJSON;
    let x = prenotazioni.length;
    showPrenotazioni(x); // Mostra tutte le prenotazioni
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

// // Seleziona il contenitore per la lista di prenotazioni
let listaPrenotazione = document.querySelector(".containerPrenotazione");

function createPrenotazioneBox(prenotazione) {
  // Crea un div per ogni prenotazione
  let div = document.createElement("div");
  div.classList.add("prenotazione-box");
  div.classList.add("rifiutate");
  let dataAppuntamento = document.createElement("p");
  dataAppuntamento.appendChild(document.createTextNode(`${prenotazione.appointmentDate}  \|  ${giorniItaliano[prenotazione.day] || prenotazione.day}  \|  ${fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot}`));
  div.appendChild(dataAppuntamento);

  // Crea e aggiungi gli elementi
  let nome = document.createElement("p");
  nome.appendChild(document.createTextNode(`Referente: ${prenotazione.userName} ${prenotazione.userSurname}`));
  div.appendChild(nome);

  let email = document.createElement("p");
  email.appendChild(document.createTextNode(`Email: ${prenotazione.userEmail}`));
  div.appendChild(email);

  let istituto = document.createElement("p");
  istituto.appendChild(document.createTextNode(`Istituto: ${prenotazione.institute}`));
  div.appendChild(istituto);

  let partecipanti = document.createElement("p");
  partecipanti.appendChild(document.createTextNode(`Partecipanti: ${prenotazione.participantQuantity}`));
  div.appendChild(partecipanti);

  return div;
}

function showPrenotazioni(num) {
  let h2NumRifiutate = document.getElementById("rifiutate");
  let numRifiutate = document.createElement("span"); // Crea l'elemento <span>
  numRifiutate.textContent = num;

  h2NumRifiutate.appendChild(numRifiutate);
  // Rimuove tutti i figli del contenitore in modo sicuro
  while (listaPrenotazione.firstChild) {
    listaPrenotazione.removeChild(listaPrenotazione.firstChild);
  }

  // Itera su tutte le prenotazioni e le aggiunge al DOM
  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
