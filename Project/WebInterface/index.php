<?php
    $config = include('config.php');
    if(isset($_COOKIE[$config['cookieUserId']])) {
        header( "Location: mainMenu.php" );
    }
?>
<!DOCTYPE HTML>
<html>
<body>
<div>
    <h1>Register</h1>
    <form action="register.php" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        Forename: <input type="text" name="forename"><br>
        Surname: <input type="text" name="surname"><br>
        <input type="submit">
    </form>
</div>
<div>
    <h1>Login</h1>
    <form action="login.php" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit">
    </form>
</div>
</body>
</html>
