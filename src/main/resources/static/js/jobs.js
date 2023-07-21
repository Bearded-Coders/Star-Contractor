function clearFilters() {
    const form = document.getElementById('filterForm');
    const inputs = form.getElementsByTagName('input');

    for (let i = 0; i < inputs.length; i++) {
        const input = inputs[i];
        if (input.type === 'checkbox') {
            input.checked = false;
        }
    }

    form.submit();
}