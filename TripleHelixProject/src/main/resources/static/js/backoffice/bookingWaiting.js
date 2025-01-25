let endpointPrenotazioni =
  "http://localhost:8080/api/bookings/status?status=PENDING";
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

  // Crea i bottoni "Accetta" e "Rifiuta"
  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-container");

  // Crea l'elemento form
  let btnForm = document.createElement("form");

  //CONFUSIONE SULLE CHIAMATE REJECTED E ACCEPTED

  // Imposta gli attributi del form
  btnForm.classList.add("hiddenForm");
  btnForm.action = "/action_page.php"; // URL di destinazione !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  btnForm.method = "get"; // Metodo HTTP
  btnForm.id = "accept_reject"; // ID del form

  // Aggiungi il form al DOM (ad esempio, al corpo del documento)
  buttonContainer.appendChild(btnForm);

  let accettaButton = document.createElement("button");
  accettaButton.textContent = "Accetta";
  accettaButton.value = "Accetta";
  accettaButton.type = "submit";
  accettaButton.classList.add("accetta-button");
  accettaButton.setAttribute("form", "accept_reject"); // Associa al form con ID "accept_reject"
  accettaButton.addEventListener("click", () => handleAccetta(prenotazione));

  let rifiutaButton = document.createElement("button");
  rifiutaButton.textContent = "Rifiuta";
  rifiutaButton.value = "Rifiuta";
  rifiutaButton.type = "submit";
  rifiutaButton.classList.add("rifiuta-button");
  rifiutaButton.setAttribute("form", "accept_reject"); // Associa al form con ID "accept_reject"
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

function showPrenotazioni(num) {
  let h2NumAttesa = document.getElementById("inAttesa");
  let numInAttesa = document.createElement("span"); // Crea l'elemento <span>
  numInAttesa.textContent = num;

  h2NumAttesa.appendChild(numInAttesa);
  // Rimuove tutti i figli del contenitore in modo sicuro
  while (listaPrenotazione.firstChild) {
    listaPrenotazione.removeChild(listaPrenotazione.firstChild);
  }

  // Itera su tutte le prenotazioni e le aggiunge al DOM
  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
