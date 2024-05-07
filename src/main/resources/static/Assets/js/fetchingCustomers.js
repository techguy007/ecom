document.addEventListener("DOMContentLoaded", function () {
    fetch('/get-new-customers')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.users tbody');
            data.forEach(customerDTO => {
                console.log(customerDTO);
                const row = `
                    <tr>
                        <td>${customerDTO.id}</td>
                        <td>${customerDTO.name}</td>
                        <td>${customerDTO.email}</td>
                        <td>${customerDTO.phone}</td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error:', error));
});


