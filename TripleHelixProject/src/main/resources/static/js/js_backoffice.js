// let endpointPrenotazioni = "https://jsonblob.com/api/jsonBlob/1327009964564340736";
// let prenotazione;

// fetch(endpointPrenotazioni)
//   .then((response) => {
//     if (response.ok) {
//       return response.json();
//     } else {
//       throw new Error("Network response was not ok.");
//     }
//   })
//   .then((prenotazioneJSON) => {
//     console.log(prenotazioneJSON);
//     prenotazione = prenotazioneJSON;
//     showPrenotazione();
//   })
//   .catch((error) => {
//     console.log("errorrino", error);
//   });
// //da undefined

// let listaPrenotazione = document.getElementsByClassName("containerPrenotazione");

// function createListItem(content) {
//   let li = document.createElement("li");
//   li.textContent = content;
//   return li;
// }

// function showPrenotazione() {
//   listaPrenotazione.appendChild(createListItem(`Name: ${prenotazione.nome}`));
// }
let endpointPrenotazioni = "https://jsonblob.com/api/jsonBlob/1327189519845482496";
let prenotazioni = [];

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
    showPrenotazioni(); // Mostra tutte le prenotazioni
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

// Seleziona il contenitore per la lista di prenotazioni
let listaPrenotazione = document.querySelector(".containerPrenotazione");

function createPrenotazioneBox(prenotazione) {
  // Crea un div per ogni prenotazione
  let div = document.createElement("div");
  div.classList.add("prenotazione-box");

  // Crea e aggiungi gli elementi
  let nome = document.createElement("p");
  nome.appendChild(document.createTextNode(`Nome: ${prenotazione.userName} ${prenotazione.userSurname}`));
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

  let dataAppuntamento = document.createElement("p");
  dataAppuntamento.appendChild(document.createTextNode(`Data Appuntamento: ${prenotazione.appointmentDate}`));
  div.appendChild(dataAppuntamento);

  let giorno = document.createElement("p");
  giorno.appendChild(document.createTextNode(`Giorno: ${giorniItaliano[prenotazione.day] || prenotazione.day}`));
  div.appendChild(giorno);

  let fasciaOraria = document.createElement("p");
  fasciaOraria.appendChild(document.createTextNode(`Fascia Oraria: ${fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot}`));
  div.appendChild(fasciaOraria);

  // Crea i bottoni "Accetta" e "Rifiuta"
  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-container");

  let accettaButton = document.createElement("button");
  accettaButton.textContent = "Accetta";
  accettaButton.classList.add("accetta-button");
  accettaButton.addEventListener("click", () => handleAccetta(prenotazione));

  let rifiutaButton = document.createElement("button");
  rifiutaButton.textContent = "Rifiuta";
  rifiutaButton.classList.add("rifiuta-button");
  rifiutaButton.addEventListener("click", () => handleRifiuta(prenotazione));

  // Aggiungi i bottoni al container
  buttonContainer.appendChild(accettaButton);
  buttonContainer.appendChild(rifiutaButton);

  // Aggiungi il container dei bottoni al div della prenotazione
  div.appendChild(buttonContainer);

  return div;
}

// Funzioni per gestire il click su "Accetta" e "Rifiuta"
function handleAccetta(prenotazione) {
  alert(`Prenotazione di ${prenotazione.userName} accettata!`);
  // Puoi aggiungere altre azioni come l'invio di una richiesta al server
}

function handleRifiuta(prenotazione) {
  alert(`Prenotazione di ${prenotazione.userName} rifiutata!`);
  // Puoi aggiungere altre azioni come l'invio di una richiesta al server
}

function showPrenotazioni() {
  // Pulisce il contenitore prima di aggiungere gli elementi
  listaPrenotazione.innerHTML = "";

  // Itera su tutte le prenotazioni e le aggiunge al DOM
  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
