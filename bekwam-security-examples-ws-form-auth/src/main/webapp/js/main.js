new Vue({
    el: "#app",
    data() {
        return {
            username: null,
            password: null,
            message: "",
            errorMessage: "",
            ws: null
        };
    },
    computed: {
        isConnected() {
            return this.ws != null;
        }
    },
    methods: {
        connect() {
            if( this.ws == null ) {
                this.errorMessage = "";
                this.message = "";
                try {
                    let url = `ws://localhost:8080/ws-form/hw`;
                    this.ws = new WebSocket(url);
                    this.ws.onerror = (err) => {
                        this.errorMessage = "Can't Connect to Socket-3";
                        this.ws = null;
                    };
                    this.ws.onmessage = ({ data }) => (this.message = data);
                } catch(e) {
                    this.errorMessage = e; // url syntax error
                }
            }
        },
        callWS() {
            try {
                this.errorMessage = "";
                this.ws.send("dummy");
            } catch(e) {
                console.error(e);
                this.errorMessage = e;
            }
        },
        close() {
            this.message = "";
            this.errorMessage = "";
            if( this.ws != null ) {
                try {
                    this.ws.close();
                    this.ws = null;
                    this.username = null;
                    this.password = null;
                } catch(e) {
                    this.errorMessage = e;
                }
            }
        }
    }
});