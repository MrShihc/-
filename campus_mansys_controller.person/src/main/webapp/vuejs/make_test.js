var vm = new Vue({
   el:"#vuediv",        //监管区域
   data:{
       gradelist:[],
       teacherlist:[]
   },
    methods:{
       //获取班级信息和老师信息
        getGradeAndTeacher:function(){
            var _this = this;
            axios.get("/teacher/getGradeAndTeacher.do").then(function(response){
                _this.gradelist = response.data.grade;
                _this.teacherlist = response.data.teacher;
            }).catch(function(error){
                console.log(error);
            })
        },
       //保存阅卷信息
       saveGradeTeacher:function(){

            for(var x=0;x<this.gradelist.length;x++){
                if(this.gradelist[x].gid==false){
                    this.gradelist.splice(x,1);
                    x--;
                }
            }



            var _this = this;
            axios.post("/teacher/saveGradeTeacher.do",_this.gradelist).then(function(response){
                if(response.data.flag){
                    document.getElementById("yuejuan").innerHTML=response.data.message;
                }else{
                    alert(response.data.message);
                }
            }).catch(function(error){
                console.log(error);
            })
       }
    },
    created:function(){
        this.getGradeAndTeacher();
    }
});