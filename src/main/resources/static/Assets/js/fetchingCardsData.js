fetch('/cards-data')
  .then(response => response.json())
  .then(data => {
    // Update HTML content with fetched data
    document.querySelector('.number.total-users').innerText = data.totalUsers;
    document.querySelector('.number.total-products').innerText = data.totalProducts;
    document.querySelector('.number.total-orders').innerText = data.totalOrders;
    document.querySelector('.number.earnings').innerText = data.earnings;
  })
  .catch(error => console.error('Error fetching dashboard data:', error));
