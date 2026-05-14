package hr.countryclub.country_club.repository;

import hr.countryclub.country_club.model.Lokacija;
import hr.countryclub.country_club.model.Termin;
import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.model.VrstaUsluge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UslugaRepositoryTest {

    @Autowired
    private UslugaRepository uslugaRepository;

    @Autowired
    private VrstaUslugeRepository vrstaUslugeRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private TerminRepository terminRepository;

    @Test
    void mozePronaciUsluguPoNazivu() {
        VrstaUsluge vrsta = new VrstaUsluge();
        vrsta.setNaziv("Test sport");
        vrsta.setOpis("Test opis");
        vrsta = vrstaUslugeRepository.save(vrsta);

        Lokacija lokacija = new Lokacija();
        lokacija.setNaziv("Test lokacija");
        lokacija.setOpis("Test opis lokacije");
        lokacija.setKapacitet(10);
        lokacija = lokacijaRepository.save(lokacija);

        Usluga usluga = new Usluga();
        usluga.setNaziv("Test golf usluga");
        usluga.setOpis("Test opis usluge");
        usluga.setCijena(BigDecimal.valueOf(50));
        usluga.setTrajanje(60);
        usluga.setVrstaUsluge(vrsta);
        usluga.setLokacija(lokacija);
        uslugaRepository.save(usluga);

        List<Usluga> rezultat = uslugaRepository.findByNazivContainingIgnoreCase("golf");

        assertFalse(rezultat.isEmpty());
        assertTrue(rezultat.get(0).getNaziv().toLowerCase().contains("golf"));
    }

    @Test
    void mozePronaciTerminePoUsluziIDanuUTjednu() {
        VrstaUsluge vrsta = new VrstaUsluge();
        vrsta.setNaziv("Test wellness");
        vrsta.setOpis("Test opis");
        vrsta = vrstaUslugeRepository.save(vrsta);

        Lokacija lokacija = new Lokacija();
        lokacija.setNaziv("Test spa");
        lokacija.setOpis("Test spa opis");
        lokacija.setKapacitet(8);
        lokacija = lokacijaRepository.save(lokacija);

        Usluga usluga = new Usluga();
        usluga.setNaziv("Test spa tretman");
        usluga.setOpis("Test opis");
        usluga.setCijena(BigDecimal.valueOf(40));
        usluga.setTrajanje(90);
        usluga.setVrstaUsluge(vrsta);
        usluga.setLokacija(lokacija);
        usluga = uslugaRepository.save(usluga);

        Termin termin = new Termin();
        termin.setDanUTjednu("PETAK");
        termin.setVrijemePocetka(LocalTime.of(9, 0));
        termin.setVrijemeZavrsetka(LocalTime.of(10, 0));
        termin.setMaksimalanBrojOsoba(5);
        termin.setUsluga(usluga);
        terminRepository.save(termin);

        List<Termin> rezultat =
                terminRepository.findByUsluga_UslugaIdAndDanUTjednu(usluga.getUslugaId(), "PETAK");

        assertEquals(1, rezultat.size());
        assertEquals("PETAK", rezultat.get(0).getDanUTjednu());
    }
}