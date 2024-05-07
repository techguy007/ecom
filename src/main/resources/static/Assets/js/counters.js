document.addEventListener("DOMContentLoaded", function () {
    fetch('/count-users')
        .then(response => response.json())
        .then(data => {
            // Access the total_users div and assign the response data to its innerText
            document.getElementById('total_users').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors if necessary
        });
});

document.addEventListener("DOMContentLoaded", function () {
    fetch('/count-products')
        .then(response => response.json())
        .then(data => {
            // Access the total_users div and assign the response data to its innerText
            document.getElementById('total_products').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors if necessary
        });
});

document.addEventListener("DOMContentLoaded", function () {
    fetch('/count-orders')
        .then(response => response.json())
        .then(data => {
            // Access the total_users div and assign the response data to its innerText
            document.getElementById('total_orders').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors if necessary
        });
});

document.addEventListener("DOMContentLoaded", function () {
    fetch('/count-earnings')
        .then(response => response.json())
        .then(data => {
            // Access the total_users div and assign the response data to its innerText
            document.getElementById('total_earnings').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors if necessary
        });
});