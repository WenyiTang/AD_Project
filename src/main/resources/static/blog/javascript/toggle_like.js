// Find all thumb logos
let thumbLogos = document.querySelectorAll(".thumb-logo");

// Add event listener to all thumb logos
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

  // if entry is not liked by user
  if (imgSrc == "/blog/images/thumb-logo-no-fill.svg") {
    fetch(
      "http://localhost:8080/api/likes/like?" +
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
      thumbLogo.setAttribute("src", "/blog/images/thumb-logo-blue-fill.svg")
    );
  }
  // if entry is liked by user
  else {
    fetch(
      "http://localhost:8080/api/likes/unlike?" +
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
      thumbLogo.setAttribute("src", "/blog/images/thumb-logo-no-fill.svg")
    );
  }
}
