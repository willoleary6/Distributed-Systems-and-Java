<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
require_once('memoryChecks.php');
require_once('./SoapInterface.php');
require_once('./Utilities.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
$memoryTest->checkGameID();

$utilities = new Utilities();

?>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        let currentTicBoard = undefined;
        let currentGameState = undefined;
        let myTurn = false;
        getBoard();

        function checker(){
            $.ajax({
                type:"post",
                url:"updateChecker.php",
                data:{
                    gameID: <?php echo json_encode($utilities->getGameID()) ?>,
                    currentBoard: currentTicBoard,
                },
                cache:false,
                success: (response) => {
                    if(response == 1){
                        getBoard();
                    }
                }
            });
        }
        //getGameState
        function getGameState(){
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    gameID: <?php echo json_encode($utilities->getGameID()) ?>,
                    getGameState: true,
                    currentGameState: currentGameState,
                },
                cache:false,
                success: (response) => {
                    if(response != "No-Change"){
                        if (response != 0) {
                            currentGameState = response;
                            disableBoard();
                        } else {

                            let element = document.getElementById("gameStatus");
                            element.innerHTML = 'Game in Progress';
                            currentGameState = response;
                            enableBoard();
                        }
                    }
                }
            });
        }

        function turnCalculator(){
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getBoard: true,
                },
                cache:false,
            }).done(function(data, textStatus, jqXHR){
                if(currentGameState == 0) {
                    let response = jqXHR.responseText;
                    if (response == "ERROR-NOMOVES") {
                        myTurn = true;
                        enableBoard();
                    } else {
                        let myID = <?php echo json_encode($utilities->getUserID()) ?>;
                        let previousMoves = response.split("\n");
                        let lastMove = previousMoves[previousMoves.length - 1].split(",");
                        if (lastMove[0] == myID) {
                            myTurn = false;
                            disableBoard();
                        } else {
                            myTurn = true;
                            enableBoard();
                        }
                    }
                }
            });


        }
        function buildBoard(previousMovesString) {

            let previousMoves = previousMovesString.split("\n");
            let board = new Array(3);
            for (let i = 0; i < board.length; i++) {
                board[i] = new Array(3);
            }
            if(previousMoves != "ERROR-NOMOVES") {
                previousMoves.forEach((move) => {
                    let moveInfo = move.split(",");
                    board[moveInfo[1]][moveInfo[2]] = moveInfo[0];
                });
            }

            let element = document.getElementById('board');
            element.innerHTML ='';
            let table = document.createElement("table");
            for(let i = 0; i < 3; i++){
                let tableRow = document.createElement("tr");
                for(let j = 0; j < 3; j++){
                    let tableColumn = document.createElement("td");
                    let button = document.createElement("button");
                    if(board[i][j]) {
                        button.innerHTML = board[i][j];
                        button.disable = true;
                    }else{
                        button.innerHTML = "click";
                    }
                    button.value = i+","+j;
                    button.onclick = () => tileClicked(button.value);
                    tableColumn.appendChild(button);
                    tableRow.appendChild(tableColumn);
                }
                table.appendChild(tableRow);
            }
            element.appendChild(table);
            currentTicBoard = previousMovesString;
        }

        function tileClicked (coordinates){
            if(myTurn == true) {
                myTurn = false;
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

        function setGameState(gameState) {
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

        function checkWin(){
            $.ajax({
                type: "post",
                url: "Utilities.php",
                data: {
                    checkWin: true,
                },
                cache: false,
                success: (event) => {

                }
            }).done(function(event){
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

        function disableBoard(){
            let element = document.getElementById("board");
                let nodes = element.getElementsByTagName('*');
                for (let i = 0; i < nodes.length; i++) {
                    nodes[i].disabled = true;
                }
                element.disabled = true;

        }
        function enableBoard(){

            let element = document.getElementById("board");
            if(element.disabled == true) {
                let nodes = element.getElementsByTagName('*');
                for (let i = 0; i < nodes.length; i++) {
                    nodes[i].disabled = false;
                }
                element.disabled = false;
            }
        }

        function getBoard(){
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getBoard: true,
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

        let worker;
        if(window.Worker){
            worker = new Worker('workers.js');
            worker.postMessage({
                data: 'start board checker',
            });
            worker.addEventListener('message', (event) => {
                checker();
                getGameState();
                turnCalculator();
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

<script>  

</script>
