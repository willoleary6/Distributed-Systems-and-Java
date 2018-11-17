<?php
/**
 * Created by PhpStorm.
 * User: willo
 * Date: 17/11/2018
 * Time: 11:19
 */
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('./SoapInterface.php');
class Utilities
{
    private $config;
    private $interface;
    public function __construct()
    {
        $this->config = include('config.php');
        $this->interface = new SoapInterface();
    }

    function logout(){
        setcookie($this->config['cookieUserId'], null, -1, '/');
        setcookie($this->config['cookieUsername'], null, -1, '/');
        session_destroy ();
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

    function getUserID(){
        return $_SESSION[$this->config['cookieUserId']];
    }

    function getUsername(){
        return $_SESSION[$this->config['cookieUsername']];
    }

    function getGameID(){
        return $_SESSION[$this->config['gameID']];
    }

    function getBoard(){
        $board = $this->interface->getBoard($this->getGameID());
        return $board;
    }

    function takeSquare(){
        $guid = $this->getGameID();
        $x = $_POST['x'];
        $y = $_POST['y'];
        $pID =  $this->getUserID();
        return $this->interface->takeSquare($guid,$x,$y,$pID);
    }

    function getGameState($gameID){
        $gameState = $this->interface->getGameState($gameID);
        return $gameState;
    }

}

$utilities = new Utilities();
$interface = new SoapInterface();
if (isset($_POST['getBoard'])) {
    echo $utilities->getBoard();
}

if (isset($_POST['logout'])) {
    $utilities->logout();
}

if (isset($_POST['newGame'])) {
    $utilities->generateNewGame();
}

if (isset($_POST['getGameState']) AND isset($_POST['gameID'])) {
    echo $utilities->getGameState($_POST['gameID']);
}

if (isset($_POST['enterGame']) AND isset($_POST['gameID'])) {
    $utilities->enterGame();
}

if (isset($_POST['joinGame']) AND isset($_POST['gameID'])) {
    $utilities->joinGame();
}

if (isset($_POST['takeSquare']) AND isset($_POST['x']) AND isset($_POST['y'])) {
    echo $utilities->takeSquare();
}
