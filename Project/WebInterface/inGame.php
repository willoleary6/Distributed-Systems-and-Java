<?php
session_start();
require_once('memoryChecks.php');
require_once('./SoapInterface.php');
$memoryTest = new memoryChecks();
$memoryTest->checkCredentials();
$memoryTest->checkGameID();

function getUserID(){
    $config = include('config.php');
    return $_SESSION[$config['cookieUserId']];
}

function getUsername()
{
    $config = include('config.php');
    return $_SESSION[$config['cookieUsername']];
}

function getGameID()
{
    $config = include('config.php');
    return $_SESSION[$config['gameID']];
}

function getBoard(){
    $interface = new SoapInterface();
    $board = $interface->getBoard(getGameID());
    return $board;
}

if (isset($_GET['takeSquare']) AND isset($_GET['x']) AND isset($_GET['y'])) {
    echo "working";
    $guid = getGameID();
    $x = $_GET['x'];
    $y = $_GET['y'];
    $pID = getUserID();
    $interface = new SoapInterface();
    $interface->takeSquare($guid,$x,$y,$pID);
    header( "Location: inGame.php" );

}
?>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function testChecker(){
            $.ajax({
                type:"post",
                url:"updateChecker.php",
                data:{
                    gameID: <?php echo json_encode(getGameID()) ?>,
                    currentBoard: <?php echo json_encode(getBoard())?>,
                },
                cache:false,
                success: (response) => {
                    if(response == 1){
                        location.reload();
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
    }

    function tileClicked (coordinates){
        let coordinatesArray = coordinates.split(",");
        //TODO replace with ajax
        window.location.href = "inGame.php?takeSquare=true&x="+coordinatesArray[0]+"&y="+coordinatesArray[1];
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
    <div id="board">

    </div>

    </body>
</html>

<?php

echo '<script>buildBoard(' . json_encode(getBoard()) . ');</script>';
?>

<script>  
    let worker;
    if(window.Worker){
        worker = new Worker('workers.js');
        worker.postMessage({
            data: 'start board checker',
        });
        worker.addEventListener('message', (event) => {
            testChecker();
        })
    }
</script>';
