$(document).ready(function() {
    $("#loginForm").submit(function(event) {
        event.preventDefault(); // Prevent the default form submission

        $("#loginSubmit").prop("disabled", true).val("Logging in...");

        var url = "http://localhost:8080/login";
        
        // Create a JavaScript object with the form data
        var formData = {
            username: $("#loginEmail").val(),
            password: $("#loginPassword").val()
        };

        // Convert the JavaScript object to JSON string
        var jsonData = JSON.stringify(formData);

        // Send an AJAX POST request with JSON data
        $.ajax({
            url: url,
            type: "POST",
            contentType: "application/json", // Set content type to JSON
            data: jsonData,
            success: function(response) {
                console.log(response);
                
                // Save JWT token and authorities in sessionStorage
                //
                //
                //
                //
                sessionStorage.setItem('jwtToken', response.token);
                // sessionStorage.setItem('authorities', JSON.stringify(response.authorities));
                
                // Redirect to home page or perform other actions on successful login
                window.location.href = "/index.html";
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText);
                $("#loginError").text("Invalid email or password").show();
                // Display error message to the user
            },
            complete: function() {
                $("#loginSubmit").prop("disabled", false).val("Login");
            }
        });
    });
});
