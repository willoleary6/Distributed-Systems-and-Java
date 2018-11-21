<?php
class memoryChecks{
    private $config;
    public function __construct()
    {
        $this->config = include('Config.php');
    }

    public function checkCredentials(){
        if(!isset($_SESSION[$this->config['cookieUserId']]) AND
            !isset($_SESSION[$this->config['cookieUsername']]) ) {
            header( "Location: index.php" );
        }
    }
    public function checkGameID(){
        if(!isset($_SESSION[$this->config['gameID']])) {
            header( "Location: MainMenu.php" );
        }
    }
}
