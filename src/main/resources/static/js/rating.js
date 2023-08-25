function generateStarRating(avgRating) {
    let html = '';

    if (avgRating === 1) {
        html += `<p class="card-text"><i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i></p>`
    } else if (avgRating === 2) {
        html += `<p class="card-text"><i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i></p>`
    } else if (avgRating === 3) {
        html += `<p class="card-text"><i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-regular fa-star"></i> <i class="fa-regular fa-star"></i>`
    } else if (avgRating === 4) {
        html += `<p class="card-text"><i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-regular fa-star"></i>`
    } else if (avgRating === 5) {
        html += `<p class="card-text"><i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i> <i class="fa-solid fa-star fa-beat-fade" style="color: #fbff00;"></i>`
    }

    return html; // Return the generated HTML
}
