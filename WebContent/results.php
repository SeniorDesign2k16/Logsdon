<?php
/**
 * GeneSpot - process and display results
 *
 * @author: Brendan Dudley
 * Date: 04-Apr-17
 */

//store job info from user
$jn = $_POST["JobName"];
?>

<html>
<head>
    <title>GeneSpot Results</title> <!--add job name? -->
    <!--<script src="results.js"></script>-->
    <!-- Plotly.js -->
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>

</head>

<body>

<h3>GeneSpot Results for: </h3>
<p>Job Name: <?php echo $_POST["JobName"]; ?><span id="jobname"></span></p>

<script> //insert Job Name into html
    //var jobName = <?php echo $jn; ?>;
    //document.getElementById("jobname").innerHTML() = jobName.value();
</script>
</p>
<div id="resultTable"><!-- Plotly chart will be drawn inside this DIV --></div>

<script>

    //reference database at job-parent node
    var db = firebase.database().ref(jobName);
    //retrieve results and store in JSON
    var jobstr = db.once('value').val();
    //parse job into Java object
    var jobob = JSON.parse(jobstr);

    //declare arrays to use for plotly data construction
    var kingdom = [];
    var species = [];
    var tax = [];
    var accession = [];
    var genes = [];
    var hitCount = [];
    var hitInfo = [];

    //old db structure
    /*
    for (i=0; i < numEntry; i++) {
        species.push(Object.keys(jobob[i]));
        tax.push(Object.keys(jobob[i][i]));
        accession.push(Object.keys(jobob[i][i][i]));
        gene.push(Object.keys(jobob[i][i][i][i]));
        //at cell level
        hitCount.push(jobob[i][i][i][i]["Hit Count"]);
        hitInfo.push(jobob[i][i][i][i]);
    }
    */

    // new access 12-Apr-17
    genes.push(Object.keys(jobob));
    var numCol = push.length;

    // new db structure (10-Apr-17)
    //extract data from job Object for plotly array construction
    /*
    for(g in jobob){
        genes.push(Object.keys())
    }
    */

    // TEST -- build data for plotly -- TEST
    var hitCount = [[2, 1],[7, 5]];
    var gene = ['Rad51', 'Dmc1'];
    var species = ['Mus', 'Fish'],

    //Ready data for Plotly
    var data = [
        {
            z: hitCount,
            x: gene,
            y: species,
            type: 'heatmap',
            showscale: true
        }
    ];


    var layout = {
        title: 'GeneSpot Results',
        font: {
            family: 'Arial',
            size: 18
        },
        annotations: [],
        xaxis: {
            ticks: '',
            side: 'top'
        },
        yaxis: {
            ticks: '',
            ticksuffix: ' '
        }
    };

    // draw resultTable
    Plotly.newPlot('resultTable', data, layout);
</script>

</body>
</html>
