function showTextBox() {
  // Get the checkbox
  var inappCheckBox = document.getElementById("inappropriate");
  var noIssuesCheckBox = document.getElementById("noIssues");
  // Get the output text
  var textBox = document.getElementById("textBox");
  var textArea = document.getElementById("textArea");
  var topError = document.getElementById("topError");
  var textBoxError = document.getElementById("textBoxError");

  // If the checkbox is checked, display the output text
  if (inappCheckBox.checked == true){
    textBox.style.display = "block";
    textArea.value = null;
    textArea.wrap = "hard";
  } 
  else if (noIssuesCheckBox.checked == true) {
    textBox.style.display = "none";
    textArea.value = "no comments";
    topError.style.display = "none";
    textBoxError.style.display = "none";
  }
}

/*function reportCount(){
	//Get the report
	var reportcount = document.getElementById("reportcount");
	
}*/