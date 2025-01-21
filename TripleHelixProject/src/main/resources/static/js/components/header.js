/**
 * @file        main.js
 * @author      Nicolò Galizia
 * @description This file contains the JavaScript code for the header of the website.
 */

const check = document.getElementById("check");
const hamburger = document.getElementById("hamburger");
const progressBar = document.getElementById("progressBar");
const body = document.body;
const profiloLogin = document.getElementById("mobile-profile-login");
const aLogout = document.querySelectorAll(".logout");
const mobileNavLink = document.getElementsByClassName("mobileNavLink");
const navArray = Array.prototype.slice.call(mobileNavLink);

let isLoggedd = true;

if (isLoggedd == true) {
  profiloLogin.textContent = "Profilo";
  aLogout.forEach((logoutItem) => {
    logoutItem.style.display = "block";
  });
} else {
  profiloLogin.textContent = "Accedi";
  aLogout.forEach((logoutItem) => {
    logoutItem.style.display = "none";
  });
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
