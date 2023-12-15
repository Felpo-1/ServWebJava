package br.edu.infnet.ServWebJava.Controller;

import br.edu.infnet.ServWebJava.exception.DigimonNotFoundException;
import br.edu.infnet.ServWebJava.model.Digimon;
import br.edu.infnet.ServWebJava.service.DigimonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/digimons")
public class DigimonController {

    private final DigimonService digimonService;

    @Autowired
    public DigimonController(DigimonService digimonService) {
        this.digimonService = digimonService;
    }

    @GetMapping
    public ResponseEntity<List<Digimon>> getAllDigimons(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "level", required = false) String level) {

        List<Digimon> digimons;

        if (name != null && level != null) {
            // Lógica para filtrar por nome e nível
            digimons = digimonService.filterByNameAndLevel(name, level);
        } else if (name != null) {
            // Lógica para filtrar por nome
            digimons = digimonService.filterByName(name);
        } else if (level != null) {
            // Lógica para filtrar por nível
            digimons = digimonService.filterByLevel(level);
        } else {
            // Sem filtros, retorna todos os Digimons
            digimons = digimonService.getAll();
        }

        return ResponseEntity.ok(digimons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Digimon> getDigimonById(@PathVariable Long id) {
        try {
            Digimon digimon = digimonService.getById(id);
            return ResponseEntity.ok(digimon);
        } catch (DigimonNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createDigimon(@RequestBody Digimon digimon) {
        Digimon createdDigimon = digimonService.createDigimon(digimon);
        return ResponseEntity.status(HttpStatus.CREATED).body("Digimon criado com ID: " + createdDigimon.getID());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDigimon(@PathVariable Long id, @RequestBody Digimon digimon) {
        try {
            if (digimonService.updateDigimon(id, digimon)) {
                return ResponseEntity.ok("Digimon atualizado com sucesso.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DigimonNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDigimon(@PathVariable Long id) {
        try {
            if (digimonService.deleteDigimon(id)) {
                return ResponseEntity.ok("Digimon excluído com sucesso.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DigimonNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/consume-api")
    public ResponseEntity<String> consumeExternalApi() {
        String result = digimonService.consumeExternalApi();
        return ResponseEntity.ok(result);
    }
}
