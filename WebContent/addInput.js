var counter = 1;
var limit = 24;
var i =0;
var empty=false;
function addInput(divName){
     if (document.getElementById(divName).childElementCount == limit)  {
          alert("You have reached the limit of adding " + (document.getElementById(divName).childElementCount-14) + " inputs");
     }
     else {
    	 var text= document.getElementsByName("FASTA Gene Sequences");
    	 var length= text.length;
    	 while(i<length)
    		 {
    		 	if(text[i].value=="")
    		 		{
    		 			empty=true;
    		 		}
    		 	i++;
    		 }
    	 if(empty)
    		 {
    		 alert("Please enter your queries before you continue");
    		 }
    	 else{
    		var newdiv = document.createElement('div');
          newdiv.innerHTML = "<p> -------------------------------------------------------------------------------------------------------- </p> \n <p></p> \n Gene Name: <input type='text' name='Gene Name' id='gene_name" +(+1)+"' required> \n <input type='file' id='file-input' onchange='readSingleFile(event);'/> \n <h3>Contents of the file:</h3> \n <textarea readonly name='FASTA Gene Sequences' id='fasta_text_area"+(counter+1)+"' cols='70' rows='5' required></textarea> \n <p></p> \n <input type='radio' name='seq_button' id='radio1"+(counter+1)+"' value='first_checkbox'> All Sequences Found on NCBI Database<br> \n <input type='radio' name='seq_button' id='radio2"+(counter+1)+"' value='second_checkbox'> Personal Sequences Uploaded <br> \n <p></p>";
          document.getElementById(divName).appendChild(newdiv);
          counter++;
         
          }
    	 i=0;
    	 empty=false;
    	 	
     }
}