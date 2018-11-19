<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('memoryChecks.php');
require_once('./SoapInterface.php');
require_once('./Utilities.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
$utilities = new Utilities();
$interface = new SoapInterface();
$userID = $utilities->getUserID();
$username = $utilities->getUserName();
$openGames = $interface->showOpenGames();
$allMyGames = $interface->showAllMyGames($userID);
$allMyOpenGames = $interface->showMyOpenGames($userID);
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
                url:"Utilities.php",
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
                url:"Utilities.php",
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
                url:"Utilities.php",
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

        function logout(){
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    logout: true,
                },
                cache:false,
                success: () => {
                    window.location.href = "index.php";
                }
            });
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
                <button onclick="logout()">Logout</button>
            </div>

        </body>
    </html>

<?php
    echo '<script>printAllOpenGames(' . json_encode($openGames) . ',' . json_encode($username) . ');</script>';
    echo '<script>printAllMyGames(' . json_encode($allMyGames) . ');</script>';
    echo '<script>printAllMyOpenGames(' . json_encode($allMyOpenGames) . ');</script>';



