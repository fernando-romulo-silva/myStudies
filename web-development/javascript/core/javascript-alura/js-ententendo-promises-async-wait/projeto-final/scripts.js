const uploadBtn = document.getElementById("upload-btn");
const inputUpload = document.getElementById("image-upload");

const inputTags = document.getElementById("input-tags");
const listaTags = document.getElementById("lista-tags");
const botaoPublicar = document.querySelector(".botao-publicar");

const imagemPrincipal = document.querySelector(".main-imagem");
const nomeDaImagem = document.querySelector(".container-imagem-nome p");

const botaoDescartar = document.querySelector(".botao-descartar");

const tagsDisponiveis = [
  "Front-end",
  "Programacao",
  "Data Science",
  "Full-stack",
  "HTML",
  "CSS",
  "JavaScript",
];

uploadBtn.addEventListener("click", (event) => {
  // const file = event.target.files[0];

  // if (!file.type.match("image/png") && !file.type.match("image/jpeg")) {
  //   alert("Por favor, selecione uma imagem PNG ou JPEG.");
  //   return;
  // }

  // if (file.size > 2 * 1024 * 1024) {
  //   alert("A imagem deve ter no máximo 2MB.");
  //   return;
  // }

  inputUpload.click();
});

function lerConteudoDoArquivo(arquivo) {
  return new Promise((resolve, reject) => {
    const leitor = new FileReader();

    leitor.onload = () => {
      resolve({ url: leitor.result, name: arquivo.name });
    };

    leitor.onerror = () => {
      reject(`Erro na leitura do arquivo ${arquivo.name}`);
    };

    leitor.readAsDataURL(arquivo);
  });
}

inputUpload.addEventListener("change", async (evento) => {
  const arquivo = evento.target.files[0];

  if (arquivo) {
    try {
      const conteudoDoArquivo = await lerConteudoDoArquivo(arquivo);
      imagemPrincipal.src = conteudoDoArquivo.url;
      nomeDaImagem.textContent = conteudoDoArquivo.nome;
    } catch (error) {
      console.error("Erro na leitura do arquivo");
    }
  }
});

listaTags.addEventListener("click", (evento) => {
  if (evento.target.classList.contains("remove-tag")) {
    const tagQueQueremosRemover = evento.target.parentElement;
    listaTags.removeChild(tagQueQueremosRemover);
  }
});

async function verificaTagsDisponiveis(tagTexto) {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(tagsDisponiveis.includes(tagTexto));
    }, 1000);
  });
}

inputTags.addEventListener("keypress", async (evento) => {
  if (evento.key === "Enter") {
    evento.preventDefault();
    const tagTexto = inputTags.value.trim();

    if (tagTexto !== "") {
      try {
        const tagExiste = await verificaTagsDisponiveis(tagTexto);

        if (tagExiste) {
          const tagNova = document.createElement("li");
          tagNova.innerHTML = `<p>${tagTexto}</p> <img src="./img/close-black.svg" class="remove-tag">`;
          listaTags.appendChild(tagNova);
          inputTags.value = "";
        } else {
          alert("Tag não encontrada");
        }
      } catch (error) {
        console.error("Erro o verificar a existencia da tag");
        alert(
          "Erro ao verificar a existencia da tagTexto, verifique o console"
        );
      }
    }
  }
});

async function publicarProjeto(nomeProjeto, descricaoDoProjeto, tagsProjeto) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const deuCerto = Math.random() > 0.5;

      if (deuCerto) {
        resolve("Projeto publicado com sucesso");
      } else {
        reject("Erro ao publicar o projeto");
      }
    }, 2000);
  });
}

botaoPublicar.addEventListener("click", async (evento) => {
  evento.preventDefault();
  const nomeDoProjeto = document.getElementById("nome").value;
  const descricaoDoProjeto = document.getElementById("descricao").value;
  const tagsProjeto = Array.from(listaTags.querySelectorAll("p")).map(
    (tag) => tag.textContent
  );

  try {
    const resultado = await publicarProjeto(
      nomeDoProjeto,
      descricaoDoProjeto,
      tagsProjeto
    );
    console.log(resultado);
    alert("Deu tudo certo!");
  } catch (error) {
    console.log("Deu errado: ", error);
    alert("Deu tudo errado!");
  }
});

botaoDescartar.addEventListener("click", (evento) => {
  evento.preventDefault();

  const formulario = document.querySelector("form");
  formulario.reset();

  imagemPrincipal.src = "./img/imagem1.png";
  nomeDaImagem.textContent = "imagem1.png";

  listaTags.innerHTML = "";
});
