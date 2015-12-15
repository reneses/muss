/* TAGS */

// Show tag form
function showTagForm() {
    $('#tagFormWrapper').removeClass('no-displayed');
    $('#tag').focus();
}

function hideTagForm() {
    $('#tagFormWrapper').addClass('no-displayed');
}

$('#showTagFormWrapper').click(function (evt) {
    evt.preventDefault();
    showTagForm();
    $(this).addClass('no-displayed');
});

// Reload the tags
function loadTags() {
    var url = "/api/objects/" + objectID + "/tags";
    $.get(url, function (tags) {
        var html = '';
        tags.forEach(function (tag) {
            html += '<a class="btn btn-md btn-default tag" href="/gallery/tag/' + tag + '"><i class="fa fa-tag"></i>' + tag + '</a>';
        });
        $('#tags').html(html);
    });
}

if (objectID >= 0)
	loadTags();

// Post a new tag
var tagInput = $('#tag');
function postTag() {
    var tag = tagInput.val();
    var url = "/api/objects/" + objectID + "/tags";
    $.post(url, {'tag': tag, 'userID': userID, 'time': time, 'HMAC': HMAC}, function () {
        loadTags();
        tagInput.val('');
    });
}
$('#tagForm').submit(function (evt) {
    evt.preventDefault();
    postTag();
});