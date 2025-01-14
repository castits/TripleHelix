window.addEventListener("DOMContentLoaded", () => {
  const registrationForm = document.getElementById("registration-form");

  // Aggiungi un container per i messaggi di errore
  const errorContainer = document.createElement("div");
  errorContainer.id = "error-container";
  errorContainer.style.color = "red";
  errorContainer.style.marginTop = "10px";
  registrationForm.appendChild(errorContainer);

  if (registrationForm) {
    registrationForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      // Pulisce i messaggi precedenti
      errorContainer.textContent = "";

      // Ottieni i valori del form
      const userName = document.getElementById("nome")?.value.trim();
      const userSurname = document.getElementById("cognome")?.value.trim();
      const userEmail = document.getElementById("email")?.value.trim();
      const userPassword = document.getElementById("password")?.value;
      const repeatPassword =
        document.getElementById("conferma-password")?.value;
      const gdprChecked = document.getElementById("gdpr")?.checked;

      // Validazione dei campi
      const errors = [];

      if (!userName) errors.push("Il campo 'Nome' è obbligatorio.");
      if (!userSurname) errors.push("Il campo 'Cognome' è obbligatorio.");
      if (!userEmail) errors.push("Il campo 'Email' è obbligatorio.");
      else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userEmail))
        errors.push("Inserisci un'email valida.");
      if (!userPassword)
        errors.push("Il campo 'Crea password' è obbligatorio.");
      else if (userPassword.length < 8)
        errors.push("La password deve contenere almeno 8 caratteri.");
      if (userPassword !== repeatPassword)
        errors.push("Le password non corrispondono.");
      if (!gdprChecked)
        errors.push(
          "Devi accettare il trattamento dei dati personali per procedere."
        );

      // Mostra errori se ci sono
      if (errors.length > 0) {
        errors.forEach((error) => {
          const errorMessage = document.createElement("p");
          errorMessage.textContent = error;
          errorContainer.appendChild(errorMessage);
        });
        return;
      }

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
          const successMessage = document.createElement("div");
          successMessage.style.color = "green";
          successMessage.style.marginTop = "10px";
          successMessage.textContent = "Registrazione completata con successo!";
          registrationForm.appendChild(successMessage);

          setTimeout(() => {
            window.location.href = "/login.html";
          }, 2000);
        } else {
          const errorData = await response.json();
          const errorMessage = document.createElement("p");
          errorMessage.textContent =
            errorData.message || "Errore durante la registrazione.";
          errorContainer.appendChild(errorMessage);
        }
      } catch (error) {
        console.error("Errore durante la richiesta di registrazione:", error);
        const errorMessage = document.createElement("p");
        errorMessage.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        errorContainer.appendChild(errorMessage);
      }
    });
  } else {
    console.error("Il form di registrazione non è stato trovato nel DOM.");
  }
});
