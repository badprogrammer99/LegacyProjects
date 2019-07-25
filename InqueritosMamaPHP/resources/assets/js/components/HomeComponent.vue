<template>
  <div class="row">
    <div class="col-md-10 offset-md-1">
      <div class="card">
        <div class="card-header">Dashboard</div>
        <div class="card-body">
          Bem-vindo ao construtor de inquéritos do hospital de Santarém! Para iniciar, 
          clique no botão abaixo e será redireccionado para uma página onde poderá criar novos inquéritos
          ou fazer a edição dos já existentes, em conjunto com os seus respetivos questionários e questões. 
          Para avançar, clique no botão:
          <br>
          <br>
          <router-link class="btn btn-success" :to="{ name: 'inquiry.builder' }">
            <i class="fas fa-angle-double-right"></i> Avançar
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>