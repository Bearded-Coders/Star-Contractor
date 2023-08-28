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


function deleteJob(id) {
    const url = "/jobs/delete/" + id;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            console.log(response)
            showAlert(); // Show the alert
        })
        .catch(error => {
            // Handle errors and display an error message if needed
            alert('Error: ' + error);
        });
}


let isCompleteJobFormVisible = false;

// JavaScript to show the modal
function showCompleteJobForm() {
    var modal = document.getElementById("completeJobModal");
    modal.style.display = "block";
    isCompleteJobFormVisible = true;
}

// JavaScript to close the modal
function closeCompleteJobForm() {
    var modal = document.getElementById("completeJobModal");
    modal.style.display = "none";
    isCompleteJobFormVisible = false;
}

// Incomplete Alert
const incompleteAlert = () => {
    closeCompleteJobForm();
    isCompleteJobFormVisible = false;
    customAlert(
        'Are you sure?',
        'Please make sure you are finished with your job before marking it as complete!',
        'Got it!',
        function () {
            console.log('User clicked OK!');
            if (isCompleteJobFormVisible) {
                showCompleteJobForm(); // Show the completeJobForm again if it was visible
            }
        }
    );
}

// Validate job has been completed
const validateForm = () => {
    let statusInput = document.getElementById("status");

    // Perform validation checks
    if (statusInput.value.trim() === "Active") {
        incompleteAlert();
        return false; // Prevent form submission
    } else {
        showCompleteAlertWithDelay();
        return false;
    }
}

// Successful completion alert with a delay
const showCompleteAlertWithDelay = () => {
    customAlert(
        'Congrats!!',
        'Job has been marked complete!',
        'Got it',
        function () {
            console.log('User clicked OK!');
            document.getElementById("completeJobForm").submit();
        }
    );

    // Schedule the form submission after a delay (e.g., 4 seconds)
    setTimeout(() => {
        document.getElementById("completeJobForm").submit();
    }, 4000);
}




