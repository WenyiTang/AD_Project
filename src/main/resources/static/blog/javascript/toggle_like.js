// Find all thumb logos
let thumbLogos = document.querySelectorAll(".thumb-logo");

// Add event listener to all thumb logos
// Can probably get thymeleaf to do this o.o
let idCount = 0;
thumbLogos.forEach((thumbLogo) => {
  thumbLogo.setAttribute("id", "thumb-logo-" + idCount.toString());
  idCount++;

  thumbLogo.setAttribute(
    "onClick",
    "toggleLike(" + `\'${thumbLogo.getAttribute("id")}\'` + ")"
  );
});

function toggleLike(id) {
  let thumbLogo = document.querySelector("#" + id);

  let imgSrc = thumbLogo.getAttribute("src");

  let mealEntryId = thumbLogo.getAttribute("data-mealEntryId");

  let userId = thumbLogo.getAttribute("data-userId");

  let totalLikesSpan = document.querySelector("#total-likes-mealentry-" + mealEntryId.toString());

  let totalLikes = totalLikesSpan.innerHTML;

  // if entry is not liked by user
  if (imgSrc == "/blog/images/thumb-logo-no-fill.svg") {
    fetch(
      "http://3.1.222.99:9999/api/likes/like?" +
        new URLSearchParams({
          userId: userId,
          mealEntryId: mealEntryId,
        }),

      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    ).then(
        // Change to blue only after http response status is ok
        () => {
            thumbLogo.setAttribute("src", "/blog/images/thumb-logo-blue-fill.svg");
            //Increment number of likes
            // Note that if another user also likes the page,
            // active user would need to refresh page to update number of likes shown
            totalLikes++;
            totalLikesSpan.innerHTML = totalLikes;
        }
    
    );
  }
  // if entry is liked by user
  else {
    fetch(
      "http://3.1.222.99:9999/api/likes/unlike?" +
        new URLSearchParams({
            userId: userId,
            mealEntryId: mealEntryId,
        }),
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    ).then(
      // Change to no-fill only after http response status is ok
      () => {
        thumbLogo.setAttribute("src", "/blog/images/thumb-logo-no-fill.svg")
        totalLikes--;
        totalLikesSpan.innerHTML = totalLikes;
      }
    );
  }


  
}
