package hr.countryclub.country_club.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "usluga")
public class Usluga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usluga_id")
    private Integer uslugaId;

    private String naziv;

    private String opis;

    private BigDecimal cijena;

    private Integer trajanje;

    @ManyToOne
    @JoinColumn(name = "vrsta_id", nullable = false)
    private VrstaUsluge vrstaUsluge;

    @ManyToOne
    @JoinColumn(name = "lokacija_id", nullable = false)
    private Lokacija lokacija;

    public Integer getUslugaId() {
        return uslugaId;
    }

    public void setUslugaId(Integer uslugaId) {
        this.uslugaId = uslugaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public Integer getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Integer trajanje) {
        this.trajanje = trajanje;
    }

    public VrstaUsluge getVrstaUsluge() {
        return vrstaUsluge;
    }

    public void setVrstaUsluge(VrstaUsluge vrstaUsluge) {
        this.vrstaUsluge = vrstaUsluge;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }
}