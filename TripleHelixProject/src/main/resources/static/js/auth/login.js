window.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("login-form");

  if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      const errorMessages = loginForm.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const userEmail = document.getElementById("email").value.trim();
      const userPassword = document.getElementById("password").value;

      let hasErrors = false;

      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.style.color = "red";
        errorElement.style.fontSize = "0.9rem";
        errorElement.style.marginTop = "5px";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      if (!userEmail) {
        showError(
          document.getElementById("email"),
          "Il campo 'Email' è obbligatorio."
        );
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userEmail)) {
        showError(
          document.getElementById("email"),
          "Inserisci un'email valida."
        );
      }

      if (!userPassword) {
        showError(
          document.getElementById("password"),
          "Il campo 'Password' è obbligatorio."
        );
      } else if (userPassword.length < 8) {
        showError(
          document.getElementById("password"),
          "La password deve contenere almeno 8 caratteri."
        );
      }

      if (hasErrors) return;

      try {
        const response = await fetch("/pub/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ userEmail, userPassword }),
          credentials: "include",
        });

        if (response.ok) {
          const role = await userRole();

          if (role == 2) {
            location.href = "./usersDashboard.html";
          } else if (role == 1) {
            location.href = "./bookingWaiting.html";
          }
        } else {
          const errorData = await response.json();
          const generalError = document.createElement("span");
          generalError.className = "error-message";
          generalError.textContent =
            errorData.message ||
            "Errore durante il login. Controlla le credenziali.";
          loginForm.appendChild(generalError);
        }
      } catch (error) {
        console.error("Errore durante la richiesta di login:", error);
        const generalError = document.createElement("span");
        generalError.className = "error-message";
        generalError.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        generalError.style.marginTop = "20px";
        loginForm.appendChild(generalError);
      }
    });
  } else {
    console.error("Il form di login non è stato trovato nel DOM.");
  }
});
