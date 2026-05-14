package hr.countryclub.country_club.service;

import hr.countryclub.country_club.model.Termin;
import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.repository.TerminRepository;
import hr.countryclub.country_club.repository.UslugaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TerminServiceTest {

    private TerminRepository terminRepository;
    private UslugaRepository uslugaRepository;
    private TerminService terminService;

    @BeforeEach
    void setUp() {
        terminRepository = mock(TerminRepository.class);
        uslugaRepository = mock(UslugaRepository.class);
        terminService = new TerminService(terminRepository, uslugaRepository);
    }

    @Test
    void neSmijeSpremitiTerminAkoJeVrijemeZavrsetkaPrijePocetka() {
        Usluga usluga = new Usluga();
        usluga.setUslugaId(1);

        Termin termin = new Termin();
        termin.setDanUTjednu("PONEDJELJAK");
        termin.setVrijemePocetka(LocalTime.of(12, 0));
        termin.setVrijemeZavrsetka(LocalTime.of(10, 0));
        termin.setMaksimalanBrojOsoba(4);
        termin.setUsluga(usluga);

        when(uslugaRepository.findById(1)).thenReturn(Optional.of(usluga));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> terminService.save(termin)
        );

        assertEquals(
                "Vrijeme završetka mora biti nakon vremena početka.",
                exception.getMessage()
        );
    }

    @Test
    void neSmijeSpremitiTerminAkoSePreklapaSPostojecim() {
        Usluga usluga = new Usluga();
        usluga.setUslugaId(1);

        Termin postojeciTermin = new Termin();
        postojeciTermin.setTerminId(1);
        postojeciTermin.setDanUTjednu("PONEDJELJAK");
        postojeciTermin.setVrijemePocetka(LocalTime.of(10, 0));
        postojeciTermin.setVrijemeZavrsetka(LocalTime.of(11, 0));
        postojeciTermin.setMaksimalanBrojOsoba(4);
        postojeciTermin.setUsluga(usluga);

        Termin noviTermin = new Termin();
        noviTermin.setDanUTjednu("PONEDJELJAK");
        noviTermin.setVrijemePocetka(LocalTime.of(10, 30));
        noviTermin.setVrijemeZavrsetka(LocalTime.of(11, 30));
        noviTermin.setMaksimalanBrojOsoba(4);
        noviTermin.setUsluga(usluga);

        when(uslugaRepository.findById(1)).thenReturn(Optional.of(usluga));
        when(terminRepository.findByUsluga_UslugaIdAndDanUTjednu(1, "PONEDJELJAK"))
                .thenReturn(List.of(postojeciTermin));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> terminService.save(noviTermin)
        );

        assertEquals(
                "Termin se preklapa s postojećim terminom za istu uslugu.",
                exception.getMessage()
        );
    }
}