function deleteInput(counter){
    var deleteDiv = document.getElementByID("gene_name"+(2));
    deleteDiv.parentNode.removeChild(deleteDiv);
    //delete deleteDiv;
    /*deleteDiv = */document.getElementByID("fasta_text_area"+(counter+1)).remove();
    //delete deleteDiv;
    /*deleteDiv = */document.getElementByID("radio1"+(counter+1)).remove();
    //delete deleteDiv;
    /*deleteDiv = */document.getElementByID("radio2"+(counter+1)).remove();
    //delete deleteDiv;
}