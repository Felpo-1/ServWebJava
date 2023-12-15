package br.edu.infnet.ServWebJava.util;

import br.edu.infnet.ServWebJava.model.Digimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class DigimonUtil {

    private final Logger logger = LoggerFactory.getLogger(DigimonUtil.class);

    private final String API_URL = "https://digi-api.com/api/v1/digimon/";

    public Digimon getDigimon(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI(API_URL + id)).build();
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Log do status code
            logger.info("API Status Code: {}", response.statusCode());

            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

            Digimon digimon = mapper.readValue(response.body(), Digimon.class);

            return digimon;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Log do erro
            logger.error("Erro ao obter Digimon da API", e);
            throw new RuntimeException(e);
        }
    }

    private List<String> getLevels(Digimon digimon) {
        List<String> listLevel = new LinkedList<>();

        for (Map<String, Object> levelMap : digimon.getLevels()) {
            Object level = levelMap.get("level");

            if (level instanceof String) {
                listLevel.add((String) level);
            } else {
                throw new RuntimeException("Nível inesperado encontrado: " + level);
            }
        }

        return listLevel;
    }
}
