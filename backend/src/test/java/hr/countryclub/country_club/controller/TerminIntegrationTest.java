package hr.countryclub.country_club.controller;

import hr.countryclub.country_club.model.Lokacija;
import hr.countryclub.country_club.model.Usluga;
import hr.countryclub.country_club.model.VrstaUsluge;
import hr.countryclub.country_club.repository.LokacijaRepository;
import hr.countryclub.country_club.repository.UslugaRepository;
import hr.countryclub.country_club.repository.VrstaUslugeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TerminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VrstaUslugeRepository vrstaUslugeRepository;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private UslugaRepository uslugaRepository;

    @Test
    void mozeDodatiTerminPrekoRestApiJa() throws Exception {
        VrstaUsluge vrsta = new VrstaUsluge();
        vrsta.setNaziv("Test sport integracija");
        vrsta.setOpis("Test opis");
        vrsta = vrstaUslugeRepository.save(vrsta);

        Lokacija lokacija = new Lokacija();
        lokacija.setNaziv("Test teren integracija");
        lokacija.setOpis("Test opis lokacije");
        lokacija.setKapacitet(4);
        lokacija = lokacijaRepository.save(lokacija);

        Usluga usluga = new Usluga();
        usluga.setNaziv("Test tenis integracija");
        usluga.setOpis("Test opis usluge");
        usluga.setCijena(BigDecimal.valueOf(30));
        usluga.setTrajanje(60);
        usluga.setVrstaUsluge(vrsta);
        usluga.setLokacija(lokacija);
        usluga = uslugaRepository.save(usluga);

        String json = """
                {
                  "danUTjednu": "SUBOTA",
                  "vrijemePocetka": "09:00:00",
                  "vrijemeZavrsetka": "10:00:00",
                  "maksimalanBrojOsoba": 4,
                  "usluga": {
                    "uslugaId": %d
                  }
                }
                """.formatted(usluga.getUslugaId());

        mockMvc.perform(post("/api/termini")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.danUTjednu").value("SUBOTA"))
                .andExpect(jsonPath("$.vrijemePocetka").value("09:00:00"))
                .andExpect(jsonPath("$.vrijemeZavrsetka").value("10:00:00"));
    }
}