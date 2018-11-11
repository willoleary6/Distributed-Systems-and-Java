<?php
session_start();
require_once('memoryChecks.php');
require_once('./SoapInterface.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
if (isset($_GET['logout'])) {
    logout();
}

function logout(){
    $config = include('config.php');
    setcookie($config['cookieUserId'], null, -1, '/');
    setcookie($config['cookieUsername'], null, -1, '/');
    session_destroy ();
    header( "Location: index.php" );
}

$interface = new SoapInterface();
$userID = getUserID();
$username = getUserName();

if (isset($_GET['newGame'])) {
    $config = include('config.php');
    $gameID = $interface->newGame($userID);
    $_SESSION[$config['gameID']] = $gameID;
    header( "Location: inGame.php" );
}

if (isset($_GET['enterGame']) AND isset($_GET['gameID'])) {
    $config = include('config.php');
    $_SESSION[$config['gameID']] = $_GET['gameID'];
    //TODO replace with ajax
    header( "Location: inGame.php" );
}

if (isset($_GET['joinGame']) AND isset($_GET['gameID'])) {
    $config = include('config.php');
    $_SESSION[$config['gameID']] = $_GET['gameID'];
    $interface->joinGame( getUserID(), $_GET['gameID']);
    header( "Location: inGame.php" );
}

function getUserID(){
    $config = include('config.php');
    return $_SESSION[$config['cookieUserId']];
}

function getUsername(){
    $config = include('config.php');
    return $_SESSION[$config['cookieUsername']];
}
?>
<!DOCTYPE HTML>
    <script>
        function printAllOpenGames(list, userId){
            let element = document.getElementById('allOpenGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    let textArray = text.split(",");
                    if (textArray[1] != userId) {
                        textArray.forEach((text) => {
                            let textnode = document.createTextNode(text + " - ");
                            listItem.appendChild(textnode);
                        })
                        let button = document.createElement("button");
                        button.innerHTML = "Join Game";
                        button.onclick = () => joinGame(textArray[0]);
                        listItem.appendChild(button);
                        node.appendChild(listItem);
                        element.appendChild(node);
                    }
                });
            }else{
                let textnode = document.createTextNode( " No Games ");
                element.appendChild(textnode);
            }
        }

        function joinGame(gameID){
            //TODO replace with ajax
            window.location.href = "mainMenu.php?joinGame=true&gameID="+gameID;
        }

        function newGame() {
            //TODO replace with ajax
            window.location.href = "mainMenu.php?newGame=true";
        }

        function enterGame(gameID) {
            //TODO replace with ajax
            window.location.href = "mainMenu.php?enterGame=true&gameID="+gameID;
        }

        function printAllMyGames(list){
            let element = document.getElementById('allMyGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    let textArray = text.split(",");
                    textArray.forEach((text) => {
                        let textnode = document.createTextNode(text + " - ");
                        listItem.appendChild(textnode);
                    })
                    let button = document.createElement("button");
                    button.innerHTML = "Re-enter Game";
                    button.onclick = () => enterGame(textArray[0]);
                    listItem.appendChild(button);
                    node.appendChild(listItem);
                    element.appendChild(node);
                });
            }else{
                let textnode = document.createTextNode( " No Games ");
                element.appendChild(textnode);
            }

        }

        function printAllMyOpenGames(list){
            let element = document.getElementById('allMyOpenGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    let textArray = text.split(",");
                    textArray.forEach((text) => {
                        let textnode = document.createTextNode(text + " - ");
                        listItem.appendChild(textnode);
                    });
                    let button = document.createElement("button");
                    button.innerHTML = "Re-enter Game";
                    button.onclick = () => enterGame(textArray[0]);
                    listItem.appendChild(button);
                    node.appendChild(listItem);
                    element.appendChild(node);
                });
            }else{
                let textnode = document.createTextNode( " No Games ");
                element.appendChild(textnode);
            }
        }

    </script>
    <style>

    </style>
    <html>
        <body>
            <h1>main menu</h1>

            <h2>
                Open games
            </h2>
            <div id = "allOpenGames">
            </div>
            <h2>
                All my games
            </h2>
            <div id = "allMyGames">
            </div>

            <h2>
                All my open games
            </h2>
            <div id = "allMyOpenGames">
            </div>
            <br>
            <div>
                <button onclick="newGame()">new Game</button>
            </div>
            <br>
            <div>
                <button>Leaderboard</button>
            </div>
            <br>
            <div>
                <a href='mainMenu.php?logout=true'>logout</a>
                <br>
            </div>

        </body>
    </html>

<?php

$openGames = $interface->showOpenGames();
echo '<script>printAllOpenGames(' . json_encode($openGames) . ',' . json_encode($username) . ');</script>';

$allMyGames = $interface->showAllMyGames($userID);
echo '<script>printAllMyGames(' . json_encode($allMyGames) . ');</script>';

$allMyOpenGames = $interface->showMyOpenGames($userID);
echo '<script>printAllMyOpenGames(' . json_encode($allMyOpenGames) . ');</script>';



