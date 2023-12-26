const app = Vue.createApp({
    data() {
        return {
            data: [],
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            isSignUpActive: false,
            modalVisible: false
        };
    },
    created() {
    },
    methods: {
        signin(){
            axios.post("/api/login?email=" + this.email + "&password=" + this.password)
            .then(response => {
                console.log(response)
                window.location.href="/web/pages/accounts.html"
				// this.role=response.role
                // console.log(this.role);
                // if (this.role === "ADMIN"){
                //     window.location.href="/h2-console/login.jsp?jsessionid=d30c97fc7f9baa6335a14fccd45e016c"
                // }
                // else{window.location.href="/web/pages/accounts.html"}
            })
        },
		register() {
            axios.post("/api/clients?firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.password)
                .then(response => {
                    console.log("registered" + this.email);
                    window.location.href="/web/pages/accounts.html"  
                })
                .catch(error => console.log(error))
        },
		getfirstName(event){
            this.firstName = event.target.value
			console.log(this.firstName);
        },

        getlastName(event){
            this.lastName= event.target.value
			console.log(this.lastName);
        },
        getEmail(event){
            this.email = event.target.value
			console.log(this.email);
        },

        getPassword(event){
            this.password = event.target.value
			console.log(this.password);
        },

        togglePassword(){
            const x = document.getElementById("passwordSignIn");
            const y =document.getElementById("passwordSignup");
            const eye = document.getElementById("togglePassword");
            eye.classList.toggle("fa-eye-slash");
            const currentInputType = x.getAttribute("type");
            if (currentInputType === "password") {
                x.setAttribute("type", "text");
                y.setAttribute("type", "text");
            } else {
                x.setAttribute("type", "password");
                y.setAttribute("type", "password");
            }
        },

        togglePanel() {
            this.isSignUpActive = !this.isSignUpActive;
        },
        showModal() {
            this.modalVisible = true;
        },
    
        closeModal() {
            this.modalVisible = false;
        },

        signUpButton(){
            container.classList.add("right-panel-active")
        },

        signInButton(){
            container.classList.remove("right-panel-active")
        }


    },
}).mount('#app');
