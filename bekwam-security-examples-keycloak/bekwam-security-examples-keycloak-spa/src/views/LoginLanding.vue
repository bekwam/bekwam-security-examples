<template>
    <div>        
        <p class="notification is-info" v-if="loading">
            Landing...            
        </p>
        <p v-else class="notification is-info">
            At Landing.vue
        </p>
        <p class="notification is-danger" v-if="errorMessage">
              <button class="delete" @click="errorMessage = ''"></button>
            {{ errorMessage }}
        </p>
        <progress v-if="loading"></progress>
    </div>
</template>
<script>
import { init, login } from "../kc";

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
            await init();
            if( this.$store.state.loggedIn ) {
                this.$router.push({name: "Home"});
            } else {
                login();
            }
        } catch(e) {
            this.errorMessage = e;
        } finally {
            this.loading = false;
        }
    }
}
</script>