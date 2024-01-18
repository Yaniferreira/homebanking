const {createApp} = Vue

const options = {
  data(){
    return {
      data:[],
      loans:[],
      selectLoan:"1",
      accountDest: "1",
      payments:"1",
      paymentsFilter:"1",
      amount:"",
      modalVisible: false,
      selectedAccount:"1",
    } 
  }, 
  created(){
    this.loadLoans()
    this.loadData()
  }, 

  methods:{
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
    showModal() {
      this.modalVisible = true 
  },
  closeModal() {
    this.modalVisible = false
},
  }, 

} 

const app = createApp(options) 
app.mount('#app')