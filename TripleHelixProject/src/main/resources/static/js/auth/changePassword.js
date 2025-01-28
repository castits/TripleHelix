document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("password-form");
  const urlParams = new URLSearchParams(window.location.search);
  const token = urlParams.get("token");

  if (!token) {
    const errorElement = document.createElement("div");
    errorElement.className = "error-message";
    errorElement.textContent = "Token non valido!";
    form.appendChild(errorElement);
    return;
  }

  document.getElementById("token").value = token;

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    const passwordField = document.getElementById("password");
    const confirmPasswordField = document.getElementById("confirm-password");
    const password = passwordField.value.trim();
    const confirmPassword = confirmPasswordField.value.trim();

    const errorMessages = form.querySelectorAll(".error-message");
    errorMessages.forEach((error) => error.remove());

    let hasErrors = false;

    const showError = (field, message) => {
      hasErrors = true;
      const errorElement = document.createElement("span");
      errorElement.className = "error-message";
      errorElement.textContent = message;
      field.parentNode.appendChild(errorElement);
    };

    if (!password || password.length < 6) {
      showError(
        passwordField,
        "La password deve contenere almeno 6 caratteri."
      );
    }

    if (!confirmPassword || confirmPassword !== password) {
      showError(confirmPasswordField, "Le password non corrispondono.");
    }

    if (hasErrors) return;

    fetch("/api/users/reset-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        token: token,
        newPassword: password,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Errore durante il reset della password");
        }
        return response.text();
      })
      .then((message) => {
        const successElement = document.createElement("div");
        successElement.className = "success-message";
        successElement.textContent = message;
        form.appendChild(successElement);

        setTimeout(() => {
          window.location.href = "/login.html";
        }, 1000);
      })
      .catch((error) => {
        console.error("Errore:", error);
        const errorElement = document.createElement("div");
        errorElement.className = "error-message";
        errorElement.textContent =
          "Errore durante il reset della password. Riprova più tardi.";
        form.appendChild(errorElement);
      });
  });
});
