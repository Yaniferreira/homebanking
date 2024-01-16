const {createApp} = Vue

const options = {
  data(){
    return {
      loans:[],
      selectLoan:"1",
      accountDest: "",
      payments:"1",
      paymentsFilter:"1",
      amount:"",
    } 
  }, 
  created(){
    this.loadLoans()
  }, 

  methods:{
loadLoans(){
    axios.get("/api/loans")
    .then(response=>{
      this.loans = response.data
      console.log(this.loans)})
    .catch(error => console.log(error))
    },
      createLoans(){
        const body =
          {
            "loanId":this.selectLoan,
            "accountNumber":this.accountDest,
            "amount":this.amount,
            "payments": this.payments
          }
      axios.post("/api/loans",body)
      .then(response => {
        console.log(response.loan)
    })
      .catch(error => console.log(error))
      },
      Payments() {
        const selectedLoanId = parseInt(this.selectLoan, 10);
        const paymentsFilter = this.loans.filter(loan => selectedLoanId === loan.id)[0];
    
        if (paymentsFilter) {
            this.paymentsFilter = paymentsFilter.payments;
        } else {
            this.paymentsFilter = [];
        }
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
  }, 

} 

const app = createApp(options) 
app.mount('#app')