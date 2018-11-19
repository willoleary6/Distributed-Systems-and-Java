<?php
    if (session_status() == PHP_SESSION_NONE) {
        session_start();
    }

    $config = include('config.php');
    if(isset($_COOKIE[$config['cookieUserId']]) AND isset($_COOKIE[$config['cookieUsername']])) {
        header('Location: mainMenu.php');
    }
?>
<!DOCTYPE HTML>
<html>
<body>
<div>
    <h1>Register</h1>
    <form action="UserValidation.php?register=true" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        Forename: <input type="text" name="forename"><br>
        Surname: <input type="text" name="surname"><br>
        <input type="submit">
    </form>
</div>
<div>
    <h1>Login</h1>
    <form action="UserValidation.php?login=true" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit">
    </form>
</div>
</body>
</html>