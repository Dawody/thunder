package thunder;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import javax.xml.soap.Name;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.io.FileUtils.readFileToString;


class link {
    public String Name;
    public int id;

    public link(String Name, int id) {
        this.Name = Name;
        this.id = id;
    }
}

class robot {
    ArrayList<String> Allowed = new ArrayList<>();
    ArrayList<String> Disallowed = new ArrayList<>();

    public robot(ArrayList<String> allowed, ArrayList<String> disallowed) {
        Allowed = allowed;
        Disallowed = disallowed;
    }
}




public class BasicWebCrawler {

    static DBman dbman;
    static Pattern pattern = Pattern.compile("(https?):\\/\\/(www\\.)?([-a-zA-Z0-9@:%._\\+~#=]{2,256})\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");
    static Map<String,robot> myMap = new ConcurrentHashMap<String,robot>();
    public static void main(String[] args) throws Exception {
        Set<String> links = ConcurrentHashMap.newKeySet();
        BlockingQueue<link> urls = new LinkedBlockingQueue<>();


        dbman = new DBman();
//        int count = dbman.GetCounter();
//        int count2 = count;
//        AtomicInteger counter = new AtomicInteger(count);
//        dbman.SetCount(0);
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService executorService;
        while (true) {
            int count = dbman.GetCounter();
            int count2 = count;
            AtomicInteger counter = new AtomicInteger(count);
            executorService = Executors.newFixedThreadPool(50);

            dbman.GetQueueAndSet(count, links, urls);
            while (true) {
                if (counter.get() >= 5000) {
                    break;
                }
                if (!urls.isEmpty()) {
                    count2++;
                    link y = urls.remove();
                    executorService.execute(new crawler(y, links, urls,counter));

                }
            }
            System.out.println("reset2");
            executorService.shutdownNow();
            executorService.awaitTermination(10,TimeUnit.MINUTES);
            urls.clear();
            links.clear();
            counter.set(0);
            System.out.println("reset");
            dbman.Reset();
        }

    }

}

class crawler implements Runnable {
    private Set<String> links;
    private Set<String> CrawledLinks;
    private BlockingQueue<link> urls;
    private link URL;
    AtomicInteger counter;
//    AtomicInteger NumOfLinks = new AtomicInteger(0);
//    AtomicInteger NameIncrement = new AtomicInteger(0);

    public crawler(link URL, Set<String> l, BlockingQueue<link> u,AtomicInteger counter) {
        links = l;
        urls = u;
        this.URL = URL;
        this.counter = counter;

    }

    public static boolean RobotChecker(String url) {
        String[] strings = url.split("/");
        String RobotURL;
        if(strings.length>=2)
        {
            RobotURL = strings[0] + "//" + strings[2] + "/" + "robots.txt";
        }
        else
        {
            return false;
        }



        try {
            ArrayList<String> Allowed = new ArrayList<>();
            ArrayList<String> Disallowed = new ArrayList<>();
            if(BasicWebCrawler.myMap.containsKey(RobotURL))
            {
//                robot x;
//                System.out.println("found");
                Allowed = BasicWebCrawler.myMap.get(RobotURL).Allowed;
                Disallowed = BasicWebCrawler.myMap.get(RobotURL).Disallowed;
            }
            else
            {
//                System.out.println("not found");
                BufferedReader in = new BufferedReader(new InputStreamReader(new URL(RobotURL).openStream()));
                String line;

                Pattern p2 = Pattern.compile("^[ ]*(Disallow:) +([-a-zA-Z0-9@:%_\\+.~#?&//=]*)[ ]*$");
                Pattern p3 = Pattern.compile("^[ ]*(Allow:) +([-a-zA-Z0-9@:%_\\+.~#?&//=]*)[ ]*$");
//            Pattern p1 = Pattern.compile("([ ]*User-agent:) +((\\*)|(ThunderBot))");
                Pattern p1 = Pattern.compile("^[ ]*(User-agent:) +(.*)[ ]*$");
                int found = 0;
                int first = 0;
                while ((line = in.readLine()) != null) {
//                System.out.println(line);
                    Matcher m1 = p1.matcher(line);
                    Matcher m2 = p2.matcher(line);
                    Matcher m3 = p3.matcher(line);
                    if (m1.matches()) {
                        if (first == 0) {
                            first = 1;
                            found = 0;
                        }
                        if (m1.group(2).equals("*") || m1.group(2).equals("ThunderBot")) {
                            found = 1;
                        }
//                    System.out.println(m1.group(2));
//                    System.out.println("m1");


                    } else if (m2.matches()) {
                        first = 0;
                        if (found == 1) {
                            Disallowed.add(m2.group(2));
                        }
//                    System.out.println(m2.group(2));
//                    System.out.println("m2");

                    } else if (m3.matches()) {
                        first = 0;
                        if (found == 1) {
                            Allowed.add(m3.group(2));
                        }
//                    System.out.println(m3.group(2));
//                    System.out.println("m3");
                        in.close();
                        BasicWebCrawler.myMap.put(RobotURL,new robot(Allowed,Disallowed));
                    }
                }

            }
            int MaxLength = 0;
            boolean Allow = true;
            for (int i = 0; i < Allowed.size(); i++) {
//                System.out.println(Allowed.get(i));
                if (url.contains(Allowed.get(i))) {
//                    System.out.println(Allowed.get(i));
                    MaxLength = Math.max(MaxLength, Allowed.get(i).length());
                }
            }
            for (int i = 0; i < Disallowed.size(); i++) {
//                System.out.println(Disallowed.get(i));
                if (url.contains(Disallowed.get(i))) {
                    if (Disallowed.get(i).length() > MaxLength) {
                        Allow = false;
                    }
                }
            }
            return Allow;

        } catch (MalformedURLException e) {
            return true;
        } catch (IOException e) {
            return true;
        }

    }

