var vm = new Vue({
    el:"#stutesthistorydiv",    //监管区域
    data:{
        stutestlist:[]
    },
    methods:{
        findStuHistoryTest:function(){
            var _this = this;
            axios.get("/student/findStuHistoryTest.do").then(function(response){
                _this.stutestlist = response.data;
            }).catch(function(error){
               console.log(error);
            });
        },
        //
        getStudengHistoryTest:function(qscore,crediskey){
            if(qscore===null||qscore===""){
                alert(qscore);
                alert("改试卷还没有批阅,不能查看！")
            }else{
                location.href="test_history_look.html?crediskey="+crediskey;
            }
        },
        //
    },
    created:function(){
        this.findStuHistoryTest();
    }
});