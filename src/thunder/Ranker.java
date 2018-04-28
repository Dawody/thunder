package thunder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ranker{

    protected static class Q_Obj {
        String s;
        String [] s1;
        int ph;
        public Q_Obj(String s, String[] sa, int p)
        {
            this.s = s;
            this.s1 = sa;
            this.ph = p;
        }

        public String getQuery() {
            return s;
        }

        public int getPh() {
            return ph;
        }
        public String[] getQuery_words() {
            return s1;
        }
    }

    Ranker(){
    }

    public static void main(String args[]) throws SQLException {
        /*String s = "segment tree";
        String [] s1 = s.split(" ");
        int x = 0;
        Q_Obj q = new Q_Obj(s,s1,x);
        Relevance r = new Relevance();
        ArrayList<String> mr = r.decide(q);
        System.out.println(mr.size());
        for (int i=0; i<mr.size();i++)
        {
            System.out.println(mr.get(i));
        }*/
        Popularity p = new Popularity();
        p.page_ranker();
    }
}