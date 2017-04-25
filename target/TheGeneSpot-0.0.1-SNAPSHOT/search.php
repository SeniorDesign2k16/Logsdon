<?php
    header("Content-type: text/xml");
    $names = array("1408658", "4896", "559292","280463","3702","3847","3880","112509");

    if(!$query) $query=$_GET['query'];
    echo "<?xml version=\"1.0\" ?>\n";
    echo "<names>\n";
    while (list($k,$v)=each($names)){
        if(stristr($v,$query))
            echo "<name>$v</name>\n";
    }
    echo "</names>\n";
?>