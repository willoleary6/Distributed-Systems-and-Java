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
                LeaderBoard
            </h2>
            <button onclick="location.href = 'mainMenu.php';">back to main menu</button>
            <div id = "leaderBoard">
            </div>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    let leaderBoardNameSpace = {
        currentLeagueTable: undefined,
    };

    /**
     * Function that builds the HTML table storing all the users stats.
     * @param listOfUsers array of JSONs containing users statistics.
     */
    const buildLeaderBoardTable = (listOfUsers) => {
        leaderBoardNameSpace.currentLeagueTable = listOfUsers;
        let element = document.getElementById('leaderBoard');
        element.innerHTML ='';
        let arrayParse = JSON.parse(listOfUsers);
        let leaderBoard = [];
        arrayParse.forEach((rawJson) => {
            leaderBoard.push(JSON.parse(rawJson));
        });
        let table = document.createElement("table");
        table.appendChild(generateTableHeaders());
        leaderBoard.sort((a, b) => parseFloat(a.wins) + parseFloat(b.wins));
        leaderBoard.forEach((userStats) => {
            let tr = document.createElement("tr");
            for (let key in userStats) {
                if (userStats.hasOwnProperty(key)) {
                    let td = document.createElement("td");
                    let textNode = document.createTextNode(userStats[key]);
                    td.appendChild(textNode);
                    tr.appendChild(td);
                }
            }
            table.appendChild(tr);
        });
        element.appendChild(table);
    }

    /**
     * Little function that builds the headers for the league table
     * @returns {HTMLElement} row of th's with each column header
     */
    const generateTableHeaders = () => {
        let arrayHeaders = ["Username", "Wins", "Losses", "Draws"];
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
     * Function that contains and ajax function that pulls the leaderboard keeping it updated.
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
                if(results !== leaderBoardNameSpace.currentLeagueTable){
                    buildLeaderBoardTable(results);
                }
            }
        });
    }

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


</body>
</html>