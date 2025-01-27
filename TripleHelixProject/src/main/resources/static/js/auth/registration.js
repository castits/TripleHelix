window.addEventListener("DOMContentLoaded", () => {
  function validateRegistrationForm() {
    const form = document.getElementById("registration-form");

    if (!form) {
      console.error("Il form di registrazione non è stato trovato nel DOM.");
      return;
    }

    form.addEventListener("submit", async (event) => {
      event.preventDefault(); // Impedisce il comportamento di default del form

      // Pulisce i messaggi di errore precedenti
      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const successMessage = form.querySelector(".success-message");
      if (successMessage) successMessage.remove();

      // Ottieni i valori inseriti nei campi del form
      const userName = form.nome.value.trim();
      const userSurname = form.cognome.value.trim();
      const userEmail = form.email.value.trim();
      const userPassword = form.password.value;
      const repeatPassword = form["conferma-password"].value;
      const gdprChecked = form.gdpr.checked;

      let hasErrors = false;

      // Funzione per mostrare un messaggio di errore sotto un campo
      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      // Regex per validazione email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      // Validazione dei campi
      if (!userName) {
        showError(form.nome, "Il campo 'Nome' è obbligatorio.");
      }

      if (!userSurname) {
        showError(form.cognome, "Il campo 'Cognome' è obbligatorio.");
      }

      if (!userEmail) {
        showError(form.email, "Il campo 'Email' è obbligatorio.");
      } else if (!emailRegex.test(userEmail)) {
        showError(form.email, "Inserisci un'email valida.");
      }

      if (!userPassword) {
        showError(form.password, "Il campo 'Crea password' è obbligatorio.");
      } else if (userPassword.length < 8) {
        showError(
          form.password,
          "La password deve contenere almeno 8 caratteri."
        );
      }

      if (userPassword !== repeatPassword) {
        showError(form["conferma-password"], "Le password non corrispondono.");
      }

      if (!gdprChecked) {
        showError(
          form.gdpr,
          "Devi accettare il trattamento dei dati personali per procedere."
        );
      }

      // Se ci sono errori, interrompe l'esecuzione
      if (hasErrors) return;

      // Se la validazione passa, invia i dati al server
      try {
        const response = await fetch("/pub/auth/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userName,
            userSurname,
            userEmail,
            userPassword,
          }),
        });

        if (response.ok && response.status === 201) {
          const successElement = document.createElement("div");
          successElement.className = "success-message";
          successElement.textContent = "Registrazione avvenuta con successo!";
          form.appendChild(successElement);

          setTimeout(() => {
            window.location.href = "/login.html";
          }, 1000);
        } else {
          const errorData = await response.json();
          const errorElement = document.createElement("div");
          errorElement.className = "error-message";
          errorElement.textContent =
            errorData.message || "Errore durante la registrazione.";
          form.appendChild(errorElement);
        }
      } catch (error) {
        console.error("Errore durante la richiesta di registrazione:", error);
        const errorElement = document.createElement("div");
        errorElement.className = "error-message";
        errorElement.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        errorElement.style.marginTop = "20px";
        form.appendChild(errorElement);
      }
    });
  }

  validateRegistrationForm();
});
