# SUCC - Country Club aplikacija

Web aplikacija za upravljanje uslugama country cluba. Aplikacija se sastoji od Spring Boot backend dijela, Vue/Vite frontend dijela i PostgreSQL baze podataka.

## Upute za pokretanje

1. Kreirajte PostgreSQL bazu podataka pod nazivom:

   ```sql
   country_club
   ```

2. Izvršite SQL skriptu za kreiranje tablica i unos podataka .

   Stvaranje tablica:

   ```
   DROP TABLE IF EXISTS Rezervacija CASCADE;
   DROP TABLE IF EXISTS Termin CASCADE;
   DROP TABLE IF EXISTS Usluga CASCADE;
   DROP TABLE IF EXISTS ZahtjevZaClanstvo CASCADE;
   DROP TABLE IF EXISTS Clanstvo CASCADE;
   DROP TABLE IF EXISTS Klijent CASCADE;
   DROP TABLE IF EXISTS Zaposlenik CASCADE;
   DROP TABLE IF EXISTS VrstaUsluge CASCADE;
   DROP TABLE IF EXISTS Lokacija CASCADE;
   DROP TABLE IF EXISTS Status CASCADE;
   DROP TABLE IF EXISTS Korisnik CASCADE;
  
   CREATE TABLE Korisnik (
       korisnik_id SERIAL PRIMARY KEY,
       ime VARCHAR(50) NOT NULL,
       prezime VARCHAR(50) NOT NULL,
       email VARCHAR(100) NOT NULL UNIQUE,
       lozinka VARCHAR(255) NOT NULL,
       telefon VARCHAR(30)
   );
  
    CREATE TABLE Klijent (
        klijent_id INT PRIMARY KEY,
        adresa VARCHAR(100),
        datum_registracije DATE NOT NULL,
        CONSTRAINT fk_klijent_korisnik
            FOREIGN KEY (klijent_id)
            REFERENCES Korisnik(korisnik_id)
            ON DELETE CASCADE
    );
    
    CREATE TABLE Zaposlenik (
        zaposlenik_id INT PRIMARY KEY,
        pozicija VARCHAR(50) NOT NULL,
        datum_zaposlenja DATE NOT NULL,
        CONSTRAINT fk_zaposlenik_korisnik
            FOREIGN KEY (zaposlenik_id)
            REFERENCES Korisnik(korisnik_id)
            ON DELETE CASCADE
    );
    
    CREATE TABLE Status (
        status_id SERIAL PRIMARY KEY,
        naziv VARCHAR(50) NOT NULL,
        opis VARCHAR(150)
    );
    
    CREATE TABLE Clanstvo (
        clanstvo_id SERIAL PRIMARY KEY,
        datum_pocetka DATE NOT NULL,
        datum_zavrsetka DATE,
        korisnik_id INT NOT NULL,
        status_id INT NOT NULL,
        CONSTRAINT fk_clanstvo_korisnik
            FOREIGN KEY (korisnik_id)
            REFERENCES Korisnik(korisnik_id),
        CONSTRAINT fk_clanstvo_status
            FOREIGN KEY (status_id)
            REFERENCES Status(status_id)
    );
    
    CREATE TABLE ZahtjevZaClanstvo (
        zahtjev_za_clanstvo_id SERIAL PRIMARY KEY,
        datum_zahtjeva DATE NOT NULL,
        korisnik_id INT NOT NULL,
        status_id INT NOT NULL,
        CONSTRAINT fk_zahtjev_korisnik
            FOREIGN KEY (korisnik_id)
            REFERENCES Korisnik(korisnik_id),
        CONSTRAINT fk_zahtjev_status
            FOREIGN KEY (status_id)
            REFERENCES Status(status_id)
    );
    
    CREATE TABLE VrstaUsluge (
        vrsta_id SERIAL PRIMARY KEY,
        naziv VARCHAR(50) NOT NULL,
        opis TEXT
    );
    
    CREATE TABLE Lokacija (
        lokacija_id SERIAL PRIMARY KEY,
        naziv VARCHAR(50) NOT NULL,
        opis TEXT,
        kapacitet INT
    );
    
    CREATE TABLE Usluga (
        usluga_id SERIAL PRIMARY KEY,
        naziv VARCHAR(100) NOT NULL,
        opis TEXT,
        cijena DECIMAL(10,2) NOT NULL,
        trajanje INT NOT NULL,
        vrsta_id INT NOT NULL,
        lokacija_id INT NOT NULL,
        CONSTRAINT fk_usluga_vrsta
            FOREIGN KEY (vrsta_id)
            REFERENCES VrstaUsluge(vrsta_id),
        CONSTRAINT fk_usluga_lokacija
            FOREIGN KEY (lokacija_id)
            REFERENCES Lokacija(lokacija_id)
    );
    
    CREATE TABLE Termin (
        termin_id SERIAL PRIMARY KEY,
        dan_u_tjednu VARCHAR(100) NOT NULL,
        vrijeme_pocetka TIME NOT NULL,
        vrijeme_zavrsetka TIME NOT NULL,
        maksimalan_broj_osoba INT NOT NULL,
        usluga_id INT NOT NULL,
        CONSTRAINT fk_termin_usluga
            FOREIGN KEY (usluga_id)
            REFERENCES Usluga(usluga_id)
    );
    
    CREATE TABLE Rezervacija (
        rezervacija_id SERIAL PRIMARY KEY,
        datum_rezervacije DATE NOT NULL,
        broj_osoba INT NOT NULL,
        korisnik_id INT NOT NULL,
        usluga_id INT NOT NULL,
        termin_id INT NOT NULL,
        status_id INT NOT NULL,
        CONSTRAINT fk_rezervacija_korisnik
            FOREIGN KEY (korisnik_id)
            REFERENCES Korisnik(korisnik_id),
        CONSTRAINT fk_rezervacija_usluga
            FOREIGN KEY (usluga_id)
            REFERENCES Usluga(usluga_id),
        CONSTRAINT fk_rezervacija_termin
            FOREIGN KEY (termin_id)
            REFERENCES Termin(termin_id),
        CONSTRAINT fk_rezervacija_status
            FOREIGN KEY (status_id)
            REFERENCES Status(status_id)
    );



Punjenje podataka:

    INSERT INTO Korisnik (ime, prezime, email, lozinka, telefon) VALUES
    ('Ana', 'Marinović', 'ana.marinovic@email.com', 'lozinka1', '0912567849'),
    ('Marko', 'Lakić', 'marko.lakic@email.com', 'lozinka2', '0923654879'),
    ('Ivana', 'Kolak', 'ivana.kolak@email.com', 'lozinka3', '0935287467'),
    ('Petar', 'Marić', 'petar.maric@email.com', 'lozinka4', '0945558721'),
    ('Jakov', 'Lovrić', 'jakov.lovric@email.com', 'lozinka5', '09598612743');
    
    INSERT INTO Zaposlenik (zaposlenik_id, pozicija, datum_zaposlenja) VALUES
    (1, 'Recepcionar', '2023-06-01'),
    (2, 'Voditelj kluba', '2022-09-15');
    
    INSERT INTO Klijent (klijent_id, adresa, datum_registracije) VALUES
    (3, 'Istarska 10, Pula', '2024-01-18'),
    (4, 'Riječka 22, Rijeka', '2024-03-12'),
    (5, 'Žajina 12, Zagreb', '2026-04-03');
    
    INSERT INTO Status (naziv, opis) VALUES
    ('Na čekanju', 'Zahtjev ili rezervacija još nije obrađena'),
    ('Odobreno', 'Zahtjev ili rezervacija je odobrena'),
    ('Odbijeno', 'Zahtjev ili rezervacija je odbijena'),
    ('Aktivno', 'Članstvo je aktivno'),
    ('Neaktivno', 'Članstvo nije aktivno');
    
    INSERT INTO Clanstvo (datum_pocetka, datum_zavrsetka, korisnik_id, status_id) VALUES
    ('2026-01-20', '2027-01-20', 1, 4),
    ('2026-03-15', '2027-03-15', 2, 4);
    
    INSERT INTO ZahtjevZaClanstvo (datum_zahtjeva, korisnik_id, status_id) VALUES
    ('2024-01-18', 1, 2),
    ('2024-03-12', 2, 2),
    ('2026-04-03', 3, 1);
    
    INSERT INTO VrstaUsluge (naziv, opis) VALUES
    ('Sport', 'Sportske aktivnosti unutar country cluba'),
    ('Wellness', 'Wellness i opuštajuće usluge'),
    ('Bazen', 'Vodene aktivnosti');
    
    INSERT INTO Lokacija (naziv, opis, kapacitet) VALUES
    ('Teniski teren 1', 'Vanjski teniski teren', 4),
    ('Teniski teren 2', 'Vanjski teniski teren', 4),
    ('Spa centar', 'Wellness zona za opuštanje', 10),
    ('Vanjski Bazen', 'Vanjski bazen', 20),
    ('Unutarnji Bazen', 'Unutarnji bazen', 14);
    
    INSERT INTO Usluga (naziv, opis, cijena, trajanje, vrsta_id, lokacija_id) VALUES
    ('Najam teniskog terena 1', 'Rezervacija teniskog terena na 1 sat', 25.00, 60, 1, 1),
    ('Najam teniskog terena 2', 'Rezervacija teniskog terena na 1 sat', 25.00, 60, 1, 2),
    ('Spa tretman', 'Korištenje wellness i spa sadržaja', 40.00, 90, 2, 3),
    ('Korištenje vanjskog bazena', 'Ulaz i korištenje bazena', 15.00, 120, 3, 4),
    ('Korištenje unutarnjeg bazena', 'Ulaz i korištenje bazena', 20.00, 120, 3, 5);
    
    INSERT INTO Termin (dan_u_tjednu, vrijeme_pocetka, vrijeme_zavrsetka, maksimalan_broj_osoba, usluga_id) VALUES
    ('UTORAK', '10:00', '11:30', 10, 3),
    ('UTORAK', '12:00', '13:30', 10, 3),
    ('UTORAK', '11:00', '13:00', 20, 4),
    ('UTORAK', '18:00', '19:00', 4, 1),
    ('UTORAK', '19:00', '20:00', 4, 1),
    ('UTORAK', '18:00', '19:00', 4, 2),
    ('UTORAK', '19:00', '20:00', 4, 2),
    ('UTORAK', '18:00', '20:00', 14, 5),
    ('ČETVRTAK', '10:00', '11:30', 10, 3),
    ('ČETVRTAK', '12:00', '13:30', 10, 3),
    ('ČETVRTAK', '11:00', '13:00', 20, 4),
    ('ČETVRTAK', '18:00', '19:00', 4, 1),
    ('ČETVRTAK', '19:00', '20:00', 4, 1),
    ('ČETVRTAK', '18:00', '19:00', 4, 2),
    ('ČETVRTAK', '19:00', '20:00', 4, 2),
    ('ČETVRTAK', '18:00', '20:00', 14, 5),
    ('SUBOTA', '10:00', '11:30', 10, 3),
    ('SUBOTA', '12:00', '13:30', 10, 3),
    ('SUBOTA', '11:00', '13:00', 20, 4),
    ('SUBOTA', '18:00', '19:00', 4, 1),
    ('SUBOTA', '19:00', '20:00', 4, 1),
    ('SUBOTA', '18:00', '19:00', 4, 2),
    ('SUBOTA', '19:00', '20:00', 4, 2),
    ('SUBOTA', '18:00', '20:00', 14, 5),
    ('NEDJELJA', '10:00', '11:30', 10, 3),
    ('NEDJELJA', '12:00', '13:30', 10, 3),
    ('NEDJELJA', '11:00', '13:00', 20, 4),
    ('NEDJELJA', '18:00', '19:00', 4, 1),
    ('NEDJELJA', '19:00', '20:00', 4, 1),
    ('NEDJELJA', '18:00', '19:00', 4, 2),
    ('NEDJELJA', '19:00', '20:00', 4, 2),
    ('NEDJELJA', '18:00', '20:00', 14, 5);
    
    INSERT INTO Rezervacija (datum_rezervacije, broj_osoba, korisnik_id, usluga_id, termin_id, status_id) VALUES
    ('2026-06-01', 1, 3, 3, 1, 2),
    ('2026-06-02', 2, 4, 1, 5, 1),
    ('2026-06-03', 3, 5, 4, 11, 2);
     ```

3. U datoteci `application.properties` unutar direktorija:

   ```text
   backend/src/main/resources/application.properties
   ```

   potrebno je postaviti podatke za povezivanje na bazu podataka:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/country_club
   spring.datasource.username=postgres
   spring.datasource.password=lozinka
   ```

4. U direktoriju `backend` pokrenite Spring Boot aplikaciju:

   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

   Na Windows:

   ```bash
   cd backend
   mvnw.cmd spring-boot:run
   ```

   Backend aplikacija pokreće se na:

   ```text
   http://localhost:8080
   ```

5. U direktoriju `frontend`:

   ```bash
   cd frontend
   npm install
   ```

6. Pokrenite frontend aplikaciju:

   ```bash
   npm run dev
   ```

   Frontend aplikacija pokreće se na:

   ```text
   http://localhost:5173
   ```

## Pokretanje testova

Backend testovi pokreću se naredbom:

```bash
cd backend
./mvnw test
```

Na Windows:

```bash
cd backend
mvnw.cmd test
```

Frontend testovi pokreću se naredbom:

```bash
cd frontend
npm run test
```





