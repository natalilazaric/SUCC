package hr.countryclub.country_club.controller;

import hr.countryclub.country_club.model.VrstaUsluge;
import hr.countryclub.country_club.service.VrstaUslugeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vrste-usluga")
@CrossOrigin(origins = "http://localhost:5173")
public class VrstaUslugeController {

    private final VrstaUslugeService vrstaUslugeService;

    public VrstaUslugeController(VrstaUslugeService vrstaUslugeService) {
        this.vrstaUslugeService = vrstaUslugeService;
    }

    @GetMapping
    public List<VrstaUsluge> getAll() {
        return vrstaUslugeService.getAll();
    }

    @GetMapping("/{id}")
    public VrstaUsluge getById(@PathVariable Integer id) {
        return vrstaUslugeService.getById(id);
    }

    @PostMapping
    public VrstaUsluge create(@RequestBody VrstaUsluge vrstaUsluge) {
        return vrstaUslugeService.save(vrstaUsluge);
    }

    @PutMapping("/{id}")
    public VrstaUsluge update(@PathVariable Integer id, @RequestBody VrstaUsluge vrstaUsluge) {
        vrstaUsluge.setVrstaId(id);
        return vrstaUslugeService.save(vrstaUsluge);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vrstaUslugeService.delete(id);
    }
}