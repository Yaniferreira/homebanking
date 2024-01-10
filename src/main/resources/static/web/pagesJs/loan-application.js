const {createApp} = Vue

const options = {
  data(){
    return {
      client: [],
      accounts:[],
      loans:[],
      payments:[],
      loanAmount:"",
      isWideScreen:false,
      successCard:false,
      failureCard:false,
      errormsg:"",
      modalVisibleAlert:false,
      selectedLoan:0,
      selectedAccount:0,
      selectedPayments:0,
      amount:0,
    } 
  }, 
  created(){
    this.loadData()
    this.loadLoans()
  }, 

  methods:{
    loadData(){
      axios.get("/api/clients/current")
      .then (data => {
        this.client = data.data
        this.accounts = data.data.accounts
        console.log(this.client)
        console.log( this.accounts)
      })
      .catch (error => console.log ("Error: ",error))      
    },
    loadLoans(){
    axios.get("/api/loans")
    .then(response =>{
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
      .then(response=>{
      })
      .catch(error => console.log(error))
      },
      searchPayments(){
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