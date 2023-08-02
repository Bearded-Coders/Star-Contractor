function customAlert(title, message, buttonText = 'OK', onOk = null) {
    const overlay = document.createElement('div');
    overlay.id = 'customAlertOverlay';

    const box = document.createElement('div');
    box.id = 'customAlertBox';

    const titleElem = document.createElement('h3');
    titleElem.id = 'customAlertTitle';
    titleElem.innerText = title;

    const messageElem = document.createElement('p');
    messageElem.id = 'customAlertMessage';
    messageElem.innerText = message;

    const okButton = document.createElement('button');
    okButton.id = 'customAlertOkButton';
    okButton.innerText = buttonText;
    okButton.onclick = function () {
        document.body.removeChild(overlay);
        if (onOk && typeof onOk === 'function') {
            onOk();
        }
    };

    box.appendChild(titleElem);
    box.appendChild(messageElem);
    box.appendChild(okButton);

    overlay.appendChild(box);
    document.body.appendChild(overlay);
}

function showAlert() {
    customAlert(
        'Removed',
        'Job Removed Successfully!',
        'Got it!',
        function () {
            console.log('User clicked OK!');
            redirectToJobs(); // Redirect after "Got it" is clicked
        }
    );

    // You can remove the setTimeout if you don't want to wait for the automatic redirection
    setTimeout(() => {
        redirectToJobs();
    }, 4000);
}

function redirectToJobs() {
    const url = "/jobs"; // Modify this URL to the desired redirect destination
    window.location.href = url;
}