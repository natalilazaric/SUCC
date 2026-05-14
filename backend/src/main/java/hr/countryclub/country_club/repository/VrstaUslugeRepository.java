package hr.countryclub.country_club.repository;

import hr.countryclub.country_club.model.VrstaUsluge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VrstaUslugeRepository extends JpaRepository<VrstaUsluge, Integer> {
}