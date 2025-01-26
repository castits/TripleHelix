/**
 * Gestisce la logica di registrazione dell'utente.
 *
 * Aggiunge un evento al form di registrazione che valida i dati inseriti dall'utente,
 * mostra gli errori di validazione, e invia i dati al server.
 */
window.addEventListener("DOMContentLoaded", () => {
  // Ottieni il form di registrazione dal DOM
  const registrationForm = document.getElementById("registration-form");

  // Aggiungi un container per i messaggi di errore
  const errorContainer = document.createElement("div");
  errorContainer.id = "error-container";
  errorContainer.style.color = "red";
  errorContainer.style.marginTop = "10px";
  registrationForm.appendChild(errorContainer);

  // Verifica se il form è presente nel DOM
  if (registrationForm) {
    // Aggiungi un event listener al submit del form
    registrationForm.addEventListener("submit", async (event) => {
      event.preventDefault(); // Impedisce il comportamento di default del form (ricarica della pagina)

      // Pulisce i messaggi di errore precedenti
      errorContainer.textContent = "";

      // Ottieni i valori inseriti nei campi del form
      const userName = document.getElementById("nome")?.value.trim();
      const userSurname = document.getElementById("cognome")?.value.trim();
      const userEmail = document.getElementById("email")?.value.trim();
      const userPassword = document.getElementById("password")?.value;
      const repeatPassword =
        document.getElementById("conferma-password")?.value;
      const gdprChecked = document.getElementById("gdpr")?.checked;

      // Array per raccogliere gli errori di validazione
      const errors = [];

      // Validazione dei campi
      if (!userName) errors.push("Il campo 'Nome' è obbligatorio.");
      if (!userSurname) errors.push("Il campo 'Cognome' è obbligatorio.");
      if (!userEmail) errors.push("Il campo 'Email' è obbligatorio.");
      else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(userEmail))
        // Verifica se l'email è valida
        errors.push("Inserisci un'email valida.");
      if (!userPassword)
        errors.push("Il campo 'Crea password' è obbligatorio.");
      else if (userPassword.length < 8)
        // Verifica che la password sia lunga almeno 8 caratteri
        errors.push("La password deve contenere almeno 8 caratteri.");
      if (userPassword !== repeatPassword)
        // Verifica che le password corrispondano
        errors.push("Le password non corrispondono.");
      if (!gdprChecked)
        // Verifica che l'utente abbia accettato i termini GDPR
        errors.push(
          "Devi accettare il trattamento dei dati personali per procedere."
        );

      // Se ci sono errori, mostra i messaggi di errore
      if (errors.length > 0) {
        errors.forEach((error) => {
          const errorMessage = document.createElement("p");
          errorMessage.textContent = error;
          errorContainer.appendChild(errorMessage);
        });
        return; // Ferma l'esecuzione del codice in caso di errori
      }

      // Se la validazione passa, invia i dati al server
      try {
        const response = await fetch("/pub/auth/register", {
          method: "POST", // Metodo HTTP per inviare i dati al server
          headers: { "Content-Type": "application/json" }, // Tipo di contenuto JSON
          body: JSON.stringify({
            userName, // Nome utente
            userSurname, // Cognome utente
            userEmail, // Email utente
            userPassword, // Password utente
          }),
        });

        // Se la registrazione è andata a buon fine (status 201)
        if (response.ok && response.status === 201) {
          // Redirigi l'utente alla pagina di login dopo 2 secondi
          window.location.href = "/login.html";
        } else {
          // Gestione degli errori di risposta del server
          const errorData = await response.json();
          const errorMessage = document.createElement("p");
          errorMessage.textContent =
            errorData.message || "Errore durante la registrazione.";
          errorContainer.appendChild(errorMessage);
        }
      } catch (error) {
        // Gestione degli errori di rete
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
