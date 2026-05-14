package hr.countryclub.country_club.controller;

import hr.countryclub.country_club.model.Termin;
import hr.countryclub.country_club.service.TerminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TerminController {

    private final TerminService terminService;

    public TerminController(TerminService terminService) {
        this.terminService = terminService;
    }

    @GetMapping("/usluge/{uslugaId}/termini")
    public List<Termin> getByUsluga(@PathVariable Integer uslugaId) {
        return terminService.getByUslugaId(uslugaId);
    }

    @GetMapping("/termini/{id}")
    public Termin getById(@PathVariable Integer id) {
        return terminService.getById(id);
    }

    @PostMapping("/termini")
    public Termin create(@RequestBody Termin termin) {
        return terminService.save(termin);
    }

    @PutMapping("/termini/{id}")
    public Termin update(@PathVariable Integer id, @RequestBody Termin termin) {
        termin.setTerminId(id);
        return terminService.save(termin);
    }

    @DeleteMapping("/termini/{id}")
    public void delete(@PathVariable Integer id) {
        terminService.delete(id);
    }
}