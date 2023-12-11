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
        axios("/api/clients")
        .then(response=>{
            this.data=response
        })
        .catch(error=> console.log(error))
    },
    createClient(event){
        event.preventDefault()
        const anotherClient={
            "firstName":this.firstName,
            "lastName":this.lastName,
           "email":this.email,
        }
        axios.post("api/clients",anotherClient
        )
        .then(response=>{
            this.data=response
            this.loadData()
        })
        .catch(error=>console.log(error))
    }
}

}).mount('#app')