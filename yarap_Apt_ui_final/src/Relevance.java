import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

//I will use stemmer(String word)-> to stem query words,
// getLink(String word,int type)-> to get list of links that have this word,
// getTotal(String link)-> to get total number of words in the document,
// getCount(String link , String word , int type)-> to get number of occurrences in the document

public class Relevance { // TF-IDF score for keywords in query found in the document
    private indexer ind;
    private DBman db;

    Relevance()
    {
        ind = new indexer();
        db = new DBman();
    }
    public ArrayList<String> decide(Query_obj qb) throws SQLException {
        ArrayList<String> o = new ArrayList<String>();
        int x = qb.getPh();
        saveQueries(qb.getQuery().toLowerCase());
        switch (x)
        {
            case 1:
                o = phraseSearch(qb.getQuery_words());
                break;
            default:
                o = tfidf(qb.getQuery_words(),x);
                break;
        }
        return o;
    }
    private class phraseObj {
        private String word;
        private ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();

        private phraseObj(String w) { this.word = w.toLowerCase(); }
        public void setPositions(ArrayList<Integer> positions) { this.positions.add(positions); }
        public ArrayList<ArrayList<Integer>> getPositions() { return positions; }
        public ArrayList<Integer> getPosition(int i) { return positions.get(i); }
        public String getWord() { return word; }
    }

    private class TfidfObj {
        private String org_word;
        private String stm_word = "";
        private ArrayList<String> org_links = new ArrayList<String>();
        private ArrayList<String> stm_links = new ArrayList<String>();
        private ArrayList<Double> org_links_tf = new ArrayList<Double>();
        private ArrayList<Double> stm_links_tf  = new ArrayList<Double>();
        private double org_idf = 0.0;
        private double stm_idf = 0.0;
        private Map<String,Integer> org_count = new HashMap<String, Integer>();
        private Map<String,Integer> stm_count = new HashMap<String, Integer>();

        private TfidfObj(String s) { this.org_word = s.toLowerCase(); }
        public void setOrg_idf(double org_idf) { this.org_idf = org_idf; }
        public void setStm_idf(double stm_idf) { this.stm_idf = stm_idf; }
        public void setOrg_links(ArrayList<String> org_links) { this.org_links = org_links; }
        public void setStm_links(ArrayList<String> stm_links) { this.stm_links = stm_links; }
        public void setStm_word(String stm_word) { this.stm_word = stm_word; }
        public void setOrg_link_tf(double org_link_tf) { this.org_links_tf.add(org_link_tf); }
        public void setStm_link_tf(double stm_link_tf) { this.stm_links_tf.add(stm_link_tf); }
        public void setOrg_count(Map<String, Integer> org_count) { this.org_count = org_count; }
        public void setStm_count(Map<String, Integer> stm_count) { this.stm_count = stm_count; }
        public ArrayList<Double> getOrg_links_tf() { return org_links_tf; }
        public ArrayList<Double> getStm_links_tf() { return stm_links_tf; }
        public double getOrg_idf() { return org_idf; }
        public double getStm_idf() { return stm_idf; }
        public ArrayList<String> getOrg_links() { return org_links; }
        public ArrayList<String> getStm_links() { return stm_links; }
        public String getOrg_word() { return org_word; }
        public String getStm_word() { return stm_word; }
        public int getOrg_count(String i) { return org_count.get(i); }
        public int getStm_count(String i) { return stm_count.get(i); }
    }

    private void saveQueries(String q) {
        //save queries in db in table queries
        String query ="INSERT IGNORE INTO queries (query) VALUES ('"+q+"')";
        db.execute(query);
    }

    private ArrayList<String> phraseSearch(String[] query) throws SQLException {
        ArrayList<phraseObj> phrase_list = new ArrayList<phraseObj>();
        ArrayList<String> intersect_list = new ArrayList<String>();
        ArrayList<String> unintersect_list = new ArrayList<String>();
        ArrayList<String> outp = new ArrayList<String>();
        phraseObj w = new phraseObj(query[0].toLowerCase());
        phrase_list.add(w);
        intersect_list = ind.getLink(w.getWord(), 1);
        for (int j = 1; j < query.length; j++) {
            phraseObj o = new phraseObj(query[j]);
            unintersect_list = ind.getLink(o.getWord(), 1);
            intersect_list.retainAll(unintersect_list); //intersect among links
            phrase_list.add(o);
        }
        for (int i = 0; i < phrase_list.size(); i++) {
            for (int j = 0; j < intersect_list.size(); j++) {
                w = phrase_list.get(i);
                w.setPositions(ind.getPositions(intersect_list.get(j), w.getWord(), 1));
            }
        }
        Set<String> out_list = new HashSet<String>();
        w = phrase_list.get(0);
        int flag, pos;
        for (int k = 0; k < w.getPositions().size(); k++) {
            for (int j = 0; j < w.getPosition(k).size(); j++) {
                pos = w.getPosition(k).get(j) + 1;
                flag = 1;
                for (int i = 1; i < phrase_list.size(); i++) {
                    ArrayList<Integer> poss = phrase_list.get(i).getPosition(k);
                    if (poss.contains(pos)) {
                        pos++;
                        flag++;
                        if (flag == phrase_list.size()) {
                            out_list.add(intersect_list.get(k));
                            break;
                        }
                    } else break;
                }
                if (flag == phrase_list.size()) {
                    break;
                }
            }
        }
        Map<String, Double> result = new HashMap<String, Double>();
        for (String s : out_list) {
            ResultSet rs = db.execute("SELECT score FROM links WHERE link = '" + s + "'");
            if(!rs.wasNull()) {
                result.put(s, rs.getDouble(7));
            }
        }
        Set<Entry<String, Double>> set = result.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(
                set);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(int i = 0; i<list.size();i++) {
            System.out.println(list.get(i).getKey()+" "+list.get(i).getValue());
            outp.add(list.get(i).getKey());
        }

        return outp;
    }

