<?php
$dsn='localhost';
$dbname='mylinks';
$user='root';
$pass='12345678';
$options =array
(
PDO::MYSQL_ATTR_INIT_COMMAND =>'SET NAMES UTF8',
);

   $con = mysqli_connect($dsn, $user, $pass, $dbname);
// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
?>