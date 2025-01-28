let endpointFeedbacks = "/api/feedbacks";
let feedbacks = [];
let x = 0;

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
    showFeedbacks(x);
  })
  .catch((error) => {
    console.log("Errore:", error);
  });

let listaFeedbacks = document.querySelector(".containerPrenotazione");

function createFeedbackBox(feedback) {
  let div = document.createElement("div");
  div.classList.add("prenotazione-box");

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

  while (listaFeedbacks.firstChild) {
    listaFeedbacks.removeChild(listaFeedbacks.firstChild);
  }

  feedbacks.forEach((feedback) => {
    listaFeedbacks.appendChild(createFeedbackBox(feedback));
  });
}
