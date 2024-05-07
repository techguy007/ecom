document.addEventListener("DOMContentLoaded", function() {
    // Retrieve data from sessionStorage
    var cartItems = JSON.parse(sessionStorage.getItem("cart"));

    // Select the table body where the cart items will be displayed
    var tableBody = document.querySelector("#cart tbody");

    // Select the element where the subtotal will be displayed
    var subtotalElement = document.getElementById("cart-subtotal");

    // Clear the table body initially
    tableBody.innerHTML = "";

    // Initialize subtotal variable
    var subtotal = 0;

    // Check if there are items in the cart
    if (cartItems && cartItems.length > 0) {
        // Loop through each item in the cart
        cartItems.forEach(function(item, index) {
            // Create a new table row
            var row = document.createElement("tr");

            // Create a delete button in the first column
            var deleteCell = document.createElement("td");
            var deleteIcon = document.createElement("i");
            deleteIcon.className = "fas fa-times"; // FontAwesome delete icon
            deleteIcon.style.cursor = "pointer";
            deleteIcon.addEventListener("click", function () {
                // Remove item from cartItems array
                cartItems.splice(index, 1);
                // Save updated cartItems to sessionStorage
                sessionStorage.setItem("cart", JSON.stringify(cartItems));
                // Remove row from the table
                tableBody.removeChild(row);
                // Recalculate subtotal
                subtotal -= item.price * item.quantity;
                // Update subtotal display
                subtotalElement.textContent = subtotal.toFixed(2);
                // Check if cart is empty and display message if so
                if (cartItems.length === 0) {
                    var emptyRow = document.createElement("tr");
                    var emptyCell = document.createElement("td");
                    emptyCell.colSpan = 7;
                    emptyCell.textContent = "Cart is Empty. ";

                    var lineBreak = document.createElement("br");
                    emptyCell.appendChild(lineBreak);
                    
                    // Create a link element
                    var shopLink = document.createElement("a");
                    shopLink.href = "shop.html";
                    shopLink.textContent = "Continue Shopping";
                    
                    // Append the link to the empty cell
                    emptyCell.appendChild(shopLink);
                    
                    emptyRow.appendChild(emptyCell);
                    tableBody.appendChild(emptyRow);
                }
            });
            deleteCell.appendChild(deleteIcon);
            row.appendChild(deleteCell);

            // Create table cells for each piece of item data
            var imageCell = document.createElement("td");
            var imageElement = document.createElement("img");
            imageElement.src = `data:image/png;base64,${item.imageUrl}`; // Set the image source
            imageElement.alt = "Product Image";
            imageElement.width = 50; // Set the image width (adjust as needed)
            imageCell.appendChild(imageElement); // Append the image to the cell

            var desCell = document.createElement("td");
            desCell.textContent = item.description.substring(0, 30) + "..."; // Set the description

            var sizeCell = document.createElement("td");
            sizeCell.textContent = item.size; // Set the size

            var priceCell = document.createElement("td");
            priceCell.textContent = item.price; // Set the price

            var quantityCell = document.createElement("td");
            quantityCell.textContent = item.quantity; // Set the quantity

            var subtotalCell = document.createElement("td");
            var itemSubtotal = item.price * item.quantity;
            subtotalCell.textContent = itemSubtotal; // Calculate and set subtotal
            subtotal += itemSubtotal; // Add to subtotal

            // Append all cells to the row
            row.appendChild(imageCell);
            row.appendChild(desCell);
            row.appendChild(sizeCell);
            row.appendChild(priceCell);
            row.appendChild(quantityCell);
            row.appendChild(subtotalCell);

            // Append the row to the table body
            tableBody.appendChild(row);
        });

        // Set the total subtotal
        subtotalElement.textContent = subtotal.toFixed(2);

        var tmp = document.getElementById("subtotal");
        var button = document.createElement("button");

        button.textContent = "Proceed To Checkout";
        button.className = "normal";
        button.addEventListener("click", checkout);

        tmp.appendChild(button);
    } else {
        // If cart is empty, display a message
        var emptyRow = document.createElement("tr");
        var emptyCell = document.createElement("td");
        emptyCell.colSpan = 7;
        emptyCell.textContent = "Cart is Empty. ";

        var lineBreak = document.createElement("br");
        emptyCell.appendChild(lineBreak);
        
        // Create a link element
        var shopLink = document.createElement("a");
        shopLink.href = "shop.html";
        shopLink.textContent = "Continue Shopping";
        
        // Append the link to the empty cell
        emptyCell.appendChild(shopLink);
        
        emptyRow.appendChild(emptyCell);
        tableBody.appendChild(emptyRow);

        // Set subtotal to 0 if cart is empty
        subtotalElement.textContent = "0.00";
    }
});

var discountedTotal;

function applyCoupon() {
    var couponInput = document.getElementById("coupon-input").value;
    var subtotalElement = document.getElementById("cart-subtotal");
    var subtotal = parseFloat(subtotalElement.textContent.replace("$", ""));
    var discountRate = 0;
    var couponButton = document.getElementById("coupon-btn");

    // Check if the coupon is valid and set the discount rate accordingly
    if (couponInput === "Abdelaziz20") {
        discountRate = 0.2; // 20% discount
        couponButton.disabled = true;
    } else {
        alert("Invalid coupon code");
        return;
    }

    // Calculate discounted total
    discountedTotal = subtotal * (1 - discountRate);

    // Update subtotal with the discounted total
    subtotalElement.textContent = discountedTotal.toFixed(2);

}

function checkout() {
    fetch('/username')
  .then(response => response.text())
    .then(data => {
      const lgElement = document.getElementById('lg');
    if (data === "NO TOKEN") {
        window.location.href = "login.html";
    } else {
        const subtotalValue = discountedTotal || document.querySelector("#cart-subtotal").textContent;
        window.location.href = `checkout.html?subtotal=${subtotalValue}`;
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });
}