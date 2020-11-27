var vm = new Vue({
    el: "#studenttestdiv",       //监管区域
    data: {
        examinfo: [],
        testtime: "",
    },
    methods: {
        //根据试卷id获取试题信息
        getTestInfoByTestid: function () {
            var _this = this;
            axios.get("/student/getTestInfoByTestid.do?testid=" + testid).then(function (response) {
                //试题信息
                _this.examinfo = response.data.data1;

                //考试时长
                _this.testtime = response.data.data2;
            }).catch(function (error) {
                console.log(error);
            });
        },
        //定时器工具类start
        countDown: function () {

            var timers = document.getElementById("timer");
            vm.testtime = vm.testtime - 1;
            var maxtime = vm.testtime;
            if (maxtime >= 0) {
                hour = Math.floor(maxtime / 60 / 60);
                minutes = Math.floor(maxtime / 60);
                seconds = Math.floor(maxtime % 60);
                msg = "距离结束还有:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "" + hour + "时" + minutes + "分" + seconds + "秒";
                timers.innerHTML = msg;
                if (maxtime == 60) {
                    alert("还剩1分钟");
                }
                if (maxtime == 10) {
                    timers.style.display="none";
                    this.actionNum();
                }
                --maxtime;
            } else {
                clearInterval(timer);
            }

        },//定时器工具类end
        actionNum: function () {
            let NUMBER = 1;
            let COUNT = 10;
            let COLORS = ['#8c00ff', '#006bff', '#4fff00', '#ffb800', '#ff0000'];
            let timer = null;
            let h4 = document.getElementById("h4");
            h4.style.display = 'block';
            timer = setInterval(() => {
                COUNT--;
                NUMBER++;
                if (COUNT >= 1) {
                    h4.classList.remove('active');
                    setTimeout(() => {
                        let num = Math.floor(Math.random() * 5);
                        h4.innerText = "考试结束倒计时："+ COUNT;
                        h4.style.color = COLORS[num];
                        h4.classList.add('active');
                    }, 100);
                } else if (COUNT == 0) {
                    let num = Math.floor(Math.random() * 5);
                    h4.innerText = "考试结束";
                    h4.style.color = COLORS[num];
                    h4.classList.add('active');
                    this.savestuToSubmit();
                } else {
                    clearInterval(timer);
                }
            }, 1000);

        },
        /***********************    定时器结尾    ***************************/

        //学生交卷
        savestuToSubmit: function () {
            var _this = this;
            axios.post("/student/savestuToSubmit.do?testid=" + testid, _this.examinfo).then(function (response) {
                if (response.data.flag) {
                    alert(response.data.message);
                    location.href = "test_history.html";
                } else {
                    alert(response.data.message);
                }
            }).catch(function (error) {
                console.log(error);
            })
        },
        //
    },
    created: function () {
        this.getTestInfoByTestid();
        timer = setInterval(this.countDown, 1000);
    },
});