const {createApp}= Vue
let app= createApp({
data(){
    return{
         data:[],
         accounts:[],
         id:1,
        }
},
created(){
this.loadData()
},
methods:{
    loadData(){
        axios.get("/api/clients/"+this.id)
        .then(response=>{
            this.data=response.data
            this.accounts=this.data.accounts
            console.log(this.data);
            console.log(this.accounts);
        })
        .catch(error=> console.log(error))
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