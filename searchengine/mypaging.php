<?php
include 'connect.php';
?>

<!DOCTYPE html>
<html>
   <head>
   <title>paging2</title>
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
       <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
       <style>
       
      a:link {
    color: red;
          font-size: 35px;
}

/* visited link */
a:visited {
    color:#FF7300;
}

/* mouse over link */
a:hover {
    color: white;
}

/* selected link */
a:active {
    color: blue;
}
 .btn-primary {
    color: #fff;
    background-color: #FFFF8E;
    border-color: #0275d8;
}
       </style>
    </head>
    <body>
  <img  src="css/images/mythunpage2.png" class="img_paging">
            
 <form class="example" action="mypaging.php">
  <input type="text" placeholder="Search.." name="results_search">
  <button type="submit"><i class="fa fa-search" name="results_submit"></i></button>
</form>
<br>
        
         <?php
     $result_per_page =10;
   $sql="SELECT ID,URL FROM links_table";
    $result=mysqli_query($con,$sql);
      $num_of_results =mysqli_num_rows($result);
    $number_of_pages=ceil($num_of_results/$result_per_page);
        
        if(! isset($_GET['page'])){
            $page=1;
        } else{
            $page=$_GET['page'];
        }
       $page_start_limit=($page-1)*$result_per_page;
        
       $sql= 'SELECT URL FROM links_table LIMIT ' . $page_start_limit . ',' . $result_per_page;
        $result=mysqli_query($con,$sql);
        if(! $result)
        {
            echo "nooooooooooooooooooooooooooooooo";
        }
        $k=$page_start_limit;
        
        while($row = mysqli_fetch_array($result)){
            $w=$row['URL'];
            echo '<div class="show_link">';
        echo "<a href='".$w."'>Result page   $k  </a>";
            echo "<br>";
            echo "<p style='font-size:15px; color:#FFFF29;'>$w</p>";
            echo "<div class='snippest'>";
            echo "<p style='font-size:18px; color:white;'>Google's generation of page titles and descriptions is completely automated and takes into account  both the content of a page as well as references to it that appear on the. </p>";
            echo "</div>";
           
            echo '</div>';
             $k++;
        }
       
        echo "<br>";
       
        for($page=1 ;$page<=$number_of_pages;$page++)
        {
            echo" ";
           echo'<a class="btn btn-primary" href="mypaging.php?page=' .$page. '" role="button">'.$page.'</a>';
            
           // echo '<a href="mypaging.php?page=' .$page. '">' .$page . '</a>';
        }
        
        
    ?>
        
     
    </body>
    
   
</html>