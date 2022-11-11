
const productList = {
    products: [
        {
            title: 'A Pillow',
            imageUrl: 'https://www.ikea.com/global/assets/navigation/images/memory-foam-foam-pillows-20536.jpeg?imwidth=400',
            price: 19.99,
            description: 'A soft pillow'
        },    
        {
            title: 'A Carpet',
            imageUrl: 'https://cdn.laredoute.com/products/4/9/2/4928a5725dd8a0f0cfaaeae0f29e1454.jpg?imgopt=twic&twic=v1/cover=400x400',
            price: 89.99,
            description: 'A carppet which you might like - or not'
        }    
    ],

    render() {
        const renderHook = document.getElementById('app');
        const prodList = document.createElement('ul');       

        for (const prod of this.products) {
            const prodEl = document.createElement('li');
            prodEl.className = 'product-item';
            prodEl.innerHTML = `
                <div>
                    <img src="${prod.imageUrl}" alt="${prod.title}" />
                    <div class="product-item__content">
                        <h2>${prod.title}</h2>
                        <h3>${prod.price}</h3>
                        <p>${prod.description}</p>
                    </div>
                    <button>Add to Cart</button>
                </div>
            `;
            prodList.append(prodEl);
        }
        renderHook.append(prodList);
    }    
};

productList.render();