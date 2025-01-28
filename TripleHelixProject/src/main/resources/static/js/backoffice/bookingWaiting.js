let endpointPrenotazioni = "/api/bookings/status?status=PENDING";
let prenotazioni = [];
let x = 0;

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
    x = prenotazioni.length;
    showPrenotazioni(x);
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

let listaPrenotazione = document.querySelector(".containerPrenotazione");

function createPrenotazioneBox(prenotazione) {
  let div = document.createElement("div");
  div.classList.add("prenotazione-box");

  let dataAppuntamento = document.createElement("p");
  dataAppuntamento.appendChild(
    document.createTextNode(
      `${prenotazione.appointmentDate}  \|  ${
        giorniItaliano[prenotazione.day] || prenotazione.day
      }  \|  ${
        fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot
      }`
    )
  );
  div.appendChild(dataAppuntamento);

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

  let buttonContainer = document.createElement("div");
  buttonContainer.classList.add("button-container");

  let accettaButton = document.createElement("button");
  accettaButton.textContent = "Accetta";
  accettaButton.classList.add("accetta-button");
  accettaButton.addEventListener("click", (event) => {
    event.preventDefault();
    handleAccetta(prenotazione);
  });

  let rifiutaButton = document.createElement("button");
  rifiutaButton.textContent = "Rifiuta";
  rifiutaButton.classList.add("rifiuta-button");
  rifiutaButton.addEventListener("click", (event) => {
    event.preventDefault();
    handleRifiuta(prenotazione);
  });

  buttonContainer.appendChild(accettaButton);
  buttonContainer.appendChild(rifiutaButton);

  div.appendChild(buttonContainer);

  return div;
}

async function handleAccetta(prenotazione) {
  await fetch(
    `/api/bookings/change-status/${prenotazione.bookingId}?status=CONFIRMED`,
    {
      method: "PUT",
    }
  );
  updatePrenotazioni();
}

async function handleRifiuta(prenotazione) {
  await fetch(
    `/api/bookings/change-status/${prenotazione.bookingId}?status=REFUSED`,
    {
      method: "PUT",
    }
  );
  updatePrenotazioni();
}

function updatePrenotazioni() {
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
      x = prenotazioni.length;
      showPrenotazioni(x);
    })
    .catch((error) => {
      console.log("Errore:", error);
    });
}

function showPrenotazioni(num) {
  let h2NumAttesa = document.getElementById("inAttesa");
  h2NumAttesa.textContent = `Bentornato, hai ${num} prenotazione/i in attesa`;

  while (listaPrenotazione.firstChild) {
    listaPrenotazione.removeChild(listaPrenotazione.firstChild);
  }

  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
