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

    /**
     * Function that makes an ajax call to get the games currently in progress or who have already won.
     */
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

    /**
     * Function that makes a ajax call to the webservice which calculates the current users stats.
     */
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

    /**
     * Function that builds the HTML list storing the current users stats
     * @param stats JSON object with the number of user wins,losses and the ratio between them.
     */

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

    /**
     * Function that builds the HTML table storing all the games currently in play or have ended.
     * @param listOfGames \n separated string containing all the games currently in progress or have been completed.
     */
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
                // instead of 0 - 3 added some text for easy human consumption.
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

    /**
     * Little function that builds the headers for the league table
     * @returns {HTMLElement} row of th's with each column header
     */
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

    /**
     * Method that takes a numeric input of the game state and returns the relevant string associated with that numeric.
     * @param rawValue
     * @returns {string}
     */

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
                return "Some crazy shit must have went down.";
        }
    }

    /**
     * Function that contains and ajax function that pulls the league table keeping it updated.
     */

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

<!-- LeaderBoard and league table
================================================== -->
<section id="home" class="s-home target-section" data-parallax="scroll" data-image-src="images/hero-bg.jpg" data-natural-width=3000 data-natural-height=2000 data-position-y=top>

    <div class="shadow-overlay"></div>

    <div class="home-content">

        <div class="row home-content__main">
            <h2 class="h2">
                Leader Board
            </h2>
            <div id = "userStats">
            </div>
            <div id = "leaderBoard">
            </div>

            <button onclick="location.href = 'mainMenu.php';">back to main menu</button>
            <br>
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