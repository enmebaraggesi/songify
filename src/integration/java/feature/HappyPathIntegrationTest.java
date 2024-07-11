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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                                    """.trim()
                            )
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.song.id", is(1)))
           .andExpect(jsonPath("$.song.title", is("till i collapse")))
           .andExpect(jsonPath("$.song.duration", is(0)))
           .andExpect(jsonPath("$.song.language", is("ENGLISH")));
//      3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with ID 2
        mvc.perform(post("/songs")
                            .content(
                                    """
                                    {
                                      "title": "lose yourself",
                                      "language": "english",
                                      "releaseDate": "2024-07-11T20:59:08.876Z",
                                      "duration": 1
                                    }
                                    """.trim()
                            )
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.song.id", is(2)))
           .andExpect(jsonPath("$.song.title", is("lose yourself")))
           .andExpect(jsonPath("$.song.duration", is(1)))
           .andExpect(jsonPath("$.song.language", is("ENGLISH")));
//      4. when I go to /genres then I can see only "default" genre
        mvc.perform(get("/genres").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.genres[0].id", is(1)))
           .andExpect(jsonPath("$.genres[0].name", is("default")));
//      5. when I post to /genres with Genre "Rap" then Genre "Rap" is returned with ID 1
        mvc.perform(post("/genres")
                            .content(
                                    """
                                    {
                                      "name": "Rap"
                                    }
                                    """.trim()
                            )
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is(2)))
           .andExpect(jsonPath("$.name", is("Rap")));
//      6. when I go to /songs/1 then I can see song with default genre
        mvc.perform(get("/songs/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.song.genre.id", is(1)))
           .andExpect(jsonPath("$.song.genre.name", is("default")));
//      7. when I put to /songs/1/genre/1 then Genre with ID 1 ("Rap") is added to Song with ID 1 ("Till I Collapse")
        mvc.perform(put("/songs/1/genres/2").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.info", is("updated song with genre: Rap")));
//      8. when I go to /songs/1 then I can see "Rap" genre
        mvc.perform(get("/songs/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.song.genre.id", is(2)))
           .andExpect(jsonPath("$.song.genre.name", is("Rap")));
//      9. when I go to /albums then I see no albums
        mvc.perform(get("/albums").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.albums", empty()));
//      10. when I post to /albums with Album "EminemAlbum1" and Song ID 1 then Album "EminemAlbum1" is returned with ID 1
        mvc.perform(post("/albums")
                            .content(
                                    """
                                    {
                                      "title": "EminemAlbum1",
                                      "releaseDate": "2024-07-11T12:54:10.610Z",
                                      "songId": 1
                                    }
                                    """.trim()
                            )
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is(1)))
           .andExpect(jsonPath("$.name", is("EminemAlbum1")))
           .andExpect(jsonPath("$.songs[0].id", is(1)));
//      11. when I go to /albums/1 while no Artist is added to Album then error "Album not found" is returned
        mvc.perform(get("/albums/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath("$.status", is("NOT_FOUND")))
           .andExpect(jsonPath("$.message", is("Album not found")));
//      12. when I post to /artists with Artist "Eminem" then Artist "Eminem" is returned with ID 1
        mvc.perform(post("/artists")
                            .content(
                                    """
                                    {
                                      "name": "Eminem"
                                    }
                                    """.trim()
                            )
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is(1)))
           .andExpect(jsonPath("$.name", is("Eminem")));
//      13. when I put to /albums/1/artists/1 then Artist with ID 1 ("Eminem") is added to Album with ID 1 ("EminemAlbum1")
        mvc.perform(put("/albums/1/artists/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", is("EminemAlbum1")))
           .andExpect(jsonPath("$.artists[0].name", is("Eminem")));
//      14. when I go to /albums/1 Album ID 1 is returned with Artist ID 1 and Song ID 1 inside
        mvc.perform(get("/albums/1").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is(1)))
           .andExpect(jsonPath("$.artists[0].id", is(1)))
           .andExpect(jsonPath("$.songs[0].id", is(1)));
    }
}
