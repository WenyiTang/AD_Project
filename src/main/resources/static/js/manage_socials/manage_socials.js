$(document).ready(function() {
	getAllFriends();
	$("#query").on("input", function (e) {
		e.preventDefault();
		$("#friendsTbl").empty();
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/api/friends/find",
			data: {
				username: $("#username").val(),
				query: $("#query").val()
			},
			success: function (response) {
				console.log(response);
				buildTable(response)
			}
		});
	});
	$("#search").click(function (e) {
		e.preventDefault();
		$("#friendsTbl").empty();
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/api/friends/find",
			data: {
				username: $("#username").val(),
				query: $("#query").val()
			},
			success: function(response) {
				console.log(response);
				buildTable(response)
			}
		});
	});
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

	for (var i = 0; i < data.length; i++) {
		var row = `<tr>
						<td><img id="thumbnail" class="img-fluid rounded mx-auto- d-block w-25 
								src="/AD_Project/${data[i].profilePicPath}"/></td>
						<td>${data[i].username}</td>
						<td>${data[i].name}</td>
						<td>
							<a id="manage" href="/socials/friend/${data[i].username}" class="btn btn-primary">Manage</a>
						</td>
					</tr>`
		table.innerHTML += row
	}
}

function getSearchResult(e) {
	e.preventDefault();
	$.ajax({
		method: 'GET',
		url: "http://localhost:8080/api/friends/find",
		data: {
			username: $("#username").val(),
			query: $("#query").val()
		},
		success: function(response) {
			console.log(response);
			buildTable(response)
		}
	});
}