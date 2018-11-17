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
                    getGameState();
                    if(response === 1){
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
                },
                cache:false,
                success: (response) => {
                    if(response != 0){
                        disableBoard();
                    }
                }
            });
        }
    function buildBoard(previousMoves) {

        previousMoves = previousMoves.split("\n");
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
        currentTicBoard = previousMoves;
    }

        function tileClicked (coordinates){
            let coordinatesArray = coordinates.split(",");
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    takeSquare: true,
                    x: coordinatesArray[0],
                    y: coordinatesArray[1],
                },
                cache:false,
            });
        }

        function disableBoard(){
            var e = $('#board');
            //console.log(e.data('disabled'));
            if(!e.data('disabled')){
                function cancel () { return false; };
                var nodes = document.getElementById("board").getElementsByTagName('*');
                for (var i = 0; i < nodes.length; i++) {
                    nodes[i].setAttribute('disabled', true);
                    nodes[i].onclick = cancel;
                }
            }
        }
        function enableBoard(){
            //let element = document.getElementById('board');
           // element.disable = false;
        }

        function getBoard(){
            $.ajax({
                type:"post",
                url:"Utilities.php",
                data:{
                    getBoard: true,
                },
                cache:false,
            }).done(function(data, textStatus, jqXHR){
                buildBoard(jqXHR.responseText);
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
                //getGameState();
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
    <h1>In Game</h1>
    <div id="board" data-disabled="false">

    </div>

    </body>
</html>

<script>  

</script>
