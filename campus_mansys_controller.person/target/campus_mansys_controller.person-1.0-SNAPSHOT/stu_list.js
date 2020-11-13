var vue = new Vue({
   el:"#listDiv",   //监管区域
   data:{
       stulist:[],
       gradelist:[],
       entity:{grade:{}},
       prolist:[],
       citylist:[],
       countrylist:[],
       sids:[]
   },
    methods:{
       getStuList:function(){
         var _this = this;
         axios.get("/user/getStuList.do").then(function(response){
             _this.stulist = response.data;
             console.log(response)
         }).catch(function(error){
             console.log(error)
         })
       },
       goAddStu:function(){
           var _this = this;
           //查询所有的班级信息
           axios.get("/user/getGradelist.do").then(function(response){
               _this.gradelist = response.data;
           }).catch(function(error){
               console.log(error);
           });

           //查询所有的省信息
           axios.get("/user/getProlistById.do?id=1").then(function(response){
               _this.prolist = response.data;
           }).catch(function(error){
              console.log(error);
           });
            //显示添加的form表单
           document.getElementById("addStudiv").style.display="block";
       },
        //查询所有的市信息
        getCity:function(event){
           var id = event.target.value;
           var _this = this;
           axios.get("/user/getProlistById.do?id="+id).then(function(response){
               _this.citylist = response.data;
               _this.countrylist = [];
           }).catch(function(error){
              console.log(error);
           });

           //获取所在省的select的id
            var myselect=document.getElementById("pro");
            //获取该id对应的省的下标
            var index=myselect.selectedIndex ; // selectedIndex代表的是你所选中项的index
            //根据下标获取option的文本值
            let text = myselect.options[index].text;
            //根据下标获取option的value值
            let value = myselect.options[index].value;
            province = text+"省";
            this.entity.address = province;

        },
        getCountry:function(event){
            var id = event.target.value;
            var _this = this;
            axios.get("/user/getProlistById.do?id="+id).then(function(response){
                _this.countrylist = response.data;
            }).catch(function(error){
                console.log(error);
            });

            var myselect=document.getElementById("city");
            var index=myselect.selectedIndex ; // selectedIndex代表的是你所选中项的index
            let text = myselect.options[index].text;
            city = text+"市";
            this.entity.address = province + city;
        },
        getCountryName:function(){
            var _this = this;
            var myselect=document.getElementById("country");
            var index=myselect.selectedIndex ; // selectedIndex代表的是你所选中项的index
            let text = myselect.options[index].text;
            myselect.options[index].getAttribute("cname");
            country = text;
            this.entity.address = province + city + country;
        },
        //保存学生信息
        saveStuInfo:function(){
          var _this = this;
          axios.post("/user/saveStuInfo.do",_this.entity).then(function(response){
                if(response.data.flag){
                    //隐藏form表单
                    document.getElementById("addStudiv").style.display="none";

                    //刷新页面
                    history.go(0)

                    //重新加载学生信息，实现刷新
                    //_this.getStuList();
                }else{
                    alert(response.data.message);
                }
          }).catch(function(error){
              console.log(error);
          });
        },
        //批量删除
        deleteStuBatchBySid:function(){
           var _this = this;
           axios.post("/user/deleteStuBatchBySid.do",_this.sids).then(function(response){
               if(response.data.flag){
                   location.reload();
               }else{
                   alert(response.data.message);
               }
           }).catch(function(error){
               console.log(error);
           });
        },

        /**
         * 日期格式化的
         * @param time
         * @returns {string}
         */
        dateFormat:function(time) {
            if(time!=null&&time!=''){
                //这个time是后台接收的参数，是个毫秒值，先把他变成js的时间
                var date=new Date(time);

                //date.getYear不可以用，他获取的是从1900年到现在的年份差，不能用
                var year=date.getFullYear();

                /* 在日期格式中，月份是从0开始的，因此要加  yyyy——MM-dd这的，1-9月份，前面要加一个0
                    要是月份加1
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                // 拼接
                return year+"-"+month+"-"+day;
            }else{
                return "";
            }
        },  // dateForm结束
        //根据学生id实现修改时回显数据
        saveStuById:function(sid){
            var _this = this;
            // alert(sid)
            //查询所有的班级信息
            axios.get("/user/getGradelist.do").then(function(response){
                _this.gradelist = response.data;
            }).catch(function(error){
                console.log(error);
            });

            //查询所有的省信息
            axios.get("/user/getProlistById.do?id=1").then(function(response){
                _this.prolist = response.data;
            }).catch(function(error){
                console.log(error);
            });

            axios.get("/user/saveStuById.do?sid="+sid).then(function(response){
                _this.entity = response.data;

                let birthday = _this.entity.birthday;
                let dateFormat1 = _this.dateFormat(birthday);
                _this.entity.birthday = dateFormat1;
            }).catch(function(error){
                console.log(error);
            });

            //激活id为addStudiv的div
            document.getElementById("addStudiv").style.display="block";
        },  //end
        //
    },
    created:function(){
        this.getStuList();
    }
});