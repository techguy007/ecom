function placeOrder() {
    // Fetch the customer ID based on the username
    fetch('/get-customer-id')
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Failed to fetch customer ID');
            }
        })
        .then(customerId => {
            // Prepare the order data
            const orderData = {
                orderDate: new Date().toISOString(),
                totalAmount: taxAmount + subtotal,
                address: document.getElementById('address').value,
                additionalInformation: document.getElementById('additionalInformation').value,
                paymentMethod: document.getElementById('paymentMethod').value,
                customer: {
                    id: parseInt(customerId)
                },
                orderItems: cart.map(item => ({
                    product: {
                        id: item.productId
                    },
                    quantity: item.quantity
                }))
            };

            // Make an AJAX request to the server to save the order
            fetch('/place-order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(orderData)
            })
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error('Failed to place order');
                    }
                })
                .then(responseText => {
                    // Order placed successfully
                    console.log(responseText);
                    // Optionally, you can redirect to a success page or display a success message
                })
                .catch(error => {
                    console.error('Error occurred while placing order:', error);
                });
        })
        .catch(error => {
            console.error('Error occurred while fetching customer ID:', error);
        });
}
