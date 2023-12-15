package br.edu.infnet.ServWebJava;

import br.edu.infnet.ServWebJava.model.Digimon;
import br.edu.infnet.ServWebJava.util.DigimonUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigimonUtilTest {

    @Test
    void getDigimon() {
        DigimonUtil digimonUtil = new DigimonUtil();
        int digimonId = 1;

        Digimon digimon = digimonUtil.getDigimon(digimonId);
        System.out.println(digimon);
        assertNotNull(digimon, "O Digimon não deve ser nulo");
        assertEquals(digimonId, digimon.getID(), "O ID do Digimon deve corresponder ao esperado");
        assertEquals("Garummon", digimon.getNome(), "O ID do Digimon deve corresponder ao esperado");


    }
}

