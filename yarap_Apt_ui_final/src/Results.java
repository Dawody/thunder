import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/paging")
public class Results extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String spageid=request.getParameter("page");
        int pageid=Integer.parseInt(spageid);
        int total=10;

        String [] arr=new String[]{"https://www.geeksforgeeks.org/arrays-in-java/","https://www.facebook.com/","https://www.youtube.com/watch?v=2IqfF4WMKGc","https://github.com/",
                "https://www.google.com.eg/","https://fantasy.premierleague.com/a/login","http://www.yallakora.com/","https://www.filgoal.com/","http://codeforces.com/",
                "http://codeforces.com/","http://cafonline.com/","http://cima4up.tv/","http://twitter.com/","https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/",
                "https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/" ,"https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/","https://www.geeksforgeeks.org/"};
        int num_pages=(int) Math.ceil(arr.length/total);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<heed>");
        out.println("<title>"+"your search"+"</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Raleway:500italic,600italic,600,700,700italic,300italic,300,400,400italic,800,900' rel='stylesheet' type='text/css'>");
        out.println(" <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,300,300italic,400italic,600italic,700,900' rel='stylesheet' type='text/css'>");
        out.println("<link rel='stylesheet' type='text/css' href='css/animate.css'>");
        out.println("<link rel='stylesheet' type='text/css' href='css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");
        out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
        out.println(" <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'>"+"</script>");
        out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'>"+"</script>");
        out.println("<style>"+".grid-container {display: grid;justify-content: center;grid-template-columns: 50% 30%;margin-left:10%;}"+".Search_result { background-image:url(../css/images/mybackg.jpg); position: fixed;margin-top:0%; margin-bottom: 2%; height:37%; width:100%; background-color:white;padding: 1%; }"+"body{background-image:url(../css/images/mybackg.jpg);background-size: cover;background-attachment:fixed;background-repeat: no-repeat;}"+"a:link {color: #FF7300;font-size: 35px;}"+"a:visited { color:#FF7300;}"+"a:hover {color: white;}"+"a:active {color: blue;}"+".btn-primary {color: #fff;background-color: #FFFF8E;border-color: #0275d8;}"+"</style>");
        out.println("</head>");
        out.println("<body>");

       out.println("<div class='Search_result'>");
        out.println("<img  src='/css/images/logo.png' height='15%' width='15%' >");
        out.println("<form  action='Search_page' method='GET'>");
          out.println(" <div class='grid-container'>");
        out.println("<input type='text' style='width:70%;' class='form-control input-lg' id='home_search' required placeholder='Search for..' name='home_search'>");
            out.println("<input type='submit' style='width: 30%;' class='btn btn-info' id='home_search_bt'  value='Search' name='home_search_bt'>");
           out.println(" </div>");
        out.println("</form>");
           out.println(" <br>");
        out.println("<form  action='Regular_search' method='GET'>");
        out.println(" <div class='grid-container'>");
        out.println("<input type='text' style='width:70%;' class='form-control input-lg' required id='home_rsearch' placeholder=' Regular Search for..' name='home_rsearch'>");
        out.println("<input type='submit' style='width: 30%;' class='btn btn-info' id='home_rsearch_bt'  value='Regular Search' name='home_rsearch_bt'>");
        out.println(" </div>");
        out.println("</form>");
        out.println("</div>");
       out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
            out.println("<div style='margin-left:2%;' >");
        for(int i=pageid*total-total;i<pageid*total;i++){
            out.print("<a href='"+arr[i]+"' style='color:#45554;'>"+"page result"+i+"</a> ");
            out.println( "<br>");
            out.println("<p style='font-size:15px; color:#FFFF29;'>"+arr[i]+"</p>");
            out.println("<div class='snippest'>");
            out.println("<p style='font-size:18px; color:white;'>Google's generation of page titles and descriptions is completely automated and takes into account  both the content of a page as well as references to it that appear on the. </p>");
            out.println("</div>");
            out.println("<br>");
        }
        for(int i=1;i<=num_pages;i++) {
            out.print("<a  class='btn btn-primary' href='paging?page="+i+ "'role='button'>"+i+"</a>" );
            //echo'<a class="btn btn-primary" href="mypaging.php?page=' .$page.'" role="button">'.$page.'</a>';

        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
