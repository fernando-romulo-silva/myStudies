let listaDeItems = [];
let itemAEditar;

const form = document.getElementById("form-itens");
const itensIput = document.getElementById("receber-item");
const ulItems = document.getElementById("lista-de-itens");
const ulItemsComprados = document.getElementById("itens-comprados");
const listaRecuperadas = localStorage.getItem("listaDeItens");

function atualizaLocalStorage() {
  localStorage.setItem("listaDeItens", JSON.stringify(listaDeItems));
}

if (listaRecuperadas) {
  listaDeItems = JSON.parse(listaRecuperadas);
  mostrarItem();
} else {
  listaDeItems = [];
}

form.addEventListener("submit", (evento) => {
  evento.preventDefault();
  salvarItem();
  mostrarItem();
  itensIput.focus;
});

function salvarItem() {
  const comprasItem = itensIput.value;

  const checarDuplicados = listaDeItems.some(
    (elemento) => elemento.valor.toUpperCase() === comprasItem.toUpperCase()
  );

  if (checarDuplicados) {
    alert("Item ja existe");
  } else {
    listaDeItems.push({
      valor: comprasItem,
      checar: false,
    });
  }

  itensIput.value = "";
}

function mostrarItem() {
  ulItems.innerHTML = "";
  ulItemsComprados.innerHTML = "";
  listaDeItems.forEach((elemento, index) => {
    if (elemento.checar) {
      ulItemsComprados.innerHTML += `
    <li class="item-compra is-flex is-justify-content-space-between" data-value="${index}">
        <div>
            <input type="checkbox" checked class="is-clickable" />  
            <span class="itens-comprados is-size-5">${elemento.valor}</span>
        </div>
        <div>
            <i class="fa-solid fa-trash is-clickable deletar"></i>
        </div>
    </li>      
      `;
    } else {
      ulItems.innerHTML += `
        <li class="item-compra is-flex is-justify-content-space-between" data-value="${index}">
            <div>
                <input type="checkbox" class="is-clickable" />
                <input type="text" class="is-size-5" 
                value="${elemento.valor}" 
                ${index !== Number(itemAEditar) ? "disabled" : ""}></input>
            </div>

            <div>
              ${
                index === Number(itemAEditar)
                  ? '<button onclick="salvarEdicao()"><i class="fa-regular fa-floppy-disk is-clickable"></i></button>'
                  : '<i class="fa-regular is-clickable fa-pen-to-square editar"></i>'
              }
              <i class="fa-solid fa-trash is-clickable deletar"></i>
            </div>
        </li>    
        `;
    }
  });

  const inputsCheck = document.querySelectorAll('input[type="checkbox"]');
  inputsCheck.forEach((i) => {
    i.addEventListener("click", (evento) => {
      const valorDoElemento =
        evento.target.parentElement.parentElement.getAttribute("data-value");
      listaDeItems[valorDoElemento].checar = evento.target.checked;
      mostrarItem();
    });
  });

  const deletarObjetos = document.querySelectorAll(".deletar");

  deletarObjetos.forEach((i) => {
    i.addEventListener("click", (evento) => {
      const valorDoElemento =
        evento.target.parentElement.parentElement.getAttribute("data-value");
      listaDeItems.splice(valorDoElemento, 1);
      mostrarItem();
    });
  });

  const editarItens = document.querySelectorAll(".editar");

  editarItens.forEach((i) => {
    i.addEventListener("click", (evento) => {
      itemAEditar =
        evento.target.parentElement.parentElement.getAttribute("data-value");
      mostrarItem();
    });
  });

  atualizaLocalStorage();
}

function salvarEdicao() {
  const itemEditado = document.querySelector(
    `[data-value="${itemAEditar}"] input[type="text"]`
  );
  listaDeItems[itemAEditar].valor = itemEditado.value;
  console.log(listaDeItems);

  itemAEditar = -1;
  mostrarItem();
}
