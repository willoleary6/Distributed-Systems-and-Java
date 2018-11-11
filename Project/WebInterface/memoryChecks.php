<?php
class memoryChecks{
    private $config;
    public function __construct()
    {
        $this->config = include('config.php');
    }

    public function checkCredentials(){
        if(!isset($_SESSION[$this->config['cookieUserId']]) AND
            !isset($_SESSION[$this->config['cookieUsername']]) ) {
            header( "Location: index.php" );
        }
    }
    public function checkGameID(){
        if(!isset($_SESSION[$this->config['gameID']])) {
            header( "Location: mainMenu.php" );
        }
    }
    /*
    if(!isset($_COOKIE[$config['cookieUserId']]) OR !isset($_COOKIE[$config['cookieUsername']])) {
        #echo '<script>alert("firing");</script>';
        #header( "Location: index.php" );
    }

    else {
        $userValidation = new UserValidation();
        $userID = $_COOKIE[$config['cookieUserId']];
        $username= $_COOKIE[$config['cookieUsername']];
        $userValidation->setItemsInSessionAndCookies($userID, $username);
    }
    */
}
