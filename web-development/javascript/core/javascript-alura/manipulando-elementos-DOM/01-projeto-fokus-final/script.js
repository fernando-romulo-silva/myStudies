const html = document.querySelector("html");

const focoBtn = document.querySelector(".app__card-button--foco");
const curtoBtn = document.querySelector(".app__card-button--curto");
const longBtn = document.querySelector(".app__card-button--longo");
const banner = document.querySelector(".app__image");
const titulo = document.querySelector(".app__title");
const botoes = document.querySelectorAll(".app__card-button");
const startPauseBtn = document.querySelector("#start-pause");
const iniciarOuPausarImagem = document.querySelector(
  ".app__card-primary-butto-icon"
);

const tempoNaTela = document.querySelector("#timer");

const iniciarOuPausarBt = document.querySelector("#start-pause span");
const musicaFocoInput = document.querySelector("#alternar-musica");
const musica = new Audio("./sons/luna-rise-part-one.mp3");
const audioTempoFinalizado = new Audio("./sons/beep.mp3");
const pause = new Audio("./sons/pause.mp3");
const play = new Audio("./sons/play.wav");
musica.loop = true;

let tempoDecorridoEmSegundos = 1500;
let intervaloId = null;

musicaFocoInput.addEventListener("change", () => {
  if (musica.paused) {
    musica.play();
  } else {
    musica.pause();
  }
});

focoBtn.addEventListener("click", () => {
  tempoDecorridoEmSegundos = 1500;
  alterarContexto("foco");
  focoBtn.classList.add("active");
});

curtoBtn.addEventListener("click", () => {
  tempoDecorridoEmSegundos = 300;
  alterarContexto("descanso-curto");
  curtoBtn.classList.add("active");
});

longBtn.addEventListener("click", () => {
  tempoDecorridoEmSegundos = 900;
  alterarContexto("descanso-longo");
  longBtn.classList.add("active");
});

function alterarContexto(contexto) {
  mostrarTempo();

  botoes.forEach((contexto) => {
    contexto.classList.remove("active");
  });

  html.setAttribute("data-contexto", contexto);
  banner.setAttribute("src", `./imagens/${contexto}.png`);

  switch (contexto) {
    case "foco":
      titulo.innerHTML = `
      Otimize sua produtividade,<br />
          <strong class="app__title-strong">mergulhe no que importa.</strong>`;
      break;
    case "descanso-curto":
      titulo.innerHTML = `
        Que tal dar uma respirada? <strong class="app__title-strong">Faça uma pausa curta!</strong>`;
      break;
    case "descanso-longo":
      titulo.innerHTML = `
        Hora de voltar à superfície.<strong class="app__title-strong"> Faça uma pausa longa.</strong>`;
      break;
  }
}

const contagemRegressiva = () => {
  if (tempoDecorridoEmSegundos <= 0) {
    audioTempoFinalizado.play();
    alert("Tempo finalizado");
    zerar();
    return;
  }

  tempoDecorridoEmSegundos--;
  mostrarTempo();
};

startPauseBtn.addEventListener("click", iniciarOuPausar);

function iniciarOuPausar() {
  if (intervaloId) {
    zerar();
    pause.play();
    return;
  }

  play.play();
  intervaloId = setInterval(contagemRegressiva, 1000);
  iniciarOuPausarBt.textContent = "Pausar";
  iniciarOuPausarImagem.src = "./imagens/pause.png";
}

function zerar() {
  clearInterval(intervaloId);
  intervaloId = null;
  iniciarOuPausarBt.textContent = "Começar";
  iniciarOuPausarImagem.src = "./imagens/play_arrow.png";
}

function mostrarTempo() {
  const tempo = new Date(tempoDecorridoEmSegundos * 1000);
  const tempoFormatado = tempo.toLocaleTimeString("pt-Br", {
    minute: "2-digit",
    second: "2-digit",
  });
  tempoNaTela.innerHTML = `${tempoFormatado}`;
}

mostrarTempo();
