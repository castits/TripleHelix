document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("password-form");
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (!token) {
        alert("Token non valido!");
        return;
    }

    document.getElementById("token").value = token;

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Previene l'invio del form

        const password = document.getElementById("password").value.trim();
        const confirmPassword = document.getElementById("confirm-password").value.trim();

        if (password !== confirmPassword) {
            alert("Le password non corrispondono!");
            return;
        }

        fetch("http://localhost:8080/api/users/reset-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                token: token,
                newPassword: password
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Errore durante il reset della password");
            }
            return response.text();
        })
        .then(message => {
            alert(message);
            window.location.href = "/login.html";  // Reindirizzamento alla pagina di login
        })
        .catch(error => {
            console.error("Errore:", error);
            alert("Errore durante il reset della password.");
        });
    });
});
