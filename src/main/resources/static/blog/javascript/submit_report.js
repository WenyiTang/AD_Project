function submitReport() {
    let otherReasonRadioBtn = document.querySelector("#other-reason");

    if(otherReasonRadioBtn.checked) {
        //Set value of other-reason radio button to value of text input
        let otherReasonTextInput = document.querySelector("#other-reason-text")
        otherReasonRadioBtn.setAttribute("value",otherReasonTextInput.value);
    }
    
    let flagForm = document.querySelector("#flag-form");

    let userId = flagForm.getAttribute("data-userId");

    let mealEntryId = flagForm.getAttribute("data-mealEntryId");

    // console.log("userId = " + userId);
    // console.log("mealEntryId = " + mealEntryId);

    // console.log("flagform value = " + flagForm.reason.value);

    fetch (
        "http://localhost:8080/api/report/submit?" +
        new URLSearchParams({
            userId: userId,
            mealEntryId: mealEntryId,
            reason: flagForm.reason.value
        }), {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        }
    ).then(
        report => {
            window.location.href = "http://localhost:8080/blog/view/entry/" + mealEntryId;

            // to view Report persisted to database, uncomment line below
            console.log(report.json());
        }
    );

    
}