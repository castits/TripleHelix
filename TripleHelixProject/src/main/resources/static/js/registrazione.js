window.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("registration-form")
    .addEventListener("submit", async (event) => {
      event.preventDefault();
      const password = document.getElementById("password").value;
      const repeatPassword = document.getElementById("conferma-password").value;
      if (password === repeatPassword) {
        const response = await fetch("/pub/auth/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userName: document.getElementById("nome").value,
            userSurname: document.getElementById("cognome").value,
            userEmail: document.getElementById("email").value,
            userPassword: document.getElementById("password").value,
          }),
        });
        if (response.status === 201) {
          window.location.href = "/login.html";
        }
      }
    });
});
