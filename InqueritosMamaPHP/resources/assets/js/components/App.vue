<template>
  <div v-if="dataReady">
    <nav class="navbar navbar-expand-md navbar-light navbar-laravel">
      <div class="container">
        <router-link class="navbar-brand" :to="{ name: 'home' }">Inquéritos Mamã</router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle Navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <!-- Right Side Of Navbar -->
          <ul class="navbar-nav mr-auto">
            <li class="nav-items">
              <router-link
                class="nav-link"
                :to="{ name: 'create.new.administrator' }">Criar novo Administrador</router-link>
            </li>
            <li class="nav-items">
              <router-link class="nav-link" :to="{ name: 'create.new.patient' }">Criar novo Paciente</router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link"
                :to="{ name: 'inquiry.builder' }">Construtor de Inquéritos</router-link>
            </li>
          </ul>
          <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
              <a
                id="navbarDropdown"
                class="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                v-text="authenticatedAdminId">
                <span class="caret"></span>
              </a>

              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <form id="logout-form" method="POST" action="/logout">
                  <a class="dropdown-item" href="#" @click.prevent="logout">Logout</a>
                </form>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <main class="py-4">
      <div class="container">
        <transition name="fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      authenticatedAdminId: "",
      dataReady: false
    };
  },

  mounted() {
    window.axios.get("/api/admin/get-authenticated-admin-id").then(
      response => {
        this.authenticatedAdminId = response.data.user_id;
        this.dataReady = true;
      },
      error => {
        console.log(error);
      }
    );
  },

  methods: {
    logout: function() {
      window.axios.post("/logout").then(
        response => {
          window.location.assign("/login");
        },
        error => {
          console.log(error);
        }
      );
    }
  }
};
</script>