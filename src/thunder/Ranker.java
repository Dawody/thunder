package thunder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ranker{

    Ranker(){
    }

    public static void main(String args[]) throws SQLException {
        String s = "segment tree implementation in java";
        String [] s1 = s.split(" ");
        int x = 1;
        Query_obj q = new Query_obj(s,s1,x);
        Relevance r = new Relevance();
        ArrayList<String> mr = r.decide(q);
        System.out.println(mr.size());
        for (int i=0; i<mr.size();i++)
        {
            System.out.println(mr.get(i));
        }
        /*Popularity p = new Popularity();
        p.page_ranker();*/
    }
}