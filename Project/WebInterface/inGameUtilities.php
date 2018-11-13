<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('./SoapInterface.php');
/**
 * Created by PhpStorm.
 * User: willo
 * Date: 13/11/2018
 * Time: 14:54
 */

class inGameUtilities
{
    private $config;
    private $interface;
    public function __construct()
    {
        $this->config = include('config.php');
        $this->interface = require_once('./SoapInterface.php');
    }

    function getUserID(){
        return $_SESSION[$this->config['cookieUserId']];
    }

    function getUsername()
    {
        return $_SESSION[$this->config['cookieUsername']];
    }

    function getGameID()
    {
        return $_SESSION[$this->config['gameID']];
    }

    function getBoard(){
        $this->interface = new SoapInterface();
        $board = $this->interface->getBoard(getGameID());
        return $board;
    }
}

$interface = new SoapInterface();

if (isset($_POST['takeSquare']) AND isset($_POST['x']) AND isset($_POST['y'])) {
    $utilities = new inGameUtilities();
    $guid = $utilities->getGameID();
    $x = $_POST['x'];
    $y = $_POST['y'];
    $pID =  $utilities->getUserID();
    echo $interface->takeSquare($guid,$x,$y,$pID);
}


if (isset($_POST['getBoard'])) {
    $utilities = new inGameUtilities();
    echo $interface->getBoard($utilities->getGameID());
}