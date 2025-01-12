window.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("login-form");

  if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      const userEmail = document.getElementById("email").value;
      const userPassword = document.getElementById("password").value;

      if (!userEmail || !userPassword) {
        alert("Please fill in both email and password fields.");
        return;
      }

      try {
        const response = await fetch("/pub/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userEmail,
            userPassword,
          }),
          credentials: "include",
        });
        if (response.ok) {
          alert("Login successful!");
          window.location.href = "/dashboard.html";
        } else {
          const errorMessage =
            (await response.json()).message || "Login failed!";
          alert(errorMessage);
        }
      } catch (error) {
        console.error("Error during login request:", error);
        alert("An error occurred. Please try again later.");
      }
    });
  } else {
    console.error("Login form not found in the DOM.");
  }
});
