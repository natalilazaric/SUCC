### `HomeView.vue`

```vue
<template>
  <div class="home-page">

    <div class="hero-card">
      <h1>Country Club aplikacija</h1>

      <p class="subtitle">
        Sustav za upravljanje uslugama, terminima i vrstama usluga.
      </p>

      <div class="actions">

        <router-link to="/usluge" class="card-link">
          <div class="link-card">
            <h3>Usluge</h3>
            <p>
              Pregled, dodavanje i uređivanje usluga i termina.
            </p>
          </div>
        </router-link>

        <router-link to="/vrste-usluga" class="card-link">
          <div class="link-card">
            <h3>Vrste usluga</h3>
            <p>
              Upravljanje šifrarnikom vrsta usluga.
            </p>
          </div>
        </router-link>

      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home-page {
  max-width: 1100px;
  margin: 0 auto;
}

.hero-card {
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 14px;
  padding: 40px;
  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.05);
}

.hero-card h1 {
  margin-top: 0;
  margin-bottom: 10px;
  font-size: 36px;
}

.subtitle {
  color: #666;
  margin-bottom: 30px;
  font-size: 17px;
}

.actions {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.card-link {
  text-decoration: none;
  color: inherit;
  flex: 1;
  min-width: 260px;
}

.link-card {
  border: 1px solid #dbe3ef;
  border-radius: 12px;
  padding: 22px;
  transition: 0.2s;
  background: #f9fbff;
}

.link-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.08);
}

.link-card h3 {
  margin-top: 0;
  margin-bottom: 8px;
  color: #1f4fa3;
}

.link-card p {
  margin: 0;
  color: #555;
}
</style>
```