    @Override
    public void run() {

        if(URL.id==-1)
        {
            try {
                URL.id=BasicWebCrawler.dbman.GetLinkId(URL.Name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (links.add(URL.Name)) {
            try {
                if (RobotChecker(URL.Name)) {
//
//                    Connection.Response html = Jsoup.connect(URL.Name).execute();
//                    System.out.println(html.body());
                    Document document = Jsoup.connect(URL.Name).get();
                    Elements paragraphs = document.select("p");
//                    for(Element p : paragraphs)
//                    {
//                        System.out.println(p.text());
//                    }


                    File f = new File("documents/" + URL.id + ".html");
//                    File f2 = new File("temp.html");
                    String s;
                    if (f.exists()&&BasicWebCrawler.dbman.GetVisited(URL.id)!=-1) {
//                        FileWriter fi = new FileWriter("documents/" + URL.id + ".html");
//                        FileWriter fi = new FileWriter("documents/"+ URL.id+"temp.html");

//                        BufferedWriter writer = new BufferedWriter(fi);
//                        String t =  readFileToString(f/*,StandardCharsets.UTF_8*/);
//                        writer.write(document.toString());
//                        writer.write(html.body());
//                        writer.close();
//                        fi.close();
//                        File file1 = new File("file1.txt");
//                        File file2 = new File("file2.txt");
//                        boolean isTwoEqual = FileUtils.contentEquals(file1, file2);
                        String TextToCompare = new String(Files.readAllBytes(Paths.get("documents/" + URL.id + ".html")));
//                        String TextToCompare2 = new String(Files.readAllBytes(Paths.get("documents/temp.html")));
                        if (TextToCompare.equals(document.outerHtml())) {
                            s="exists and equal";
                            BasicWebCrawler.dbman.SetLastChanged(URL.id, 1);
                        } else {
//                            System.out.println(t);
                            s="exists and not equal";
//                            FileWriter writer = new FileWriter(f);
//                            writer.write(document.toString());
//                            writer.close();
                            FileWriter fi = new FileWriter("documents/" + URL.id + ".html");
                            BufferedWriter writer = new BufferedWriter(fi);
                            writer.write(document.outerHtml());
//                            writer.write(html.body());
                            writer.close();
                            fi.close();
                            BasicWebCrawler.dbman.SetLastChanged(URL.id, -1);

                        }
                    } else {
                        s="does not exist";
//                        FileWriter writer = new FileWriter(f);
//                        writer.write(document.toString());
//                        writer.close();
                        FileWriter fi = new FileWriter("documents/" + URL.id + ".html");
                        BufferedWriter writer = new BufferedWriter(fi);
                        writer.write(document.toString());
                        writer.close();
                        fi.close();
                        BasicWebCrawler.dbman.SetLastChanged(URL.id, -1);

                    }
                    PreparedStatement ps = DBman.myconn.prepareStatement("INSERT IGNORE into links (link) VALUES (?)");
                    PreparedStatement ps2 = DBman.myconn.prepareStatement("INSERT IGNORE into in_out (link1,link2) VALUES (?,?)");
                    Elements linksOnPage = document.select("a[href]");
                    ArrayList<link> temp= new ArrayList<>();
                    for (Element page : linksOnPage) {
                        Matcher m1 = BasicWebCrawler.pattern.matcher(page.absUrl("href"));
                        if(m1.matches()&&page.absUrl("href").length()<=255)
                        {
                            String y= page.absUrl("href");
                            if(!y.endsWith("/"))
                                y = y.concat("/");
                            y = new StringBuilder(y).replace(m1.start(3), m1.end(3), m1.group(3).toLowerCase()).toString();
                            y = new StringBuilder(y).replace(m1.start(1), m1.end(1), m1.group(1).toLowerCase()).toString();
//                            System.out.println(y);
//                            int x = BasicWebCrawler.dbman.AddLinK(page.absUrl("href"));


                            ps.setString(1,y);
                            ps.addBatch();
                            ps2.setString(1,URL.Name);
                            ps2.setString(2,y);
                            ps2.addBatch();
                            temp.add(new link(y, -1));
                        }





                    }
                    System.out.println(Thread.currentThread().getName() + "  " + URL.Name +"  " + s+"  "+URL.id);
                    ps.executeBatch();
                    ps2.executeBatch();
//                    int nn = 0;
//                    ResultSet rs = ps.getGeneratedKeys();
//                    while (rs.next()) {
//                        nn++;
////                        int id = rs.getInt(1);
//                    }
//                    System.out.println(nn+"  "+temp.size());
//                    if(urls.size()+counter.get()<=6000)
//                    {
//                        urls.addAll(temp);
//                    }
//                    for (int i=0;i<temp.size();i++)
//                    {
//                        urls.add(temp.get(i));
//                    }
                    BasicWebCrawler.dbman.addOutLinks(URL.id, temp.size());
                    BasicWebCrawler.dbman.IncCounter();
                    BasicWebCrawler.dbman.SetVisited(URL.id);
                    counter.incrementAndGet();
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}