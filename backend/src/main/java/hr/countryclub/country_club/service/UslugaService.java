package hr.countryclub.country_club.service;

import hr.countryclub.country_club.model.Lokacija;
import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.model.VrstaUsluge;
import hr.countryclub.country_club.repository.LokacijaRepository;
import hr.countryclub.country_club.repository.UslugaRepository;
import hr.countryclub.country_club.repository.VrstaUslugeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UslugaService {

    private final UslugaRepository uslugaRepository;
    private final VrstaUslugeRepository vrstaUslugeRepository;
    private final LokacijaRepository lokacijaRepository;

    public UslugaService(
            UslugaRepository uslugaRepository,
            VrstaUslugeRepository vrstaUslugeRepository,
            LokacijaRepository lokacijaRepository
    ) {
        this.uslugaRepository = uslugaRepository;
        this.vrstaUslugeRepository = vrstaUslugeRepository;
        this.lokacijaRepository = lokacijaRepository;
    }

    public List<Usluga> getAll(String search) {
        if (search == null || search.isBlank()) {
            return uslugaRepository.findAll();
        }

        return uslugaRepository.findByNazivContainingIgnoreCase(search);
    }

    public Usluga getById(Integer id) {
        return uslugaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usluga nije pronađena."));
    }

    public Usluga save(Usluga usluga) {
        Integer vrstaId = usluga.getVrstaUsluge().getVrstaId();
        Integer lokacijaId = usluga.getLokacija().getLokacijaId();

        VrstaUsluge vrstaUsluge = vrstaUslugeRepository.findById(vrstaId)
                .orElseThrow(() -> new RuntimeException("Vrsta usluge nije pronađena."));

        Lokacija lokacija = lokacijaRepository.findById(lokacijaId)
                .orElseThrow(() -> new RuntimeException("Lokacija nije pronađena."));

        usluga.setVrstaUsluge(vrstaUsluge);
        usluga.setLokacija(lokacija);

        return uslugaRepository.save(usluga);
    }

    public void delete(Integer id) {
        uslugaRepository.deleteById(id);
    }
}