package hr.countryclub.country_club.service;

import hr.countryclub.country_club.model.VrstaUsluge;
import hr.countryclub.country_club.repository.VrstaUslugeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VrstaUslugeService {

    private final VrstaUslugeRepository vrstaUslugeRepository;

    public VrstaUslugeService(VrstaUslugeRepository vrstaUslugeRepository) {
        this.vrstaUslugeRepository = vrstaUslugeRepository;
    }

    public List<VrstaUsluge> getAll() {
        return vrstaUslugeRepository.findAll();
    }

    public VrstaUsluge getById(Integer id) {
        return vrstaUslugeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vrsta usluge nije pronađena."));
    }

    public VrstaUsluge save(VrstaUsluge vrstaUsluge) {
        return vrstaUslugeRepository.save(vrstaUsluge);
    }

    public void delete(Integer id) {
        vrstaUslugeRepository.deleteById(id);
    }
}