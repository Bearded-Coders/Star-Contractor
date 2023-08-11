// Select the image gallery container
const gallery = document.querySelector('.image-gallery');

// Set the interval (in milliseconds) between image changes
const interval = 5000;

// Get the total number of images in the gallery
const totalImages = gallery.children.length;

// Initialize the current image index
let currentImageIndex = 0;

// Function to scroll to the next image
function scrollToNextImage() {
    // Calculate the scroll position based on the current image index
    const scrollPosition = gallery.clientWidth * currentImageIndex;

    // Scroll to the next image
    gallery.scroll({ left: scrollPosition, behavior: 'smooth' });

    // Increment the current image index
    currentImageIndex = (currentImageIndex + 1) % totalImages;
}

// Start the timer
setInterval(scrollToNextImage, interval);
