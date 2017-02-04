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
  var element = document.getElementById('fasta_text_area');

  var hold = [];
  var ids = "";
  var lines = [];
    
  //if data is fasta formatted file (probably needs more test than this to determine if it is a valid file)
  if(contents[0] == '>'){
      
      lines = contents.split('\n');
      
      i = 0
      while(i < lines.length){
          
          if(lines[i][0] == '>'){
            
              //console.log(lines[i].split(' ')[0].split('>')[1]);
              ids+=(lines[i].split(' ')[0].split('>')[1])+'\n'
              //hold.concat(lines[i].split(' ')[0].split('>')[1]);

          }
          
          i++;
      }
      console.log(ids);
      element.innerHTML = ids;   
  }
}

window.onload = function(){
    
    var el = document.getElementById("file_button")
    
    if(el){
        addEventListener("change", readSingleFile, false);
    }
}