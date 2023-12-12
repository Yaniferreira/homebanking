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
        this.loadData2()
    },
    methods: {
        loadData(){
            axios.get("/api/clients/1")
            .then(response=>{
                this.data=response.data
                this.account=response.data.accounts
                .find(account =>account.id ==this.id)
                console.log(this.data);
                console.log(this.account);
            })
            .catch(error=> console.log(error))
        },
        loadData2() {
            axios.get("/api/accounts/" +this.id+"/transaction")
                .then(response => {
                    this.transactions =response.data
                    console.log(this.transactions);
                })
                .catch(error => console.log(error))
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