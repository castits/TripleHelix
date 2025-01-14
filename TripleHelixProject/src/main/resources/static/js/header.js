/**
 * @file        main.js
 * @author      Nicolò Galizia
 * @description This file contains the JavaScript code for the header of the website.
 */

const check = document.getElementById("check");
const hamburger = document.getElementById("hamburger");
const progressBar = document.getElementById("progressBar");
const body = document.body;

// Check if the checkbox is checked and add or remove the class to the body and change the hamburger icon
check.addEventListener("change", () => {
  if (check.checked) {
    body.classList.add("body-no-scroll");
    progressBar.classList.remove("progress-bar");
    hamburger.src = "./assets/svg/closure.svg";
    hamburger.alt = "Close navigation menu";
  } else {
    body.classList.remove("body-no-scroll");
    progressBar.classList.add("progress-bar");
    hamburger.src = "./assets/svg/hamburger.svg";
    hamburger.alt = "Open navigation menu";
  }
});
