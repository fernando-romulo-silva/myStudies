let bolaImagem;
let jogadorImagem;
let computadorImagem;
let fundoImagem;
let quicarSom;
let golSom;

let pontosJogador = 0;
let pontosComputador = 0;

class Raquete {
  constructor(x) {
    this.x = x;
    this.y = height / 2;

    this.w = 20;
    this.h = 60;
  }

  update() {
    // se a raquete for do jogador, seguir o mouse
    if (this.x < width / 2) {
      this.y = mouseY;
    } else {
      // se a bola estiver acima da raquete, mover para cima, se estiver abaixo, mover para baixo
      if (bola.y < this.y) {
        this.y -= 5;
      } else if (bola.y > this.y) {
        this.y += 5;
      }
    }

    // limitar a raquete para nao sair da tela
    if (this.y < 0) {
      this.y = this.h / 2;
    }

    if (this.y > height - this.h / 2) {
      this.y = height - this.h / 2;
    }
  }

  draw() {
    // se a raquete for do jogador, usar a imagem do jogador, se for do computador, usar a imagem do computador
    if (this.x < width / 2) {
      imageMode(CENTER);
      image(jogadorImagem, this.x + this.w / 2, this.y, this.w, this.h);
    } else {
      imageMode(CENTER);
      image(computadorImagem, this.x + this.w / 2, this.y, this.w, this.h);
    }
  }
}

// extrair uma classe chamada bola para representar a posicao, movinmento e update da mesma
class Bola {
  constructor() {
    this.r = 15;
    this.reset();

    // angulo de rotacao atual da bola
    this.angulo = 0;
  }

  // quando toca na borda em x reseta velocidade tambem atraves da funcao reset
  reset() {
    this.x = width / 2;
    this.y = height / 2;
    const velocidade = 10;
    this.vx = Math.random() * velocidade * 2 - 2.5;
    this.vy = Math.random() * velocidade - velocidade;
    this.angulo = 0;
  }

  update() {
    this.x += this.vx;
    this.y += this.vy;

    // rotacionar a bola de acordo com a velocidade
    this.angulo += Math.sqrt(this.vx * this.vx + this.vy * this.vy) / 30;

    if (this.x > width - this.r || this.x < this.r) {
      if (this.x < this.r) {
        pontosComputador++;
      } else {
        pontosJogador++;
      }
      golSom.play();
      falaPontos();

      this.reset();
    }

    if (this.y > height - this.r || this.y < this.r) {
      this.vy *= -1;
    }

    if (
      circleRectCollision(
        this.x,
        this.y,
        this.r,
        jogador.x,
        jogador.y,
        jogador.w,
        jogador.h,
      ) ||
      circleRectCollision(
        this.x,
        this.y,
        this.r,
        computador.x,
        computador.y,
        computador.w,
        computador.h,
      )
    ) {
      this.vx *= -1;
      this.vx *= 1.1;
      this.vy *= 1.1;
      quicarSom.play();
    }
  }

  draw() {
    // rotacionar a bola de acordo com o angulo
    push();
    translate(this.x, this.y);
    rotate(this.angulo);
    imageMode(CENTER);
    image(bolaImagem, 0, 0, this.r * 2, this.r * 2);
    pop();
  }
}

// verifica a colisao entre um ciculo e um retangulo onde cx, cy e retangulo x, y, w, h
function circleRectCollision(cx, cy, r, rx, ry, rw, rh) {
  // encontrar o ponto mais proximo do centro do circulo no retangulo
  let closestX = constrain(cx, rx, rx + rw);
  let closestY = constrain(cy, ry, ry + rh);

  // calcular a distancia entre o centro do circulo e o ponto mais proximo
  let distanceX = cx - closestX;
  let distanceY = cy - closestY;

  // se a distancia for menor que o raio do circulo, houve colisao
  let distanceSquared = distanceX * distanceX + distanceY * distanceY;
  return distanceSquared < r * r;
}

// criar uma instancia da classe bola
let bola;
let jogador;
let computador;

function falaPontos() {
  // use speechapi para falar os pontos do jogador e do computador
  const msg = new SpeechSynthesisUtterance(
    `Pontos do jogador: ${pontosJogador}, Pontos do computador: ${pontosComputador}`,
  );
  // em portugues do brasil  msg.lang = "pt-BR";
  msg.lang = "pt-BR";
  window.speechSynthesis.speak(msg);
}

function preload() {
  bolaImagem = loadImage("bola.png");
  jogadorImagem = loadImage("barra01.png");
  computadorImagem = loadImage("barra02.png");
  fundoImagem = loadImage("fundo2.png");
  quicarSom = loadSound("quicar.wav");
  golSom = loadSound("gol.wav");
}

// codigo base do p5js
function setup() {
  createCanvas(800, 400);
  background(color(0, 0, 0));
  bola = new Bola();
  jogador = new Raquete(30);
  computador = new Raquete(width - 40);
}

function draw() {
  background(0, 0, 0);

  image(fundoImagem, width / 2, height / 2, width, height);
  bola.update();
  bola.draw();
  jogador.update();
  jogador.draw();
  computador.update();
  computador.draw();
}
