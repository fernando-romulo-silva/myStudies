import { products } from './products';
import { renderProducts } from './rendering';

function addProduct(event) {
    event.preventDefault();
    import('./product-management.js').then(module => {
        module.addProduct(event);
    });
}
function deleteProduct(productId) {
    import('./product-management.js').then(module => {
        module.deleteProduct(productId);
    });
}

function initProducts() {
    renderProducts(products, deleteProduct);
}

const addProductForm = document.getElementById('new-product');

initProducts();

addProductForm.addEventListener('submit', addProduct);
