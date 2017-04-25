function deleteInput(divName){
    var del = document.getElementById(divName);
    del.removeChild(del.lastChild);
}