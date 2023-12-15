package br.edu.infnet.ServWebJava.service;

import br.edu.infnet.ServWebJava.exception.DigimonNotFoundException;
import br.edu.infnet.ServWebJava.model.Digimon;
import br.edu.infnet.ServWebJava.util.DigimonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DigimonService {

    private final DigimonUtil digimonUtil;
    private Map<Long, Digimon> listDigimon = iniciarDigis();

    @Autowired
    public DigimonService(DigimonUtil digimonUtil) {
        this.digimonUtil = digimonUtil;
    }

    private Map<Long, Digimon> iniciarDigis() {
        Map<Long, Digimon> digimons = new HashMap<>();
        for (int i = 1; i <= 15; i++) {
            Digimon digi = digimonUtil.getDigimon(i);
            Digimon p1 = new Digimon(digi.getID(), digi.getNome(), digi.getLevels());
            digimons.put((long) p1.getID(), p1);
        }
        return digimons;
    }

    public List<Digimon> getAll() {
        return List.copyOf(listDigimon.values());
    }

    public Digimon getById(Long id) {
        Digimon digimon = listDigimon.get(id);
        if (digimon == null) {
            throw new DigimonNotFoundException("Digimon não encontrado");
        }
        return digimon;
    }

    public Digimon createDigimon(Digimon digimon) {
        // Lógica para criar um Digimon
        Long id = listDigimon.size() + 1L;
        digimon.setID(id);
        listDigimon.put(id, digimon);
        return digimon;
    }

    public boolean updateDigimon(Long id, Digimon digimon) {
        Digimon existingDigimon = listDigimon.get(id);
        if (existingDigimon != null) {
            digimon.setID(id);
            listDigimon.put(id, digimon);
            return true;
        } else {
            throw new DigimonNotFoundException("Digimon não encontrado para atualização");
        }
    }

    public boolean deleteDigimon(Long id) {
        Digimon digimon = listDigimon.remove(id);
        if (digimon == null) {
            throw new DigimonNotFoundException("Digimon não encontrado para exclusão");
        }
        return true;
    }

    public String consumeExternalApi() {
        // Lógica para consumir a API externa usando DigimonUtil
        Digimon externalDigimon = digimonUtil.getDigimon(1);
        return "External Digimon: " + externalDigimon.getNome();
    }

    public List<Digimon> filterByName(String name) {
        return listDigimon.values()
                .stream()
                .filter(digimon -> digimon.getNome().startsWith(name))
                .collect(Collectors.toList());
    }

    public List<Digimon> filterByLevel(String level) {
        return listDigimon.values()
                .stream()
                .filter(digimon -> digimon.getLevels()
                        .stream()
                        .anyMatch(levelMap -> levelMap.containsValue(level)))
                .collect(Collectors.toList());
    }

    public List<Digimon> filterByNameAndLevel(String name, String level) {
        List<Digimon> filteredDigimonsByName = filterByName(name);
        List<Digimon> filteredDigimonsByLevel = filterByLevel(level);

        // Retornar a interseção das listas filtradas por nome e por nível
        return filteredDigimonsByName
                .stream()
                .filter(filteredDigimonsByLevel::contains)
                .collect(Collectors.toList());
    }
}
