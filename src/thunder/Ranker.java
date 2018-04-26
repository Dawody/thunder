package thunder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ranker{

    private List<Double> scored_doc;
    private Double individual_score;
    private Popularity p;
    private Relevance r;

    Ranker(){
        /*db = new RankerDBConnection();
        individual_score=0.0;*/
        p = new Popularity();
        r = new Relevance();
    }
    public void calculateScore(){
    }
    public static void main(String args[]) throws SQLException {
        Ranker r1 = new Ranker();
        /*Map<String, Double> mr;
        String [] s1 = {"netherlands","vs","portugal", "today"};
        mr = r1.r.tfidf(s1);*/
        ArrayList<String> s = r1.p.page_ranker();
        for (int i=0; i<s.size();i++)
        {
            System.out.print(s.get(i)+" ");
        }

    }

}