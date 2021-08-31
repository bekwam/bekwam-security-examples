import store from "./store/index";

/* global Keycloak */
let keycloak = null;

async function init() {
  keycloak = new Keycloak({
    url: process.env.VUE_APP_KEYCLOAK_BASE_URL + "/auth",
    realm: process.env.VUE_APP_KEYCLOAK_REALM,
    clientId: process.env.VUE_APP_KEYCLOAK_CLIENT_ID
  });
  const loggedIn = await keycloak.init({
    onLoad: 'check-sso',
    silentCheckSsoRedirectUri: window.location.origin + process.env.BASE_URL + '/silent-check-sso.html'
  });
  store.commit("SET_LOGGED_IN", loggedIn);
  if( loggedIn ) {
    store.commit("SET_TOKEN", keycloak.token);
    store.commit("SET_NAME", keycloak.idTokenParsed.name);
  }
}

function login() {
    if( keycloak != null ) {
        keycloak.login();
    }
}

function logout() {
    if( keycloak != null ) {
        keycloak.logout();
    }
}

export { init, login, logout };
