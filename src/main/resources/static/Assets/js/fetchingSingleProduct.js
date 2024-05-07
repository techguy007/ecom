document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    // Function to fetch product data from the server
    function getProduct(productId) {
        fetch(`/get-product/${productId}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                // Once data is received, update the HTML elements with the product details
                updateProductDetails(data);
            })
            .catch(error => console.error('Error fetching product:', error));
    }

    // Function to update HTML elements with product details
    function updateProductDetails(productData) {
        // Get the HTML elements
        const productName = document.querySelector('.product-name');
        const productPrice = document.querySelector('.product-price');
        const productDescription = document.querySelector('.product-description');
        const productImage = document.querySelector('.single-pro-image img');
        // const smallImgGroup = document.querySelector('.small-img-group');

        // Update product name
        if (productName) {
            productName.textContent = productData.brand;
        }

        // Update product price
        if (productPrice) {
            productPrice.textContent = `$ ${productData.price}`;
        }

        // Update product description
        if (productDescription) {
            productDescription.textContent = productData.description;
        }

        // Update product image
        if (productImage) {
            productImage.src = `data:image/png;base64,${productData.imageUrl}`;
        }
    }

    // Call getProduct function with the product ID
    if (productId) {
        getProduct(productId);
    }

    // Add click event listener to the "Add To Cart" button
    const addToCartButton = document.querySelector('.normal');
    addToCartButton.addEventListener('click', function () {
        if (!productId) {
            console.error('Product ID is missing.');
            return;
        }

        fetch(`/get-product/${productId}`)
            .then(response => response.json())
            .then(data => {
                const productDetails = {
                    productId: productId,
                    price: data.price,
                    imageUrl: data.imageUrl,
                    description: data.description,
                    size: document.getElementById("size").value,
                    quantity: parseInt(document.getElementById("quantity").value)
                };

                var cartItems = JSON.parse(sessionStorage.getItem("cart")) || [];

                const existingItemIndex = cartItems.findIndex(
                    item => item.productId === productId && item.size === productDetails.size
                );
                
                if (existingItemIndex !== -1) {
                    cartItems[existingItemIndex].quantity += productDetails.quantity;
                } else {
                    cartItems.push(productDetails);
                }
                
                sessionStorage.setItem("cart", JSON.stringify(cartItems));
                window.location.href = "cart.html";
            })
            .catch(error => console.error('Error fetching product in session:', error));
    });
});
