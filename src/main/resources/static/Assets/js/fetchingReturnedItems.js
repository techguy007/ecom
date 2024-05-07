document.addEventListener("DOMContentLoaded", function () {
    fetch('/get-returned-items')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.returned-items tbody');
            data.forEach(returnedItemDTO => {
                const row = `
                    <tr>
                        <td>${returnedItemDTO.orderId}</td>
                        <td>${returnedItemDTO.customerEmail}</td>
                        <td>${returnedItemDTO.productDescription}</td>
                        <td>${returnedItemDTO.productPrice}</td>
                        <td>${returnedItemDTO.quantity}</td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error:', error));
});