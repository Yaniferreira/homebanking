const {createApp} = Vue

const options = {
  data(){
    return {
      data:[],
      selectedLoan:"1",
      accountDest: "",
      payments:"1",
      paymentsFilter:"1",
      amount:"",
    } 
  }, 
  created(){
    this.loadData()
    this.loadLoans()
  }, 

  methods:{
    loadData() {
      axios.get("/api/loans")
          .then(response => {
              this.data = response.data
              this.payments = response.data
              console.log("hola", this.payments)

              console.log(this.data)
          })
          .catch(error => {
              console.log(error)
          })
  },
    loadLoans(){
    axios.get("/api/loans")
    .then(response=>{
      this.loans = response.data
      console.log(this.loans)})
    .catch(error => console.log(error))
    },
      createLoan(){
        const body =
          {
            "loanId":this.selectedLoan,
            "accountNumber":this.selectedAccount,
            "amount":this.amount,
            "payments": this.selectedPayments
          }
      axios.post("/api/loans",body)
      .then(response =>{this.data = response.data
        console.log(this.data)})
      .catch(error => console.log(error))
      },
      Payments(){
        const pays = this.loans.find(loan => loan.id == this.selectedLoan)
        this.loanAmount = pays.amount
        this.payments = pays.payments
      },
      formatBudget(balance){
        if(balance !== undefined && balance !== null){
          const sign = balance < 0 ? "-":""
          const formattedBalance = Math.abs(balance).toLocaleString("en-US",{
            style: "currency",
            currency: "USD",
            currencyDisplay:"narrowSymbol",
            minimumFractionDigits: 2,
          })
          return `USD ${sign}${formattedBalance}`
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