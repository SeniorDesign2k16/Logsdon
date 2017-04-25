//global variable to manage time out
var t;

// Start a timeout with each keypress
function StartSearch(){
    if(t) window.clearTimeout(t);
    t=window.setTimeout("LiveSearch()",200);
}

// Perform Search
function LiveSearch(){

    // Assemble the PHP filename
    query = document.getElementById("searchlive").value;
    filename = "search.php?query=" + query;

    // DisplayResults will handle the Ajax response
    ajaxCallback = DisplayResults;

    // Send Ajax request
    ajaxRequest(filename);

}

// Display search results
function DisplayResults(){

    // Remove old list
    ul = document.getElementById("list");
    div = document.getElementById("results");
    div.removeChild(ul);

    // Make a new list
    ul = document.createElement("UL");
    ul.id="list";
    names = ajaxreq.responseXML.getElementByTagName("name");

    for(i=0;i<names.length;i++){

        li = document.createElement("LI");
        name = names[i].firstChild.nodeValue;
        text = document.createTextNode(name);
        li.appendChild(text);
        ul.appendChild(li);

    }

    if(names.length==0){
        li = document.createElement("LI");
        li.appendChild(document.createTextNode("No Results"));
        ul.appendChild(li);
    }

    // Display the new list
    div.appendChild(ul);

}

// Set up event handler
obj=document.getElementById("searchlive");
obj.onkeydown = StartSearch;