<?php
$config = include('config.php');
if(!isset($_COOKIE[$config['cookieUserId']])) {
    header( "Location: index.php" );
}