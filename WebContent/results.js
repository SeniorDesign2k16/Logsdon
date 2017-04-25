/**
 * Part of the bioinformatics web application, the GeneSpot
 *
 * Contains functions for storing user query and job information,
 * and parsing job info to prepare for results table construction/data visualization
 *
 * The Cookie `cJobName` is currently set to overwrite and always offer the last job viewed
 *
 * @author  Brendan Dudley
 *
 */

/*
 * different storage options, depending on use/frequency of access in the future:
 */

//store in local or session storage? (what about the job obj?)
//if so, add check for session first, will need enumeration for local?
//sessionStorage.setItem('JobName', currentJobName);


/**
 * setJobCookie()
 * takes user-input from resultsGet and stores locally as a cookie for resultsShow to access
 */
function setJobCookie() {
    var ujn = document.getElementById("ujobname").value;
    document.cookie = "cJobName=" + ujn + ";path=/";
}


/**
 * checkCookie()
 * loads with the body of 'resultsGet.html' to check for stored job information, potentially bypassing
 * extra keystrokes by the user. If previous job information is found, the user is presented with the option to proceed
 * to viewing these results, or may hit cancel to request info on a different job.
 *
 * basic logic from tutorialspoint.com/javascript/javascript_cookies.htm -- src code heavily modified
 */
function checkCookie() {
    var jc = getCookie("cJobName");
    if (jc != "") {
        if (confirm("'OK' to view results for: " + jc + "\n'Cancel' to request results for a different job."))
        {
            window.open("/TheGeneSpot/WebContent/resultsShow.html", "_self"); //or call JS to do the same
        } else {
            //for now, continue with resultsGet.html -- in the future:
                //provide option to erase job info from history
                //display list of saved job keys and allow user to choose at prompt
        }
    } else {
       //no alternative here, unless we encounter null/undefined bugs down the road
    }
}

/** readCookies() also adapted from a "readCookie()" function at:
 * https://www.tutorialspoint.com/javascript/javascript_cookies.htm
 *
 * Most likely, users will only have one job (and therefore only one cookie saving its name) at the tool's inception,
 * hence the logic in the `checkCookie()` function defaulting to the previously viewed.
 * Expecting multiple job cookies now will make saving/selecting multiple jobs much easier to implement in the future,
 * and should have a negligible impact on compute time.
 *
 */
function readCookies() {
    //store all cookies as single string to parse
    var allCookies = document.cookie;

    // Get all the cookie pairs in an array
    var cookiearray = allCookies.split(';');
    //declare an array to store Job Name(s)
    var userJobs = [];

    // Now take key value pair out of this array
    // Not interested in keys atm: all we're saving is Job Names -- may have to enumerate later cookie key later
    for(var i=0; i<cookiearray.length; i++){
        //name = cookiearray[i].split('=')[0];
        userJobs.push(cookiearray[i].split('=')[1]);
    }
}


// If going the local storage route:
/* Determine if user has session or local storage -- optimal for local storage of returned job info
 * Lines ??-?? taken from https://mathiasbynens.be/notes/localstorage-pattern
 */
// Feature detect + local reference
/*var storage = (function() {
    var uid = new Date;
    var storage;
    var result;
    try {
        (storage = window.localStorage).setItem(uid, uid);
        result = storage.getItem(uid) == uid;
        storage.removeItem(uid);
        return result && storage;
    } catch (exception) {}
}());
*/


/**
 * storageAvailable(type)
 * Checks to ensure local/session storage is both supported and available
 * source: https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API/Using_the_Web_Storage_API
 *
 * @param type
 * @returns {boolean}
 */
function storageAvailable(type) {
    try {
        var storage = window[type],
            x = '__storage_test__';
        storage.setItem(x, x);
        storage.removeItem(x);
        return true;
    }
    catch(e) {
        return false;
    }
}

/**
 * parse out hit info into java object with key-value pairs for all info
 * @constructor
 */
function HitInformation(info){
    this.hitCount = info.shift();
    this.hitSeq = [];
    for(i=0;i<info.length;i++) {
        this.hitSeq.push(new DetailedHitInformation(info[i])); //use DHI
    }
    //use average of max score of hits for heatmap?
}

/**
 *
 * @param input
 * @constructor
 */
function DetailedHitInformation(input){
    var hitsplit = input.split("|");
    this.sequence = hitsplit[0];
    this.max = hitsplit[1];
    this.scores = hitsplit[2];
}

/**
 * store keys from db as values
 * @constructor
 */
function CellInformation(tg, tk, tt, ti, ta, th) {
    this.geneName = tg;
    this.kingdom = tk;
    this.subtype = tt;
    this.taxid = ti;
    this.assembly = ta;
    this.hitInfo = th;
}


function cellBuild(jobString){
    var jobObject = JSON.parse(jobString);
}








/*
function printInfo(val){
    console.log(val);
}
*/