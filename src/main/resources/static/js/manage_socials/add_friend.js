$(document).ready(function() {
    $("#tblHeader").hide();
    $("#search").click(function (e) {
        e.preventDefault();
        $("#usersTbl").empty();
        $.ajax({
            method: 'GET',
            url: "http://3.1.222.99:9999/api/friends/find_users",
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
    var tableData = "";
    data.forEach(function (user) {
        tableData += '<tr>' +
            '<td><img id="thumbnail" style="width: 100px" src="/images/' + user.userId + '/' + user.profilePic + '"/>' + '</td>' +
            '<td>' + user.name + '</td>' +
            '<td>' + user.username + '</td>' +
            '<td id="action_space">' +
            '<button id="add_friend' + user.userId + '" class="btn btn-primary" value="' + user.username + '" data-toggle="modal" data-target="#confirmModal">Add Friend</button>' +
            '</td>' +
            '</tr>';
    });

    $("#TblId>tbody").html(tableData);

    data.forEach(function(user) {
        $("#add_friend" + user.userId).click(function(e) {
            var name = user.name;
            var username = user.username;
            $("#to_msg").html("To: " + name);
            $("#yes_btn").attr("href", "/socials/friend/add/" + username);
        })
    })
}


