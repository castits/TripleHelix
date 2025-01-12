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
      errorContainer.innerHTML = ""; // Resetta i messaggi di errore

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

      if (errors.length > 0) {
        errorContainer.innerHTML = errors.join("<br>");
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
          const successMessage = document.createElement("div");
          successMessage.style.color = "green";
          successMessage.style.marginTop = "10px";
          successMessage.textContent = "Login avvenuto con successo!";
          loginForm.appendChild(successMessage);

          setTimeout(() => {
            window.location.href = "/dashboard.html";
          }, 2000);
        } else {
          const errorData = await response.json();
          errorContainer.innerHTML =
            errorData.message ||
            "Errore durante il login. Controlla le credenziali.";
        }
      } catch (error) {
        console.error("Errore durante la richiesta di login:", error);
        errorContainer.innerHTML =
          "Si è verificato un errore. Riprova più tardi.";
      }
    });
  } else {
    console.error("Il form di login non è stato trovato nel DOM.");
  }
});
