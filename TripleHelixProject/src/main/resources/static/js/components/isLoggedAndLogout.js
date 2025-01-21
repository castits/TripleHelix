/**
 * Verifica se l'utente è loggato e, in base alla risposta, reindirizza
 * alla pagina appropriata: dashboard se loggato, login altrimenti.
 */
function isLogged() {
  const profileIcons = document.querySelectorAll(".profile");
  const logoutBtn = document.querySelectorAll(".logout");
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
            profiloLogin.textContent = "Profilo";
            logoutBtn.forEach((logoutItem) => {
              logoutItem.style.display = "block";
            });
            location.href = "./usersDashboard.html";
          } else {
            profiloLogin.textContent = "Accedi";
            logoutBtn.forEach((logoutItem) => {
              logoutItem.style.display = "none";
            });
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
  const logout = document.querySelectorAll(".logout");

  logout.forEach((logoutItem) => {
    logoutItem.addEventListener("click", async (e) => {
      e.preventDefault();
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
  });
}

async function userRole() {
  const response = await fetch("/api/users/auth-role");
  const role = await response.text();
  console.log(role);
}

isLogged();
logout();
userRole();
