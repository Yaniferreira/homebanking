const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            selectedType: 'CREDIT',
            selectedColor: 'GOLD',
        }
    },
    methods: {
        createCard()
         {const body =
            {
              "color":this.selectedColor,
              "type":this.selectedType,
              
            }
            axios.post("/api/clients/current/cards",body)
                .then(response => {
                    console.log(response)
                })
                .catch(error => {
                    console.log(error)
                })
        },
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