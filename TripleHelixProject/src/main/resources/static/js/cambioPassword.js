document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("password-form");

    form.addEventListener("submit", function (event) {
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirm-password").value;

        if (password !== confirmPassword) {
            event.preventDefault(); // Blocca l'invio del form
            alert("Le password non corrispondono!");
        }
    });
});
