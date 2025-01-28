window.addEventListener("DOMContentLoaded", () => {
  async function fetchLoggedUser() {
    let endpointInfoUtente = "/pub/auth/user-info";

    try {
      const response = await fetch(endpointInfoUtente);
      if (!response.ok) {
        throw new Error(`Errore HTTP! stato: ${response.status}`);
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Si è verificato un errore:", error);
    }
  }

  async function fetchPrenotazioni() {
    try {
      const userData = await fetchLoggedUser();
      if (!userData) {
        console.error(
          "Dati dell'utente non disponibili, impossibile recuperare le prenotazioni."
        );
        return;
      }
      const endpointPrenotazioni = `/api/bookings/user?email=${encodeURIComponent(
        userData.userEmail
      )}&status=CONFIRMED`;
      const response = await fetch(endpointPrenotazioni);
      if (!response.ok) {
        throw new Error(`Errore HTTP! stato: ${response.status}`);
      }
      const prenotazioni = await response.json();
      console.log("Prenotazioni confermate dell'utente:", prenotazioni);
      let nPrenotazioni = prenotazioni.length;
      showPrenotazioni(prenotazioni, nPrenotazioni);
    } catch (error) {
      console.error(
        "Si è verificato un errore nel recupero delle prenotazioni:",
        error
      );
    }
  }

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

  function createPrenotazioneBox(prenotazione) {
    const div = document.createElement("div");
    div.classList.add("prenotazione-box");

    const dataAppuntamento = document.createElement("p");
    dataAppuntamento.appendChild(
      document.createTextNode(
        `${prenotazione.appointmentDate}  |  ${
          giorniItaliano[prenotazione.day] || prenotazione.day
        }  |  ${
          fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot
        }`
      )
    );
    div.appendChild(dataAppuntamento);

    const nome = document.createElement("p");
    nome.textContent = `Referente: ${prenotazione.userName} ${prenotazione.userSurname}`;
    div.appendChild(nome);

    const email = document.createElement("p");
    email.textContent = `Email: ${prenotazione.userEmail}`;
    div.appendChild(email);

    const istituto = document.createElement("p");
    istituto.textContent = `Istituto: ${prenotazione.institute}`;
    div.appendChild(istituto);

    const partecipanti = document.createElement("p");
    partecipanti.textContent = `Partecipanti: ${prenotazione.participantQuantity}`;
    div.appendChild(partecipanti);

    return div;
  }

  function showPrenotazioni(prenotazioni, num) {
    const listaPrenotazione = document.querySelector(".containerPrenotazione");
    if (!listaPrenotazione) {
      console.error("Contenitore prenotazioni non trovato.");
      return;
    }
    let h2NumPrenotazione = document.getElementById("confermate");
    let numPrenotazione = document.createElement("span"); // Crea l'elemento <span>
    numPrenotazione.textContent = " " + num;

    h2NumPrenotazione.appendChild(numPrenotazione);

    while (listaPrenotazione.firstChild) {
      listaPrenotazione.removeChild(listaPrenotazione.firstChild);
    }

    if (prenotazioni.length === 0) {
      const nessunaPrenotazione = document.createElement("p");
      nessunaPrenotazione.textContent = "Nessuna prenotazione confermata.";
      listaPrenotazione.appendChild(nessunaPrenotazione);
      return;
    }

    prenotazioni.forEach((prenotazione) => {
      listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
    });
  }

  fetchPrenotazioni();
});
