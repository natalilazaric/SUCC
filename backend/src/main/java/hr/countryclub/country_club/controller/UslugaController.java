package hr.countryclub.country_club.controller;

import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.service.UslugaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usluge")
@CrossOrigin(origins = "http://localhost:5173")
public class UslugaController {

    private final UslugaService uslugaService;

    public UslugaController(UslugaService uslugaService) {
        this.uslugaService = uslugaService;
    }

    @GetMapping
    public List<Usluga> getAll(@RequestParam(required = false) String search) {
        return uslugaService.getAll(search);
    }

    @GetMapping("/{id}")
    public Usluga getById(@PathVariable Integer id) {
        return uslugaService.getById(id);
    }

    @PostMapping
    public Usluga create(@RequestBody Usluga usluga) {
        return uslugaService.save(usluga);
    }

    @PutMapping("/{id}")
    public Usluga update(@PathVariable Integer id, @RequestBody Usluga usluga) {
        usluga.setUslugaId(id);
        return uslugaService.save(usluga);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        uslugaService.delete(id);
    }
}