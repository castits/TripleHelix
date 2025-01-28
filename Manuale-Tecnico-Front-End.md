# **Frontend Documentation for the Triple Helix Project**

## **Overview**

The frontend of the Triple Helix Project was built using HTML, CSS, and vanilla JavaScript. This structure provides a clean and maintainable solution, ensuring high compatibility across all browsers without relying on external frameworks. The project emphasizes modularity and reusability, with a clear file organization that separates concerns like authentication, bookings, feedbacks, and administrative tools.

Additionally, the frontend prioritizes user experience (UX) by implementing intuitive navigation, visually engaging elements, and accessibility features to ensure seamless interaction for all users, including individuals with disabilities. Features like ARIA attributes, descriptive `alt` text for images, and `title` attributes for interactive elements demonstrate a strong commitment to inclusivity. Users can also contact Cascina Caccia directly via dedicated forms and clear call-to-action buttons.

To enhance visibility and ranking on search engines, SEO best practices such as structured data using JSON-LD have been implemented.

---

## **Technologies Used**

- **HTML5**: Provides the semantic structure for the project’s pages. Each page is designed to be standalone and includes modular components like headers, footers, and forms.
- **CSS3**: Handles styling, animations, and responsiveness.
  - **CSS Modules**: Organized by feature or component (e.g., `navbar.css`, `form.css`).
  - **Reset Stylesheet**: Included to standardize browser styles across all platforms.
- **JavaScript (ES6+)**: Implements interactivity and dynamic behavior.
  - **Modules**: JavaScript files are organized into reusable components and feature-specific scripts.
  - **Fetch API**: Used for asynchronous calls to backend endpoints for data retrieval and submission.
- **Google Fonts**: For scalable, vector-based icons and fonts used throughout the UI.

---

## **File Structure**

```
.
├── assets
│   ├── img
│   │   ├── beni-confiscati.jpg
│   │   ├── bruno-caccia.jpg
│   │   ├── carla-e-bruno-caccia.jpg
│   │   ├── cascina_caccia_mobile.jpg
│   │   ├── cascina_caccia.jpg
│   │   ├── deck.jpg
│   │   ├── logo-cascina-caccia-2.png
│   │   ├── logo-cascina-caccia.png
│   │   ├── logo-libera-2.png
│   │   ├── logo-libera.png
│   │   ├── miele-e-api.jpg
│   │   ├── regolegalita.jpg
│   │   ├── ricicliamo.jpg
│   │   ├── riunione-laboratorio.jpg
│   │   ├── sentiero-cascina-caccia.jpg
│   │   ├── soldi-finti.jpg
│   │   └── tavolo-imbandito.jpg
│   └── svg
│       ├── account.svg
│       ├── arrow.svg
│       ├── cancel.svg
│       ├── carousel_arrow.svg
│       ├── cascina.svg
│       ├── check.svg
│       ├── closure.svg
│       ├── coloredClosure.svg
│       ├── coloredHamburger.svg
│       ├── confirmed.svg
│       ├── delete_24dp_UNDEFINED_FILL0_wght400_GRAD0_opsz24.svg
│       ├── double_arrow.svg
│       ├── facebook.svg
│       ├── feedback.svg
│       ├── hamburger.svg
│       ├── homepage.svg
│       ├── instagram.svg
│       ├── location.svg
│       ├── logo.svg
│       ├── logout.svg
│       ├── mail.svg
│       ├── open-backoffice.svg
│       ├── phone.svg
│       ├── waiting.svg
│       └── youtube.svg
├── carousel-info
│   ├── carousel1.html
│   ├── carousel10.html
│   ├── carousel2.html
│   ├── carousel3.html
│   ├── carousel4.html
│   ├── carousel5.html
│   ├── carousel6.html
│   ├── carousel7.html
│   ├── carousel8.html
│   └── carousel9.html
├── js
│   ├── auth
│   │   ├── changePassword.js
│   │   ├── isLoggedAndLogout.js
│   │   ├── login.js
│   │   ├── mailChangePassword.js
│   │   └── registration.js
│   ├── backoffice
│   │   ├── booking.js
│   │   ├── bookingConfirmed.js
│   │   ├── bookingRefuse.js
│   │   ├── bookingWaiting.js
│   │   ├── feedbacks.js
│   │   ├── userBookingConfirmed.js
│   │   ├── userBookingRefused.js
│   │   └── usersDashboard.js
│   └── components
│       ├── header.js
│       └── main.js
├── styles
│   ├── auth
│   │   └── form.css
│   ├── backoffice
│   │   ├── backoffice.css
│   │   ├── booking.css
│   │   └── userBackoffice.css
│   ├── components
│   │   ├── footer.css
│   │   ├── form-messages.css
│   │   └── navbar.css
│   ├── homepage
│   │   ├── carousel.css
│   │   ├── faq.css
│   │   ├── formContacts.css
│   │   ├── hero.css
│   │   └── labInfo.css
│   ├── booking.css
│   ├── reset.css
│   └── style.css
├── booking.html
├── bookingConfirmed.html
├── bookingRefused.html
├── bookingWaiting.html
├── changePassword.html
├── feedbacks.html
├── index.html
├── login.html
├── mailChangePassword.html
├── package-lock.json
├── package.json
├── registration.html
├── robot.txt
├── sitemap.xml
├── userDashboardConfirmed.html
├── userDashboardRefused.html
└── usersDashboard.html
```

