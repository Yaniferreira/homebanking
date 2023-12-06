const {createApp}= Vue
let app= createApp({
data(){
    return{
         data:[],
         clients:"",
         firstName:"",
         lastName:"",
         email:"",
        }
},
created(){
this.loadData()
},
methods:{
    loadData(){
        axios("/clients")
        .then(response=>{
            this.data=response
            this.clients=response.data._embedded.clients})
        .catch(error=> console.log(error))
    },
    createClient(event){
        event.preventDefault()
        const anotherClient={
            "firstName":this.firstName,
            "lastName":this.lastName,
           "email":this.email,
        }
        axios.post("/clients",anotherClient
        )
        .then(response=>{
            this.clients=response
            this.loadData()
        })
        .catch(error=>console.log(error))
    }
}

}).mount('#app')