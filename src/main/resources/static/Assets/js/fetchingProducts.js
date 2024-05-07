function displayProducts(products) {
    const productContainer = document.querySelector('.pro-container');
    productContainer.innerHTML = ''; // Clear previous products

    products.forEach(product => {
        // Create HTML elements to display product details
        const productDiv = document.createElement('div');
        productDiv.classList.add('pro');
        // Set product details
        productDiv.innerHTML = `
            <a style="text-decoration: none;" href="single-product.html?id=${product.id}">
                <img src="data:image/png;base64,${product.imageUrl}" alt="Product Image">
                <div class="des">
                    <span>${product.brand}</span>
                    <h5>${product.description}</h5>
                    <div class="star">
                        ${Array(product.rating).fill('<i class="fas fa-star"></i>').join('')}
                    </div>
                    <h4>$${product.price}</h4>
                </div>
                <a><i class="fa-solid fa-heart"></i></a>
            </a>`;
        // Append productDiv to productContainer
        productContainer.appendChild(productDiv);

        const heartIcon = productDiv.querySelector('.fa-heart');
        heartIcon.addEventListener('click', () => {
            addToWishlist(product);
        })
    });
}

// Fetch products
function fetchProducts(url) {
    fetch(url)
        .then(response => response.json())
        .then(products => {
            displayProducts(products);
        })
        .catch(error => console.error('Error fetching products:', error));
}

function addToWishlist(product) {
    // Check if the user is logged in
    if (!isLoggedIn()) {
        alert('Please log in to add items to your wishlist.');
        return;
    }

    // Add the product to the wishlist (you'll need to implement the logic for this)
    // For example, you could store the wishlist items in local storage or send the data to a server
    const wishlist = getWishlistFromSession();

    const exist = wishlist.some(item => item.id === product.id);

    if (exist) {
        alert('Product already in your wishlist!');
        return;
    }

    
    wishlist.push(product);
    saveWishlistToSession(wishlist);

    alert('Product added to your wishlist!');
}

function isLoggedIn() {
    return fetch('/username')
      .then(response => response.text())
      .then(data => {
        if (data === 'NO TOKEN') {
          return false;
        } else {
          return true;
        }
      })
      .catch(error => {
        console.error('Error checking login status:', error);
        return false;
      });
}
  
function getWishlistFromSession() {
    const wishlistJson = sessionStorage.getItem('wishlist');
    return wishlistJson ? JSON.parse(wishlistJson) : [];
}
  
function saveWishlistToSession(wishlist) {
    const wishlistJson = JSON.stringify(wishlist);
    sessionStorage.setItem('wishlist', wishlistJson);
}
  

// Export the fetchProducts function
export { fetchProducts };
