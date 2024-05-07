// Fetch categories from the backend
fetch('/get-all-categories')
    .then(response => response.json())
    .then(categories => {
    console.log(categories);
    const categorySelect = document.getElementById('category');
    // Clear existing options
    categorySelect.innerHTML = '';
    // Add default option
    const defaultOption = document.createElement('option');
    defaultOption.value = 'default';
    defaultOption.text = 'Category';
    defaultOption.disabled = true;
    defaultOption.hidden = true;
    categorySelect.appendChild(defaultOption);
    // Add categories to select
    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.text = category.name;
        categorySelect.appendChild(option);
    });
})
.catch(error => console.error('Error fetching categories:', error));

// Fetch brands from the backend
fetch('/get-distinct-brands')
    .then(response => response.json())
    .then(brands => {
        const brandSelect = document.getElementById('brand');
        // Clear existing options
        brandSelect.innerHTML = '';
        // Add default option
        const defaultOption = document.createElement('option');
        defaultOption.value = 'default';
        defaultOption.text = 'Brand';
        defaultOption.disabled = true;
        defaultOption.hidden = true;
        brandSelect.appendChild(defaultOption);
        // Add brands to select
        brands.forEach(brand => {
            const option = document.createElement('option');
            option.value = brand;
            option.text = brand;
            brandSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Error fetching brands:', error));