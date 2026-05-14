<template>
  <div class="page">

    <div class="header">
      <div>
        <h1>Usluge</h1>
        <p>Pregled i upravljanje uslugama country cluba. Za upravljanje terminima odaberi uslugu iz popisa.</p>
      </div>
    </div>

    <div class="card">
      <h2>
        {{ form.uslugaId ? 'Uredi uslugu' : 'Dodaj novu uslugu' }}
      </h2>

      <form @submit.prevent="saveUsluga" class="form">

        <input
          v-model="form.naziv"
          placeholder="Naziv usluge"
          required
        />

        <input
          v-model="form.opis"
          placeholder="Opis usluge"
        />

        <input
          v-model.number="form.cijena"
          type="number"
          placeholder="Cijena"
          required
        />

        <input
          v-model.number="form.trajanje"
          type="number"
          placeholder="Trajanje (min)"
          required
        />

        <select v-model="form.vrstaId" required>
          <option disabled value="">Odaberi vrstu usluge</option>

          <option
            v-for="vrsta in vrste"
            :key="vrsta.vrstaId"
            :value="vrsta.vrstaId"
          >
            {{ vrsta.naziv }}
          </option>
        </select>

        <select v-model="form.lokacijaId" required>
          <option disabled value="">Odaberi lokaciju</option>

          <option
            v-for="lokacija in lokacije"
            :key="lokacija.lokacijaId"
            :value="lokacija.lokacijaId"
          >
            {{ lokacija.naziv }}
          </option>
        </select>

        <div class="form-actions">
          <button type="submit" class="primary-btn">
            {{ form.uslugaId ? 'Spremi izmjene' : 'Dodaj uslugu' }}
          </button>

          <button
            type="button"
            @click="resetForm"
            v-if="form.uslugaId"
            class="secondary-btn"
          >
            Odustani
          </button>
        </div>
      </form>
    </div>

    <div class="card">

      <div class="table-header">
        <h2>Popis usluga</h2>

        <input
          v-model="search"
          placeholder="Pretraži..."
          class="search-input"
        />
      </div>

      <p v-if="message" class="success-message">
        {{ message }}
      </p>

      <p v-if="error" class="error-message">
        {{ error }}
      </p>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Naziv</th>
            <th>Cijena</th>
            <th>Vrsta</th>
            <th>Lokacija</th>
            <th></th>
          </tr>
        </thead>

        <tbody>

          <tr
            v-for="usluga in filteredUsluge"
            :key="usluga.uslugaId"
            :class="{ selected: selectedUsluga?.uslugaId === usluga.uslugaId }"
            @click="selectUsluga(usluga)"
            class="clickable-row"
            >
            <td>{{ usluga.uslugaId }}</td>

            <td class="name-cell">
              {{ usluga.naziv }}
            </td>

            <td>{{ usluga.cijena }} €</td>

            <td>{{ usluga.vrstaUsluge?.naziv }}</td>

            <td>{{ usluga.lokacija?.naziv }}</td>

            <td class="buttons-cell">

              <button
                @click="editUsluga(usluga)"
                class="small-btn edit-btn"
              >
                Uredi
              </button>

              <button
                @click="deleteUsluga(usluga.uslugaId)"
                class="small-btn delete-btn"
              >
                Obriši
              </button>

            </td>
          </tr>

        </tbody>
      </table>
    </div>

    <div v-if="selectedUsluga" class="card">

      <div class="table-header">
        <h2>
          Termini za uslugu:
          {{ selectedUsluga.naziv }}
        </h2>
      </div>

      <form @submit.prevent="saveTermin" class="form">

        <select v-model="terminForm.danUTjednu" required>
          <option disabled value="">Odaberi dan</option>
          <option>PONEDJELJAK</option>
          <option>UTORAK</option>
          <option>SRIJEDA</option>
          <option>ČETVRTAK</option>
          <option>PETAK</option>
          <option>SUBOTA</option>
          <option>NEDJELJA</option>
        </select>

        <input
          v-model="terminForm.vrijemePocetka"
          type="text"
          placeholder="Početak (HH:mm)"
          required
        />

        <input
          v-model="terminForm.vrijemeZavrsetka"
          type="text"
          placeholder="Završetak (HH:mm)"
          required
        />

        <input
          v-model.number="terminForm.maksimalanBrojOsoba"
          type="number"
          placeholder="Maks. broj osoba"
          required
        />

        <div class="form-actions">

          <button type="submit" class="primary-btn">
            {{ terminForm.terminId ? 'Spremi termin' : 'Dodaj termin' }}
          </button>

          <button
            type="button"
            @click="resetTerminForm"
            v-if="terminForm.terminId"
            class="secondary-btn"
          >
            Odustani
          </button>

        </div>
      </form>

      <p v-if="error" class="error-message">
        {{ error }}
      </p>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Dan</th>
            <th>Početak</th>
            <th>Završetak</th>
            <th>Maks. osoba</th>
            <th></th>
          </tr>
        </thead>

        <tbody>

          <tr v-for="termin in termini" :key="termin.terminId">

            <td>{{ termin.terminId }}</td>

            <td>{{ termin.danUTjednu }}</td>

            <td>{{ termin.vrijemePocetka }}</td>

            <td>{{ termin.vrijemeZavrsetka }}</td>

            <td>{{ termin.maksimalanBrojOsoba }}</td>

            <td class="buttons-cell">

              <button
                @click="editTermin(termin)"
                class="small-btn edit-btn"
              >
                Uredi
              </button>

              <button
                @click="deleteTermin(termin.terminId)"
                class="small-btn delete-btn"
              >
                Obriši
              </button>

            </td>

          </tr>

        </tbody>
      </table>
    </div>

    <div v-else class="card empty-card">
      Odaberi uslugu za prikaz termina.
    </div>

  </div>
