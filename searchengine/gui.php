<?php
include 'connect.php';
?>

<!DOCTYPE html>
<html>

<head>
    <title>DEEP WEB YA RAY2</title>
    <meta charset="utf-8">
    <link href='https://fonts.googleapis.com/css?family=Raleway:500italic,600italic,600,700,700italic,300italic,300,400,400italic,800,900' rel='stylesheet' type='text/css'>
        
        <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,300,300italic,400italic,600italic,700,900' rel='stylesheet' type='text/css'>
        
         <!-- Animate CSS -->
        <link rel="stylesheet" type="text/css" href="css/animate.css">
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <!-- CSS File-->
        <link rel="stylesheet" type="text/css" href="css/style.css"> 
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
    
    .btn-info {
    color: #fff;
    background-color: #FF7300;
    border-color: #fff;
        width: 50%;
        height: 140%;
    font-size: 20px;
}
    
    </style>
    
</head>
    <body>
         <img  src="css/images/thun.png" class="img_home">
        <div class="content">
        <form  action="mypaging.php" method="post">
        <input type="text" class="form-control input-lg" id="input-lg" placeholder="Search for.." name="home_search">
            <br>
            <div class="grid-container">
            <input type="submit" class="btn btn-info" value="Search" name="home_search_bt"> 
                <input type="submit" class="btn btn-info" value="Regular Search" name="home_Rsearch_bt">
                </div>
        </form>
            </div>
      </body>
</html>