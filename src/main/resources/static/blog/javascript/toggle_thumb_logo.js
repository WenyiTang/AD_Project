

// Find all thumb logos
let thumbLogos = document.querySelectorAll(".thumb-logo");

// Add event listener to all thumb logos
let idCount = 0;
thumbLogos.forEach(thumbLogo => {
    thumbLogo.setAttribute("id","thumb-logo-" + idCount.toString());
    idCount++;

    thumbLogo.setAttribute("onClick","toggleThumbLogo("+`\'${thumbLogo.getAttribute("id")}\'`+")")

}); 



function toggleThumbLogo(id) {

    let thumbLogo = document.querySelector("#" + id);

    let imgSrc = thumbLogo.getAttribute("src");

    if (imgSrc == "/blog/images/thumb-logo-no-fill.svg") {
        thumbLogo.setAttribute("src", "/blog/images/thumb-logo-blue-fill.svg");
    }
    else {
        thumbLogo.setAttribute("src", "/blog/images/thumb-logo-no-fill.svg");
    }

}