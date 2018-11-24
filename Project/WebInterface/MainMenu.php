<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('MemoryChecks.php');
require_once('./WebServiceHandler.php');
require_once('./Utilities.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
?>
<!DOCTYPE HTML>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        const mainMenuNameSpace = {
            currentListOfOpenGames: "",
            getAllMyOpenGames: () => getAllMyOpenGames(),
            getAllOpenGames : () => getAllOpenGames(),
            getAllMyGames: () => getAllMyGames(),
            worker: undefined,
        }
        const printAllOpenGames = (list) => {
            let element = document.getElementById('allOpenGames');
            element.innerHTML = '';
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    if(text != "") {
                        let textArray = text.split(",");
                        textArray.forEach((text) => {
                            let textNode = document.createTextNode(text + " - ");
                            listItem.appendChild(textNode);
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
                let textNode = document.createTextNode( " No Games ");
                element.appendChild(textNode);
            }
        }

        const joinGame = (gameID) => {
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

        const newGame = () => {
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

        const enterGame = (gameID) => {
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

        const printAllMyGames = (list) => {
            let element = document.getElementById('allMyGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    let textArray = text.split(",");
                    textArray.forEach((text) => {
                        let textNode = document.createTextNode(text + " - ");
                        listItem.appendChild(textNode);
                    })
                    let button = document.createElement("button");
                    button.innerHTML = "Re-enter Game";
                    button.onclick = () => enterGame(textArray[0]);
                    listItem.appendChild(button);
                    node.appendChild(listItem);
                    element.appendChild(node);
                });
            }else{
                let textNode = document.createTextNode( " No Games ");
                element.appendChild(textNode);
            }

        }

        const logout = () => {
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

        const printAllMyOpenGames = (list) => {
            let element = document.getElementById('allMyOpenGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                arrayOfText.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    let textArray = text.split(",");
                    textArray.forEach((text) => {
                        let textNode = document.createTextNode(text + " - ");
                        listItem.appendChild(textNode);
                    });
                    let button = document.createElement("button");
                    button.innerHTML = "Re-enter Game";
                    button.onclick = () => enterGame(textArray[0]);
                    listItem.appendChild(button);
                    node.appendChild(listItem);
                    element.appendChild(node);
                });
            }else{
                let textNode = document.createTextNode( " No Games ");
                element.appendChild(textNode);
            }
        }

        const getAllMyOpenGames = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getAllMyOpenGames: true,
                },
                cache:false,
                success: (results) => {
                    printAllMyOpenGames(results);
                }
            });
        }

        const getAllOpenGames = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getAllOpenGames: true,
                },
                cache:false,
                success: (results) => {
                    mainMenuNameSpace.currentListOfOpenGames = results;
                    printAllOpenGames(results);
                }
            });
        }

        const getAllMyGames = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getAllMyGames: true,
                },
                cache:false,
                success: (results) => {
                    printAllMyGames(results);
                }
            });
        }

        const checkAllOpenGames = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getAllOpenGames: true,
                },
                cache:false,
                success: (results) => {
                    if(results != mainMenuNameSpace.currentListOfOpenGames){
                        getAllOpenGames();
                    }
                }
            });
        }

        mainMenuNameSpace.getAllMyOpenGames();
        mainMenuNameSpace.getAllOpenGames();
        mainMenuNameSpace.getAllMyGames();

        if(window.Worker){
            mainMenuNameSpace.worker = new Worker('UpdateWorker.js');
            mainMenuNameSpace.worker.postMessage({
                data: '',
            });
            mainMenuNameSpace.worker.addEventListener('message', () => {
                checkAllOpenGames();
            })
        }



    </script>
    <style>

    </style>
    <html>
        <body>


        </body>
    </html>


<!DOCTYPE html>
<html class="no-js" lang="en">
<head>

    <!--- basic page needs
    ================================================== -->
    <meta charset="utf-8">
    <title>Tic-Tac-To</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- mobile specific metas
    ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS
    ================================================== -->
    <link rel="stylesheet" href="css/base.css">
    <link rel="stylesheet" href="css/vendor.css">
    <link rel="stylesheet" href="css/main.css">

    <!-- script
    ================================================== -->
    <script src="js/modernizr.js"></script>
    <script src="js/pace.min.js"></script>

    <!-- favicons
    ================================================== -->

</head>

<body id="top">

<!-- home
================================================== -->
<section id="home" class="s-home target-section" data-parallax="scroll" data-image-src="images/hero-bg.jpg" data-natural-width=3000 data-natural-height=2000 data-position-y=top>

    <div class="shadow-overlay"></div>

    <div class="home-content">
        <div class="col-block service-item">
            <h2 class="h2">
                Menu
            </h2>
            <br>
            <div>
                <button onclick="newGame()">new Game</button>
            </div>
            <br>
            <div>
                <button onclick="location.href = 'LeaderBoard.php';">Leaderboard</button>
            </div>
            <br>
            <div>
                <button onclick="logout()">Logout</button>
            </div>
        </div>

        <div class="col-block service-item">
            <h2 class="h2">
                Open games
            </h2>
            <div id = "allOpenGames">
            </div>
        </div>
        <div class="col-block service-item">
            <h2 class="h2">
                All my games
            </h2>
            <div id = "allMyGames">
            </div>
        </div>
        <div class=" col-block service-item">
            <h2 class="h2">
                All my open games
            </h2>
            <div id = "allMyOpenGames">
            </div>
        </div>

    </div>

</section>


<!-- preloader
================================================== -->
<div id="preloader">
    <div id="loader">
    </div>
</div>


<!-- Java Script
================================================== -->
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/plugins.js"></script>
<script src="js/main.js"></script>

</body>
</html>

