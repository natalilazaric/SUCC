package hr.countryclub.country_club.service;

import hr.countryclub.country_club.model.Termin;
import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.repository.TerminRepository;
import hr.countryclub.country_club.repository.UslugaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final UslugaRepository uslugaRepository;

    public TerminService(TerminRepository terminRepository, UslugaRepository uslugaRepository) {
        this.terminRepository = terminRepository;
        this.uslugaRepository = uslugaRepository;
    }

    public List<Termin> getByUslugaId(Integer uslugaId) {
        return terminRepository.findByUsluga_UslugaId(uslugaId);
    }

    public Termin getById(Integer id) {
        return terminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin nije pronađen."));
    }

    public Termin save(Termin termin) {
        Integer uslugaId = termin.getUsluga().getUslugaId();

        Usluga usluga = uslugaRepository.findById(uslugaId)
                .orElseThrow(() -> new RuntimeException("Usluga nije pronađena."));

        termin.setUsluga(usluga);

        validateTermin(termin);

        return terminRepository.save(termin);
    }

    public void delete(Integer id) {
        terminRepository.deleteById(id);
    }

    private void validateTermin(Termin termin) {
        if (!termin.getVrijemeZavrsetka().isAfter(termin.getVrijemePocetka())) {
            throw new RuntimeException("Vrijeme završetka mora biti nakon vremena početka.");
        }

        Integer uslugaId = termin.getUsluga().getUslugaId();
        String danUTjednu = termin.getDanUTjednu();

        List<Termin> postojeciTermini =
                terminRepository.findByUsluga_UslugaIdAndDanUTjednu(uslugaId, danUTjednu);

        boolean preklapanje = postojeciTermini.stream()
                .filter(t -> termin.getTerminId() == null || !t.getTerminId().equals(termin.getTerminId()))
                .anyMatch(t ->
                        termin.getVrijemePocetka().isBefore(t.getVrijemeZavrsetka()) &&
                        termin.getVrijemeZavrsetka().isAfter(t.getVrijemePocetka())
                );

        if (preklapanje) {
            throw new RuntimeException("Termin se preklapa s postojećim terminom za istu uslugu.");
        }
    }
}