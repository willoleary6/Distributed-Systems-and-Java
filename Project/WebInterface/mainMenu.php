<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
            $.ajax({
                type:"post",
                url:"mainMenuUtilities.php",
                data:{
                    joinGame: true ,
                    gameID: gameID,
                },
                cache:false,
                success: () => {
                    window.location.href = "inGame.php";
                }

            });
        }

        function newGame() {
            $.ajax({
                type:"post",
                url:"mainMenuUtilities.php",
                data:{
                    newGame: true,
                },
                cache:false,
                success: () => {
                    window.location.href = "inGame.php";
                }
            });
        }

        function enterGame(gameID) {
            $.ajax({
                type:"post",
                url:"mainMenuUtilities.php",
                data:{
                    enterGame: true,
                    gameID: gameID,
                },
                cache:false,
                success: () => {
                    window.location.href = "inGame.php";
                }
            });
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



