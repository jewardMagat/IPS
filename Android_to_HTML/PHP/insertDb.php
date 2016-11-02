<?php
    $con=mysql_connect('localhost', 'root','');
    mysql_select_db("newdatabase", $con);
    
    $name=$_POST['name'];
    $age=$_POST['age'];
    $email=$_POST['email'];

    //query for inserting data 
//   mysql_query("insert into newTable(name, age, email) values('{$name}', '{$age}','{$email}')") 

    //query for updating the data if it exists and inserting a new one if not.
    mysql_query("insert into newTable(name, age, email) values('{$name}', '{$age}','{$email}')ON DUPLICATE KEY UPDATE
    name='{$name}', age='{$age}', email='{$email}'") 
?>









