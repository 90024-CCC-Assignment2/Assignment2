import dao.TwitterDao;
import domain.Person;
import domain.Twitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestCouchDB {

   @Autowired
   private TestDao testDao;
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
      List<Twitter> china = twitterDao.searchByCountry("china");
      System.out.println(china);
   }

}
