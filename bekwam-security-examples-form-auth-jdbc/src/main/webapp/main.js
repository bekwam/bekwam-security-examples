const Home = {
    template: `
        <div class="container">
            <router-link :to="{ name: 'login' }">Login</router-link>
            <p class="content">
                You are on the home screen.
            </p>
        </p>
        </div>        
`
};

const Login = {
    template: `
        <form action="j_security_check" method="POST">
            <div class="field">
                <label class="label">Username</label>
                <div class="control">
                    <input class="input" type="text" name="j_username"/>
                </div>
            </div>
            <div class="field">
                <label class="label">Password</label>
                <div class="control">
                    <input class="input" type="secret" name="j_password"/>
                </div>
            </div>
            <div class="buttons">
                <input class="button is-success" type="submit" value="Login"/>
            </div>
        </form>    
    `
};

const LoginError = {
    template: "<p>Login Error</p>"
};

const routes = [
    {
        path: "/",
        redirect: "/protected/home"
    },
    {
        path: "/protected/home",
        name: "Home",
        component: Home
    },
    {
        path: "/login",
        name: "login",
        component: Login
    },
    {
        path: "/loginError",
        name: "loginError",
        component: LoginError
    }
];

const router = new VueRouter({
    mode: "history",
    base: "/form",
    routes
});

new Vue({
    el: "#app",
    router,
    template: `
<div id="wrapper">
    <section class="section">
        <h1 class="title">Hello from Vue!</h1>
        <router-view />
    </section>
</div>
    `
});
