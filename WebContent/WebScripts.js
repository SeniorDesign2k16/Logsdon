/*Notes
When sending to database, can either use curl command in java (preferred) and convert xml to json
OR send each individual piece to the database
match id
get all pieces of Json
get ether present or just a binary yes or no to depict red/green squares. Should be in order, else we need to order it properly
XML
job: job id
{
  gene:
  Organism
  Accuracy
  Present: (1,0 or yes/no. 1,0 is faster)

}
Need to check
IP issue with web hosting
dynamic table in html/css
in HTML code

<script src="https://www.gstatic.com/firebasejs/3.6.1/firebase.js"></script>
<script>
  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyCvLxY6fjWaS2BNaYjsVlFoj-368w8V-1s",
    authDomain: "thegenespot-efb8a.firebaseapp.com",
    databaseURL: "https://thegenespot-efb8a.firebaseio.com",
    storageBucket: "thegenespot-efb8a.appspot.com",
    messagingSenderId: "139442074898"
  };
  firebase.initializeApp(config);
</script>*/
var seqString;
function initialize()
{
  //alert("Hey were connected lmao");

	var gene = document.getElementById("gene_name").value;
	var sequences =document.getElementById("fasta_text_area1").value;

	var i =3;
	var j=3;
	//alert(i+"i");
	//alert(j+"j");
	while(document.getElementById("gene_name"+i))
	{
		gene= gene +"%"+document.getElementById("gene_name"+i).value;
		i=i+2;
		//alert(i+"i");
		sequences=sequences+"%"+document.getElementById("fasta_text_area"+(j)).value;
		j=j+2;
		//alert(j+"j");
	}


	
	//[JobName, sequences,evalue,kingdom, subtype, (need genome), assembly level]
	//var input = ["job","gene","17234",".00001","Animilia","fishes","GCA001.1","IDC"];
  	var input=[document.getElementById("job_name").value,gene, sequences,document.getElementById('evalue').value,document.getElementById("select-kingdom").value,"Fish","GCA_000180675.1",document.getElementById("select-assembly-level").value];  //repeat for how many
  callGeneSpotService(input);
}

function callGeneSpotService(inputArray)
{

    var location =window.location.href+"analysis/GeneSpot"
    //Results should be the encrypted job id to be able to access the results on the web page.
    var results = $.post(location,{inputArray:inputArray},function(results){
    })
    .done(function(results)
  {
    alert(results);
	  //alert("Now Processing, please check back in the results section with your job id in a few moments: "+ uuid);
  })
  .fail(function(results)
  {
    alert("Something went wrong please contact the systems administrator for help");
  })
}


function readSingleFile(e) {
	  var file = e.target.files[0];
	  if (!file) {
	    return;
	  }
	  var reader = new FileReader();
	  reader.onload = function(e) {
	    var contents = e.target.result;
	    displayContents(contents);
	  };
	  reader.readAsText(file);
	}

	function displayContents(contents) {
	    
	  console.log("-------Sequences in file-------");
	    
	  var element = contents;

	  var hold = [];
	  var ids = "";
	  var lines = [];
	    
	  var buffer = true;    
	  var seqeunces = [];
	  var sequence_hold = "";    
	    
	  //if data is fasta formatted file (probably needs more test than this to determine if it is a valid file)
	  if(contents[0] == '>'){
	      
	      lines = contents.split('\n');
	      
	      i = 0
	    
	      //going through file
	      while(i < lines.length){
	          
	          //fasta header
	          if(lines[i][0] == '>'){
	            
	              //ignoring the first line
	              if(buffer == false){
	                    
	                  seqeunces.push(sequence_hold);
	                
	                  console.log(sequence_hold + '\n');
	                  sequence_hold = "";
	              }
	            
	              ids+=(lines[i].split(' ')[0].split('>')[1])+'\n';
	          }
	          
	          //ignoring empty spaces
	          else if(lines[i] != ""){
	              
	              buffer = false;
	              
	              sequence_hold+=lines[i].split('\n')[0];
	              
	          }

	          i++;
	      }
	      
	      console.log(sequence_hold + '\n');
	      
	      seqeunces.push(sequence_hold);
	      
	      
	      //do blastp sequences[0] vs sequence[1 + n] (A=B AND B=C, then A=C)
	      i = 1;
	      while(i < seqeunces.length){
	          
	          alignment(seqeunces[0], seqeunces[i]);

	          i++;
	      }

	      console.log(ids);
	      element.innerHTML = ids;   
	  }
	   seqString=sequences.toString();
	}

	window.onload = function(){
	    
	    var el = document.getElementById("file-input")
	    
	    if(el){
	        addEventListener("change", readSingleFile, false);
	    }
	}


	//https://github.com/knyga/Needleman-Wunsch-sequence-alignment-js/blob/master/sequence-alignment.js
	function alignment(sequence1, sequence2){
	    
	    console.log("Aligning Sequences")
	    
	    
	    var s1 = sequence1;
	    var s2 = sequence2;
	    
	    
	    var sp = 1;
	    var gp = -1;
	    var gc = "-";

	    //generate grid array
	    var arr = [];

	    for(var i=0;i<=s2.length;i++) {
	        
	        arr[i] = [];
	        
	        for(var j=0;j<=s1.length;j++) {
	            arr[i][j] = null;
	        }
	    }

	    arr[0][0] = 0;

	    for(var i=1;i<=s2.length;i++) {
	        arr[0][i] = arr[i][0] = -1 * i;
	    }

	    for(var i=1;i<=s2.length;i++) {
	        for(var j=1;j<=s1.length;j++) {
	            arr[i][j] = Math.max(
	                arr[i-1][j-1] + (s2[i-1] === s1[j-1] ? sp : gp),
	                arr[i-1][j] + gp,
	                arr[i][j-1] + gp
	            );
	        }
	    }

	    var as1 = "";
	    var as2 = "";

	    var i = s2.length;
	    var j = s1.length;
	    var sq1 = [];
	    var sq2 = [];

	    do {

	        var t = arr[i-1][j];
	        var d = arr[i-1][j-1];
	        var l = arr[i][j-1];
	        var max = Math.max(t, d, l);

	        switch(max) {
	            case t:
	                i--;
	                sq1.push(gc);
	                sq2.push(s2[i]);
	                break;
	            case d:
	                j--;
	                i--;
	                sq1.push(s1[j]);
	                sq2.push(s2[i]);
	                break;
	            case l:
	                j--;
	                sq1.push(s1[j]);
	                sq2.push(gc);
	                break;
	        }

	    } while(i>0 && j>0);

	    
	    hold1 = sq1.reverse();
	    hold2 = sq2.reverse();
	    
	    sequence1 = "";
	    sequence2 = "";
	    
	    for(i = 0; i < hold1.length; i++){
	        
	        sequence1+=hold1[i];
	    }
	    
	    for(i = 0; i < hold2.length; i++){
	        
	        sequence2+=hold2[i]; 
	    }
	    
	    console.log(sequence1);
	    console.log(sequence2);
	    
	}
