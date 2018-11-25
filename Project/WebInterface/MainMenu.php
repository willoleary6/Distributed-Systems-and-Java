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

        /**
         * Function that prints out a list of open games the user can join.
         * @param list String of join able games delimited by "\n".
         */
        const printAllOpenGames = (list) => {
            let element = document.getElementById('allOpenGames');
            element.innerHTML = '';
            // Checking that there is open games to join
            if(!list.startsWith('ERROR')) {
                let arrayOfGames = list.split("\n");
                arrayOfGames.forEach((text) => {
                    let node = document.createElement("ul");
                    let listItem = document.createElement("li");
                    // Ensuring we print out actual games and not empty indexes of the array.
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

        /**
         * Function that executes an ajax request to join a currently open game.
         * On Success the user will be redirected to the inGame page
         * @param gameID id number of the game we wish to join.
         */
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

        /**
         * Function that executes an ajax request to create a new game and redirects the user to the InGame page.
         */
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

        /**
         * Function that allows a user to re enter a game they are currently participating in.
         * @param gameID id number of the game we wish to re enter.
         */

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

        /**
         * Function that prints all the games the user is currently involved in.
         * @param list String of the users games delimited by "\n".
         */
        const printAllMyGames = (list) => {
            let element = document.getElementById('allMyGames');
            // Checking that there is open games to join
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

        /**
         * Function that executes an ajax request logging the user out and redirecting them to the index page.
         */
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

        /**
         * Function that prints out a list of all the users open games.
         * @param list String of games delimited by "\n".
         */

        const printAllMyOpenGames = (list) => {
            let element = document.getElementById('allMyOpenGames');
            if(!list.startsWith('ERROR')) {
                let arrayOfText = list.split("\n");
                // Checking that there is open games to join
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

        /**
         * Function that executes an ajax request to
         * retrieve a list of all the users currently open games.
         */
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

        /**
         * Function that executes an ajax request to
         * retrieve a list of all the open games.
         */
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

        /**
         * Function that executes an ajax request to
         * retrieve a list of all the user's games.
         */
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

        /**
         * Function that updates the list of open games if there is a change in the webservice.
         */
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