    private ArrayList<String> tfidf(String[] query, int x){ //tf() * idf() for each document
        int []xarr={0,0};
        if(x == 2)
        {
            xarr[0]=3;
            xarr[1]=2;
        }
        else {
            xarr[0]=1;
            xarr[1]=0;
        }
        ArrayList<TfidfObj> data = new ArrayList<TfidfObj>();
        ArrayList<String> output = new ArrayList<String>();
        int[] a={0,0};
        //----------------------------------------------------------------------------------
        //tf part
        a = tf(query, data, a,xarr);
        //idf part
        idf(data, a);
        //tf-idf part
        tf_idf(data, output);
        return output;
    }
    private int[] tf(String[] query, ArrayList<TfidfObj> d, int[] arr ,int[] x) {
        for(int j = 0; j < query.length; j++) {
            TfidfObj o = new TfidfObj(query[j]);
            o.setStm_word(ind.stemmer(o.getOrg_word()));
            o.setOrg_links(ind.getLink(o.getOrg_word(), x[0]));
            o.setStm_links(ind.getLink(o.getStm_word(), x[1]));
            if (o.getOrg_links().size() > 0) {
                o.setOrg_count(ind.getCount(o.getOrg_links(), query[j], x[0]));
                if (o.getStm_links().size() > 0) {
                    o.setStm_count(ind.getCount(o.getStm_links(), o.getStm_word(), x[1]));
                    arr[0] += o.getOrg_links().size();
                    arr[1] += o.getStm_links().size();

                    for (int i = 0; i < o.getOrg_links().size(); i++) {
                        double total = ind.getTotal(o.getOrg_links().get(i));
                        double count = o.getOrg_count(o.getOrg_links().get(i));
                        double tf = count / total * 1000;
                        o.setOrg_link_tf(tf);
                    }
                    for (int i = 0; i < o.getStm_links().size(); i++) {
                        double total = ind.getTotal(o.getStm_links().get(i));
                        double count = o.getStm_count(o.getStm_links().get(i));
                        double tf = count / total * 1000;
                        o.setStm_link_tf(tf);
                    }
                    d.add(o);
                }
            }
        }
        return arr;
    }

    private void idf(ArrayList<TfidfObj> d, int[] arr) {
        for(int i = 0; i < d.size(); i++) {
            d.get(i).setOrg_idf(Math.log((double)arr[0] /
                    (double)d.get(i).getOrg_links().size()));
            d.get(i).setStm_idf(Math.log((double)arr[1] /
                    (double)d.get(i).getStm_links().size()));
        }
    }

    private void tf_idf(ArrayList<TfidfObj> d, ArrayList<String> outp) {
        Map<String, Double> set_outp = new HashMap<String, Double>();
        TfidfObj ob1;
        ArrayList<String> ob2,ob3;
        for (int i = 0; i < d.size(); i++) {
            ob1 = d.get(i);
            ob2 = ob1.getOrg_links();
            ob3 = ob1.getStm_links();
            for (int j = 0; j < ob2.size(); j++) {
                if (!set_outp.containsKey(ob2.get(j))) {
                    set_outp.put(ob2.get(j), ob1.getOrg_links_tf().get(j) * ob1.getOrg_idf()); }
                else {
                    set_outp.put(ob2.get(j), ob1.getOrg_links_tf().get(j) * ob1.getOrg_idf()
                            + set_outp.get(ob2.get(j))); }
            }
            for (int k = 0; k < ob3.size(); k++) {
                if (!set_outp.containsKey(ob3.get(k))){
                    set_outp.put(ob3.get(k), ob1.getStm_links_tf().get(k) * ob1.getStm_idf()*0.1); }
                else {
                    set_outp.put(ob3.get(k), ob1.getStm_links_tf().get(k) * ob1.getStm_idf()*0.1
                            + set_outp.get(ob3.get(k))); }
            }
        }

        Set<Entry<String, Double>> set = set_outp.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(
                set);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(int i = 0; i<list.size();i++) {
            System.out.println(list.get(i).getKey()+" "+list.get(i).getValue());
            outp.add(list.get(i).getKey());
        }
    }
}
