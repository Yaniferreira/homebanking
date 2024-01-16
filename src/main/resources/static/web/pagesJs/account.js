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
        },
        formatDate(dateString) {
            const dateObject = new Date(dateString);
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            return dateObject.toLocaleDateString('en-US', options);
          },
      
          formatTime(dateString) {
            const dateObject = new Date(dateString);
            const options = { hour: '2-digit', minute: '2-digit', second: '2-digit' };
            return dateObject.toLocaleTimeString('en-US', options);
          },   
    }

}).mount('#app')