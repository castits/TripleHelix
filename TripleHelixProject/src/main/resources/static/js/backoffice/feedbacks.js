// let endpointfeedbacks = "/api/feedbacks";
let endpointfeedbacks = "https://jsonblob.com/api/jsonBlob/1333236738700795904";

let feedbacks = [];
let x = 0;

//api/bookings/status/pending/refused/confirmed
//status=pending

// Fetch per ottenere il JSON
fetch(endpointfeedbacks)
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error("Network response was not ok.");
    }
  })
  .then((feedbackJSON) => {
    feedbacks = feedbackJSON;

    let x = feedbacks.length;
    showfeedbacks(x); // Mostra tutte i feedbacks
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

// // Seleziona il contenitore per la lista di feedbacks
let listafeedback = document.querySelector(".containerFeedbacks");

function createfeedbackBox(feedback) {
  // Crea un div per ogni feedback
  let div = document.createElement("div");
  div.classList.add("feedback-box");
  div.classList.add("feedbacks");

  // Crea e aggiungi gli elementi

  // Crea e aggiungi gli elementi
  let labName = document.createElement("p");
  labName.textContent = `Laboratorio: ${feedback.whichLab}`;
  div.appendChild(labName);

  let formative = document.createElement("p");
  formative.textContent = `Formativo: ${feedback.formative}/5`;
  div.appendChild(formative);

  let engaging = document.createElement("p");
  engaging.textContent = `Coinvolgente: ${feedback.engaging}/5`;
  div.appendChild(engaging);

  let staffQuality = document.createElement("p");
  staffQuality.textContent = `Qualità dello Staff: ${feedback.staffQuality}/5`;
  div.appendChild(staffQuality);

  let recommend = document.createElement("p");
  recommend.textContent = `Consiglierebbe il laboratorio: ${feedback.recommendLab}`;
  div.appendChild(recommend);

  let advices = document.createElement("p");
  advices.textContent = `Suggerimenti: ${feedback.advices}`;
  div.appendChild(advices);

  let date = document.createElement("p");
  let formattedDate = new Date(feedback.date).toLocaleDateString("it-IT", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
  date.textContent = `Data: ${formattedDate}`;
  div.appendChild(date);
  return div;
}

function showfeedbacks(num) {
  let h2Numfeedbacks = document.getElementById("feedbacks");
  let numfeedbacks = document.createElement("span"); // Crea l'elemento <span>
  numfeedbacks.textContent = num;

  h2Numfeedbacks.appendChild(numfeedbacks);
  // Rimuove tutti i figli del contenitore in modo sicuro

  // Itera su tutte le feedbacks e le aggiunge al DOM
  feedbacks.forEach((feedback) => {
    listafeedback.appendChild(createfeedbackBox(feedback));
  });
}