---

## **Frontend Features**

### **1. Authentication**

**Files Involved**:

- `js/auth/changePassword.js`
- `js/auth/isLoggedAndLogout.js`
- `js/auth/login.js`
- `js/auth/mailChangePassword.js`
- `js/auth/registration.js`

**Features**:

- **Registration**: Validates form fields and submits data.
- **Login**: Authenticates users and handles session management.
- **Logout**: Clears session data and redirects users to the login page.
- **Password change**: Allows users to change their password after validating the current password.

---

### **2. Bookings Management**

**Files Involved**:

- `js/backoffice/booking.js`
- `js/backoffice/bookingConfirmed.js`
- `js/backoffice/bookingRefuse.js`
- `js/backoffice/bookingWaiting.js`
- `js/backoffice/usersDashboard.js`

**Features**:

- Fetches and displays bookings.
- Filters bookings based on their status (e.g., `PENDING`, `CONFIRMED`).
- Allows admins to approve or reject bookings.
- Users can make bookings through a dedicated form.
- Users have a private area to view their confirmed, refused and pending bookings.

---

### **3. Carousel Implementation**

**Files Involved**:

- `index.html`
- `styles/homepage/carousel.css`
- `js/components/main.js`
- `carousel-info/*.html`

**Features**:

- Displays a dynamic carousel on the homepage.
- Fetches content from modular HTML files in `carousel-info/`.
- Uses JavaScript to implement slide transitions and navigation.

---

### **4. Feedback Management**

**Files Involved**:

- `js/backoffice/feedbacks.js`
- `feedbacks.html`

**Features**:

- Fetches and displays users feedbacks.
- Allows admins to check feedback entries.

---

### **5. User Experience Enhancements**

The frontend design includes the following UX features:

- **Responsive Design**: Ensures optimal viewing experiences across desktops, tablets, and smartphones.
- **Intuitive Navigation**: Uses a consistent header and footer for smooth browsing and accessibility.
- **Visual Feedback**: Provides feedback through animations, form validation messages, and dynamic updates.
- **Contact with Cascina Caccia**:
  - Users can easily reach Cascina Caccia via the dedicated **Contact Form** on the homepage.
  - Clear buttons and links guide users to the booking form.
  - Social media icons (Facebook, Instagram, YouTube) are accessible for direct interaction with the community.

---

### **6. Accessibility and Inclusivity**

To ensure the platform is usable for individuals with disabilities, the following accessibility practices have been implemented:

- **ARIA Attributes**: Used for dynamic and interactive elements (e.g., carousels, forms) to ensure screen readers can interpret the content correctly.
- **Descriptive `alt` Text**: Every image includes meaningful `alt` text to describe its content for visually impaired users.
- **Keyboard Navigation**: Interactive elements like buttons, links, and forms are fully navigable via keyboard inputs.
- **`title` Attributes**: Added to links and interactive components to provide additional context when users hover over them.
- **Semantic HTML**: Proper use of `<header>`, `<nav>`, `<main>`, `<footer>`, and `<section>` ensures that assistive technologies can parse and navigate the page structure easily.

### **7. SEO Improvements**

To enhance the website's visibility and search engine ranking, the following SEO best practices have been implemented:

- **JSON-LD (Linked Data)**: Structured data is provided using JSON-LD to help search engines better understand the content and context of the website.

---

## **Testing and Validation**

### **1. Browser Compatibility**

- Verified on various browsers.
- Responsive design tested on devices of various screen sizes.

### **2. Error Handling**

- Implemented user-friendly messages for invalid form submissions and API errors.

### **3. Manual Testing**

- **Forms**: Checked validation logic (e.g., empty fields, invalid email formats).
- **API Requests**: Validated with various payloads using mock data.

---

## **Conclusion**

The frontend of the Triple Helix Project is a lightweight, modular, and responsive implementation, leveraging HTML, CSS, and vanilla JavaScript to deliver a seamless user experience. Its well-organized structure ensures scalability and maintainability, aligning with modern web development best practices.
