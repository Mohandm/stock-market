/*!
 * Start Bootstrap - Grayscale Bootstrap Theme (http://startbootstrap.com)
 * Code licensed under the Apache License v2.0.
 * For details, see http://www.apache.org/licenses/LICENSE-2.0.
 */
var slide_once = 0;
// jQuery to collapse the navbar on scroll
$(window).scroll(function() {
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav-collapse");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav-collapse");
    }
});

// jQuery for page scrolling feature - requires jQuery Easing plugin
$(function() {
    $('a.page-scroll').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 600, 'easeInQuad');
        event.preventDefault();
    });
});

// Closes the Responsive Menu on Menu Item Click
$('.navbar-collapse ul li a').click(function() {
    $('.navbar-toggle:visible').click();
});

/*$.fn.waypoint.defaults = {
    context: window,
    continuous: true,
    enabled: true,
    horizontal: false,
    offset: 0,
    triggerOnce: true
};*/

$(document).ready(function() {
    if($('#flexHome').length){

        $('#flexHome').flexslider({
            animation: "slide",
            controlNav:true,
            directionNav:false,
            touch: true,
            direction: "vertical",
            slideshowSpeed : 3000
        });
    }


    $("body").floatingShare({
        place: "top-right",
        buttons: ["facebook","twitter","linkedin"],
        title : ' Misys - Stock Market League',
        description : '(A Game to Enthrall and Engage you on Stock Markets!).',
        url : 'http://www.misys.com/'
    });

    /*$('#about').waypoint(function() {
        $("#aboutContentLeft").effect( 'slide', {direction: "left" }, 500);
        $("#aboutContentRight").effect( 'slide', {direction: "Right" }, 500);
    });*/
});
