document.addEventListener("DOMContentLoaded", function () {
    fetch('/get-roles')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.roles tbody');
            data.forEach(customerDTO => {
                console.log(customerDTO);
                const row = `
                    <tr>
                        <td>${customerDTO.id}</td>
                        <td>${customerDTO.name}</td>
                        <td>${customerDTO.role}</td>
                        <td>
                            <button class="edit-button">
                                <i class="fas fa-pen"></i>
                            </button>
                        </td>
                    </tr>`;
                tableBody.innerHTML += row;
            });

            // Add event listener to dynamically created buttons
            const editButtons = document.querySelectorAll('.edit-button');
            editButtons.forEach(button => {
                button.addEventListener('click', confirmAndExecute);
            });
        })
        .catch(error => console.error('Error:', error));
});

function confirmAndExecute() {
    var confirmation = confirm("Are you sure you want to make him an Admin?");

    if (confirmation) {
        // Add your code to perform the desired action here
        alert("Action executed!");
    }
}
