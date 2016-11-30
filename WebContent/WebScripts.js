/*Notes
When sending to database, can either use curl command in java (prefereed) and convert xml to json
OR send each individual piece to the database
match id
get all peices of Json
get ether perent or just a binary yes or no to depect red/green squares. Should be in order, else we need to order it properly
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
function initialize()
{
  alert("Hey were connected lmao");
  /*
  var check = getElementsByClassName('box');
  var organism=' ';
  for(var i = 0; i<check.length; i++)
  {
    if(check.checked)
    {
      organism=organism+check[i]+' ';
    }
  }
  var input=[document.getElementById("job_name_text_area"), document.getElementById("fasta_text_area"),organism,document.getElementById('word_length'),document.getElementById('evalue')];  //repeat for how many
  callGeneSpotService(input);*/

}
function callGeneSpotService(input)
{
  //Connect to Java
    var location = window.location.href+"/location/of/java/from/base"
    //Results should be the encrypted job id to be able to access the results on the web page.
    var results = $.post(location,{input:inputArray})
    .done(function(uuid)
  {
    alert("Now Processing, please check back in the results section with your job id in a few moments: "+ uuid);
  })
  .fail(function(response)
  {
    alert("Something went wrong please contact the systems administrator for help");
  })

}
//If we split it up then can just replace name with call firebase and use it to get results
