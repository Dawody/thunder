package thunder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class Popularity extends Ranker{ // PageRank algorithm, score for edges between nodes
    static double InitialScore = 1;

    private class LinkNode {
        private String name;
        private long out_edges;
        private ArrayList<String> linked_nodes;
        private double init_score;
        private double first_score;
        private double second_score;
        private double third_score;
        private double fourth_score;
        private double fifth_score;

        LinkNode(String name, long n) {
            this.name = name;
            this.out_edges = n;
            this.init_score = InitialScore;
            this.linked_nodes = new ArrayList<String>();
        }
        public void setFirst_score(double score, int n) { this.first_score =  this.init_score + score; }
        public void setSecond_score(double score, int n) { this.second_score = this.first_score + score; }
        public void setThird_score(double score, int n) { this.third_score = this.second_score + score; }
        public void setFourth_score(double score, int n) { this.fourth_score = this.third_score + score; }
        public void setFifth_score(double score, int n) { this.fifth_score = this.fourth_score + score; }
        public double getFifth_score() { return this.fifth_score; }
        public double getFourth_score() { return this.fourth_score; }
        public double getThird_score() { return this.third_score; }
        public double getSecond_score() { return this.second_score; }
        public double getFirst_score() { return this.first_score; }
        public double getInit_score() { return this.init_score; }
        public String getName() { return this.name; }
        public long getOut_edges() { return this.out_edges; }
        public void setLinked_nodes(String l) { this.linked_nodes.add(l); }
        public ArrayList<String> getLinked_nodes() { return this.linked_nodes; }
    }

    private ArrayList<LinkNode> links = new ArrayList<LinkNode>();
    // link the pagerank with dbmaria
    public DBman db;

    Popularity()
    {
        db = new DBman();
    }
    private void setScore(int i, LinkNode ln, double sc, int si)
    {
        switch (i)
        {
            case 0:
                ln.setFirst_score(sc,si);
                break;
            case 1:
                ln.setSecond_score(sc,si);
                break;
            case 2:
                ln.setThird_score(sc,si);
                break;
            case 3:
                ln.setFourth_score(sc,si);
                break;
            case 4:
                ln.setFifth_score(sc,si);
                break;
        }
    }
    private double getScore(int i, LinkNode ln)
    {
        double s = 0.0;
        switch (i)
        {
            case 0:
                s = ln.getInit_score();
                break;
            case 1:
                s = ln.getFirst_score();
                break;
            case 2:
                s = ln.getSecond_score();
                break;
            case 3:
                s = ln.getThird_score();
                break;
            case 4:
                s = ln.getFourth_score();
                break;
        }
        return s;
    }

    public void page_ranker() throws SQLException {
        //get data from tables of thunder
        String q1 = "SELECT link, out_links FROM links WHERE visited != -1";
        ResultSet t_links = db.execute(q1);
        String x;
        LinkNode link;
        //handle the links edges among themselves
        while (t_links.next())
        {
            link = new LinkNode(t_links.getString("link"),
                    t_links.getLong("out_links"));
            x=link.getName();
            String q2 = "SELECT link1 FROM in_out WHERE in_out.link2='"+x+"'";
            ResultSet t_edges = db.execute(q2);
            while(t_edges.next())
            {
                link.setLinked_nodes(t_edges.getString("link1"));
            }
            links.add(link);
        }
        //iterations to update the score of documents
        ArrayList<String> update_links;
        String link_name;
        double score;
        LinkNode linki;
        for(int l=0;l<5;l++) {
            for (int i = 0; i < links.size(); i++) {
                score = 0;
                update_links = links.get(i).getLinked_nodes();
                linki=links.get(i);
                for (int j = 0; j < update_links.size(); j++) {
                    link_name = update_links.get(j);
                    for (int k = 0; k < links.size(); k++) {
                        if (links.get(k).getName().equals(link_name) && links.get(k).getOut_edges()>0) {
                            link = links.get(k);
                            score += (getScore(l,link) / link.getOut_edges());
                        }
                    }
                }
                setScore(l,linki, score, links.size());
            }
        }

        //sort arraylist of links by score
        links.sort(Comparator.comparingDouble(LinkNode::getFifth_score));
        PreparedStatement ps = DBman.myconn.prepareStatement("UPDATE links SET score=? WHERE link=?");
        for(int i=links.size()-1;i>=0;i--)
        {
            System.out.println(links.get(i).getName()+" "+links.get(i).getFifth_score());
            ps.setDouble(1,links.get(i).getFifth_score());
            ps.setString(2,links.get(i).getName());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}
