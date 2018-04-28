//package dineshkrish;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/paging")
public class Results extends HttpServlet {
    //private Ranker r = new Ranker();
      String myfirst_q="";
      int c=0;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String query = request.getParameter("home_search");
        String spageid=request.getParameter("page");
        int pageid=Integer.parseInt(spageid);
        //show if query between double qutoes or not if yes return 1 no return 0
        int ph=-1;
        String[] query_words;
        String []w;


        c++;
        if(query!=null) {
            if(pageid==1 &&query.length()>0){myfirst_q=query;}
            ph = clacph(myfirst_q);
            query_words = getquery_words(ph, myfirst_q);
            Query_object my_query = new Query_object(query_words, ph);
            w = my_query.getQuery_words();
        }

        //split query into array of words

        boolean typical=false;
        //creat object from query and ph

        System.out.println(myfirst_q);
        MySpellChecker spellChecker = new MySpellChecker("C:\\Users\\HeshamMahmoud95\\IdeaProjects\\yarap_Apt_ui_final\\web\\WEB-INF\\dictionary\\my_dictionary.txt");
        String after="";
      after=spellChecker.doCorrection(myfirst_q);
        System.out.println(after);
        //ArrayList<String> res = r.relevance(my_query);


        int total=10;
        String after_correction="";
        System.out.println("Before Correction : "+ myfirst_q);
     //   after_correction=my_do.get_spell_correct(query);
        System.out.println("Before Correction : "+ after_correction);
        if (after!=myfirst_q)
        {
            typical=true;
        }
        String [] arr=new String[]{"https://www.geeksforgeeks.org/","http://www.geeksforgeeks.org/hard/","http://www.geeksforgeeks.org/geometric-algorithms/","https://practice.geeksforgeeks.org/recent.php?ref=home/","https://practice.geeksforgeeks.org/topic-tags/?ref=home/","https://practice.geeksforgeeks.org/Medium/0/0/?ref=home/","http://www.geeksforgeeks.org/mathematical-algorithms/","https://www.geeksforgeeks.org/two-dimensional-segment-tree-sub-matrix-sum/"
        ,"https://www.geeksforgeeks.org/hard/","https://www.geeksforgeeks.org/tag/segment-tree/","https://www.geeksforgeeks.org/tag/array-range-queries/","https://www.geeksforgeeks.org/multistage-graph-shortest-path/"
        ,"https://www.geeksforgeeks.org/page/2/","https://www.geeksforgeeks.org/page/20/","http://www.geeksforgeeks.org/data-structures/","https://www.geeksforgeeks.org/#content/","https://www.geeksforgeeks.org/"
        ,"http://www.geeksforgeeks.org/operating-systems/","http://www.geeksforgeeks.org/fundamentals-of-algorithms/","http://www.geeksforgeeks.org/gate-cs-notes-gq/"};
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
        out.println("<form  action='paging' method='GET'>");
        out.println(" <div class='grid-container'>");
        out.println("<input type='hidden' name='page' value='1'>");
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
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");

        if(typical) {
            out.println("<p style='color:yellow; font-size:30px;'>" + "Do you maean: " + after + "</p>");

        }
        out.println("<div style='margin-left:2%;' >");
        for(int i=pageid*total-total;i<pageid*total;i++){
            snippets snip=new snippets();
            String sn=snip.getSnippet(arr[i],myfirst_q);
            out.print("<a href='"+arr[i]+"' style='color:#45554;'>"+"page result"+i+"</a> ");
            out.println( "<br>");
            out.println("<p style='font-size:15px; color:#FFFF29;'>"+arr[i]+"</p>");
            out.println("<div class='snippest'>");
            out.println("<p style='font-size:18px; color:white;'>"+sn+"</p>");
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

