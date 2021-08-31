import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: null,
    name: null,
    loggedIn: false,
    message: null
  },
  mutations: {
    SET_TOKEN: (state, token) => state.token = token,
    SET_NAME: (state, name) => state.name = name,
    SET_LOGGED_IN: (state, loggedIn) => state.loggedIn = loggedIn,
    SET_MESSAGE: (state, message) => state.message = message
  },
  actions: {
    async fetchMessage({commit, state}) {
      
      if( !state.token ) {
        throw "Token is not set";
      }
      
      const url = "http://localhost:8080/kc-rest/api/message";
      
      const response = await fetch(url, {
        headers: {
          Accept: "text/plain",
          Authorization: `Bearer ${state.token}`
        }
      });
      const message = await response.text();
      commit("SET_MESSAGE", message);
    }
  },
  modules: {
  }
})
