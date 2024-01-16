const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            selectedAccount: "",
            amount: "",
            targetAccount: "",
            description: "",
            modalVisible: false,
            modalVisible2: false,
            modalVisible3: false,
            selectedAccount2: "",
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data = response.data.accounts
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        createTransfer() {
            const body =
            {
              "amount":this.amount ,
              "descriptions":this.description,
              "sourceAccountNumber":this.selectedAccount,
              "targetAccountNumber": this.targetAccount + this.selectedAccount2
            }
            axios.post("/api/transactions/transfer",body)
                .then(response => {
                    this.data = response.data
                    console.log(this.data)
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
        showModal() {
            this.modalVisible = true 
        },
        showModal2() {
            this.modalVisible2 = true 
        },
        showModal3() {
            this.modalVisible3 = true 
        },
    
        closeModal() {
            this.modalVisible = false
        },
        closeModal2() {
            this.modalVisible2 = false
        },
        closeModal3() {
            this.modalVisible3 = false
        },
    
    }
}).mount('#app')