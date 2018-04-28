//package thunder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ranker{
    
    Ranker(){
    }
    
    public static void main(String args[]) throws SQLException {
        String s = "segment tree implementation java"; // take care of (in) damage the array numbering!
        String [] s1 = s.split(" ");
//        //---------------------------------------------------------------------
//        indexer indx = new indexer();
//        //indx.initTotal();
//        String word,small_word,stem_word;
//        Map<String, Integer> stopWordList =indx.getStopWords();
//        
//        
//        for(int i =0 ; i<s1.length ; i++ ){
//            word = s1[i];
//            // in this section , do what you need for indexing the original word
//            small_word = word.toLowerCase();
//            stem_word = indx.stemmer(small_word);
//            stem_word = stem_word.replaceAll(" ","");
//            if(stopWordList.get(stem_word)!=null || stem_word.equals(""))
//                continue;
//            s1[i]=stem_word;
//            
//            System.out.println("s["+i+"] = "+s1[i]);
//            
//        }
//        //---------------------------------------------------------------------
        int x = 0;
        Query_obj q = new Query_obj(s,s1,x);
        Relevance r = new Relevance();
        ArrayList<String> mr = r.decide(q);
        /*System.out.println(mr.size());
        for (int i=0; i<mr.size();i++)
        {
        System.out.println(mr.get(i));
        }*/
        /*Popularity p = new Popularity();
        p.page_ranker();*/
    }
}