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
    
  var element = document.getElementById('fasta_text_area');

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