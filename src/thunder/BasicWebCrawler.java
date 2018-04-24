package thunder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import javax.xml.soap.Name;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
//import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class link {
    public String Name;
    public int id;

    public link(String Name, int id) {
        this.Name = Name;
        this.id = id;
    }
}

class robot {
    ArrayList<String> Allowed = new ArrayList<String>();
    ArrayList<String> Disallowed = new ArrayList<String>();

    public robot(ArrayList<String> allowed, ArrayList<String> disallowed) {
        Allowed = allowed;
        Disallowed = disallowed;
    }
}




public class BasicWebCrawler {

    static DBman dbman;
    static Pattern pattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");
    static Map<String,robot> myMap = new ConcurrentHashMap<String,robot>();
    public static void main(String[] args) throws Exception {
        Set<String> links = ConcurrentHashMap.newKeySet();
        BlockingQueue<link> urls = new LinkedBlockingQueue<>();


        dbman = new DBman();
        int count = dbman.GetCounter();
        AtomicInteger counter = new AtomicInteger(count);
//        dbman.SetCount(0);
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService executorService;
        while (true) {
            executorService = Executors.newFixedThreadPool(1);

            dbman.GetQueueAndSet(count, links, urls);
            while (true) {
                if (counter.get() >= 100) {
                    break;
                }
                if (!urls.isEmpty()) {
                    link y = urls.remove();
                    executorService.execute(new crawler(y, links, urls,counter));

                }
            }
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
//            System.out.println("hoho"+url);
            RobotURL = strings[0] + "//" + strings[2] + "/" + "robots.txt";
//            System.out.println(RobotURL);
        }
        else
        {
            return false;
        }



        try {
            ArrayList<String> Allowed = new ArrayList<String>();
            ArrayList<String> Disallowed = new ArrayList<String>();
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
                String line = null;

                Pattern p2 = Pattern.compile("^[ ]*(Disallow:) +([-a-zA-Z0-9@:%_\\+.~#?&//=]*)[ ]*$");
                Pattern p3 = Pattern.compile("^[ ]*(Allow:) +([-a-zA-Z0-9@:%_\\+.~#?&//=]*)[ ]*$");
//            Pattern p1 = Pattern.compile("([ ]*User-agent:) +((\\*)|(ThunderBot))");
                Pattern p1 = Pattern.compile("^[ ]*(User-Agent:) +(.*)[ ]*$");
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
                System.out.println(Disallowed.get(i));
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

//        String URL;
//        System.out.println(Thread.currentThread().getName());
//        while(true)
        /*if (!urls.isEmpty())*/
        //            System.out.println(URL);

        if (links.add(URL.Name)) {
            try {
                if (RobotChecker(URL.Name)) {

                    Document document = Jsoup.connect(URL.Name).get();
//                    BufferedWriter writer = new BufferedWriter(new FileWriter("documents/" +URL.id+".html"));
//                    writer.write(document.toString());
//                    if(URL.id==-1)
//                        URL.id = BasicWebCrawler.dbman.AddLinK(URL.Name);
                    File f = new File("documents/" + URL.id + ".html");
//                    File f2 = new File("temp.html");
                    String s;
                    if (f.exists()) {
//                        BufferedReader reader = new BufferedReader(new FileReader("documents/" +URL.id+".html"));

//                        BufferedWriter writer = new BufferedWriter(new FileWriter("documents/temp.html"));
//                        writer.write(document.toString());
//                        BasicWebCrawler.dbman.SetLastChanged(URL.id, -1);
//                        writer.close();


                        String TextToCompare = new String(Files.readAllBytes(Paths.get("documents/" + URL.id + ".html")));

                        if (TextToCompare.equals(document.toString())) {
//                            System.out.println(TextToCompare);
//                            System.out.println(document.toString());

                            s="exists and equal";
                            BasicWebCrawler.dbman.SetLastChanged(URL.id, 1);
                        } else {
                            s="exists and not equal";
                            FileWriter writer = new FileWriter(f);
                            writer.write(document.toString());
                            writer.close();
//                            BufferedWriter writer = new BufferedWriter(new FileWriter("documents/" + URL.id + ".html"));
//                            writer.write(document.toString());
//                            writer.close();
                            BasicWebCrawler.dbman.SetLastChanged(URL.id, -1);

                        }
                    } else {
                        s="does not exist";
                        FileWriter writer = new FileWriter(f);
                        writer.write(document.toString());
                        writer.close();
//                        BufferedWriter writer = new BufferedWriter(new FileWriter("documents/" + URL.id + ".html"));
//                        writer.write(document.toString());
//                        writer.close();
                        BasicWebCrawler.dbman.SetLastChanged(URL.id, -1);

                    }
                    System.out.println(Thread.currentThread().getName() + "  " + URL.Name +"  " + s);
//                    synchronized (urls)
//                    {
//                        System.out.print(Thread.currentThread().getName() + "  " + URL.Name);
//                        System.out.print("  ");
//                        System.out.println(URL.Name);
//                        System.out.println("  " + s);
//                    }



                    Elements linksOnPage = document.select("a[href]");
                    for (Element page : linksOnPage) {
                        if(BasicWebCrawler.pattern.matcher(page.absUrl("href")).matches())
                        {
                            int x = BasicWebCrawler.dbman.AddLinK(page.absUrl("href"));
                            urls.add(new link(page.absUrl("href"), x));
                        }
                    }
                    counter.incrementAndGet();
                    BasicWebCrawler.dbman.IncCounter();
                    BasicWebCrawler.dbman.SetVisited(URL.id);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}