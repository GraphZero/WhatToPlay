package whattoplay.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import whattoplay.domain.entities.Developer;
import whattoplay.persistence.GameFieldsDatabaseRepository;
import whattoplay.services.persistance.GameFieldsDaoService;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class IgdbGameDatabaseCacherTest {
    @Autowired
    InternetGameDatabaseCacher internetGameDatabaseCacher;



    @Test
    void responseStatusOfGenericScrollShouldBe200(){
        String developersFields = "id," +
                "logo," +
                "name," +
                "url," +
                "description," +
                "website," +
                "start_date";
        try {
            assertEquals( internetGameDatabaseCacher
                    .getScrollFromIGDB( "https://api-2445582011268.apicast.io/companies/", developersFields  )
                    .asObject(Developer[].class)
                    .getStatus(), 200);
        } catch (UnirestException e) {
            assertTrue(false);
        }
    }

    @Test
    void shouldBeAbleToCastDevelopers(){
        ArrayList<Developer> developers;
        try {
            developers = new ArrayList<>( Arrays.asList( (internetGameDatabaseCacher
                    .getScrollFromIGDB( "https://api-2445582011268.apicast.io/companies/", "id," +
                            "logo," +
                            "name," +
                            "url," +
                            "description," +
                            "website," +
                            "start_date"  )
                    .asObject(Developer[].class).getBody())));
            assertTrue( developers.size() != 0  );
        } catch (UnirestException e) {
            assertTrue(false);
        }
    }

    @Test
    void shouldnyThrowExceptions(){
        internetGameDatabaseCacher.saveAllCollections();
    }

}