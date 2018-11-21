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
                <button onclick="location.href = 'LeaderBoard.php';">Leaderboard</button>
            </div>
            <br>
            <div>
                <button onclick="logout()">Logout</button>
            </div>

        </body>
    </html>




