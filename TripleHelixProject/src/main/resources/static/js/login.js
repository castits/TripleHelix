window.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("login-form")
    .addEventListener("submit", async (event) => {
      event.preventDefault();
      const response = await fetch("/pub/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          userEmail: document.getElementById("email").value,
          userPassword: document.getElementById("password").value,
        }),
        credentials: "include",
      });
      if (response.ok) {
        alert("Login successful!");
        window.location.href = "/dashboard.html";
      } else {
        alert("Login failed! Incorrect credentials.");
      }
    });
});
