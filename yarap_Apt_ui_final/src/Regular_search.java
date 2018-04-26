import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/Regular_search")
public class Regular_search extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String query = request.getParameter("home_rsearch");
        //show if query between double qutoes or not if yes return 1 no return 0
        int ph= clacph(query);
        //split query into array of words
        String[] query_words = getquery_words(ph,query);
        //creat object from query and ph
        Query_object my_query=new Query_object(query_words,ph);
        String []w=my_query.getQuery_words();

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
        out.print("<a  class='btn btn-primary' style='font-size:30px; margin-top:10%;' href='paging?page="+"1"+ "'role='button'>"+"Click to show Result of search"+"</a>" );
        out.println("</body>");
        out.println("</html>");
    }
    public int clacph(String query)
    {
        int ph=0; int flagb=0; int flage=0; int count=0;


        for(int i=0;i<query.length();i++)
        {
            if(i==0 && query.charAt(i)=='"')
            {
                flagb=1;
            }
            if(i==query.length()-1 && query.charAt(i)=='"')
            {
                flage=1;
            }
            if(query.charAt(i)=='"')
            {
                count++;
            }

        }
        if(flagb==1&&flage==1&&count%2==0)
        {
            ph=1;
        }
        else
        {
            ph=0;
        }
        return ph;
    }
    String[] getquery_words(int ph,String query)
    {
        if(ph==1) {
            StringBuilder sb = new StringBuilder(query);
            sb.deleteCharAt(0);
            sb.deleteCharAt(query.length() - 2);
            query = sb.toString();
        }
        String[] query_words = query.split(" ");
        return query_words;
    }
}
