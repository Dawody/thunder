import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/auto_complete_Servlet")
public class auto_complete_Servlet extends HttpServlet {



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   String keyword=request.getParameter("home_search");
      if(keyword!=null)
      {
          response.setContentType("text/html");
          response.getWriter().write( generate_jsondata(keyword));
      }
    }
    public  String generate_jsondata(String keyword)
    {
        StringBuffer returndata=null;
        if(keyword.equals("ja")){

         returndata=new StringBuffer("{\"topic\":{");
         returndata.append("\"name\": \"Suggestion\",");
         returndata.append("\"tutorial\": [");
         returndata.append("{\"name\": \"java tutorial\"},");
            returndata.append("{\"name\": \"javascript\"},");
            returndata.append("{\"name\": \"javazeft\"}]");
            returndata.append("}}");
        }
        else if(keyword.equals("goo"))
        {
            returndata=new StringBuffer("{\"topic\":{");
            returndata.append("\"name\": \"Suggestion\",");
            returndata.append("\"tutorial\": [");
            returndata.append("{\"name\": \"google\"},");
            returndata.append("{\"name\": \"googlejuy\"},");
            returndata.append("{\"name\": \"javazeft\"}]");
            returndata.append("}}");
        }
        else {
            returndata=new StringBuffer("{\"topic\":{");
            returndata.append("\"name\": \"Suggestion\",");
            returndata.append("\"tutorial\": [");
            returndata.append("{\"name\": \"google\"},");
            returndata.append("{\"name\": \"googlejuy\"},");
            returndata.append("{\"name\": \"javazeft\"}]");
            returndata.append("}}");
        }
        return returndata.toString();
    }
}
