package hr.countryclub.country_club.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vrstausluge")
public class VrstaUsluge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vrsta_id")
    private Integer vrstaId;

    private String naziv;

    private String opis;

    public Integer getVrstaId() {
        return vrstaId;
    }

    public void setVrstaId(Integer vrstaId) {
        this.vrstaId = vrstaId;
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
}