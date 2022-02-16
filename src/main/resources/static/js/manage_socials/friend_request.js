$(document).ready(function() {
    $("#sent").click(function (e) {
        e.preventDefault();
        $("#requestsTbl").empty();
        document.getElementById("req_display").innerHTML = "Sent Requests";
        $.ajax({
            method: 'GET',
            url: "http://localhost:8080/api/friends/requests",
            data: {
                username: $("#username").val(),
                sent: "true"
            },
            success: function(response) {
                console.log(response);
                buildTable_sent(response)
            }
        });
    });
    $("#received").click (function (e, msg) {
        e.preventDefault();
        $("#requestsTbl").empty();
        document.getElementById("req_display").innerHTML = "Received Requests";
        $.ajax({
            method: 'GET',
            url: "http://localhost:8080/api/friends/requests",
            data: {
                username: $("#username").val(),
                sent: "false"
            },
            success: function(response) {
                console.log(response);
                buildTable_received(response)
            }
        });
    });
});

function buildTable_sent(data) {
    var table = document.getElementById("requestsTbl")

    for (var i = 0; i < data.length; i++) {
        var row = `<tr>
						<td><img id="thumbnail" style="width: 100px" 
								src="/images/${data[i].userId}/${data[i].profilePic}"/></td>
						<td>${data[i].username}</td>
						<td>${data[i].name}</td>
						<td id="action_space">
							 <button id="delete_req" class="btn btn-danger" value="${data[i].username}">Delete Request</button>
						</td>
					</tr>`
        table.innerHTML += row

        $('#delete_req').click(function(e) {
            e.preventDefault();
            $.ajax({
                method: 'GET',
                url: "http://localhost:8080/api/friends/request/process",
                data: {
                    username: $("#username").val(),
                    sender: $("#delete_req").val(),
                    action: "delete"
                },
                success: function(response) {
                    console.log(response);
                    // var obj = JSON.parse(response);
                    // var msg = obj['message'];
                    document.getElementById("sent").click();
                }
            });
        });
    }
}

function buildTable_received(data) {
    var table = document.getElementById("requestsTbl")

    for (var i = 0; i < data.length; i++) {
        var row = `<tr>
						<td><img id="thumbnail" style="width: 100px" 
								src="/images/${data[i].userId}/${data[i].profilePic}"/></td>
						<td id="sender_username">${data[i].username}</td>
						<td>${data[i].name}</td>
						<td>
						    <div id="action_space">
						       <button id="accept_req" class="btn btn-success" value="${data[i].username}">Accept</button>
							   <button id="reject_req" class="btn btn-danger" value="${data[i].username}">Reject</button>
                            </div>
						</td>
					</tr>`
        table.innerHTML += row

        $('#accept_req').click(function(e) {
            e.preventDefault();
            $.ajax({
                method: 'GET',
                url: "http://localhost:8080/api/friends/request/process",
                data: {
                    username: $("#username").val(),
                    sender: $("#accept_req").val(),
                    action: "accept"
                },
                success: function(response) {
                    console.log(response);
                    // var obj = JSON.parse(response);
                    // var msg = obj['message'];
                    document.getElementById("received").click();
                }
            });
        });
        $('#reject_req').click(function(e) {
            e.preventDefault();
            $.ajax({
                method: 'GET',
                url: "http://localhost:8080/api/friends/request/process",
                data: {
                    username: $("#username").val(),
                    sender: $("#reject_req").val(),
                    action: "reject"
                },
                success: function(response) {
                    console.log(response);
                    // var obj = JSON.parse(response);
                    // var msg = obj['message'];
                    // document.getElementById("message").innerHTML = "<span class='text-danger'>msg</span>";
                    document.getElementById("received").click();
                }
            });
        });
    }
}

