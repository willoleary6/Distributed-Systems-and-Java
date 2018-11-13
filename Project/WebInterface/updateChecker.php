<?php
require_once('./SoapInterface.php');
$interface = new SoapInterface();
if (isset($_POST['gameID']) && isset($_POST['currentBoard'])) {
    $gameID = $_POST['gameID'];
    $currentBoard = $_POST['currentBoard'];

    $newBoard = $interface->getBoard($gameID);
    if($currentBoard == $newBoard){
        echo 0;
    }else{
        echo 1;
    }
}