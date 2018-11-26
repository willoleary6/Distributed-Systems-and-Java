<?php
    if (session_status() == PHP_SESSION_NONE) {
        session_start();
    }
    class UserValidation
    {
        private $interface;
        private $config;
        public function __construct()
        {
            require_once('./WebServiceHandler.php');
            $this->interface = new WebServiceHandler();
            $this->config = include('Config.php');
        }

        public function login($username, $password){
            $loginResults = $this->interface->login($username, $password);
            if ($loginResults < 1) {
                echo '<script> alert(\'Invalid credentials\');window.location=\'index.php\'</script>';
            } else {
                $this->setItemsInSessionAndCookies($loginResults, $username);
            }
        }

        public function register($username, $password, $forename, $surname){
            $registrationResults = $this->interface->register($username, $password,$forename, $surname);
            if(substr($registrationResults, 0, strlen("ERROR")) === "ERROR"){
                echo '<script> alert(\''.$registrationResults.'\');window.location=\'index.php\'</script>';
            }else{
                $this->setItemsInSessionAndCookies($registrationResults, $username);
            }
        }

        public function setItemsInSessionAndCookies($id, $username){
            $this->setSession($this->config['cookieUserId'], $id);
            $this->setSession($this->config['cookieUsername'], $username);

            $this->setCookie($this->config['cookieUserId'], $id);
            $this->setCookie($this->config['cookieUsername'], $username);

            $this->redirect("MainMenu.php");
        }
        private function setCookie($cookieName, $cookieValue){
            setcookie($cookieName, $cookieValue, time() + (86400 * 30), "/"); // 86400 = 1 day
        }
        private function setSession($sessionName, $sessionValue){
            $_SESSION[$sessionName] = $sessionValue;
        }

        private function redirect($newLocation){
            header( "Location: ". $newLocation);
        }
    }

    $config = include('Config.php');

    $userValidation = new UserValidation();
    if(isset($_COOKIE[$config['cookieUserId']]) AND isset($_COOKIE[$config['cookieUsername']])) {
        $userID = $_COOKIE[$config['cookieUserId']];
        $username= $_COOKIE[$config['cookieUsername']];
        $userValidation->setItemsInSessionAndCookies($userID, $username);
    }

    if (isset($_GET['login'])) {
        $username = $_POST["username"];
        $password = $_POST["password"];
        $userValidation->login($username, $password);
    }

    if (isset($_GET['register'])) {
        $username = $_POST["username"];
        $password = $_POST["password"];
        $forename = $_POST["forename"];
        $surname = $_POST["surname"];
        $userValidation->register($username, $password, $forename, $surname);
    }
