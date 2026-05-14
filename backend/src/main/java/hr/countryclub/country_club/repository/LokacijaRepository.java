package hr.countryclub.country_club.repository;

import hr.countryclub.country_club.model.Lokacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LokacijaRepository extends JpaRepository<Lokacija, Integer> {
}