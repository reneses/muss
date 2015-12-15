// Object likes
function loadObjectLikes() {
    var url = "/api/objects/" + objectID + "/likes/";
    $.get(url, function (usernames) {
        var html = '';
        var userHasLiked = false;
        if (usernames != '') {
            usernames.forEach(function (u) {
                if (username === u)
                    userHasLiked = true;
                html += '<a class="btn btn-md btn-default tag" href="/profile/' + u + '">' + u + '</a>';
            });
        }
        if (userID > 0) {
            if (userHasLiked) {
                $('#likeObjectButton').addClass('no-displayed');
                $('#unlikeObjectButton').removeClass('no-displayed');
            }
            else {
                $('#unlikeObjectButton').addClass('no-displayed');
                $('#likeObjectButton').removeClass('no-displayed');
            }
        }
        $('#objectLikes').html(html);
    });
}

if (objectID >= 0)
	loadObjectLikes();

function likeObject() {
    var url = "/api/objects/" + objectID + "/likes";
    $.post(url, {'userID': userID, 'time': time, 'HMAC': HMAC}, function () {
        loadObjectLikes();
    });
}
function unlikeObject() {
    var url = "/api/objects/" + objectID + "/unlike";
    $.post(url, {'userID': userID, 'time': time, 'HMAC': HMAC}, loadObjectLikes);
}
$('#likeObjectButton').click(function () {
    likeObject();
});
$('#unlikeObjectButton').click(function () {
    unlikeObject();
});


// Like reviews
function loadReviewLikes(reviewID) {
    var url = "/api/reviews/" + reviewID + "/likes/";
    $.get(url, function (usernames) {
        var html = 'Liked by';
        var userHasLiked = false;
        if (usernames != '') {
            usernames.forEach(function (u) {
                if (username === u)
                    userHasLiked = true;
                html += ' <a href="/profile/' + u + '">' + u + '</a>';
            });
        }
        else {
            html += ' no one';
        }
        if (userID > 0) {
            if (userHasLiked) {
                $('#likeReview' + reviewID + 'Button').addClass('no-displayed');
                $('#unlikeReview' + reviewID + 'Button').removeClass('no-displayed');
            }
            else {
                $('#unlikeReview' + reviewID + 'Button').addClass('no-displayed');
                $('#likeReview' + reviewID + 'Button').removeClass('no-displayed');
            }
        }
        $('#review-' + reviewID + ' > footer > span').html(html);
    });
}
var areButtonsLoaded = false;
function loadReviewsLikes() {
    $('.review').each(function (i, review) {
        var reviewID = review.id.split("-")[1];
        loadReviewLikes(reviewID);
        if (!areButtonsLoaded) {
            document.getElementById('likeReview'+reviewID+'Button').addEventListener('click', function () {
                likeReview(reviewID);
            });
            document.getElementById('unlikeReview'+reviewID+'Button').addEventListener('click', function () {
                unlikeReview(reviewID);
            });
        }
    });
    if (!areButtonsLoaded)
        areButtonsLoaded = true;
}
loadReviewsLikes();

function likeReview(reviewID) {
    var url = "/api/reviews/" + reviewID + "/likes";
    $.post(url, {'userID': userID, 'time': time, 'HMAC': HMAC}, function () {
        loadReviewLikes(reviewID)
    });
}
function unlikeReview(reviewID) {
    var url = "/api/reviews/" + reviewID + "/unlike";
    $.post(url, {'userID': userID, 'time': time, 'HMAC': HMAC}, function () {
        loadReviewLikes(reviewID)
    });
}