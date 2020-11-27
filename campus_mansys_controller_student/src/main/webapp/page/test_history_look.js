var vm = new Vue({
    el:"#historylook",  //监管区域
    data:{
        examinfo:[]
    },
    methods:{
        //获取试题
        getStudentHistoryTest: function (crediskey) {
            var _this = this;
            axios.get("/student/getStudentHistoryTest.do?crediskey=" + crediskey).then(function (response) {
                //试题信息
                _this.examinfo = response.data;
            }).catch(function (error) {
                console.log(error);
            });
        },
    }
});
vm.getStudentHistoryTest(crediskey);