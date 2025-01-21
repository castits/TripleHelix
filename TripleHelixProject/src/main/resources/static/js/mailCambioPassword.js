document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("forgot-password-form");

    if (form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();  // Previene il comportamento predefinito del form

            const email = document.getElementById("email").value.trim();

            fetch("http://localhost:8080/users/forgot-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email: email })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('User not found or other error');
                }
                return response.text();
            })
            .then(message => alert(message))
            .catch(error => {
                console.error('Error:', error);
                alert("Errore nell'invio della richiesta. Riprova più tardi.");
            });
        });
    } else {
        console.error("Form non trovato. Controlla l'ID nel file HTML.");
    }
});
