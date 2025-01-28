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
        console.error("Dati dell'utente non disponibili, impossibile recuperare le prenotazioni.");
        return;
      }
      const endpointPrenotazioni = `/api/bookings/user?email=${encodeURIComponent(userData.userEmail)}`;
      const response = await fetch(endpointPrenotazioni);
      if (!response.ok) {
        throw new Error(`Errore HTTP! stato: ${response.status}`);
      }
      const prenotazioni = await response.json();
      console.log("Prenotazioni utente:", prenotazioni);
      let nPrenotazioni = prenotazioni.length;
      // Mostra le prenotazioni
      showPrenotazioni(prenotazioni, nPrenotazioni);
    } catch (error) {
      console.error("Si è verificato un errore nel recupero delle prenotazioni:", error);
    }
  }

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

  // Funzione per creare un elemento HTML per una prenotazione
  function createPrenotazioneBox(prenotazione) {
    const div = document.createElement("div");
    div.classList.add("prenotazione-box");

    // Aggiungi dettagli prenotazione
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

    const dataAppuntamento = document.createElement("p");
    dataAppuntamento.textContent = `Data Appuntamento: ${prenotazione.appointmentDate}`;
    div.appendChild(dataAppuntamento);

    const giorno = document.createElement("p");
    giorno.textContent = `Giorno: ${giorniItaliano[prenotazione.day] || prenotazione.day}`;
    div.appendChild(giorno);

    const fasciaOraria = document.createElement("p");
    fasciaOraria.textContent = `Fascia Oraria: ${fasceOrarieItaliano[prenotazione.timeSlot] || prenotazione.timeSlot}`;
    div.appendChild(fasciaOraria);

    return div;
  }

  // Funzione per mostrare tutte le prenotazioni
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

    // Rimuove il contenuto precedente
    while (listaPrenotazione.firstChild) {
      listaPrenotazione.removeChild(listaPrenotazione.firstChild);
    }

    if (prenotazioni.length === 0) {
      const nessunaPrenotazione = document.createElement("p");
      nessunaPrenotazione.textContent = "Nessuna prenotazione trovata.";
      listaPrenotazione.appendChild(nessunaPrenotazione);
      return;
    }

    // Aggiunge ogni prenotazione al DOM
    prenotazioni.forEach((prenotazione) => {
      listaPrenotazione.appendChild(createPrenotazioneBox(prenotazione));
    });
  }

  // Richiama fetchPrenotazioni al caricamento della pagina
  fetchPrenotazioni();
});
