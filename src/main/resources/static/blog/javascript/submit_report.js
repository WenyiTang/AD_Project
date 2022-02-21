function submitReport() {
    let otherReasonRadioBtn = document.querySelector("#other-reason");
    let otherReasonTextInput = document.querySelector("#other-reason-text");


    if(otherReasonRadioBtn.checked) {

        //Set value of other-reason radio button to value of text input
        
        otherReasonRadioBtn.setAttribute("value",otherReasonTextInput.value);
    }
    
    let flagForm = document.querySelector("#flag-form");

     // trim() removes trailing whitespaces
    let reason = flagForm.reason.value.trim();

    //Form validation
    // Cannot submit form without reason
    // Will also be triggered if user selects "Other", but leaves text input blank 
    // or if user only inputs whitespaces to text input.
    if(!reason) {
        let warningText = "Please select a reason";
        if(flagForm.firstChild.innerText === warningText) {
            // Don't add warning text if it is already there
            return;
        }
        
        let warningTextDiv = document.createElement("p");
        let warningTextNode = document.createTextNode(warningText);
        warningTextDiv.appendChild(warningTextNode);
        warningTextDiv.setAttribute("style","color:red;")

        flagForm.insertBefore(warningTextDiv,flagForm.firstChild);
        return;
    }

    let userId = flagForm.getAttribute("data-userId");

    let mealEntryId = flagForm.getAttribute("data-mealEntryId");

    fetch (
        "http://localhost:8080/api/report/submit?" +
        new URLSearchParams({
            userId: userId,
            mealEntryId: mealEntryId,
            reason: reason
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
            // console.log(report.json());
        }
    );

    
}