let endpointPrenotazioni =
  "http://localhost:8080/api/bookings/status&status=CONFIRMED";
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
  div.classList.add("confermate");
  // Crea e aggiungi gli elementi
  let nome = document.createElement("p");
  nome.appendChild(
    document.createTextNode(
      `Referente: ${prenotazione.userName} ${prenotazione.userSurname}`
    )
  );
  div.appendChild(nome);

  let email = document.createElement("p");
  email.appendChild(
    document.createTextNode(`Email: ${prenotazione.userEmail}`)
  );
  div.appendChild(email);

  let istituto = document.createElement("p");
  istituto.appendChild(
    document.createTextNode(`Istituto: ${prenotazione.institute}`)
  );
  div.appendChild(istituto);

  let partecipanti = document.createElement("p");
  partecipanti.appendChild(
    document.createTextNode(`Partecipanti: ${prenotazione.participantQuantity}`)
  );
  div.appendChild(partecipanti);

  let dataAppuntamento = document.createElement("p");
  dataAppuntamento.appendChild(
    document.createTextNode(
      `Data Appuntamento: ${prenotazione.appointmentDate}`
    )
  );
  div.appendChild(dataAppuntamento);

  let giorno = document.createElement("p");
  giorno.appendChild(
    document.createTextNode(
      `Giorno: ${giorniItaliano[prenotazione.day] || prenotazione.day}`
    )
  );
  div.appendChild(giorno);

  let fasciaOraria = document.createElement("p");
  fasciaOraria.appendChild(
    document.createTextNode(
      `Fascia Oraria: ${
        fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot
      }`
    )
  );
  div.appendChild(fasciaOraria);

  // Crea bottone "cancella"
  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-container");

  // Crea l'elemento form
  let btnForm = document.createElement("form");

  // Imposta gli attributi del form
  btnForm.classList.add("hiddenForm");
  btnForm.action = `http://localhost/api/bookings/delete/${prenotazione.bookingsId}`;
  btnForm.method = "get"; // Metodo HTTP
  btnForm.id = "reject"; // ID del form

  // Aggiungi il form al DOM (ad esempio, al corpo del documento)
  buttonContainer.appendChild(btnForm);

  let cancellaButton = document.createElement("button");
  cancellaButton.textContent = "Cancella";
  cancellaButton.value = "Cancella";
  cancellaButton.type = "submit";
  cancellaButton.classList.add("cancella-button");
  cancellaButton.setAttribute("form", "reject"); // Associa al form con ID "reject"
  cancellaButton.addEventListener("click", () => handleRifiuta(prenotazione));

  // Aggiungi il bottone al container

  buttonContainer.appendChild(cancellaButton);

  // Aggiungi il container del bottone al div della prenotazione
  div.appendChild(buttonContainer);

  return div;
}

function handleRifiuta(prenotazione) {
  alert(`Prenotazione di ${prenotazione.userName} cancellata!`);
  // Puoi aggiungere altre azioni come l'invio di una richiesta al server
}

function showPrenotazioni(num) {
  let h2NumConfermate = document.getElementById("confermate");
  let numConfermate = document.createElement("span"); // Crea l'elemento <span>
  numConfermate.textContent = num;

  h2NumConfermate.appendChild(numConfermate);
  // Rimuove tutti i figli del contenitore in modo sicuro
  while (listaPrenotazione.firstChild) {
    listaPrenotazione.removeChild(listaPrenotazione.firstChild);
  }

  // Itera su tutte le prenotazioni e le aggiunge al DOM
  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
