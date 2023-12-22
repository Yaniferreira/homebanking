const {createApp}= Vue
let app= createApp({
data(){
    return{
         data:[],
         accounts:[],
         loans:[]
        }
},
created(){
this.loadData()
this.logout()
},

methods:{
    loadData(){
        axios.get("/api/clients/current")
        .then(response=>{
            this.data=response.data
            this.accounts=this.data.accounts
            this.loans=this.data.loans
            console.log(this.data);
            console.log(this.accounts);
            console.log(this.loans);
        })
        .catch(error=> console.log(error))
    },
    logout(){
        axios.post("/api/logout")
            .then(response => {
                console.log(response)
                if (response.status == 200) {
                    window.location.href = "./login.html"
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
    },
}

}).mount('#app')
