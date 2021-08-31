<template>
  <div class="home">
    <p class="notification is-danger" v-if="errorMessage">
        <button class="delete" @click="errorMessage = ''"></button>
        {{ errorMessage }}
    </p>
    <div class="message">
      <div class="message-header">
        Message Content
      </div>
      <div class="message-body">
        {{ message }}
      </div>
    </div>
    <div class="buttons">
    <button class="button is-primary" @click="doFetch">Fetch</button>
    </div>
    <progress v-if="loading"></progress>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: 'Home',
  data() {
    return {
      loading: false,
      errorMessage: ""
    };
  },
  computed: {
    ...mapState(["name","message"])
  },
  methods: {
    ...mapActions(["fetchMessage"]),
    async doFetch() {
      try {
        this.loading = true;
        await this.fetchMessage();
      } catch(e) {
        this.errorMessage = `Error fetching; ${e}`;
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>
