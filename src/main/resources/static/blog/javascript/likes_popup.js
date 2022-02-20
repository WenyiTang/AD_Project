// Find all thumb logos
let thumbLogo = document.querySelector(".thumb-logo");

let id = thumbLogo.getAttribute("data-mealentryid");


let btn = document.querySelector("#myBtn");
// console.log(btn);

let modal = document.querySelector("#myModal");



let modalContent = document.querySelector(".modal-content");


btn.onclick = function() {

    fetch(
        "http://localhost:8080/api/likes/get?" +
        new URLSearchParams({
            mealEntryId: id,
        }), {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        }

    )
    .then(res => res.json())
    .then(data => {
        let renderThis = ""
        renderThis += "<span class=\"close\">&times;</span>";
    
        renderThis += "<p>Liked by: </p>";
        
        renderThis += "<ul>";
        data.forEach(username => {
            renderThis += `<li>${username}</li>`
        });


        renderThis += "</ul>"
        modalContent.innerHTML = renderThis;
    
        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];
    
        span.onclick = function() {
            modal.style.display = "none";
        }
    
        window.onclick = function (event) {
            if(event.target == modal) {
                modal.style.display = "none";
            }
        }
    
        modal.style.display = "block";





    });
  


   
}



