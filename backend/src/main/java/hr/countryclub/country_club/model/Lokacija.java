package hr.countryclub.country_club.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lokacija")
public class Lokacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lokacija_id")
    private Integer lokacijaId;

    private String naziv;

    private String opis;

    private Integer kapacitet;

    public Integer getLokacijaId() {
        return lokacijaId;
    }

    public void setLokacijaId(Integer lokacijaId) {
        this.lokacijaId = lokacijaId;
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

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }
}