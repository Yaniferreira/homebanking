const { createApp } = Vue
const app = createApp({
    data() {
        return {
          
        }
    },
    methods: {
        logout(){
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "/web/index.html"
                    }
                })
        },
    }
}).mount('#app')