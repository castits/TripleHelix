window.addEventListener("DOMContentLoaded", () => {
  function book() {
    const form = document.querySelector("form");

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const successMessage = form.querySelector(".success-message");
      if (successMessage) successMessage.remove();

      const name = form.name.value.trim();
      const surname = form.surname.value.trim();
      const email = form.email.value.trim();
      const phone = form["phone-number"].value.trim();
      const institute = form.ente.value.trim();
      const participantQuantity = form.partecipanti.value.trim();
      const appointmentDate = form.data.value.trim();
      const timeSlot = form.durata.value.trim();
      const activity = form.attivita.value.trim();
      const bookingInfoReq = form.messaggio.value.trim();

      const nameSurnameRegex = /^[a-zA-ZàèéìòùÀÈÉÌÒÙ\s'-]{2,}$/;
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
      const phoneRegex = /^\d{8,15}$/;
      const participantRegex = /^[1-9][0-9]*$/;

      let hasErrors = false;

      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      if (!name || !nameSurnameRegex.test(name)) {
        showError(
          form.name,
          "Il campo 'Nome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!surname || !nameSurnameRegex.test(surname)) {
        showError(
          form.surname,
          "Il campo 'Cognome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!email || !emailRegex.test(email)) {
        showError(form.email, "Inserisci un'email valida.");
      }

      if (!phone || !phoneRegex.test(phone)) {
        showError(
          form["phone-number"],
          "Il numero di telefono deve contenere solo cifre (tra 8 e 15 caratteri)."
        );
      }

      if (!institute) {
        showError(form.ente, "Il campo 'Ente' è obbligatorio.");
      }

      if (!participantQuantity || !participantRegex.test(participantQuantity)) {
        showError(
          form.partecipanti,
          "Inserisci un numero di partecipanti valido (intero positivo)."
        );
      }

      if (!appointmentDate) {
        showError(form.data, "Il campo 'Data' è obbligatorio.");
      } else {
        const today = new Date();
        const selectedDate = new Date(appointmentDate);

        today.setHours(0, 0, 0, 0);
        selectedDate.setHours(0, 0, 0, 0);

        if (isNaN(selectedDate.getTime())) {
          showError(form.data, "Inserisci una data valida.");
        } else if (selectedDate < today) {
          showError(form.data, "La data non può essere precedente a oggi.");
        }
      }

      if (!timeSlot) {
        showError(form.durata, "Seleziona una durata valida.");
      }

      if (!activity) {
        showError(form.attivita, "Seleziona un'attività valida.");
      }

      if (bookingInfoReq.length > 500) {
        showError(
          form.messaggio,
          "Il messaggio non può superare i 500 caratteri."
        );
      }

      if (hasErrors) return;

      try {
        const response = await fetch("/api/bookings/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            name,
            surname,
            email,
            phone,
            institute,
            participantQuantity,
            appointmentDate,
            timeSlot,
            activity,
            bookingInfoReq,
          }),
        });

        if (response.ok) {
          const successElement = document.createElement("div");
          successElement.className = "success-message";
          successElement.textContent = "Prenotazione effettuata con successo!";
          form.appendChild(successElement);
          setTimeout(() => {
            location.href = "./usersDashboard.html";
          }, 1000);
        } else {
          const errorElement = document.createElement("div");
          errorElement.className = "error-message";
          errorElement.textContent =
            "Errore durante l'invio della prenotazione. Riprova più tardi.";
          form.appendChild(errorElement);
        }
      } catch (error) {
        const errorElement = document.createElement("div");
        errorElement.className = "error-message";
        errorElement.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        form.appendChild(errorElement);
      }
    });
  }

  book();
});
