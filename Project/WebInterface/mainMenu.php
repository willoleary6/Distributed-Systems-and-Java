<!DOCTYPE HTML>
    <script>
        function printAllOpenGames(list){
            let arrayOfText = list.split("\n");
            arrayOfText.forEach((text) => {
                let element = document.getElementById('allOpenGames');
                let node = document.createElement("li");
                let textnode = document.createTextNode(text);
                node.appendChild(textnode);
                element.appendChild(node);
            });
        }

        function printAllMyGames(list){
            let arrayOfText = list.split("\n");
            arrayOfText.forEach((text) => {
                let element = document.getElementById('allMyGames');
                let node = document.createElement("li");
                let textnode = document.createTextNode(text);
                node.appendChild(textnode);
                element.appendChild(node);
            });
        }

        function printAllMyOpenGames(list){
            let arrayOfText = list.split("\n");
            arrayOfText.forEach((text) => {
                let element = document.getElementById('allMyOpenGames');
                let node = document.createElement("li");
                let textnode = document.createTextNode(text);
                node.appendChild(textnode);
                element.appendChild(node);
            });
        }

    </script>
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
            <div>
                <button>new Game</button>
                <button>Leaderboard</button>
                <a href='mainMenu.php?logout=true'>logout</a>
            </div>
        </body>
    </html>

<?php
include('cookieCheck.php');
require_once('./SoapInterface.php');
$interface = new SoapInterface();
$userID = getUserID();


$openGames = $interface->showOpenGames();
echo '<script>printAllOpenGames(' . json_encode($openGames) . ');</script>';

$allMyGames = $interface->showAllMyGames($userID);
echo '<script>printAllMyGames(' . json_encode($allMyGames) . ');</script>';

$allMyOpenGames = $interface->showMyOpenGames($userID);
echo '<script>printAllMyOpenGames(' . json_encode($allMyOpenGames) . ');</script>';


if (isset($_GET['logout'])) {
    logout();
}
function logout()
{
    $config = include('config.php');
    setcookie($config['cookieUserId'], null, -1, '/');
    header("Refresh:0");
}

function getUserID(){
    $config = include('config.php');
    return $_COOKIE[$config['cookieUserId']];
}
?>



