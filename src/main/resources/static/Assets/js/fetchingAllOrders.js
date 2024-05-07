document.addEventListener("DOMContentLoaded", function () {
    // Fetch recent orders and populate the table
    fetch('/get-all-orders')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('.orders-table tbody');
            data.forEach(orderDTO => {
                console.log(orderDTO);
                const row = `
                    <tr>
                        <td>${orderDTO.id}</td>
                        <td>${orderDTO.customerPhone}</td>
                        <td>${orderDTO.orderDate}</td>
                        <td>${orderDTO.totalAmount}</td>
                        <td class="status-cell" data-order-id="${orderDTO.id}">${orderDTO.status}</td>
                        <td>${orderDTO.address}</td>
                        <td>
                            <i class="fas fa-edit" onclick="openEditModal(${orderDTO.id})"></i>
                        </td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
            // Add double click event listener to status cells
            const statusCells = document.querySelectorAll('.status-cell');
            statusCells.forEach(cell => {
                cell.addEventListener('dblclick', function () {
                    const orderId = this.getAttribute('data-order-id');
                    openEditModal(orderId);
                });
            });
        })
        .catch(error => console.error('Error:', error));
});

function openEditModal(id) {
    document.getElementById("editModal").style.display = "block";
    document.getElementById("OrderId").value = id;
}

function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}