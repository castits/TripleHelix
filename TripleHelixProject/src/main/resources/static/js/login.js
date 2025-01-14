window.addEventListener("DOMContentLoaded", () => {
  /**
   * Crea un contenitore per i messaggi di errore, aggiungendolo al modulo di login.
   * @type {HTMLElement}
   */
  const errorContainer = document.createElement("div");
  errorContainer.id = "error-container";
  errorContainer.style.color = "red";
  errorContainer.style.marginTop = "10px";
  loginForm.appendChild(errorContainer);

  /**
   * Gestisce l'evento submit del modulo di login. Esegue la validazione dei dati inseriti e
   * invia una richiesta di login al server. In caso di errore, mostra i messaggi di errore.
   * @param {Event} event - L'evento di submit del form.
   */
  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault(); // Previene il comportamento predefinito del form

    // Pulisce i messaggi di errore precedenti
    errorContainer.textContent = "";

    const userEmail = document.getElementById("email").value.trim();
    const userPassword = document.getElementById("password").value;

    // Array di errori di validazione
    const errors = [];

    // Validazione dell'email
    if (!userEmail) errors.push("Il campo 'Email' è obbligatorio.");
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userEmail))
      errors.push("Inserisci un'email valida.");

    // Validazione della password
    if (!userPassword) errors.push("Il campo 'Password' è obbligatorio.");
    else if (userPassword.length < 8)
      errors.push("La password deve contenere almeno 8 caratteri.");

    // Mostra i messaggi di errore se ce ne sono
    if (errors.length > 0) {
      errors.forEach((error) => {
        const errorMessage = document.createElement("p");
        errorMessage.textContent = error;
        errorContainer.appendChild(errorMessage);
      });
      return; // Esce dalla funzione per non inviare i dati
    }

    try {
      const response = await fetch("/pub/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userEmail, userPassword }),
        credentials: "include",
      });

      // Se il login è riuscito
      if (response.ok) {
        const successMessage = document.createElement("div");
        successMessage.style.color = "green";
        successMessage.style.marginTop = "10px";
        successMessage.textContent = "Login avvenuto con successo!";
        loginForm.appendChild(successMessage);

        setTimeout(() => {
          window.location.href = "/dashboard.html"; // Redirige alla dashboard
        }, 2000);
      } else {
        // Se il login non è riuscito, mostra il messaggio di errore
        const errorData = await response.json();
        const errorMessage = document.createElement("p");
        errorMessage.textContent =
          errorData.message ||
          "Errore durante il login. Controlla le credenziali.";
        errorContainer.appendChild(errorMessage);
      }
    } catch (error) {
      // Se si verifica un errore nella richiesta di login
      console.error("Errore durante la richiesta di login:", error);
      const errorMessage = document.createElement("p");
      errorMessage.textContent =
        "Si è verificato un errore. Riprova più tardi.";
      errorContainer.appendChild(errorMessage);
    }
  });
});