</template>

<script>
import api from '@/services/api'

export default {
  name: 'UslugeView',

  data() {
    return {
      usluge: [],
      vrste: [],
      lokacije: [],
      termini: [],
      selectedUsluga: null,
      search: '',
      message: '',
      error: '',

      form: {
        uslugaId: null,
        naziv: '',
        opis: '',
        cijena: null,
        trajanje: null,
        vrstaId: '',
        lokacijaId: ''
      },

      terminForm: {
        terminId: null,
        danUTjednu: '',
        vrijemePocetka: '',
        vrijemeZavrsetka: '',
        maksimalanBrojOsoba: null
      }
    }
  },

  computed: {
    filteredUsluge() {
      return this.usluge.filter(usluga =>
        usluga.naziv.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },

  mounted() {
    this.loadUsluge()
    this.loadVrste()
    this.loadLokacije()
  },

  methods: {
    async loadUsluge() {
      const response = await api.get('/usluge')
      this.usluge = response.data
    },

    async loadVrste() {
      const response = await api.get('/vrste-usluga')
      this.vrste = response.data
    },

    async loadLokacije() {
      const response = await api.get('/lokacije')
      this.lokacije = response.data
    },

    async selectUsluga(usluga) {
      this.selectedUsluga = usluga
      this.message = ''
      this.error = ''
      this.resetTerminForm()
      await this.loadTermini(usluga.uslugaId)
    },

    async loadTermini(uslugaId) {
      const response = await api.get(`/usluge/${uslugaId}/termini`)
      this.termini = response.data
    },

    async saveUsluga() {
      this.message = ''
      this.error = ''

      const body = {
        naziv: this.form.naziv,
        opis: this.form.opis,
        cijena: this.form.cijena,
        trajanje: this.form.trajanje,
        vrstaUsluge: {
          vrstaId: this.form.vrstaId
        },
        lokacija: {
          lokacijaId: this.form.lokacijaId
        }
      }

      try {
        if (this.form.uslugaId) {
          await api.put(`/usluge/${this.form.uslugaId}`, body)
          this.message = 'Usluga je ažurirana.'
        } else {
          await api.post('/usluge', body)
          this.message = 'Usluga je dodana.'
        }

        this.resetForm()
        await this.loadUsluge()
      } catch (e) {
        this.error = 'Greška pri spremanju usluge.'
      }
    },

    editUsluga(usluga) {
      this.form = {
        uslugaId: usluga.uslugaId,
        naziv: usluga.naziv,
        opis: usluga.opis,
        cijena: usluga.cijena,
        trajanje: usluga.trajanje,
        vrstaId: usluga.vrstaUsluge.vrstaId,
        lokacijaId: usluga.lokacija.lokacijaId
      }
      this.message = ''
      this.error = ''
    },

    async deleteUsluga(id) {
      if (!confirm('Jeste li sigurni da želite obrisati ovu uslugu?')) return

      try {
        await api.delete(`/usluge/${id}`)
        this.message = 'Usluga je obrisana.'
        if (this.selectedUsluga?.uslugaId === id) {
          this.selectedUsluga = null
          this.termini = []
        }
        await this.loadUsluge()
      } catch (e) {
        this.error = 'Uslugu nije moguće obrisati jer možda ima povezane termine.'
      }
    },

    isValidTimeFormat(time) {
      const regex = /^([01]\d|2[0-3]):([0-5]\d)$/
      return regex.test(time)
    },

    timeToMinutes(time) {
      const [hours, minutes] = time.split(':').map(Number)
      return hours * 60 + minutes
    },

    async saveTermin() {
      this.message = ''
      this.error = ''

      if (
        !this.isValidTimeFormat(this.terminForm.vrijemePocetka) ||
        !this.isValidTimeFormat(this.terminForm.vrijemeZavrsetka)
        ) {
        this.error = 'Vrijeme mora biti uneseno u formatu HH:mm, npr. 09:30.'
        return
       }

      const pocetak = this.timeToMinutes(this.terminForm.vrijemePocetka)
      const zavrsetak = this.timeToMinutes(this.terminForm.vrijemeZavrsetka)

      if (zavrsetak <= pocetak) {
        this.error = 'Vrijeme završetka mora biti nakon vremena početka.'
        return
      }

      const body = {
        danUTjednu: this.terminForm.danUTjednu,
        vrijemePocetka: this.terminForm.vrijemePocetka + ':00',
        vrijemeZavrsetka: this.terminForm.vrijemeZavrsetka + ':00',
        maksimalanBrojOsoba: this.terminForm.maksimalanBrojOsoba,
        usluga: {
          uslugaId: this.selectedUsluga.uslugaId
        }
      }

      try {
        if (this.terminForm.terminId) {
          await api.put(`/termini/${this.terminForm.terminId}`, body)
          this.message = 'Termin je ažuriran.'
        } else {
          await api.post('/termini', body)
          this.message = 'Termin je dodan.'
        }

        this.resetTerminForm()
        await this.loadTermini(this.selectedUsluga.uslugaId)
      } catch (e) {
        this.error = 'Termin nije moguće spremiti. Moguće je da se preklapa s postojećim terminom.'
      }
    },

    editTermin(termin) {
      this.terminForm = {
        terminId: termin.terminId,
        danUTjednu: termin.danUTjednu,
        vrijemePocetka: termin.vrijemePocetka.substring(0, 5),
        vrijemeZavrsetka: termin.vrijemeZavrsetka.substring(0, 5),
        maksimalanBrojOsoba: termin.maksimalanBrojOsoba
      }
      this.message = ''
      this.error = ''
    },

    async deleteTermin(id) {
      if (!confirm('Obrisati termin?')) return

      try {
        await api.delete(`/termini/${id}`)
        this.message = 'Termin je obrisan.'
        await this.loadTermini(this.selectedUsluga.uslugaId)
      } catch (e) {
        this.error = 'Termin nije moguće obrisati.'
      }
    },

    resetForm() {
      this.form = {
        uslugaId: null,
        naziv: '',
        opis: '',
        cijena: null,
        trajanje: null,
        vrstaId: '',
        lokacijaId: ''
      }
    },

    resetTerminForm() {
      this.terminForm = {
        terminId: null,
        danUTjednu: '',
        vrijemePocetka: '',
        vrijemeZavrsetka: '',
        maksimalanBrojOsoba: null
      }
      this.message = ''
      this.error = ''
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  margin-bottom: 20px;
}

.header h1 {
  margin-bottom: 4px;
}

.header p {
  color: #666;
  margin-top: 0;
}

.card {
  background: white;
  border: 1px solid #e2e2e2;
  border-radius: 10px;
  padding: 18px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.card h2 {
  margin-top: 0;
  margin-bottom: 16px;
}

.form {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

input,
select {
  padding: 9px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  min-width: 200px;
}

button {
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.form-actions {
  display: flex;
  gap: 8px;
}

.primary-btn {
  background: #1f6feb;
  color: white;
  padding: 9px 14px;
}

.secondary-btn {
  background: #e5e7eb;
  padding: 9px 14px;
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
}

.search-input {
  width: 220px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background: #f3f4f6;
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

td {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.name-cell {
  font-weight: 600;
}

.selected {
  background-color: #eef4ff;
}

.buttons-cell {
  text-align: right;
  white-space: nowrap;
}

.small-btn {
  padding: 7px 10px;
  margin-left: 6px;
}

.clickable-row {
  cursor: pointer;
}

.clickable-row:hover {
  background-color: #f8fbff;
}

.selected.clickable-row {
  background-color: #eef4ff;
}

.edit-btn {
  background: #e0ecff;
  color: #174ea6;
}

.delete-btn {
  background: #ffe1e1;
  color: #b42318;
}

.success-message {
  background: #dcfce7;
  color: #166534;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 14px;
}

.error-message {
  background: #fee2e2;
  color: #991b1b;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 14px;
}

.empty-card {
  text-align: center;
  color: #666;
}
</style>