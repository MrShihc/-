var vm = new Vue({
    el:"#stutestdiv",
    data:{
        stutestlist:{}
    },
    methods:{
        //获取学生的考试信息
        getStuTestInfo:function(){
            var _this = this;
            axios.get("/student/getStuTestInfo.do").then(function(response){
                _this.stutestlist = response.data;
            }).catch(function(error){
                console.log(error);
            });
        },
        //学生去考试
        goTest:function(testinfo){
          var _this = this;
          if(testinfo.testStatus=="考试尚未开放"){
              alert("请到考试时间再来考试!");
          }else{
              location.href="test_student.html?testid="+testinfo.testid;
          }
        },
        //
    },
    created:function(){
        this.getStuTestInfo();
    }
});