function submitReport() {
    let otherReasonRadioBtn = document.querySelector("#other-reason");

    if(otherReasonRadioBtn.checked) {
        //Set value of other-reason radio button to value of text input
        let otherReasonTextInput = document.querySelector("#other-reason-text")
        otherReasonRadioBtn.setAttribute("value",otherReasonTextInput.value);
    }
    
    let flagForm = document.querySelector("#flag-form");

    // console.log("flagform value = " + flagForm.reason.value);

    // fetch (
    //     "http://localhost:8080/api/report/flag?" +
    //     new URLSearchParams({

    //     })
    // )

    
}