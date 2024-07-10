package feature;

import com.songify.SongifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class HappyPathIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");
    
    @Autowired
    public MockMvc mvc;
    
    @DynamicPropertySource
    public static void propertySet(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }
    
    @Test
    public void f() throws Exception {
//      1. when I go to /songs then I can see no songs
        mvc.perform(get("/songs").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.songs", empty()));
        
        // Object Mapper alternative
//        ObjectMapper objectMapper = new ObjectMapper();
//        ResultActions perform = mvc.perform(get("/songs").contentType(MediaType.APPLICATION_JSON));
//        MvcResult result = perform.andExpect(status().isOk()).andReturn();
//        String resultAsString = result.getResponse().getContentAsString();
//        GetAllSongsResponseDto allSongsResponseDto = objectMapper.readValue(resultAsString, GetAllSongsResponseDto.class);
//        assertThat(allSongsResponseDto.songs()).isEmpty();

//      2. when I post to /songs with Song "Till I Collapse" then Song "Till I Collapse" is returned with ID 1
        mvc.perform(post("/songs")
                            .content(
                                    """
                                    {
                                      "title": "till i collapse",
                                      "language": "english",
                                      "releaseDate": "2024-07-10T20:59:08.876Z",
                                      "duration": 0
                                    }
                                    """
                            )
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.title", is("till i collapse")))
                .andExpect(jsonPath("$.song.duration", is(0)))
                .andExpect(jsonPath("$.song.language", is("ENGLISH")));
//      3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with ID 2
//      4. when I go to /genre then I can see no genres
//      5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with ID 1
//      6. when I go to /song/1 then I can see default genre
//      7. when I put to /song/1/genre/1 then Genre with ID 1 ("Rap") is added to Song with ID 1 ("Till I Collapse")
//      8. when I go to /song/1 then I can see "Rap" genre
//      9. when I put to /song/2/genre/1 then Genre with ID 1 ("Rap") is added to Song with ID 2 ("Lose Yourself")
//      10. when I go to /album then I see no albums
//      11. when I post to /album with Album "EminemAlbum1" and Song with ID 1 then Album "EminemAlbum1" is returned with ID 1
//      12. when I go to /album/1 then I can see song with ID 1 added to album
//      13. when I put to /album/1/song/1 then Song with ID 1 ("Till I Collapse") is added to Album with ID 1 ("EminemAlbum1")
//      14. when I put to /album/1/song/2 then Song with ID 2 ("Lose Yourself") is added to Album with ID 1 ("EminemAlbum1")
//      15. when I go to /album/1/song then I can see 2 songs (id1, id2)
//      16. when I post to /artist with Artist "Eminem" then Artist "Eminem" is returned with ID 1
//      17. when I put to /album/1/artist/1 then Artist with ID 1 ("Eminem") is added to Album with ID 1 ("EminemAlbum1")
    }
}
