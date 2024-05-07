fetch('/get_Role')
  .then(response => response.text())
  .then(data => {
    const lgElement = document.getElementById('mobile');
    const adminDashboard = document.getElementById('navbar');
    if (data === "ADMIN") {
      const adminLink = document.createElement('a');
      adminLink.href = 'admin.html';
      adminLink.innerHTML = '<i class="fa-solid fa-table-cells"></i>';
      lgElement.appendChild(adminLink);

      const dashboard = document.createElement('li');
      dashboard.innerHTML = '<a href="admin.html"><i class="fa-solid fa-table-cells"></i></a>';
      adminDashboard.appendChild(dashboard);
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });
