const {createApp}= Vue
let app= createApp({
data(){
    return{
         data:[],
         accounts:[],
         id:1,
         cards:[],
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
            this.cards=this.data.cards
            console.log(this.data);
            console.log(this.accounts);
            console.log(this.cards);
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

}

}).mount('#app')