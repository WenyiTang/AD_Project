$(document).ready(function() {
	getAllFriends(); 
}); 


function getAllFriends() {	
	$.ajax({
		method: 'GET', 
		url: "http://localhost:8080/api/friends/all",
		data: {
			username: $("#username").val()
		}, 
		success: function(response) {
			console.log(response);
			buildTable(response)
		}
	}); 
}


function buildTable(data) {
	var table = document.getElementById("friendsTbl")
	
	var filePath = "AD_Project/"
	
	for (var i = 0; i < data.length; i++) {
		var row = `<tr>
						<td><img id="thumbnail" class="img-fluid rounded mx-auto- d-block w-25 
								src="/AD_Project/${data[i].profilePicPath}"/></td>
						<td>${data[i].name}</td>
						<td><a href="/socials/friend/${data[i].username}" class="btn btn-primary">Manage</a></td>
					</tr>`
		table.innerHTML += row
	}
}