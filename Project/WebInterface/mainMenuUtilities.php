<?php
/**
 * Created by PhpStorm.
 * User: willo
 * Date: 13/11/2018
 * Time: 16:54
 */
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('./SoapInterface.php');
class mainMenuUtilities
{
    private $config;
    private $interface;
    public function __construct()
    {
        $this->config = include('config.php');
        $this->interface = new SoapInterface();
    }

    function getUserID(){
        return $_SESSION[$this->config['cookieUserId']];
    }

    function getUsername(){
        return $_SESSION[$this->config['cookieUsername']];
    }

    function logout(){
        setcookie($this->config['cookieUserId'], null, -1, '/');
        setcookie($this->config['cookieUsername'], null, -1, '/');
        session_destroy ();
        header( "Location: index.php" );
    }

    function generateNewGame(){
        $userID = $this->getUserID();
        $gameID = $this->interface->newGame($userID);
        $_SESSION[$this->config['gameID']] = $gameID;
    }

    function enterGame(){
        $_SESSION[$this->config['gameID']] = $_POST['gameID'];
    }

    function joinGame(){
        $_SESSION[$this->config['gameID']] = $_POST['gameID'];
        $this->interface->joinGame( getUserID(), $_POST['gameID']);

    }
}
$utilities = new mainMenuUtilities();

if (isset($_POST['newGame'])) {
    $utilities->generateNewGame();
}

if (isset($_POST['enterGame']) AND isset($_POST['gameID'])) {
    $utilities->enterGame();
}

if (isset($_POST['joinGame']) AND isset($_POST['gameID'])) {
    $utilities->joinGame();
}