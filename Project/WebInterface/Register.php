<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
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

<!-- home
================================================== -->
<section id="home" class="s-home target-section" data-parallax="scroll" data-image-src="images/hero-bg.jpg" data-natural-width=3000 data-natural-height=2000 data-position-y=top>

    <div class="shadow-overlay"></div>

    <div class="home-content">

        <div class="row home-content__main">
            <div >
                <form action="UserValidation.php?register=true" method="post">
                    Username: <input type="text" name="username"><br>
                    Password: <input type="password" name="password"><br>
                    Forename: <input type="text" name="forename"><br>
                    Surname: <input type="text" name="surname"><br>
                    <button type="submit">rEGISTER</button>
                </form>
                <a href="index.php">Already have an Account? Login Here!</a>
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