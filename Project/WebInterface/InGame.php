<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('MemoryChecks.php');
require_once('./WebServiceHandler.php');
require_once('./Utilities.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
$memoryTest->checkGameID();

$utilities = new Utilities();

?>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        // global nameSpace that we'll use for out global variables
        let nameSpace = {
            currentTicBoard: undefined,
            currentGameState: undefined,
            myTurn: false,
            worker: undefined,
            retrieveBoardFromWebService: () => retrieveBoardFromWebService(),
        };

        /**
         * Retrieve board from webservice and build it in the UI
         */
        const retrieveBoardFromWebService = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    retrieveBoardFromWebService: true,
                },
                cache:false,
                success: (data, textStatus, jqXHR) => {
                    buildBoard(jqXHR.responseText);
                    if(jqXHR.responseText != "ERROR-NOMOVES"){
                        checkWin();
                    }
                }
            });
        }

        /**
         * Function that checks if there has been a change in the board server side and if there has been a change
         * registered rebuild the board.
         */
        const boardUpdater = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    updateBoard: true,
                    currentBoard: nameSpace.currentTicBoard,
                },
                cache:false,
                success: (response) => {
                    if(response == 1){
                        nameSpace.retrieveBoardFromWebService();
                    }
                }
            });
        }

        /**
         * Retrieve the gameState from the server and see if it matches the one, client side.
         */
        const getGameState = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getGameState: true,
                    currentGameState: nameSpace.currentGameState,
                },
                cache:false,
                success: (response) => {
                    if(response != "No-Change"){
                        if (response != 0 || response == '') {
                            nameSpace.currentGameState = response;
                            disableBoard();
                        } else {
                            let element = document.getElementById("gameStatus");
                            element.innerHTML = 'Game in Progress';
                            nameSpace.currentGameState = response;
                            enableBoard();
                        }
                    }
                }
            });
        }
        /**
         * Function used to check if it's the current user's turn to tick a box.
         */
        const turnUpdater = () => {
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    retrieveBoardFromWebService: true,
                },
                cache:false,
            }).done((data, textStatus, jqXHR) => {
                if(nameSpace.currentGameState == 0) {
                    let response = jqXHR.responseText;
                    if (response == "ERROR-NOMOVES") {
                        nameSpace.myTurn = true;
                        enableBoard();
                    } else {
                        let myID = <?php echo json_encode($utilities->getUserID()) ?>;
                        let previousMoves = response.split("\n");
                        let lastMove = previousMoves[previousMoves.length - 1].split(",");
                        if (lastMove[0] == myID) {
                            nameSpace.myTurn = false;
                            disableBoard();
                        } else {
                            nameSpace.myTurn = true;
                            enableBoard();
                        }
                    }
                }
            });


        }

        /**
         * This function builds the board that the user interacts with
         * @param previousMovesString This is a string retrieved from the webservice that contains all the
         * previous moves made in this game
         */
        const buildBoard = (previousMovesString) => {
            // split string into individual moves.
            let previousMoves = previousMovesString.split("\n");
            // assuming we are working with a 3x3 board
            let board = new Array(3);
            for (let i = 0; i < board.length; i++) {
                board[i] = new Array(3);
            }
            // check if anyone has made a move yet
            if(previousMoves != "ERROR-NOMOVES") {
                previousMoves.forEach((move) => {
                    let moveInfo = move.split(",");
                    board[moveInfo[1]][moveInfo[2]] = moveInfo[0];
                });
            }
            // access the board div
            let element = document.getElementById('board');
            // clear out any previous boards
            element.innerHTML ='';
            let table = document.createElement("table");
            // build the board row by row
            for(let i = 0; i < 3; i++){
                let tableRow = document.createElement("tr");
                for(let j = 0; j < 3; j++){
                    let tableColumn = document.createElement("td");
                    let button = document.createElement("button");
                    // if that tile has already been clicked, disable it.
                    if(board[i][j]) {
                        button.innerHTML = board[i][j];
                        button.disable = true;
                    }else{
                        button.innerHTML = "click";
                    }
                    // setting its coordinates as the value
                    button.value = i+","+j;
                    button.onclick = () => tileClicked(button.value);
                    tableColumn.appendChild(button);
                    tableRow.appendChild(tableColumn);
                }
                table.appendChild(tableRow);
            }
            element.appendChild(table);
            // update current board for use in checkers.
            nameSpace.currentTicBoard = previousMovesString;
        }

        /**
         * This function is triggered when a user clicks on a valid tile, making an ajax request to the webservice
         * with the coordinates of the tile clicked.
         * @param coordinates comma separated string containing the x - y coordinates.
         */
        const tileClicked = (coordinates) => {
            // ensuring no problems if the board is unable to be disabled.
            if(nameSpace.myTurn == true) {
                // tile has been clicked so now user cant click any others till its their turn again.
                nameSpace.myTurn = false;
                let coordinatesArray = coordinates.split(",");
                $.ajax({
                    type: "post",
                    url: "Utilities.php",
                    data: {
                        takeSquare: true,
                        x: coordinatesArray[0],
                        y: coordinatesArray[1],
                    },
                    cache: false,
                });
            }
        }

        /**
         * Function that alters the games state.
         * @param gameState Integer from -1 to 3
         */
        const setGameState = (gameState) => {
            $.ajax({
                type: "post",
                url: "Utilities.php",
                data: {
                    setGameState: true,
                    newGameState: gameState,
                },
                cache: false,
            })
        }

        /**
         * Function routinely called to check if a user has won the game.
         */
        const checkWin = () => {
            $.ajax({
                type: "post",
                url: "Utilities.php",
                data: {
                    checkWin: true,
                },
                cache: false,
            }).done((event) => {
                setGameState(event);
                if(event > 0){
                    let element = document.getElementById("gameStatus");
                    if(event == 1){
                        element.innerHTML = 'Player 1 won';
                    }else if(event == 2){
                        element.innerHTML = 'Player 2 won';
                    }else{
                        element.innerHTML = 'Draw';
                    }
                }
            });

        }

        /**
         * Updates the UI blocking user input on the board.
         */
        const disableBoard = () => {
            let element = document.getElementById("board");
            let nodes = element.getElementsByTagName('*');
            for (let i = 0; i < nodes.length; i++) {
                nodes[i].disabled = true;
            }
            element.disabled = true;
        }

        /**
         * Updates the UI allowing user input on the board.
         */
        const enableBoard = () => {
            let element = document.getElementById("board");
            // ensuring that the board actually needs to be re-enabled.
            if(element.disabled == true) {
                let nodes = element.getElementsByTagName('*');
                for (let i = 0; i < nodes.length; i++) {
                    nodes[i].disabled = false;
                }
                element.disabled = false;
            }
        }

        // Build the board and run workers that update the board when necessary.
        nameSpace.retrieveBoardFromWebService();

        if(window.Worker){
            nameSpace.worker = new Worker('UpdateWorker.js');
            nameSpace.worker.postMessage({
                data: '',
            });
            nameSpace.worker.addEventListener('message', () => {
                boardUpdater();
                getGameState();
                turnUpdater();
            })
        }
</script>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<html>
    <body>
    <h1 id="gameStatus">Waiting on player 2 to join</h1>
    <button onclick="location.href = 'mainMenu.php';">back to main menu</button>
    <div id="board" disabled=false>

    </div>
    </body>
</html>

