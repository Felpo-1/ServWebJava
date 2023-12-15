package br.edu.infnet.ServWebJava;

import br.edu.infnet.ServWebJava.exception.DigimonNotFoundException;
import br.edu.infnet.ServWebJava.model.Digimon;
import br.edu.infnet.ServWebJava.service.DigimonService;
import br.edu.infnet.ServWebJava.util.DigimonUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("DigimonService Test")
class DigimonServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(DigimonServiceTests.class);

    @Mock
    private DigimonUtil digimonUtil;

    private DigimonService digimonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        digimonService = new DigimonService(digimonUtil);
    }

    @Test
    @DisplayName("Test getAll method")
    void testGetAll() {
        List<Digimon> digimons = digimonService.getAll();
        assertNotNull(digimons);
        assertEquals(15, digimons.size());
        logger.info("getAll test passed");
    }

    @Test
    @DisplayName("Test getById method with valid ID")
    void testGetByIdValidId() {
        Digimon digimon = digimonService.getById(1L);
        assertNotNull(digimon);
        assertEquals(1L, digimon.getID());
        logger.info("getById with valid ID test passed");
    }

    @Test
    @DisplayName("Test getById method with invalid ID")
    void testGetByIdInvalidId() {
        assertThrows(DigimonNotFoundException.class, () -> digimonService.getById(100L));
        logger.info("getById with invalid ID test passed");
    }

    // Add more tests for other methods in a similar manner

    @AfterEach
    void tearDown() {
        // Any cleanup code
        logger.info("Test case completed.");
    }
}
