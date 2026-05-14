<template>
  <div class="page">
    <div class="header">
      <div>
        <h1>Vrste usluga</h1>
        <p>Šifrarnik vrsta usluga koje se koriste u ponudi country cluba.</p>
      </div>
    </div>

    <div class="card">
      <h2>{{ form.vrstaId ? 'Uredi vrstu usluge' : 'Dodaj novu vrstu usluge' }}</h2>

      <form @submit.prevent="saveVrsta" class="form">
        <input
          v-model="form.naziv"
          placeholder="Naziv vrste"
          required
        />

        <input
          v-model="form.opis"
          placeholder="Opis"
        />

        <div class="form-actions">
          <button type="submit" class="primary-btn">
            {{ form.vrstaId ? 'Spremi izmjene' : 'Dodaj vrstu' }}
          </button>

          <button
            type="button"
            @click="resetForm"
            v-if="form.vrstaId"
            class="secondary-btn"
          >
            Odustani
          </button>
        </div>
      </form>
    </div>

    <div class="card">
      <div class="table-header">
        <h2>Popis vrsta usluga</h2>

        <input
          v-model="search"
          placeholder="Pretraži..."
          class="search-input"
        />
      </div>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Naziv</th>
            <th>Opis</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="vrsta in filteredVrste" :key="vrsta.vrstaId">
            <td>{{ vrsta.vrstaId }}</td>
            <td class="name-cell">{{ vrsta.naziv }}</td>
            <td>{{ vrsta.opis }}</td>
            <td class="buttons-cell">
              <button @click="editVrsta(vrsta)" class="small-btn edit-btn">
                Uredi
              </button>

              <button @click="deleteVrsta(vrsta.vrstaId)" class="small-btn delete-btn">
                Obriši
              </button>
            </td>
          </tr>

          <tr v-if="filteredVrste.length === 0">
            <td colspan="4" class="empty-cell">
              Nema pronađenih vrsta usluga.
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <p v-if="message" class="success-message">{{ message }}</p>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script>
import api from '@/services/api'

export default {
  name: 'VrsteUslugaView',

  data() {
    return {
      vrste: [],
      search: '',
      message: '',
      error: '',
      form: {
        vrstaId: null,
        naziv: '',
        opis: ''
      }
    }
  },

  computed: {
    filteredVrste() {
      return this.vrste.filter(vrsta =>
        vrsta.naziv.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },

  mounted() {
    this.loadVrste()
  },

  methods: {
    async loadVrste() {
      try {
        const response = await api.get('/vrste-usluga')
        this.vrste = response.data
      } catch (e) {
        this.error = 'Greška pri učitavanju vrsta usluga.'
      }
    },

    async saveVrsta() {
      this.message = ''
      this.error = ''

      const body = {
        naziv: this.form.naziv,
        opis: this.form.opis
      }

      try {
        if (this.form.vrstaId) {
          await api.put(`/vrste-usluga/${this.form.vrstaId}`, body)
          this.message = 'Vrsta usluge je ažurirana.'
        } else {
          await api.post('/vrste-usluga', body)
          this.message = 'Vrsta usluge je dodana.'
        }

        this.resetForm()
        await this.loadVrste()
      } catch (e) {
        this.error = 'Greška pri spremanju vrste usluge.'
      }
    },

    editVrsta(vrsta) {
      this.form = { ...vrsta }
      this.message = ''
      this.error = ''
    },

    async deleteVrsta(id) {
      if (!confirm('Jeste li sigurni da želite obrisati ovu vrstu usluge?')) {
        return
      }

      try {
        await api.delete(`/vrste-usluga/${id}`)
        this.message = 'Vrsta usluge je obrisana.'
        await this.loadVrste()
      } catch (e) {
        this.error = 'Vrstu usluge nije moguće obrisati jer se možda koristi u uslugama.'
      }
    },

    resetForm() {
      this.form = {
        vrstaId: null,
        naziv: '',
        opis: ''
      }
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 1000px;
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
  margin-bottom: 14px;
  font-size: 20px;
}

.form {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

input {
  padding: 9px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  min-width: 220px;
}

.form-actions {
  display: flex;
  gap: 8px;
}

button {
  border: none;
  border-radius: 6px;
  cursor: pointer;
  padding: 9px 12px;
}

.primary-btn {
  background: #1f6feb;
  color: white;
}

.secondary-btn {
  background: #e5e7eb;
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.search-input {
  min-width: 220px;
}

table {
  border-collapse: collapse;
  width: 100%;
  overflow: hidden;
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

.buttons-cell {
  text-align: right;
  white-space: nowrap;
}

.small-btn {
  padding: 7px 10px;
  margin-left: 6px;
}

.edit-btn {
  background: #e0ecff;
  color: #174ea6;
}

.delete-btn {
  background: #ffe1e1;
  color: #b42318;
}

.empty-cell {
  text-align: center;
  color: #777;
  padding: 20px;
}

.success-message {
  color: #16803c;
  font-weight: 600;
}

.error-message {
  color: #c62828;
  font-weight: 600;
}
</style>