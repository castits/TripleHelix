/**
 * Checks if the user is logged in and, based on the response, redirects
 * to the appropriate page: dashboard if logged in, login otherwise.
 */
async function isLogged() {
  const profileIcons = document.querySelectorAll(".profile");
  const bookBtns = document.querySelectorAll(".book-now-btn");

  let isUserLogged = false;

  try {
    const response = await fetch("/pub/auth/is-logged");

    if (response.ok) {
      const text = await response.text();
      isUserLogged = text === "true";
    }

    profileIcons.forEach((profile) => {
      profile.addEventListener("click", async (event) => {
        event.preventDefault();
        if (isUserLogged) {
          const role = await userRole();
          location.href =
            role == 2 ? "./usersDashboard.html" : "./bookingWaiting.html";
        } else {
          location.href = "./login.html";
        }
      });
    });

    if (bookBtns) {
      bookBtns.forEach((bookBtn) => {
        bookBtn.addEventListener("click", (event) => {
          event.preventDefault();
          location.href = isUserLogged ? "./booking.html" : "./login.html";
        });
      });
    }

    if (isUserLogged) {
      compileForm();
    }
  } catch (error) {
    console.error(error);
  }
}

/**
 * Handles user logout by sending a POST request to the server to
 * disconnect the user and redirect them to the homepage.
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

  return Number(role);
}

async function compileForm() {
  const form = document.querySelector("form");

  if (!form) return;

  const response = await fetch("/pub/auth/user-info");
  const user = await response.json();

  if (user) {
    const name = user.userName;
    const surname = user.userSurname;
    const email = user.userEmail;

    form.name.value = name;
    form.surname.value = surname;
    form.email.value = email;
    form.name.readOnly = true;
    form.surname.readOnly = true;
    form.email.readOnly = true;
  }
}

isLogged();
logout();
