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
            html += '<a class="btn btn-md btn-default tag" href="/gallery/p/1/tag/' + tag + '"><i class="fa fa-tag"></i>' + tag + '</a>';
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


/* LIKES */
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
                html += '<a class="btn btn-md btn-default tag" href="/user/profile/' + u + '">' + u + '</a>';
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
                html += ' <a href="/user/profile/' + u + '">' + u + '</a>';
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
            var likeButton = document.getElementById('likeReview' + reviewID + 'Button');
            if (likeButton != null) {
                likeButton.addEventListener('click', function () {
                    likeReview(reviewID);
                });
            }
            var unlikeButton = document.getElementById('unlikeReview' + reviewID + 'Button');
            if (unlikeButton != null) {
                unlikeButton.addEventListener('click', function () {
                    unlikeReview(reviewID);
                });
            }
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


/* RATING */

// Chose an start event
function chooseStart(rating) {

    // Visual
    var i;
    for (i = 1; i <= rating; i++) {
        $('#star-' + i).addClass('star-selected');
    }
    for (i = rating + 1; i <= 5; i++) {
        $('#star-' + i).removeClass('star-selected');
    }

    // Form
    $('#rating').val(rating);

}

// Bind events to starts
$('#star-1').click(function () {
    chooseStart(1);
});
$('#star-2').click(function () {
    chooseStart(2);
});
$('#star-3').click(function () {
    chooseStart(3);
});
$('#star-4').click(function () {
    chooseStart(4);
});
$('#star-5').click(function () {
    chooseStart(5);
});

// By default, choose 1 star
chooseStart(1);


/* GALLERY */
function changeImage(url) {
    document.getElementById('gallery-main').src = url;
}