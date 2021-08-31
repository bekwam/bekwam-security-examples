<template>
    <div>
        <p class="notification is-info" v-if="loading">
            Landing...            
        </p>
        <p v-else class="notification is-info">
            At LogoutLanding.vue
        </p>
        <p class="notification is-danger" v-if="errorMessage">
              <button class="delete" @click="errorMessage = ''"></button>
            {{ errorMessage }}
        </p>
        <progress v-if="loading"></progress>
    </div>
</template>
<script>
import { logout } from "../kc";

export default {
    data() {
        return {
            loading: false,
            errorMessage: ""
        };
    },
    async mounted() {
        try {
            this.loading = true;
            logout();            
        } catch(e) {
            this.errorMessage = e;
        } finally {
            this.loading = false;
            this.$router.push({name: "Login"});
        }
    }
}
</script>