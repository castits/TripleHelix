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

// Seleziona il contenitore per la lista di prenotazioni
let listaPrenotazione = document.querySelector(".containerPrenotazione");

function createPrenotazioneBox(prenotazione) {
  // Crea un div per ogni prenotazione
  let div = document.createElement("div");
  div.classList.add("prenotazione-box");
  div.classList.add("rifiutate");

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

  // Crea il bottone "Cancella"
  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-container");

  let cancellaButton = document.createElement("button");
  cancellaButton.textContent = "Cancella";
  cancellaButton.type = "button";
  cancellaButton.classList.add("cancella-button");
  cancellaButton.addEventListener("click", () => handleRifiuta(prenotazione));

  buttonContainer.appendChild(cancellaButton);
  div.appendChild(buttonContainer);

  return div;
}

async function handleRifiuta(prenotazione) {
  try {
    const response = await fetch(
      `/api/bookings/delete/${prenotazione.bookingId}`,
      {
        method: "DELETE",
      }
    );
    if (response.ok) {
      prenotazioni = prenotazioni.filter(
        (p) => p.bookingId !== prenotazione.bookingId
      );
      showPrenotazioni(prenotazioni.length); // Aggiorna il numero delle prenotazioni
    } else {
      console.error("Failed to delete booking");
    }
  } catch (error) {
    console.error("Errore:", error);
  }
}

function showPrenotazioni(num) {
  let h2NumRifiutate = document.getElementById("rifiutate");

  // Rimuove il contatore precedente (se esiste)
  let existingSpan = h2NumRifiutate.querySelector("span");
  if (existingSpan) {
    existingSpan.remove();
  }

  let numRifiutate = document.createElement("span"); // Crea l'elemento <span>
  numRifiutate.textContent = " " + num;

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
