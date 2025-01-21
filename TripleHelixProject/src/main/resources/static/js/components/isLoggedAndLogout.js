/**
 * Verifica se l'utente è loggato e, in base alla risposta, reindirizza
 * alla pagina appropriata: dashboard se loggato, login altrimenti.
 */
function isLogged() {
  const profileIcons = document.querySelectorAll(".profile");
  let isUserLogged = false;

  profileIcons.forEach((profile) => {
    profile.addEventListener("click", async (event) => {
      event.preventDefault();
      try {
        const response = await fetch("/pub/auth/is-logged");

        if (response.ok) {
          const text = await response.text();
          if (text === "true") {
            isUserLogged = true;
            console.log(isUserLogged);
          }
          if (isUserLogged) {
            location.href = "./usersDashboard.html";
          } else {
            location.href = "./login.html";
          }
        }
      } catch (error) {
        console.error(error);
      }
    });
  });
}

/**
 * Gestisce il logout dell'utente, inviando una richiesta POST al server per
 * disconnettere l'utente e reindirizzarlo alla homepage.
 */
function logout() {
  const logout = document.getElementById("logout");

  logout.addEventListener("click", async () => {
    try {
      const response = await fetch("/pub/auth/logout", {
        method: "POST",
      });

      if (response.ok) {
        location.href = "/index.html";
        console.log("user logged out");
      }
    } catch (error) {
      console.error(error);
    }
  });
}
