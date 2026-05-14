### `App.vue`

```vue
<template>
  <div id="app">

    <header class="navbar">

      <div class="logo">
        Country Club
      </div>

      <nav class="nav-links">

        <router-link to="/" exact-active-class="active-link">
          Početna
        </router-link>

        <router-link
          to="/vrste-usluga"
          active-class="active-link"
        >
          Vrste usluga
        </router-link>

        <router-link
          to="/usluge"
          active-class="active-link"
        >
          Usluge
        </router-link>

      </nav>

    </header>

    <main class="content">
      <router-view />
    </main>

  </div>
</template>

<style>
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Arial, sans-serif;
  background: #f5f7fb;
  color: #222;
}

#app {
  min-height: 100vh;
}

.navbar {
  background: white;
  border-bottom: 1px solid #e5e7eb;
  padding: 16px 30px;

  display: flex;
  justify-content: space-between;
  align-items: center;

  position: sticky;
  top: 0;
  z-index: 10;
}

.logo {
  font-size: 22px;
  font-weight: bold;
  color: #1f4fa3;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-links a {
  text-decoration: none;
  color: #444;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: 6px;
  transition: 0.2s;
}

.nav-links a:hover {
  background: #eef4ff;
  color: #1f4fa3;
}

.active-link {
  background: #1f6feb;
  color: white !important;
}

.content {
  padding: 30px;
}
</style>
```
