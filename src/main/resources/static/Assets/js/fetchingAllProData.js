document.addEventListener("DOMContentLoaded", function () {
    fetch('/get-all-products')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.products tbody');
            data.forEach(customerDTO => {
                console.log(customerDTO);
                const row = `
                    <tr>
                        <td>${customerDTO.id}</td>
                        <td>${customerDTO.brand}</td>
                        <td>${customerDTO.description}</td>
                        <td>${customerDTO.price}</td>
                        <td>${customerDTO.rating}</td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error:', error));
});
