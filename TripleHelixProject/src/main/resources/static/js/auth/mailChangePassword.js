document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("forgot-password-form");

  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault(); // Previene il comportamento predefinito del form

      const emailField = document.getElementById("email");
      const email = emailField.value.trim();

      // Pulisce i messaggi di errore precedenti
      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      // Regex per validazione dell'email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

      let hasErrors = false;

      // Funzione per mostrare un messaggio di errore sotto il campo
      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      // Validazione del campo email
      if (!email || !emailRegex.test(email)) {
        showError(emailField, "Inserisci un'email valida.");
      }

      if (hasErrors) return; // Se ci sono errori, non invia la richiesta

      // Se i dati sono validi, invia la richiesta
      fetch("/api/users/forgot-password", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email: email }),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("User not found or other error");
          }
          return response.text();
        })
        .then((message) => {
          const successElement = document.createElement("div");
          successElement.className = "success-message";
          successElement.textContent = message;
          form.appendChild(successElement);
        })
        .catch((error) => {
          console.error("Error:", error);
          const errorElement = document.createElement("div");
          errorElement.className = "error-message";
          errorElement.textContent =
            "Errore nell'invio della richiesta. Riprova più tardi.";
          form.appendChild(errorElement);
        });
    });
  } else {
    console.error("Form non trovato. Controlla l'ID nel file HTML.");
  }
});
