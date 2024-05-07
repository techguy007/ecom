fetch('/username')
  .then(response => response.text())
    .then(data => {
      const lgElement = document.getElementById('lg');
    if (data === "NO TOKEN") {
      // User is not logged in, display login icon
        lgElement.innerHTML = '<a href="login.html"><i class="fa-solid fa-sign-in-alt"></i></a>';
    } else {
      // User is logged in, display logout icon and account details
      lgElement.innerHTML = '<a href="#" onclick="logout()"><i class="fa-solid fa-sign-out-alt"></i></a>'
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });

function logout() {
  fetch('/logout', {
    method: 'GET'
  })
    .then(response => response.text())
    .then(data => {
      console.log(data);
      // Redirect to home page or perform other actions on successful logout
      window.location.href = "/index.html";
      // sessionStorage.clear();
    })
    .catch(error => {
      console.log("Logout failed: " + error);
    });
  
  
}