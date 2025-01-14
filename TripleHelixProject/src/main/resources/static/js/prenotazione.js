window.addEventListener("DOMContentLoaded", () => {
  function book() {
    const form = document.querySelector("form");

    // Aggiungi un container per i messaggi di errore o successo
    const messageContainer = document.createElement("div");
    messageContainer.id = "message-container";
    messageContainer.style.marginTop = "20px";
    form.appendChild(messageContainer);

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      // Pulisce i messaggi precedenti
      messageContainer.innerHTML = "";

      // Validazione dei campi
      const errors = [];
      const name = form.name.value.trim();
      const surname = form.surname.value.trim();
      const email = form.email.value.trim();
      const institute = form.ente.value.trim();
      const participantQuantity = form.partecipanti.value.trim();
      const appointmentDate = form.data.value.trim();
      const timeSlot = form.durata.value.trim();
      const activity = form.attivita.value.trim();
      const bookingInfoReq = form.messaggio.value.trim();

      // Regex per validare l'email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      // Validazione specifica per ogni campo
      if (!name) errors.push("Il campo 'Nome' è obbligatorio.");
      if (!surname) errors.push("Il campo 'Cognome' è obbligatorio.");
      if (!email || !emailRegex.test(email)) {
        errors.push("Inserisci un'email valida.");
      }
      if (!institute) errors.push("Il campo 'Ente' è obbligatorio.");
      if (
        !participantQuantity ||
        isNaN(participantQuantity) ||
        participantQuantity <= 0
      ) {
        errors.push("Inserisci un numero di partecipanti valido.");
      }
      if (!appointmentDate) errors.push("Il campo 'Data' è obbligatorio.");
      if (!timeSlot) errors.push("Seleziona una durata valida.");
      if (!activity) errors.push("Seleziona un'attività valida.");
      if (bookingInfoReq.length > 500) {
        errors.push("Il messaggio non può superare i 500 caratteri.");
      }

      // Mostra errori se presenti
      if (errors.length > 0) {
        messageContainer.style.color = "red";
        messageContainer.innerHTML = errors.join("<br>");
        return;
      }

      // Se i dati sono validi, invia la richiesta
      try {
        const response = await fetch("api/bookings/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            name,
            surname,
            email,
            institute,
            participantQuantity,
            appointmentDate,
            timeSlot,
            activity,
            bookingInfoReq,
          }),
        });

        if (response.ok) {
          messageContainer.style.color = "green";
          messageContainer.innerHTML = "Prenotazione effettuata con successo!";
          setTimeout(() => {
            location.href = "./index.html";
          }, 2000);
        } else {
          const errorData = await response.json();
          messageContainer.style.color = "red";
          messageContainer.innerHTML =
            errorData.message || "Errore durante la prenotazione.";
        }
      } catch (error) {
        console.error("Errore durante la prenotazione:", error);
        messageContainer.style.color = "red";
        messageContainer.innerHTML =
          "Si è verificato un errore. Riprova più tardi.";
      }
    });
  }

  book();
});
