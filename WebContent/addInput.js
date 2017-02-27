var counter = 1;
var limit = 10;
function addInput(divName){
     if (counter == limit)  {
          alert("You have reached the limit of adding " + counter + " inputs");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "<p> -------------------------------------------------------------------------------------------------------- </p> \n <p></p> \n Gene Name: <input type='text' name='Gene Name' id='gene_name" +(counter+1)+"' required> \n <input type='file' id='file-input' /> \n <h3>Contents of the file:</h3> \n <textarea readonly name='FASTA Gene Sequences' id='fasta_text_area"+(counter+1)+"' cols='70' rows='5' required></textarea> \n <p></p> \n <label><input type='checkbox' id='cbox1"+(counter+1)+"' value='first_checkbox'> All Sequences Found on NCBI Database </label><br> \n <label><input type='checkbox' id='cbox2"+(counter+1)+"' value='second_checkbox'> Personal Sequences Uploaded </label><br> \n <p></p>";
          document.getElementById(divName).appendChild(newdiv);
          counter++;
     }
}