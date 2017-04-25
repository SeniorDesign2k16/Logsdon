/** AJAX Library*/
// Global variables to keep track to the request
// and the function to call when done
var ajaxreq=false, ajaxCallback;

// ajaxRequest: sets up a request
function ajaxRequest(filename){

    try{
        // Firefox/IE7/Others
        ajaxreq = new XMLHttpRequest();
    } catch(error){
        try{
            // IE5/IE6
            ajaxreq = new ActiveXObject("Microsoft.XMLHTTP");
        } catch(error){
            return false;
        }
    }
    ajaxreq.open("GET", filename);
    ajaxreq.onreadystatechange = ajaxResponse;
    ajaxreq.send(null);
}

// ajaxResponse: waits for response and calls a function
function ajaxResponse(){
    if(ajaxreq.readyState != 4) return;
    if(ajaxreq.status==200){
        // If the request succeeded
        if(ajaxCallback) ajaxCallback();
    } else alert("Request failed: "+ ajaxreq.statusText);
    return true;
}