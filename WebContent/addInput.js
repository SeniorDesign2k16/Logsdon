var counter = 1;
var limit = 10;
function addInput(divName){
     if (counter == limit)  {
          alert("You have reached the limit of adding " + counter + " inputs");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "<p> -------------------------------------------------------------------------------------------------------- </p> \n <p></p> \n Gene Name: <input type='text' name='Gene Name' id='gene_name" +(counter+1)+"' required> \n <input type='file' id='file-input' /> \n <h3>Contents of the file:</h3> \n <textarea readonly name='FASTA Gene Sequences' id='fasta_text_area"+(counter+1)+"' cols='70' rows='5' required></textarea> \n <p></p> \n <input type='radio' name='seq_button' id='radio1"+(counter+1)+"' value='first_checkbox'> All Sequences Found on NCBI Database<br> \n <input type='radio' name='seq_button' id='radio2"+(counter+1)+"' value='second_checkbox'> Personal Sequences Uploaded <br> \n <p></p> <input type='button' value='Delete Gene' onClick='deleteInput(counter+1);>'<p></p> ";
          document.getElementById(divName).appendChild(newdiv);
          counter++;
     }
}