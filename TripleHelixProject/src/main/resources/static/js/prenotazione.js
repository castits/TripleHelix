window.addEventListener("DOMContentLoaded", () => {
  function book() {
    const form = document.querySelector("form");

    form.addEventListener("submit", (e) => {
      e.preventDefault();
      fetch("api/bookings/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          institute: form.ente.value,
          participantQuantity: form.partecipanti.value,
          appointmentDate: form.data.value,
          timeSlot: form.durata.value,
          activity: form.attivita.value,
          bookingInfoReq: form.messaggio.value,
        }),
      }).then((response) => {
        if (response.ok) {
          alert("Prenotazione effettuata con successo!");
          location.href = "./index.html";
        } else {
          alert("Errore durante la prenotazione");
        }
      });
    });
  }

  book();
});
