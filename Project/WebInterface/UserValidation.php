<?php
/**
 * Created by PhpStorm.
 * User: willo
 * Date: 11/11/2018
 * Time: 12:46
 */

class UserValidation
{
    private $interface;
    private $config;
    public function __construct()
    {
        require_once('./SoapInterface.php');
        $this->interface = new SoapInterface();
        $this->config = include('config.php');
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

        $this->redirect("mainMenu.php");
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