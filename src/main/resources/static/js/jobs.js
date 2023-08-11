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

function performSearch(filter) {
    if (filter.length >= 2) {
        fetch(`/jobs/filter?filter=${filter}`)
            .then(response => response.text()) // Fetch the HTML content of the job cards
            .then(data => {
                // Create a temporary container to hold the extracted content
                const tempContainer = document.createElement('div');
                tempContainer.innerHTML = data;

                // Extract the specific job card section (adjust the selector as needed)
                const jobCardSection = tempContainer.querySelector('.jobs-container');

                // Update your job container with the extracted content
                const jobContainer = document.querySelector('.jobs-container');
                jobContainer.innerHTML = jobCardSection.innerHTML;

                // Highlight the keywords in the job cards
                const keywordRegex = new RegExp(`(${filter})`, 'gi'); // Create a regex to match keywords
                const jobCards = jobContainer.querySelectorAll('.job-card'); // Select all job cards

                jobCards.forEach(card => {
                    const textNodes = getTextNodes(card); // Custom function to get text nodes
                    textNodes.forEach(node => {
                        const text = node.textContent;
                        const highlightedText = text.replace(keywordRegex, '<span class="highlight">$1</span>');
                        const span = document.createElement('span');
                        span.innerHTML = highlightedText;
                        node.parentNode.replaceChild(span, node); // Replace the original text node with the highlighted version
                    });
                });
            });
    }
}

// Custom function to get text nodes recursively
function getTextNodes(node) {
    const textNodes = [];
    if (node.nodeType === Node.TEXT_NODE) {
        textNodes.push(node);
    } else {
        const children = node.childNodes;
        for (const child of children) {
            textNodes.push(...getTextNodes(child));
        }
    }
    return textNodes;
}




