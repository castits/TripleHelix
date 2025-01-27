# **Frontend Documentation for the Triple Helix Project**

## **Overview**

The frontend of the Triple Helix Project was built using HTML, CSS, and vanilla JavaScript. This structure provides a clean and maintainable solution, ensuring high compatibility across all browsers without relying on external frameworks. The project emphasizes modularity and reusability, with a clear file organization that separates concerns like authentication, bookings, feedbacks, and administrative tools.

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
│       ├── carousel_arrow.svg
│       ├── cascina.svg
│       ├── closure.svg
│       ├── coloredClosure.svg
│       ├── coloredHamburger.svg
│       ├── double_arrow.svg
│       ├── facebook.svg
│       ├── hamburger.svg
│       ├── instagram.svg
│       ├── location.svg
│       ├── logo.svg
│       ├── logout.svg
│       ├── mail.svg
│       ├── phone.svg
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
│   │   └── usersDashboard.js
│   └── components
│       ├── header.js
│       └── main.js
├── styles
│   ├── auth
│   │   └── form.css
│   ├── backoffice
│   │   ├── backoffice.css
│   │   └── bookingWaiting.css
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
- Users have a private area to view their confirmed and pending bookings.

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
