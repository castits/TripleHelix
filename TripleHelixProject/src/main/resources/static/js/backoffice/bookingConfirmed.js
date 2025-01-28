let endpointPrenotazioni = "/api/bookings/status?status=CONFIRMED";
let prenotazioni = [];

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
    showPrenotazioni(prenotazioni.length);
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

let listaPrenotazione = document.querySelector(".containerPrenotazione");

function createPrenotazioneBox(prenotazione) {
  let div = document.createElement("div");
  div.classList.add("prenotazione-box", "confermate");

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
  nome.textContent = `Referente: ${prenotazione.userName} ${prenotazione.userSurname}`;
  div.appendChild(nome);

  let email = document.createElement("p");
  email.textContent = `Email: ${prenotazione.userEmail}`;
  div.appendChild(email);

  let istituto = document.createElement("p");
  istituto.textContent = `Istituto: ${prenotazione.institute}`;
  div.appendChild(istituto);

  let partecipanti = document.createElement("p");
  partecipanti.textContent = `Partecipanti: ${prenotazione.participantQuantity}`;
  div.appendChild(partecipanti);

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
      showPrenotazioni(prenotazioni.length);
    } else {
      console.error("Failed to delete booking");
    }
  } catch (error) {
    console.error("Errore:", error);
  }
}

function showPrenotazioni(num) {
  let h2NumConfermate = document.getElementById("confermate");
  h2NumConfermate.textContent = `Prenotazioni confermate: ${num}`;

  while (listaPrenotazione.firstChild) {
    listaPrenotazione.removeChild(listaPrenotazione.firstChild);
  }

  prenotazioni.forEach((prenotazione) => {
    listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
  });
}
