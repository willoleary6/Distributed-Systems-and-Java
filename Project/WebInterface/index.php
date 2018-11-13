<!DOCTYPE HTML>
<html>
<body>
<div>
    <h1>Register</h1>
    <form action="index.php?register=true" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        Forename: <input type="text" name="forename"><br>
        Surname: <input type="text" name="surname"><br>
        <input type="submit">
    </form>
</div>
<div>
    <h1>Login</h1>
    <form action="index.php?login=true" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit">
    </form>
</div>
</body>
</html>

<?php
    if (session_status() == PHP_SESSION_NONE) {
        session_start();
    }
    $config = include('config.php');
    require_once('./UserValidation.php');
    if(isset($_COOKIE[$config['cookieUserId']]) AND isset($_COOKIE[$config['cookieUsername']])) {
        $userValidation = new UserValidation();
        $userID = $_COOKIE[$config['cookieUserId']];
        $username= $_COOKIE[$config['cookieUsername']];
        $userValidation->setItemsInSessionAndCookies($userID, $username);
    }
    if (isset($_GET['login'])) {
        $userValidation = new UserValidation();
        $username = $_POST["username"];
        $password = $_POST["password"];
        $userValidation->login($username, $password);
    }
    if (isset($_GET['register'])) {
        $userValidation = new UserValidation();
        $username = $_POST["username"];
        $password = $_POST["password"];
        $forename = $_POST["forename"];
        $surname = $_POST["surname"];
        $userValidation->register($username, $password, $forename, $surname);
    }