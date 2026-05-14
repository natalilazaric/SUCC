package hr.countryclub.country_club.repository;

import hr.countryclub.country_club.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Integer> {

    List<Termin> findByUsluga_UslugaId(Integer uslugaId);

    List<Termin> findByUsluga_UslugaIdAndDanUTjednu(Integer uslugaId, String danUTjednu);
}