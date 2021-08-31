<template>
  <div id="app">
    <top-nav :name="name" />
    <div class="container is-max-desktop">
      <router-view/>
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
import TopNav from "./TopNav.vue";

export default {
  components: { TopNav },
  computed: {
    ...mapState(["name"])
  }
};
</script>
<style lang="scss">
@import "bulma/bulma.sass";

</style>

