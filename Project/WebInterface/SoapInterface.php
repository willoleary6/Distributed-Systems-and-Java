<?php
/**
 * Created by PhpStorm.
 * User: willo
 * Date: 09/11/2018
 * Time: 19:32
 */

class SoapInterface
{
    private $proxyInstance;
    private $config;

    public function __construct()
    {
        $this->config = include('config.php');
       try {
           $this->proxyInstance = new SoapClient($this->config['wsdl']);
       }catch (Exception $e) {
           echo $e->getMessage ();
       }
    }

    public function test(){
        $result = $this->proxyInstance->test(array());
        print_r($result->return.'<br>');
    }

    public function register($username, $password, $forename, $surname){
        $result = $this->proxyInstance->register(
            array(
                'username' => $this->sanitiseInput($username),
                'password' => $this->sanitiseInput($password),
                'name'     => $this->sanitiseInput($forename),
                'surname'  => $this->sanitiseInput($surname)
            ));
        //returns autokey
        return $result->return;
    }

    public function login($username, $password){
        $result = $this->proxyInstance->login(
            array(
                'username' => $this->sanitiseInput($username),
                'password' => $this->sanitiseInput($password)
            ));
        //returns autokey
        return $result->return;
    }

    public function newGame($uid){
        $result = $this->proxyInstance->newGame(
            array(
                'uid'=>$this->sanitiseInput($uid)));
        //returns autokey
        return $result->return;
    }

    public function joinGame($userID, $gameID){
        $result = $this->proxyInstance->joinGame(
            array(
                'uid'=>$this->sanitiseInput($userID),
                'gid'=>$this->sanitiseInput($gameID)
            ));
        //returns autokey
        return$result->return;
    }

    public function getGameState($gameID){
        $result = $this->proxyInstance->getGameState(
            array(
                'gid'=>$this->sanitiseInput($gameID)));
        //returns autokey
        return $result->return;
    }

    public function setGameState($gameID,$gameState){
        $result = $this->proxyInstance->setGameState(
            array(
                'gid'=>$this->sanitiseInput($gameID),
                'gstate'=>$this->sanitiseInput($gameState)
            ));
        //returns autokey
        return$result->return;
    }

    public function checkSquare($gameID, $xValue, $yValue){
        $result = $this->proxyInstance->checkSquare(
            array(
                'gid'=>$this->sanitiseInput($gameID),
                'x'=>$this->sanitiseInput($xValue),
                'y'=>$this->sanitiseInput($yValue)
            ));
        //returns autokey
        return $result->return;
    }

    public function takeSquare($gameID, $xValue, $yValue, $playerID){
        $result = $this->proxyInstance->takeSquare(
            array(
                'gid'=>$this->sanitiseInput($gameID),
                'x'=>$this->sanitiseInput($xValue),
                'y'=>$this->sanitiseInput($yValue),
                'pid'=>$this->sanitiseInput($playerID)
            ));
        //returns autokey
        return $result->return;
    }

    public function getBoard($gameID){
        $result = $this->proxyInstance->getBoard(
            array(
                'gid'=>$this->sanitiseInput($gameID)));
        //returns autokey
        return $result->return;
    }

    public function getLeagueTable(){
        $result = $this->proxyInstance->leagueTable();
        return$result->return;
    }

    public function checkWin($gameID){
        $result = $this->proxyInstance->checkWin(
            array(
                'gid'=>$this->sanitiseInput($gameID)));
        //returns autokey
        return$result->return;
    }

    public function deleteGame($userID, $gameID){
        $result = $this->proxyInstance->deleteGame(
            array(
                'uid'=>$this->sanitiseInput($userID),
                'gid'=>$this->sanitiseInput($gameID)
            ));
        //returns autokey
        return$result->return;
    }

    public function showMyOpenGames($uid){
        $result = $this->proxyInstance->showMyOpenGames(
            array(
                'uid'=>$this->sanitiseInput($uid)));
        //returns autokey
        return $result->return;
    }

    public function showAllMyGames($uid){
        $result = $this->proxyInstance->showAllMyGames(
            array(
                'uid'=>$this->sanitiseInput($uid)));
        //returns autokey
        return $result->return;
    }

    public function showOpenGames(){
        $result = $this->proxyInstance->showOpenGames(
            array());
        //returns autokey
        return$result->return;
    }

    private function sanitiseInput($input){
        // Replaces all spaces with hyphens.
        $string = str_replace(' ', '-', $input);
        return preg_replace('/[^A-Za-z0-9\-]/', '', $string);
    }
}