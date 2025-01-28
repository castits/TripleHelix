window.addEventListener("DOMContentLoaded", () => {
  /**
   * Function that updates the height of the #hero section based on the window height
   * and the header height, adjusting related elements such as the background,
   * dark overlay, and section content.
   */
  function updateHeroHeight() {
    const header = document.querySelector("header");
    const hero = document.getElementById("hero");
    const heroBg = document.querySelector("#hero-bg");
    const heroDarkness = document.querySelector("#hero-bg-darkness");
    const heroContent = document.querySelector("#hero-content");

    // Calculate the available height for the hero section, subtracting the header height
    const heroHeight = window.innerHeight - header.offsetHeight;

    // Set the height of the hero section and related elements
    hero.style.height = `${heroHeight}px`;
    heroBg.style.height = `${heroHeight}px`;
    heroDarkness.style.height = `${heroHeight}px`;

    // Set the height of the hero section content to 90% of the hero section height
    heroContent.style.height = `${(heroHeight / 100) * 90}px`;
  }

  window.addEventListener("resize", updateHeroHeight);

  /**
   * Initializes the accordion functionality for the FAQ section, allowing users
   * to toggle open and close on clicked questions.
   */
  function openAccordion() {
    const faqItems = document.querySelectorAll(".faq-container");

    faqItems.forEach((faq) => {
      faq.addEventListener("click", () => {
        faq.classList.toggle("active");

        // If another item is open, close it
        faqItems.forEach((item) => {
          if (item !== faq && item.classList.contains("active")) {
            item.classList.remove("active");
          }
        });
      });
      faq.addEventListener("keydown", () => {
        faq.classList.toggle("active");

        // If another item is open, close it
        faqItems.forEach((item) => {
          if (item !== faq && item.classList.contains("active")) {
            item.classList.remove("active");
          }
        });
      });
    });
  }

  /**
   * Adds smooth scrolling behavior to "discover more" buttons, which scroll
   * the user to the #about-us section with animation.
   */
  function discoverMoreScroll() {
    const ctas = document.querySelectorAll(".discover-more");

    ctas.forEach((cta) => {
      cta.addEventListener("click", (event) => {
        event.preventDefault();
        document.getElementById("about-us").scrollIntoView();
      });
    });
  }

  /**
   * Sets up the behavior of navbar links, enabling smooth scrolling to
   * different sections of the page while accounting for header height.
   */
  function setupNavbarLinks() {
    const navLinks = document.querySelectorAll(".nav-mobile .scroll");
    const check = document.getElementById("check"); // The checkbox (hamburger menu) for mobile navigation
    const headerHeight = document.querySelector("header").offsetHeight;

    navLinks.forEach((link) => {
      link.addEventListener("click", (event) => {
        event.preventDefault();

        const href = link.getAttribute("href");

        if (href.startsWith("#")) {
          targetElement = document.querySelector(href);

          if (targetElement) {
            if (targetElement.id === "contact-form") {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight,
              });
            } else {
              window.scrollTo({
                top: targetElement.offsetTop - headerHeight - 40,
              });
            }
          }
        } else {
          window.location.href = href;
        }

        // Closes the mobile menu after clicking
        handleClick();
        if (check.checked) {
          check.checked = false;
        }
      });
    });
  }

  const dots = document.querySelectorAll(".dot");
  const items = document.querySelectorAll(".carousel-item");
  const container = document.querySelector(".carousel-container");

  let currentIndex = 0; // Tracks the current carousel item index

  // Set the first dot as active initially
  dots[currentIndex].classList.add("active");

  /**
   * Function that updates the active dot based on the current carousel
   * index, changing the highlighted dot.
   */
  function updateDots() {
    dots.forEach((dot) => dot.classList.remove("active"));
    dots[currentIndex].classList.add("active");
  }

  /**
   * Function that calculates the current carousel index based on the
   * scroll position of the container, updating the active dot accordingly.
   */
  function updateCurrentIndex() {
    const scrollPosition = container.scrollLeft;
    const itemWidth = items[0].offsetWidth;
    currentIndex = Math.round(scrollPosition / itemWidth);
    updateDots();
  }

  // Listener for the scroll event on the carousel
  container.addEventListener("scroll", updateCurrentIndex);

  // Navigation behavior through dots, to scroll between carousel items
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      // Scroll to the corresponding carousel item
      container.scrollTo({
        left: items[index].offsetLeft,
        behavior: "smooth",
      });
    });
  });

  // Function to navigate to the next carousel item
  function goToNextItem() {
    if (currentIndex < items.length - 1) {
      currentIndex++;
    } else {
      currentIndex = 0; // Loop back to the start
    }
    container.scrollTo({
      left: items[currentIndex].offsetLeft,
      behavior: "smooth",
    });
    setTimeout(updateDots, 300); // Add delay to ensure animation starts correctly
  }

  // Function to navigate to the previous carousel item
  function goToPreviousItem() {
    if (currentIndex > 0) {
      currentIndex--;
    } else {
      currentIndex = items.length - 1; // Loop back to the end
    }
    container.scrollTo({
      left: items[currentIndex].offsetLeft,
      behavior: "smooth",
    });
    setTimeout(updateDots, 300); // Add delay to ensure animation starts correctly
  }

  // Add Event Listeners to the arrows
  const prevArrow = document.querySelector(".carousel-arrow:first-of-type");
  const nextArrow = document.querySelector(".carousel-arrow:last-of-type");

  prevArrow.addEventListener("click", goToPreviousItem);
  nextArrow.addEventListener("click", goToNextItem);

  /**
   * Handles the progress bar that updates based on the page scroll position,
   * giving an idea of the user's reading progress on the page.
   */
  window.addEventListener("scroll", handleScroll);

  function handleScroll() {
    const winScroll =
      document.body.scrollTop || document.documentElement.scrollTop;

    const height =
      document.documentElement.scrollHeight -
      document.documentElement.clientHeight;

    const scrolled = (winScroll / height) * 100;

    document.getElementById("progressBar").style.width = scrolled + "%";
  }

  /**
   * Handles the submission of the contact form and validates the entered data.
   * Displays errors for invalid fields, or sends the data via fetch if valid.
   */
  function contact() {
    const form = document.querySelector("#contact");

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      // Clear previous error messages
      const errorMessages = form.querySelectorAll(".error-message");
      errorMessages.forEach((error) => error.remove());

      const successMessage = form.querySelector(".success-message");
      if (successMessage) successMessage.remove();

      const name = form.name.value.trim();
      const surname = form.surname.value.trim();
      const email = form.email.value.trim();
      const phone = form.phone.value.trim();
      const message = form.message.value.trim();

      // Regex for validation
      const nameRegex = /^[a-zA-ZàèéìòùÀÈÉÌÒÙ\s'-]{2,}$/; // Name and surname: minimum 2 characters, only letters, spaces, apostrophes, and dashes
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/; // Valid email with domain and extension
      const phoneRegex = /^\d{8,15}$/; // Only digits, length between 8 and 15

      let hasErrors = false;

      // Function to display an error message below a field
      const showError = (field, message) => {
        hasErrors = true;
        const errorElement = document.createElement("span");
        errorElement.className = "error-message";
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
      };

      // Specific validation for each field
      if (!name || !nameRegex.test(name)) {
        showError(
          form.name,
          "Il campo 'Nome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!surname || !nameRegex.test(surname)) {
        showError(
          form.surname,
          "Il campo 'Cognome' è obbligatorio e deve contenere solo lettere (minimo 2 caratteri)."
        );
      }

      if (!email || !emailRegex.test(email)) {
        showError(form.email, "Inserisci un'email valida.");
      }

      if (phone && !phoneRegex.test(phone)) {
        showError(
          form.phone,
          "Il numero di telefono deve contenere solo cifre e avere una lunghezza compresa tra 8 e 15 caratteri."
        );
      }

      if (message.length > 500) {
        showError(
          form.message,
          "Il messaggio non può superare i 500 caratteri."
        );
      }

      if (hasErrors) {
        return;
      }

      // If the data is valid, send the request
      try {
        const response = await fetch("/api/information-requests/send", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            userName: name,
            userSurname: surname,
            userEmail: email,
            userPhone: phone,
            informationRequestText: message,
          }),
        });

        if (response.ok) {
          const successElement = document.createElement("div");
          successElement.className = "success-message";
          successElement.textContent = "Messaggio inviato con successo!";
          form.appendChild(successElement);
          form.reset(); // Reset the form after submission
        } else {
          const errorElement = document.createElement("div");
          errorElement.className = "error-message";
          errorElement.textContent =
            "Errore durante l'invio del messaggio. Riprova più tardi.";
          form.appendChild(errorElement);
        }
      } catch (error) {
        console.error("Errore:", error);
        const errorElement = document.createElement("div");
        errorElement.className = "error-message";
        errorElement.textContent =
          "Si è verificato un errore. Riprova più tardi.";
        form.appendChild(errorElement);
      }
    });
  }

  updateHeroHeight(); // Sets the hero section to the correct height
  discoverMoreScroll(); // Initializes the scrolling behavior of CTAs
  openAccordion(); // Initializes the accordion functionalities
  setupNavbarLinks(); // Initializes the behavior of navbar links
  isLogged(); // Checks if the user is logged in
  contact(); // Sends the contact form
});
