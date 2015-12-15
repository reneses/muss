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