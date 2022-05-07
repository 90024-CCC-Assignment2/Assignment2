import dao.TwitterDao;
import dao.impl.HistoricalTweetDaoImpl;
import domain.Person;
import domain.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestCouchDB {

  // @Autowired
  // private TestDao testDao;
//   @Autowired
//   private CouchDbClient couchDbClient;
   @Autowired
   private TwitterDao twitterDao;
   /**
    * Test Retrieve Resource
    */
   @Test
    public void testRetrieve(){
       //read couchdb.properties and create a connection with couchdb
       CouchDbClient couchDbClient = new CouchDbClient();
      Person person1 = couchDbClient.find(Person.class, "e91ef80cdbe671a680880152820000b1");
      System.out.println(person1);
      couchDbClient.shutdown();
   }

   /**
    * Test Create Resource
    */
   @Test
   public void testCreate(){
      //GET /comp90024a2
      CouchDbClient couchDbClient = new CouchDbClient();
      Person person = new Person();
      person.setName("Coco");
      person.setAge(22);
      /**
       *       add with a given id
       *       String id = UUID.randomUUID().toString().replace("-","");
       *       person.set_id(id);
       */
      String id = UUID.randomUUID().toString().replace("-","");
      person.set_id(id);
      //PUT /comp90024a2/1bd5c90564af4e6da88d4a03e13ac6fc
      couchDbClient.save(person);
      couchDbClient.shutdown();
   }

   /**
    * Test Update Resource
    */
   @Test
   public void testUpdate(){
      //GET /comp90024a2
      CouchDbClient couchDbClient = new CouchDbClient();
      //Get Person      GET /comp90024a2/e91ef80cdbe671a680880152820000b1
      Person person = couchDbClient.find(Person.class, "e91ef80cdbe671a680880152820000b1");
      //reset Person
      person.setAge(50);
      // PUT /comp90024a2/e91ef80cdbe671a680880152820000b1  Status: 201
      couchDbClient.update(person);
   }

   /**
    * Test Delete Resource
    */
   @Test
   public void testDelete(){
      //GET /comp90024a2
      CouchDbClient couchDbClient = new CouchDbClient();
      Person person = couchDbClient.find(Person.class, "1bd5c90564af4e6da88d4a03e13ac6fc");
      couchDbClient.remove(person);
      couchDbClient.shutdown();
   }

   /**
    * Test Retrieve Matched Resource (name=Bob age>18)
    */
   @Test
   public void testRetrieveMatched(){
      //read couchdb.properties and create a connection with couchdb
      CouchDbClient couchDbClient = new CouchDbClient();
      //{"selector":{"name":"bob","age":{"$gt":18}}}
      String jsonQuery = " {\"selector\":{\"name\":\"bob\",\"age\":{\"$gt\":18}}}";
      List<Person> list = couchDbClient.findDocs(jsonQuery, Person.class);
      System.out.println("Matched Count: "+ list.size());
      list.forEach(ele -> System.out.println(ele));
      couchDbClient.shutdown();
   }

   @Test
   public void test1(){
      //testDao.test();
      //DesignDocument designDoc = couchDbClient.design().getFromDesk("couchview.js");
      //System.out.println(designDoc);
   }
   @Test
   public void test2(){
      List<Tweet> china = twitterDao.searchByCountry("china");
      System.out.println(china);
   }

//   List<Foo> list = dbClient.view("example/foo")
//           .startKey("start-key")
//           .endKey("end-key")
//           .limit(10)
//           .includeDocs(true)
//           .query(Foo.class);


    /**
     * local db test index Country
     */
   @Test
   public void test3() throws IOException {
       CouchDbClient dbClient = new CouchDbClient("myhistorical", false, "http", "127.0.0" +
               ".1",
               5984,
               "admin", "123456");
       List<Tweet> list = dbClient.view("HistoricalInfo/Country")
               .key("China")
               .includeDocs(true)
               .query(Tweet.class);
       dbClient.close();
       list.forEach(ele -> System.out.println(ele));
   }

    /**
     * historical database(local db): test index(country,created_at)
     * Requirement: ask for Tweets(country: china, date from 2014-07-28 to 2014-07-31) from
     * historical database.
     */
    @Test
    public void test4() throws IOException {
       ArrayList start = new ArrayList<String>();
       start.add("China");
       start.add("2014-07-28");
       ArrayList end = new ArrayList<String>();
       end.add("China");
       end.add("2014-07-31");
       CouchDbClient dbClient = new CouchDbClient("myhistorical", false, "http", "127.0.0" +
               ".1",
               5984,
               "admin", "123456");
       List<Tweet> list = dbClient.view("HistoricalInfo/CountryAndCreated_at")
               .startKey(start) //["China","2014-07-28"]
               .endKey(end) //["China","2014-07-30"]
               .includeDocs(true)
               .query(Tweet.class);
       dbClient.close();
       list.forEach(ele -> System.out.println(ele));
    }
    @Test
    public void test5(){
//        List<Tweet> china = new HistoricalTweetDaoImpl().findByCountryPeriod("China", "2014-07-28", "2014-07-31");
//        System.out.println(china);
        int num = new HistoricalTweetDaoImpl().countByCountryAndTag("China", 1);
        System.out.println(num);
    }
}
