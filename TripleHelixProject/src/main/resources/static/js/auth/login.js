window.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("login-form");

  // Aggiungi un container per i messaggi di errore
  const errorContainer = document.createElement("div");
  errorContainer.id = "error-container";
  errorContainer.style.color = "red";
  errorContainer.style.marginTop = "10px";
  loginForm.appendChild(errorContainer);

  if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      // Pulisce i messaggi precedenti
      errorContainer.textContent = "";

      const userEmail = document.getElementById("email").value.trim();
      const userPassword = document.getElementById("password").value;

      // Validazione dei campi
      const errors = [];

      if (!userEmail) errors.push("Il campo 'Email' è obbligatorio.");
      else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userEmail))
        errors.push("Inserisci un'email valida.");

      if (!userPassword) errors.push("Il campo 'Password' è obbligatorio.");
      else if (userPassword.length < 8)
        errors.push("La password deve contenere almeno 8 caratteri.");

      // Mostra errori se ci sono
      if (errors.length > 0) {
        // Aggiungi i messaggi di errore come nuovi paragrafi
        errors.forEach((error) => {
          const errorMessage = document.createElement("p");
          errorMessage.textContent = error;
          errorContainer.appendChild(errorMessage);
        });
        return;
      }

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
            location.href = "./dashboardAdmin.html";
          }
        } else {
          const errorData = await response.json();
          const errorMessage = document.createElement("p");
          errorMessage.textContent =
            errorData.message ||
            "Errore durante il login. Controlla le credenziali.";
          errorContainer.appendChild(errorMessage);
        }
      } catch (error) {
        console.error("Errore durante la richiesta di login:", error);
        const errorMessage = document.createElement("p");
        errorMessage.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        errorContainer.appendChild(errorMessage);
      }
    });
  } else {
    console.error("Il form di login non è stato trovato nel DOM.");
  }
});
