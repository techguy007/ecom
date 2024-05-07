const wishlist = JSON.parse(sessionStorage.getItem('wishlist')) || [];

if (wishlist.length === 0) {
    // If wishlist is empty, display the fav section
    document.getElementById('fav').style.display = 'block';
} else {
    // If wishlist is not empty, hide the fav section
    document.getElementById('fav').style.display = 'none';
}

function displayWishlist(wishlist) {
    const productContainer = document.querySelector('.proo-container');
    productContainer.innerHTML = ''; // Clear previous products
  
    wishlist.forEach(product => {
      const productDiv = document.createElement('div');
      productDiv.classList.add('proo');
  
      const productHTML = `
        <a href="single-product.html?id=${product.id}">
          <img src="data:image/jpeg;base64,${product.imageUrl}" alt="">
          <div class="dess">
            <span>${product.brand}</span>
            <h5>${product.description}</h5>
            ${Array(product.rating).fill('<i class="fas fa-star"></i>').join('')}
            <h4>$ ${product.price}</h4>
          </div>
        </a>
        <a href="#" onclick="removeFromWishlist('${product.id}')"><i class="fa-solid fa-trash-can"></i></a>
      `;
  
      productDiv.innerHTML = productHTML;
      productContainer.appendChild(productDiv);
    });
}

window.addEventListener('DOMContentLoaded', () => {
    const wishlist = getWishlistFromSession();
    displayWishlist(wishlist);
});

function getWishlistFromSession() {
  const wishlistJson = sessionStorage.getItem('wishlist');
    const wishlist = wishlistJson ? JSON.parse(wishlistJson) : [];
    console.log('wishlist', wishlist);
    return wishlist;
    
}

function removeFromWishlist(id) {
    console.log(parseInt(id));
    const wishlist = getWishlistFromSession();
    const updatedWishlist = wishlist.filter(product => product.id !== parseInt(id));
    console.log(updatedWishlist);
    sessionStorage.setItem('wishlist', JSON.stringify(updatedWishlist));
    // console.log(updatedWishlist);
    displayWishlist(updatedWishlist);
    // alert('Product removed from your wishlist!');
}