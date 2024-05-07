document.addEventListener('DOMContentLoaded', function () {
    // Set default values for select fields
    setDefaultFilterValue('brand', 'Brand'); // Replace 'Brand Default' with your desired default value
    setDefaultFilterValue('category', 'Category'); // Replace 'Category Default' with your desired default value
    setDefaultFilterValue('category', 'Category'); // Replace 'Category Default' with your desired default value
    setDefaultFilterValue('size', 'Size'); // Replace 'Category Default' with your desired default value
    setDefaultFilterValue('color', 'Color'); // Replace 'Category Default' with your desired default value
    setDefaultFilterValue('sort', 'Sort By'); // Replace 'Category Default' with your desired default value

    // Add more filter fields and default values as needed

    function setDefaultFilterValue(filterId, defaultValue) {
        const filterSelect = document.getElementById(filterId);
        if (filterSelect) {
            const defaultOption = document.createElement('option');
            defaultOption.value = ''; // Set the value attribute as needed
            defaultOption.text = defaultValue;
            defaultOption.selected = true; // Mark the option as selected
            filterSelect.add(defaultOption, 0);
        }
    }
});