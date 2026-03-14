function tocaSom(seletorAudio) {
  const elemento = document.querySelector(seletorAudio);

  if (!elemento || elemento.localName !== "audio") {
    return;
  }

  elemento.play();
}

document.querySelector(".tecla_pom").onclick = tocaSom;

const teclas = document.querySelectorAll(".tecla");

teclas.forEach((tecla) => {
  tecla.onclick = () => {
    const teclaNome = tecla.classList[1];
    tocaSom(`#som_${teclaNome}`); // template string
  };

  tecla.onkeydown = (event) => {
    if (event.code === "Enter" || event.code == "Space") {
      tecla.classList.add("ativa");
    }
  };

  tecla.onkeyup = () => tecla.classList.remove("ativa");
});
