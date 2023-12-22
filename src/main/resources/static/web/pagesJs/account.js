const { createApp } = Vue
let app = createApp({
    data() {
        return {
            data:[],
            account: [],
            transactions:[],
            id:null,
        }
    },
    created() {
        const search = location.search
        const params = new URLSearchParams(search)
        this.id = params.get("id")
        this.loadData() 
    },
    methods: {
        loadData(){
            axios.get("/api/clients/current")
            .then(response=>{
                this.data=response.data
                this.account=response.data.accounts
                .find(account =>account.id ==this.id)
                this.transactions=this.account.transactions
                console.log(this.data);
                console.log(this.account);
            })
            .catch(error=> console.log(error))
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
        formatBudget(balance) {
            if (balance !== undefined && balance !== null) {
                return balance.toLocaleString("en-US", {
                    style: "currency",
                    currency: "ARS",
                    minimumFractingDigits: 0,
                })
            }
        }   
    }

}).mount('#app')