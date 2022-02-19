$(document).ready(function() {
	getAllFriends();
	$("#query").on("input", function (e) {
		e.preventDefault();
		$("#friendsTbl").empty();
		$.ajax({
			method: 'GET',
			url: "http://3.1.222.99:9999/api/friends/find",
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
			url: "http://3.1.222.99:9999/api/friends/find",
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
		url: "http://3.1.222.99:9999/api/friends/all",
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
						<td>
							<img id="thumbnail" style="width: 100px"  
							src="/images/${data[i].userId}/${data[i].profilePic}"/>
						</td>
						<td>${data[i].name}</td>
						<td>${data[i].username}</td>
						<td>
							<a id="manage" href="/socials/friend/${data[i].username}" class="btn btn-secondary">Manage</a>
						</td>
					</tr>`
		table.innerHTML += row
	}
}

function getSearchResult(e) {
	e.preventDefault();
	$.ajax({
		method: 'GET',
		url: "http://3.1.222.99:9999/api/friends/find",
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