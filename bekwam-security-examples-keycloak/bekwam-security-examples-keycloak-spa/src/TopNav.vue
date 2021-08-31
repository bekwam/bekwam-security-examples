<template>
    <nav class="navbar" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <h1 class="title">kc-spa</h1>
      </div>
      <div class="navbar-end">
        <p class="navbar-item">Welcome {{ name }}</p>
        <div class="navbar-item">
          <div class="buttons">
            <router-link class="button is-light" :to="{ name: 'Logout' }">
              Log Out
            </router-link>
          </div>
        </div>
      </div>
    </nav>
</template>
<script>
export default {
    props: [ "name" ]
};
</script>
