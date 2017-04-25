/**
 * resultsCookies.js    contains scripts for added cookie complexity if needed
 *
 * Modified by:     Brendan Dudley
 * Updated:         12-Apr-2017
 */


/*
 * src code for following `getCookie()` function found at:
 * <https://www.w3schools.com/js/js_cookies.asp>
 */

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}



// for more complex construction/validation/use of cookies if necessary in future:

/*function setExpCookie(cname, cvalue, exdays) {
 var d = new Date();
 d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
 var expires = "expires="+d.toUTCString();
 document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/resultsShow.html";
 //open resultsShow.html
 window.open("/TheGeneSpot/WebContent/resultsShow.html", "_self");
 }
 */