window.addEventListener("DOMContentLoaded", () => {
  /**
   * Updates the height of the #hero section and adjusts related elements
   * to ensure they fit perfectly within the viewport, minus the header height.
   */
  function updateHeroHeight() {
    // Get references to the necessary DOM elements
    const header = document.querySelector("header");
    const hero = document.getElementById("hero");
    const heroBg = document.querySelector("#hero-bg");
    const heroDarkness = document.querySelector("#hero-bg-darkness");
    const heroContent = document.querySelector("#hero-content");

    // Calculate the height of the hero section as the window height minus the header height
    const heroHeight = window.innerHeight - header.offsetHeight;

    // Apply the calculated height to the hero section and its child elements
    hero.style.height = `${heroHeight}px`;
    heroBg.style.height = `${heroHeight}px`;
    heroDarkness.style.height = `${heroHeight}px`;

    // Set the height of the hero content to 90% of the total hero height for proper spacing
    heroContent.style.height = `${(heroHeight / 100) * 90}px`;
  }

  /**
   * Initializes the FAQ accordion functionality by toggling the "active" class
   * on the clicked FAQ container and optionally closing others.
   */
  function openAccordion() {
    // Select all FAQ container elements
    const faqItems = document.querySelectorAll(".faq-container");

    faqItems.forEach((faq) => {
      // Add a click event listener to each FAQ container
      faq.addEventListener("click", () => {
        // Toggle the "active" class on the clicked FAQ
        faq.classList.toggle("active");

        // Close all other FAQ containers if they're open (optional)
        faqItems.forEach((item) => {
          if (item !== faq && item.classList.contains("active")) {
            item.classList.remove("active");
          }
        });
      });
    });
  }

  /**
   * Adds smooth scrolling behavior to "discover more" buttons,
   * scrolling to the height of the #hero section.
   */
  function ctaScroll() {
    // Select all call-to-action buttons with the "discover-more" class
    const ctas = document.querySelectorAll(".discover-more");
    const heroHeight = document.getElementById("hero").offsetHeight;

    ctas.forEach((cta) => {
      // Add a click event listener to each button
      cta.addEventListener("click", () => {
        // Scroll smoothly to the position just below the #hero section
        window.scrollTo({ top: heroHeight, behavior: "smooth" });
      });
    });
  }

  function setupNavbarLinks() {
    // Seleziona tutti i link della navbar
    const navLinks = document.querySelectorAll(".nav-mobile .scroll");
    const check = document.getElementById("check"); // Il checkbox del menu mobile
    const headerHeight = document.querySelector("header").offsetHeight;

    navLinks.forEach((link) => {
      link.addEventListener("click", (event) => {
        // Previene il comportamento predefinito
        event.preventDefault();

        const href = link.getAttribute("href");

        if (href.startsWith("#")) {
          targetElement = document.querySelector(href);

          if (targetElement) {
            // Scrolla alla sezione desiderata considerando l'altezza dell'header
            if (targetElement.id === "contact-form") {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight,
                behavior: "smooth",
              });
            } else {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight - 40,
                behavior: "smooth",
              });
            }
          }
        } else {
          // Per gli href che puntano a file o URL esterni
          window.location.href = href;
        }

        // Chiudi il menu mobile
        handleClick();
        if (check.checked) {
          check.checked = false;
        }
      });
    });
  }

  // Carousel elements
  const dots = document.querySelectorAll(".dot");
  const items = document.querySelectorAll(".carosello-item");
  const container = document.querySelector(".carosello-container");

  let currentIndex = 0; // Tracks the current carousel item index

  /**
   * Updates the "active" state of carousel dots based on the current index.
   */
  function updateDots() {
    dots.forEach((dot) => dot.classList.remove("active")); // Remove active state from all dots
    dots[currentIndex].classList.add("active"); // Add active state to the current dot
  }

  /**
   * Synchronizes the current index with the carousel scroll position,
   * ensuring the correct dot is highlighted as the user scrolls.
   */
  function updateCurrentIndex() {
    const scrollPosition = container.scrollLeft; // Get the horizontal scroll position
    const itemWidth = items[0].offsetWidth; // Determine the width of a single carousel item
    currentIndex = Math.round(scrollPosition / itemWidth); // Calculate the closest item index
    updateDots(); // Update the dots based on the current index
  }

  // Add a scroll event listener to the carousel container
  container.addEventListener("scroll", updateCurrentIndex);

  // Add click event listeners to the dots for manual navigation
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      // Scroll to the corresponding carousel item smoothly
      container.scrollTo({
        left: items[index].offsetLeft,
        behavior: "smooth",
      });
    });
  });

  // Initialize the current dot state
  updateCurrentIndex();
  updateHeroHeight(); // Ensure the hero section has the correct height
  ctaScroll(); // Initialize the CTA scrolling behavior
  openAccordion(); // Initialize the FAQ accordion functionality
  setupNavbarLinks();

  /**
   * Updates the progress bar width based on the scroll position of the page.
   */
  window.addEventListener("scroll", handleScroll);

  function handleScroll() {
    // Get the current vertical scroll position
    const winScroll =
      document.body.scrollTop || document.documentElement.scrollTop;

    // Calculate the total scrollable height of the page
    const height =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;

    // Determine the percentage of the page that has been scrolled
    const scrolled = (winScroll / height) * 100;

    // Set the progress bar's width based on the scroll percentage
    document.getElementById("progressBar").style.width = scrolled + "%";
  }

  /**
   * Toggles the hamburger menu state by changing the icon and alt text.
   */
  document.getElementById("hamburger").addEventListener("click", handleClick);

  function handleClick() {
    const hamburger = document.getElementById("hamburger");
    const hamburgerCheck = document.getElementById("check");

    // Check if the hamburger menu is currently open
    if (!hamburgerCheck.checked) {
      // Update the icon and alt text for the open state
      hamburger.src = "./assets/svg/closure.svg";
      hamburger.alt = "Close navigation menu";
    } else {
      // Update the icon and alt text for the closed state
      hamburger.src = "./assets/svg/hamburger.svg";
      hamburger.alt = "Open navigation menu";
    }
  }

  function isLogged() {
    const profile = document.getElementById("profile");
    let isUserLogged = false;

    profile.addEventListener("click", async () => {
      try {
        const response = await fetch("/pub/auth/is-logged");

        if (response.ok) {
          const text = await response.text();
          if (text === "true") {
            isUserLogged = true;
            console.log(isUserLogged);
          }
          if (isUserLogged) {
            location.href = "./dashboard.html";
          } else {
            location.href = "./login.html";
          }
        }
      } catch (error) {
        console.error(error);
      }
    });
  }

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

  isLogged();
  logout();

  function contact() {
    const form = document.querySelector("#contact-form");

    form.addEventListener("submit", (e) => {
      e.preventDefault();
      fetch("api/information-requests/send", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          userName: form.name.value,
          userSurname: form.surname.value,
          userEmail: form.email.value,
          userPhone: form.phone.value,
          informationRequestText: form.message.value,
        }),
      }).then((response) => {
        if (response.ok) {
          console.log("Email inviata con successo!");
          location.href = "./index.html";
        } else {
          alert("Errore nell'invio dell'email. Riprova più tardi.");
        }
      });
    });
  }

  contact();
});
