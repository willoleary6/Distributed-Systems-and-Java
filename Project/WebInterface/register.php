<?php
 require_once('./SoapInterface.php');

 $interface = new SoapInterface();
 $username = $_POST["username"];
 $password = $_POST["password"];
 $forename = $_POST["forename"];
 $surname = $_POST["surname"];

 $registrationResults = $interface->register($username,$password,$forename,$surname);
 if(substr($registrationResults, 0, strlen("ERROR")) === "ERROR"){
     echo '<script> alert(\''.$registrationResults.'\');window.location=\'index.php\'</script>';
 }else{
     $cookie_name = "userID";
     $cookie_value = $registrationResults;
     setcookie($cookie_name, $cookie_value, time() + (86400 * 30), "/"); // 86400 = 1 day
     header( "Location: mainMenu.php" );
 }

