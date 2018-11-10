<?php
require_once('./SoapInterface.php');

$interface = new SoapInterface();
$username = $_POST["username"];
$password = $_POST["password"];


$loginResults = $interface->login($username,$password);
if($loginResults < 1){
    echo '<script> alert(\'Invalid credentials\');window.location=\'index.php\'</script>';
}else{
    $cookie_name = "userID";
    $cookie_value = $loginResults;
    setcookie($cookie_name, $cookie_value, time() + (86400 * 30), "/"); // 86400 = 1 day
    header( "Location: mainMenu.php" );
}

