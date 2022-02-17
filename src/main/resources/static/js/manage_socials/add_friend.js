$(document).ready(function() {
    $("#tblHeader").hide();
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
    $("#tblHeader").show();
    var table = document.getElementById("usersTbl")

    for (var i = 0; i < data.length; i++) {
        var row = `<tr>
						<td>
						    <img id="thumbnail" style="width: 100px" 
						    src="/images/${data[i].userId}/${data[i].profilePic}"/>
						</td>
						<td>${data[i].name}</td>
						<td>${data[i].username}</td>
						<td>
							<a id="manage" href="/socials/friend/add/${data[i].username}" class="btn btn-primary">Add Friend</a>
						</td>
					</tr>`
        table.innerHTML += row
    }
}
