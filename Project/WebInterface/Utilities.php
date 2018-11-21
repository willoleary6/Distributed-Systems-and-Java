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
    require_once('./WebServiceHandler.php');

    class Utilities
    {
        private $config;
        private $interface;
        private $currentGameState;

        public function __construct()
        {
            $this->config = include('Config.php');
            $this->interface = new WebServiceHandler();
            $this->currentGameState = null;
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
            $this->interface->joinGame( $this->getUserID(), $_POST['gameID']);
            $this->interface->setGameState($_POST['gameID'], 0);
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

        function checkWin(){
            $winner = $this->interface->checkWin($this->getGameID());
            return $winner;
        }

        function takeSquare(){
            $guid = $this->getGameID();
            $x = $_POST['x'];
            $y = $_POST['y'];
            $pID =  $this->getUserID();
            return $this->interface->takeSquare($guid,$x,$y,$pID);
        }

        function getGameState($currentGameState){
            $gameState = $this->interface->getGameState($this->getGameID());
            if($currentGameState == $gameState ){
                return "No-Change";
            }
            return $gameState;
        }
        //setGameState
        function setGameState( $newGameState){
            $gameState = $this->interface->setGameState($this->getGameID(), $newGameState);
            return $gameState;
        }
    }

    $utilities = new Utilities();
    $interface = new WebServiceHandler();
    if (isset($_POST['retrieveBoardFromWebService'])) {
        echo $utilities->getBoard();
    }

    if (isset($_POST['logout'])) {
        $utilities->logout();
    }

    if (isset($_POST['newGame'])) {
        $utilities->generateNewGame();
    }

    if (isset($_POST['checkWin'])) {
        echo $utilities->checkWin();
    }

    if (isset($_POST['getGameState'])  AND isset($_POST['currentGameState'])) {
        echo $utilities->getGameState($_POST['currentGameState']);
    }

    if (isset($_POST['setGameState'])  AND isset($_POST['newGameState'])) {
        echo $utilities->setGameState($_POST['newGameState']);
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

    if (isset($_POST['updateBoard']) && isset($_POST['currentBoard'])) {
        $currentBoard = $_POST['currentBoard'];
        $newBoard = $utilities->getBoard();
        if($currentBoard == $newBoard){
            echo 0;
        }else{
            echo 1;
        }
    }
