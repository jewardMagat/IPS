<?php
    $num = 0;

    $conn=new mysqli('localhost', 'root','', 'newDatabase');
    if($conn->connect_error){
        die("Connection error!" .$conn->connect_error);
    }

    $result = $conn-> query("SELECT name,age FROM newTable" );
    $outputData = array();

    if($result -> num_rows>0){
        while($row = $result->fetch_assoc()){
            $num++;

            array_push($outputData, array(
                $num => $row['age']     
            ));
        }

        echo json_encode($outputData);
    }
?>