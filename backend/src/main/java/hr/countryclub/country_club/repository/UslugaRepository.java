package hr.countryclub.country_club.repository;

import hr.countryclub.country_club.model.Usluga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UslugaRepository extends JpaRepository<Usluga, Integer> {

    List<Usluga> findByNazivContainingIgnoreCase(String naziv);
}