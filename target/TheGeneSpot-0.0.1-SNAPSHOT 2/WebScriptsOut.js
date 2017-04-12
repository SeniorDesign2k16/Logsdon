/*// Initialize Firebase
<script src="https://www.gstatic.com/firebasejs/3.6.1/firebase.js"></script>

var config = {
	apiKey: "AIzaSyCvLxY6fjWaS2BNaYjsVlFoj-368w8V-1s",
	authDomain: "thegenespot-efb8a.firebaseapp.com",
	databaseURL: "https://thegenespot-efb8a.firebaseio.com",
	storageBucket: "thegenespot-efb8a.appspot.com",
	messagingSenderId: "139442074898"
};
firebase.initializeApp(config);
// 	End Initialize Firebase*/

//Reference firebase database
var db = firebase.database();

//Attach listener at job node
var results = firebase.database().ref(jobId);
results.on('value', function(snapshot) {
  updateResults(jobId, snapshot.val());
});

