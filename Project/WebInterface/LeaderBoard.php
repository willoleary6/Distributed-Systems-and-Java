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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    let leaderBoardNameSpace = {
        currentLeagueTable: undefined,
        getLeagueTable : () => getLeagueTable(),
        calculateUserStats : () => calculateUserStats(),
    };
    const getLeagueTable = () => {
        $.ajax({
            type:"post",
            url:"Utilities.php",
            data:{
                getLeagueTable: true,
            },
            cache:false,
            success: (results) => {
                buildLeaderBoardTable(results);
            }
        });
    }

    const calculateUserStats = () => {
        $.ajax({
            type:"post",
            url:"Utilities.php",
            data:{
                calculateUserStats: true,
            },
            cache:false,
            success: (results) => {
                buildUserStatsTable(JSON.parse(results));
            }
        });
    }

    const buildUserStatsTable = (stats) => {
        let element = document.getElementById('userStats');
        element.innerHTML ='';
        let unOrderedList = document.createElement("ul");
        let username = document.createElement("li");
        let usernameTextNode = document.createTextNode("Username: "+stats.username);
        username.appendChild(usernameTextNode);
        unOrderedList.appendChild(username);

        let wins = document.createElement("li");
        let winsTextNode = document.createTextNode("Wins: "+stats.wins);
        wins.appendChild(winsTextNode);
        unOrderedList.appendChild(wins);

        let losses = document.createElement("li");
        let lossesTextNode = document.createTextNode("Losses: "+stats.losses);
        losses.appendChild(lossesTextNode);
        unOrderedList.appendChild(losses);

        let winLossRatio = document.createElement("li");
        let winLossRatioTextNode = document.createTextNode("Win/Loss Ratio: "+parseFloat(stats.wins/stats.losses));
        winLossRatio.appendChild(winLossRatioTextNode);
        unOrderedList.appendChild(winLossRatio);


        element.appendChild(unOrderedList);
    }

    const buildLeaderBoardTable = (listOfGames) => {
        leaderBoardNameSpace.currentLeagueTable = listOfGames;
        let element = document.getElementById('leaderBoard');
        element.innerHTML ='';
        let gamesArray = listOfGames.split("\n");
        let table = document.createElement("table");
        table.appendChild(generateTableHeaders());
        gamesArray.forEach((gameDetails) => {
            let tr = document.createElement("tr");
            let gameDetailsArray = gameDetails.split(',');
            gameDetailsArray.forEach((detail, index) => {
                if(index == 3){
                    detail = formatGameStatus(detail);
                }
               let td = document.createElement("td");
               let textNode = document.createTextNode(detail);
               td.appendChild(textNode);
               tr.appendChild(td);
            });
            table.appendChild(tr);
        });
        element.appendChild(table);
    }

    const generateTableHeaders = () => {
        let arrayHeaders = ["Game Id", "Player 1", "Player 2", "Status", "Date"];
        let tr = document.createElement("tr");
        arrayHeaders.forEach((header) => {
            let th = document.createElement("th");
            let textNode = document.createTextNode(header);
            th.appendChild(textNode);
            tr.appendChild(th);
        })
        return tr;
    }

    const formatGameStatus = (rawValue) => {
        switch(rawValue) {
            case "-1":
                return "not started yet";
                break;
            case "0":
                return "In progress";
                break;
            case "1":
                return "Player 1 won";
                break;
            case "2":
                return "Player 2 won";
                break;
            case "3":
                return "Draw";
                break;
            default:
                return "Some crazy shit must have went down in this case";
        }
    }

    const checkLeaderBoard = () => {
        $.ajax({
            type:"post",
            url:"Utilities.php",
            data:{
                getLeagueTable: true,
            },
            cache:false,
            success: (results) => {
                if(results != leaderBoardNameSpace.currentLeagueTable){
                    getLeagueTable();
                }
            }
        });
    }

    leaderBoardNameSpace.getLeagueTable();
    leaderBoardNameSpace.calculateUserStats();


    if(window.Worker){
        leaderBoardNameSpace.worker = new Worker('UpdateWorker.js');
        leaderBoardNameSpace.worker.postMessage({
            data: '',
        });
        leaderBoardNameSpace.worker.addEventListener('message', () => {
            checkLeaderBoard();
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
        <h1>
            Leader Board
        </h1>
        <div id = "userStats">
        </div>
        <div id = "leaderBoard">
        </div>

        <button onclick="location.href = 'mainMenu.php';">back to main menu</button>
        <br>

    </body>
</html>