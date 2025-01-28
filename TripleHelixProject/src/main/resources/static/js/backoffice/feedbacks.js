let endpointFeedbacks = "/api/feedbacks";
let feedbacks = [];
let x = 0;

// Fetch per ottenere il JSON
fetch(endpointFeedbacks)
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error("Network response was not ok.");
    }
  })
  .then((feedbackJSON) => {
    feedbacks = feedbackJSON;
    x = feedbacks.length;
    showFeedbacks(x); // Mostra tutti i feedbacks
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

// Seleziona il contenitore per la lista di feedbacks
let listaFeedbacks = document.querySelector(".containerPrenotazione");

function createFeedbackBox(feedback) {
  // Crea un div per ogni feedback
  let div = document.createElement("div");
  div.classList.add("prenotazione-box"); // Stesso stile di prenotazioni

  // Crea e aggiungi gli elementi
  let laboratorio = document.createElement("p");
  laboratorio.appendChild(
    document.createTextNode(`Laboratorio: ${feedback.whichLab}`)
  );
  div.appendChild(laboratorio);

  let formative = document.createElement("p");
  formative.appendChild(
    document.createTextNode(`Formativo: ${feedback.formative}/5`)
  );
  div.appendChild(formative);

  let engaging = document.createElement("p");
  engaging.appendChild(
    document.createTextNode(`Coinvolgente: ${feedback.engaging}/5`)
  );
  div.appendChild(engaging);

  let staffQuality = document.createElement("p");
  staffQuality.appendChild(
    document.createTextNode(`Qualità dello Staff: ${feedback.staffQuality}/5`)
  );
  div.appendChild(staffQuality);

  let recommend = document.createElement("p");
  recommend.appendChild(
    document.createTextNode(
      `Consiglierebbe il laboratorio: ${feedback.recommendLab}`
    )
  );
  div.appendChild(recommend);

  let advices = document.createElement("p");
  advices.appendChild(
    document.createTextNode(`Suggerimenti: ${feedback.advices}`)
  );
  div.appendChild(advices);

  let date = document.createElement("p");
  let formattedDate = new Date(feedback.date).toLocaleDateString("it-IT", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
  date.appendChild(document.createTextNode(`Data: ${formattedDate}`));
  div.appendChild(date);

  return div;
}

function showFeedbacks(num) {
  let h2NumFeedbacks = document.getElementById("feedbacks");
  h2NumFeedbacks.textContent = `Feedbacks: ${num}`;

  // Rimuove tutti i figli del contenitore in modo sicuro
  while (listaFeedbacks.firstChild) {
    listaFeedbacks.removeChild(listaFeedbacks.firstChild);
  }

  // Itera su tutti i feedbacks e li aggiunge al DOM
  feedbacks.forEach((feedback) => {
    listaFeedbacks.appendChild(createFeedbackBox(feedback));
  });
}
