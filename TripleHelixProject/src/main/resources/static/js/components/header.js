/**
 * @file        main.js
 * @author      Nicolò Galizia
 * @description This file contains the JavaScript code for the header of the website.
 */

const check = document.getElementById("check");
const hamburger = document.getElementById("hamburger");
const progressBar = document.getElementById("progressBar");
const body = document.body;
const profileLogin = document.getElementsByClassName("profile-login");
const aLogout = document.getElementsByClassName("logout");
const mobileNavLink = document.getElementsByClassName("mobileNavLink");
const navArray = Array.prototype.slice.call(mobileNavLink);
const profileLoginArray = Array.prototype.slice.call(profileLogin);
const aLogoutArray = Array.prototype.slice.call(aLogout);

try {
  const response = await fetch("/pub/auth/is-logged");

  if (response.ok) {
    const text = await response.text();
    if (text === "true") {
      isUserLogged = true;
      console.log(isUserLogged);
    }
    if (isUserLogged) {
      profileLoginArray.forEach((item) => {
        item.textContent = "Profilo";
      });
      aLogoutArray.forEach((item) => {
        item.style.display = "block";
      });
      location.href = "./dashboardUtente.html";
    } else {
      profileLoginArray.forEach((item) => {
        item.textContent = "Accedi";
      });
      aLogoutArray.forEach((item) => {
        item.style.display = "none";
      });
      location.href = "./login.html";
    }
  }
} catch (error) {
  console.error(error);
}

// Check if the checkbox is checked and add or remove the class to the body and change the hamburger icon
check.addEventListener("change", handleNavCheck);

function handleNavCheck() {
  if (check.checked) {
    body.classList.add("body-no-scroll");
    progressBar.classList.remove("progress-bar");
    hamburger.src = "./assets/svg/closure.svg";
    hamburger.alt = "Chiusura del menu di navigazione";
  } else {
    body.classList.remove("body-no-scroll");
    progressBar.classList.add("progress-bar");
    hamburger.src = "./assets/svg/hamburger.svg";
    hamburger.alt = "Apertura del menu di navigazione";
  }
}

navArray.forEach((item) => {
  item.addEventListener("click", function () {
    check.checked = false;
    handleNavCheck();
  });
});
