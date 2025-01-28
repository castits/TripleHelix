document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("forgot-password-form");

  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();

      const emailField = document.getElementById("email");
      const email = emailField.value.trim();

      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

      let hasErrors = false;

      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      if (!email || !emailRegex.test(email)) {
        showError(emailField, "Inserisci un'email valida.");
      }

      if (hasErrors) return;

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
