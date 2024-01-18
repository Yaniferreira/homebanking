const {createApp}= Vue
let app= createApp({
data(){
    return{
         data:[],
         accounts:[],
         loans:[],
         accountId:"",
         error:"",
        }
},
created(){
this.loadData()
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
                    window.location.href = "/web/index.html"
                }
            })
    },
    newAccount(){
        axios.post("/api/clients/current/accounts")
        .then(response => {console.log(response)
            Swal.fire({
                title: "Success",
                text: "Account created successfully",
                icon: "success"
            });
            this.loadData()})
          .catch(error =>{console.log(error)
        this.error=error.response.data} )
        },
        changeActive(accountId){
            axios.patch("/api/clients/current/accounts?id= "+ accountId)
            .then(response => {
                Swal.fire({
                    title: "Delete Account?",
                    text: "You won't be able to revert this!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Proceed"
                }).then((result) => {
                    if (result.isConfirmed) {

                        Swal.fire({
                            title: "Success",
                            text: "Account Deleted Succesfully",
                            icon: "success"
                        });

                        setTimeout(() => {
                            window.location.reload();
                        }, 3000);
                    }
                })})
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
    },
}

}).mount('#app')
