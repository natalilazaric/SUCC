package hr.countryclub.country_club.controller;

import hr.countryclub.country_club.model.Lokacija;
import hr.countryclub.country_club.service.LokacijaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lokacije")
@CrossOrigin(origins = "http://localhost:5173")
public class LokacijaController {

    private final LokacijaService lokacijaService;

    public LokacijaController(LokacijaService lokacijaService) {
        this.lokacijaService = lokacijaService;
    }

    @GetMapping
    public List<Lokacija> findAll() {
        return lokacijaService.findAll();
    }

    @GetMapping("/{id}")
    public Lokacija getById(@PathVariable Integer id) {
        return lokacijaService.getById(id);
    }
}