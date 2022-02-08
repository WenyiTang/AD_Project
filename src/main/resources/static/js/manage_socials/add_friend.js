$(document).ready(function() {
    $("#search").click(function (e) {
        e.preventDefault();
        $("#usersTbl").empty();
        $.ajax({
            method: 'GET',
            url: "http://localhost:8080/api/friends/find_users",
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

function buildTable(data) {
    var table = document.getElementById("usersTbl")

    for (var i = 0; i < data.length; i++) {
        var row = `<tr>
						<td><img id="thumbnail" class="img-fluid rounded mx-auto- d-block w-25 
								src="/AD_Project/${data[i].profilePicPath}"/></td>
						<td>${data[i].username}</td>
						<td>${data[i].name}</td>
						<td>
							<a id="manage" href="/socials/friend/${data[i].username}" class="btn btn-primary">Add Friend</a>
						</td>
					</tr>`
        table.innerHTML += row
    }
}
