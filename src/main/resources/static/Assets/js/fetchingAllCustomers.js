document.addEventListener("DOMContentLoaded", function () {
    fetch('/get-customers')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.users tbody');
            data.forEach(customerDTO => {
                const row = `
                    <tr data-id="${customerDTO.id}">
                        <td>${customerDTO.id}</td>
                        <td>${customerDTO.name}</td>
                        <td>${customerDTO.email}</td>
                        <td>${customerDTO.phone}</td>
                        <td>
                            <i style="cursor: pointer;" class="fas fa-edit" id="editButton_${customerDTO.id}"></i>
                        </td>
                        <td>
                            <i style="cursor: pointer;" class="fas fa-trash" id="deleteButton_${customerDTO.id}"></i>
                        </td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
            initializeEventHandlers();
        })
        .catch(error => console.error('Error:', error));
});

let editingUserId = null;

function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}

function openEditModal(id) {
    console.log(id);
    const rowData = document.querySelector(`.users tbody tr[data-id="${id}"]`).children;
    document.getElementById("editModal").style.display = "block";
    editingUserId = id;
    document.getElementById("UserId").value = id;
    document.getElementById("Name").value = rowData[1].textContent;
    document.getElementById("Email").value = rowData[2].textContent;
    document.getElementById("Phone").value = rowData[3].textContent;
}

document.addEventListener("DOMContentLoaded", function () {
    function updateCustomer() {
        const name = document.getElementById("Name").value;
        const email = document.getElementById("Email").value;
        const phone = document.getElementById("Phone").value;
    
        const updatedData = { name: name, email: email, phone: phone };
    
        fetch(`/update-customer/${editingUserId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedData),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update customer');
            }
            return response.json(); // Parse the JSON response
        })
        .then(updatedCustomer => {
            const rowData = document.querySelector(`.users tbody tr[data-id="${editingUserId}"]`).children;
            rowData[1].textContent = updatedCustomer.name;
            rowData[2].textContent = updatedCustomer.email;
            rowData[3].textContent = updatedCustomer.phone;
            
            closeEditModal();
            // Show success message
            alert(`Customer with id ${editingUserId} updated successfully`);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    // Add event listener to the form submission
    document.getElementById("editForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent form submission
        updateCustomer(); // Call the updateCustomer function
    });
});


// Function to delete a customer
function deleteCustomer(id) {
    if (confirm("Are you sure you want to delete this customer?")) {
        fetch(`/delete-customer/${id}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete customer');
            }
            document.querySelector(`.users tbody tr[data-id="${id}"]`).remove();
            alert(`Customer with id ${id} deleted successfully`);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
}

function initializeEventHandlers() {
    document.querySelectorAll('.fa-edit').forEach(editButton => {
        editButton.addEventListener('click', function(event) {
            const id = this.id.split('_')[1]; //
            openEditModal(id);
        });
    });

    document.querySelectorAll('.fa-trash').forEach(deleteButton => {
        deleteButton.addEventListener('click', function(event) {
            const id = this.id.split('_')[1];
            deleteCustomer(id);
        });
    });
}
