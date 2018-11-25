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
        currentUserStats: undefined,
    };

    /**
     * Function that builds the HTML list storing the current users stats
     * @param stats JSON object with the number of user wins,losses and the ratio between them.
     */

    const buildUserStatsTable = (stats) => {
        leaderBoardNameSpace.currentUserStats = stats;
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

        let draws = document.createElement("li");
        let drawsTextNode = document.createTextNode("Draws: "+stats.draws);
        draws.appendChild(drawsTextNode);
        unOrderedList.appendChild(draws);

        let winLossRatio = document.createElement("li");
        let winLossRatioTextNode = document.createTextNode("Win/Loss Ratio: "+parseFloat(stats.wins/stats.losses));
        winLossRatio.appendChild(winLossRatioTextNode);
        unOrderedList.appendChild(winLossRatio);


        element.appendChild(unOrderedList);
    }


    /**
     * Function that contains and ajax function that pulls the user stats keeping them updated.
     */
    const checkUserStats = () => {
        $.ajax({
            type:"post",
            url:"Utilities.php",
            data:{
                calculateUserStats: true,
            },
            cache:false,
            success: (results) => {
                if(results !== leaderBoardNameSpace.currentUserStats){
                    buildUserStatsTable(JSON.parse(results));
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
            checkUserStats();
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
                User Statistics
            </h2>
            <div id = "userStats">
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