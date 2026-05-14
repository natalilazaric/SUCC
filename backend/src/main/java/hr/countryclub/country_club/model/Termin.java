package hr.countryclub.country_club.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "termin")
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termin_id")
    private Integer terminId;

    @Column(name = "dan_u_tjednu")
    private String danUTjednu;

    @Column(name = "vrijeme_pocetka")
    private LocalTime vrijemePocetka;

    @Column(name = "vrijeme_zavrsetka")
    private LocalTime vrijemeZavrsetka;

    @Column(name = "maksimalan_broj_osoba")
    private Integer maksimalanBrojOsoba;

    @ManyToOne
    @JoinColumn(name = "usluga_id", nullable = false)
    private Usluga usluga;

    public Integer getTerminId() {
        return terminId;
    }

    public void setTerminId(Integer terminId) {
        this.terminId = terminId;
    }

    public String getDanUTjednu() {
        return danUTjednu;
    }

    public void setDanUTjednu(String danUTjednu) {
        this.danUTjednu = danUTjednu;
    }

    public LocalTime getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(LocalTime vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public LocalTime getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(LocalTime vrijemeZavrsetka) {
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }

    public Integer getMaksimalanBrojOsoba() {
        return maksimalanBrojOsoba;
    }

    public void setMaksimalanBrojOsoba(Integer maksimalanBrojOsoba) {
        this.maksimalanBrojOsoba = maksimalanBrojOsoba;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }
}