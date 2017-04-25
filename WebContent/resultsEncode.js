/**
 * Included as part of TheGeneSpot bioinformatic pipeline/web application
 *
 * resultsEncode
 * functions for encoding html strings
 *
 * @author: CMS
 * source:  http://stackoverflow.com/questions/1219860/html-encoding-lost-when-attribute-read-from-input-field
 */

function htmlEncode(value){
    //create a in-memory div, set it's inner text(which jQuery automatically encodes)
    //then grab the encoded contents back out.  The div never exists on the page.
    return $('<div/>').text(value).html();
}

function htmlDecode(value){
    return $('<div/>').html(value).text();
}
