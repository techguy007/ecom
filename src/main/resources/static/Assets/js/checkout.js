// Get the subtotal from the URL query parameter
const urlParams = new URLSearchParams(window.location.search);
const subtotal = parseFloat(urlParams.get('subtotal'));

// Update the total amount elements with the subtotal
const subtotalAmountElement = document.getElementById('subtotal-checkout');
subtotalAmountElement.textContent = `$ ${subtotal}`;

// Calculate and display taxes (assuming 10% tax rate)
const taxRate = 0.1;
const taxAmount = subtotal * taxRate;

// Calculate and display grand total (subtotal + taxes)
const totalAmountElement = document.getElementById('total-checkout');
totalAmountElement.textContent = `$ ${taxAmount + subtotal}`;

// Rest of the code remains the same...

fetch("/username")
  .then(response => response.text())
  .then(username => {
    const usernameInput = document.getElementById("username");
    usernameInput.value = username;
  })
  .catch(error => console.error(error));

fetch('/get-email')
  .then(response => response.text())
  .then(email => {
    const emailInput = document.getElementById("email");
    emailInput.value = email;
  })
  .catch(error => console.error(error));

let customerId;

fetch('/get-customer-id')
  .then(response => response.text())
  .then(id => {
    customerId = id;
  })

const cart = JSON.parse(sessionStorage.getItem("cart"));
console.log(cart);

const container = document.querySelector(".nn");

cart.forEach(product => {
  const productCard = document.createElement("div");
  productCard.classList.add("proo");

  productCard.innerHTML = `
    <div class="dess">
      <a href="single-product.html?id=${product.productId}">
        <span>${product.description.substring(0, 22)}</span>
        <h5>Quantity: <span>${product.quantity}</span></h5>
        <h5>Size: <span>${product.size}</span></h5>
        <h4>Price: ${product.price} $</h4>
      </a>
    </div>
    <img src="data:image/png;base64,${product.imageUrl}" style=" width: 80px; height: 100px; display: block;" alt="${product.name}">
  `;

  container.appendChild(productCard);
});

// Add a click event listener to the "Place Order" button
const placeOrderButton = document.getElementById('placeOrderButton');
placeOrderButton.addEventListener('click', function(event) {
  event.preventDefault(); // Prevent the form from being submitted

  const orderData = {
    orderDate: new Date().toISOString().split('T')[0],
    totalAmount: taxAmount + subtotal,
    address: document.getElementById('address').value,
    additionalInformation: document.getElementById('additionalInformation').value,
    paymentMethod: document.querySelector('input[name="payment"]:checked').value, // Get selected payment method
    customerId: parseInt(customerId),
    customerEmail: document.getElementById('email').value,
    customerName: document.getElementById('username').value,
    orderItems: cart.map(item => ({
      productId: parseInt(item.productId),
      quantity: item.quantity
    }))
  };

  console.log(JSON.stringify(orderData));

  // Send POST request to the server to save the order
  fetch('/place-order', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(orderData)
  })
  .then(response => {
    if (response.ok) {
      console.log('Order placed successfully');
      alert('Order placed successfully');
      sessionStorage.removeItem("cart");
      window.location.href = "/index.html";
      // Optionally, you can redirect the user to a success page or display a success message
    } else {
      console.error('Error placing order');
      // Optionally, you can display an error message to the user
    }
  })
  .catch(error => {
    console.error('Error placing order:', error);
    // Optionally, you can display an error message to the user
  });
});