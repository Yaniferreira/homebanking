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
        createCard() {
            axios.post("/api/clients/current/cards?color=" + this.selectedColor + "&type=" + this.selectedType)
                .then(response => {
                    console.log(response.card)
                })
                .catch(error => {
                    console.log(error.response.data)
                })
        },
    }
}).mount('#app')