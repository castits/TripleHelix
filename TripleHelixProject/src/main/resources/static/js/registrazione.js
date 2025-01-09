document
  .getElementById("registration-form")
  .addEventListener("submit", async (event) => {
    event.preventDefault();
    const response = await fetch("/pub/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userName: document.getElementById("nome").value,
        userSurname: document.getElementById("cognome").value,
        userEmail: document.getElementById("email").value,
        userPassword: document.getElementById("password").value,
        roleId: 2,
      }),
    });
    if (response.status === 201) {
      window.location.href = "/login.html";
    }
  });
