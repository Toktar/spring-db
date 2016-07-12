package com.example;

import com.example.dao.JdbsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
@EnableAutoConfiguration
public class DemoApplication implements CommandLineRunner {
    @Autowired
    static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("file:src/main/resources/Beans.xml");
        SpringApplication.run(DemoApplication.class, args);

        try {
            ConsoleApp.run(context);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {
        JdbsDao.jdbcTemplate = jdbcTemplate;

        /*jdbcTemplate.execute("DROP TABLE contacts IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE contacts(" +
                "id SERIAL, name VARCHAR(255), email VARCHAR(255), phone VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo 1", "Jeff Dean 2", "Josh Bloch 3", "Josh Long 4").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());
        Contact curContact = new Contact("John", "mail@", "12323232");

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> System.out.println(String.format("Inserting customer record for %s %s", name[0], name[1])));


        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        List<Object[]> curContactList = curContact.toList();
        jdbcTemplate.batchUpdate("INSERT INTO contacts(name, email, phone) VALUES (?,?,?)", curContactList);

        System.out.println("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, name, email, phone FROM contacts WHERE name = ?", new Object[]{"John"},
                (rs, rowNum) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"))
        ).forEach(contact -> System.out.println(contact.toString()));
*/

    }
}
