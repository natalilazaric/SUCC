package hr.countryclub.country_club.service;

import hr.countryclub.country_club.model.Lokacija;
import hr.countryclub.country_club.repository.LokacijaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LokacijaService {

    private final LokacijaRepository lokacijaRepository;

    public LokacijaService(LokacijaRepository lokacijaRepository) {
        this.lokacijaRepository = lokacijaRepository;
    }

    public List<Lokacija> findAll() {
        return lokacijaRepository.findAll();
    }

    public Lokacija getById(Integer id) {
        return lokacijaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lokacija nije pronađena."));
    }

    public Lokacija save(Lokacija lokacija) {
        return lokacijaRepository.save(lokacija);
    }

    public void delete(Integer id) {
        lokacijaRepository.deleteById(id);
    }
}