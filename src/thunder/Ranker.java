package thunder;

import java.util.List;

public class Ranker{

    private List<Double> scored_doc;
    private Double individual_score;
    private Popularity p;
    private Relevance r;

    Ranker(String query){
        /*db = new RankerDBConnection();
        individual_score=0.0;*/
        p = new Popularity();
        r = new Relevance(query);
    }
    public void calculateScore(){
    }
    public static void main(String args[]){
        Ranker r1 = new Ranker("netherlands vs portugal today");
    }

}