<template>
    <div class="container problemSet">
        <a-row>
            <a-col :span="18">
                <a-row>
                    <a-col :span="24">
                        <div class="cards-title">
                            <a-icon type="block" />&nbsp;&nbsp;题目列表
                        </div>
                    </a-col>
                </a-row>
                <a-row  class="margin-1-30">
                    <a-col :span="17">
                        <a-input placeholder="输入题目名称" v-model="like"/>
                    </a-col>
                    <a-col :span="6" :offset="1">
                        <div class="btns-space">
                            <a-button @click="getProblemLike">搜索题目</a-button><a-button @click="randomStart">随机开始</a-button>
                        </div>
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24" class="ques-list" v-if="problems.length>0">
                        <a-row class="ques-head">
                            <a-col :span="4">ID</a-col>
                            <a-col :span="12">题名</a-col>
                            <a-col :span="4">分数</a-col>
                            <a-col :span="4">难度</a-col>
                        </a-row>
                        <div v-for="p in problems" :key="p.id">
                            <div class="ques-row" @click="$goRoute(`problem/${p.id}`)">
                                <a-row>
                                <a-col :span="4">{{p.id}}</a-col>
                                <a-col :span="12">{{p.name}}</a-col>
                                <a-col :span="4">{{p.point}}</a-col>
                                <a-col :span="4">
                                    <a-tag color="red" v-if="p.level==3">
                                        困难
                                    </a-tag>
                                    <a-tag color="orange" v-else-if="p.level==2">
                                        中等
                                    </a-tag>
                                    <a-tag color="green" v-else>
                                        简单
                                    </a-tag>
                                </a-col>
                                </a-row>
                            </div>
                        </div>
                    </a-col>
                    <a-col :span="24" v-else>
                        <a-empty description="暂无数据" />
                    </a-col>
                </a-row>
            </a-col>
            <a-col :span="6">
                <div class="m-rate" v-if="login">
                    <div class="cards-title">
                        <a-icon type="line-chart" />
                        &nbsp;我的进展
                    </div>
                    <div class="rate-top">
                        当前进度：
                        <span>{{udata.solved}}</span>
                        /
                        <span>{{total}}</span>
                    </div>
                    <div class="cvs-container">
                        <canvas id="cvs" style="margin: 10px 0"></canvas>
                    </div>
                    <div class="solved-info">
                        <div class="solved-item ques-level-easy">
                            <span>{{udata.easy}}</span>
                            <span>简单</span>
                        </div>
                        <div class="solved-item ques-level-medium">
                            <span>{{udata.medium}}</span>
                            <span>中等</span>
                        </div>
                        <div class="solved-item ques-level-hard">
                            <span>{{udata.hard}}</span>
                            <span>困难</span>
                        </div>
                    </div>
                    <div class="rate-bottom">
                        <div>积分：<span>{{udata.point}}</span></div>
                        <div>等级：<span>{{udata.level}}</span></div>
                    </div>
                </div>
                <div class="m-rate m-hot-card">
                    <div class="cards-title">
                        <a-icon type="fire" />
                        &nbsp;热门题目
                    </div>
                    <div class="blank-10"></div>
                    <template v-for="p in hot_problems">
                        <!--eslint-disable-next-line-->
                        <div class="hot-ques" @click="$goRoute(`problem/${p.id}`)">
                            <a-icon type="ordered-list" class="hot-ques-pre"/> {{p.name}}
                        </div>
                    </template>
                </div>

            </a-col>
        </a-row>
    </div>
</template>

<script>
    import {get} from '@/utils/util'
    import {API_PROBLEM,API_PROBLEM_HOT,API_PROBLEM_LIKE,API_USER_COUNT} from '@/utils/urls'
    export default {
        name: "ProblemSet",
        data:function () {
            return {
                udata:{
                    solved:0,
                    easy:0,
                    medium:0,
                    hard:0,
                    point:0,
                    level:"不存在"
                },
                problems:[
                    {id:-1,name:"列表加载中，请稍候",point:0,level:1}
                ],
                hot_problems:[
                    {id:-1,name:"列表加载中，请稍候"}
                ],
                like:"",
                total:1
            }
        },
        computed:{
          login:function () {
              return this.$store.state.login;
          }
        },
        mounted:async function(){
            await this.getProblemList();
            this.getHotList();
            if (this.login){
                this.getUserData();
            }
        },
        watch:{
            login(flag){
                if (flag){
                    this.getUserData();
                }
            }
        },
        methods:{
            async getProblemList(){
                let response = await get(API_PROBLEM,{from:0,limit:20});
                this.problems = response.data.list;
                this.total = response.data.total;
            },
            async getHotList(){
                let response = await get(API_PROBLEM_HOT);
                let data = response.data;
                data = data.map(e=>{
                    let arr = e.split('_');
                    return {name:arr[0],id:arr[1]}
                })
                this.hot_problems = data;
            },
            async getProblemLike(){
                if (this.like.trim()===""){
                    return this.getProblemList();
                }
                this.like = this.like.trim();
                let response = await get(API_PROBLEM_LIKE,{like:this.like});
                this.problems = response.data;
            },
            randomStart(){
                let len = this.total;
                let index = Math.floor(Math.random()*len);
                this.$message.info("祝您好运");
                this.$goRoute(`/problem/${index}`)
            },
            async getUserData(){
                let uid = this.$store.state.info.id;
                let response = await get(API_USER_COUNT,{uid});
                let newData = response.data;
                newData.point = this.$store.state.info.point;
                newData.level = '斗之气';
                this.udata = newData;
                let all = this.total;
                let data_arr = [
                    newData.easy / all,
                    newData.medium / all,
                    newData.hard / all,
                    (all-newData.solved)/all
                ];

                this.drawCircle(data_arr);
            },
            drawCircle(data_arr) {
                let color_arr = ["#28a745", "#ffc107", "#dc3545","#eee"];
                let c = document.getElementById("cvs");
                c.width = document.querySelector(".cvs-container").clientWidth;
                c.height = c.width;
                var ctx = c.getContext("2d");

                var radius = c.height / 2 - 20; //半径
                var ox = radius + 20, oy = radius + 20; //圆心

                var startAngle = -0.5*Math.PI; //起始弧度
                var endAngle = -0.5*Math.PI;   //结束弧度
                ctx.strokeStyle = "#fff"
                ctx.lineWidth = 2;
                for (var i = 0; i < data_arr.length; i++)
                {
                    //绘制饼图
                    endAngle = endAngle + data_arr[i] * Math.PI * 2; //结束弧度
                    ctx.fillStyle = color_arr[i];
                    ctx.save();
                    ctx.globalAlpha = 0.2;
                    ctx.beginPath();
                    ctx.moveTo(ox,oy);
                    ctx.arc(ox, oy, radius*1.1, startAngle, endAngle, false);
                    ctx.fill();
                    ctx.closePath();
                    ctx.restore();
                    ctx.beginPath();
                    ctx.moveTo(ox, oy); //移动到到圆心
                    ctx.arc(ox, oy, radius, startAngle, endAngle, false);
                    ctx.closePath();
                    ctx.fill();
                    ctx.stroke();

                    startAngle = endAngle; //设置起始弧度
                }
            }
        }
    }
</script>

<style scoped>

    .margin-1-30{
        margin: 30px 0;
    }
    .problemSet{
        margin-top: 40px;
    }
    .m-menu{
        min-width: 120px;
        width: 120px;
    }
    .m-menu>a{
        font-size: 12px ;
        color: rgba(0,0,0,0.65);
        padding: 2px 14px;
    }

    .head-link>img {
        width: 100%;
    }

    .cards-title{
        font-weight:lighter ;
        font-size: 22px;
        line-height: 30px;
        height: 30px;
        width: 100%;
    }

    .cards-container{
        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;
        border-bottom: 1px solid #aaa;
        padding-bottom: 20px;
        margin: 20px 0;
    }
    .card-outer-orange{
        border-radius: 15px;
        margin: 10px;
        background: linear-gradient(#f7dc58, #fea116);
    }
    .card-outer-blue{
        border-radius: 15px;
        margin: 10px;
        background:linear-gradient(#70def7, #106afe);
    }
    .card-outer-green{
        border-radius: 15px;
        margin: 10px;
        background:linear-gradient(#8eeb98, #449d44);
    }
    .card-outer-purple{
        border-radius: 15px;
        margin: 10px;
        background: linear-gradient(#c889f6, #6d4cb5);
    }
    .card{
        display: flex;
        justify-content: center;
        align-items: center;
        background: url(https://static.leetcode-cn.com/cn-assets/webpack_bundles/images/algorithm_bg.90562ce29.png);
        background-size: 100% 100%;
        height: 100px;
        min-width: 120px;
        border-radius: 15px;
        color: white;
        font-size: 16px;
    }

    .btns-space{
        width: 100%;
        display: flex;
        justify-content: space-around;
    }

    .solved{
        margin: 20px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .m-label {
        padding: .3em .7em .4em;
        font-weight: 700;
        line-height: 1;
        color: #fff;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: 1020px;
    }

    .ques-list{
        margin-top: 20px;
    }
    .ques-head{
        color: black;
        font-size: 16px;
        height: 35px;
        line-height: 35px;
        border-top: 1px solid #f1f1f1;
    }
    .ques-row{
        padding: 10px 0;
        cursor: pointer;
    }

    .ques-row:hover{
        background: rgba(231, 246, 252, 0.78);
    }

    .m-rate{
        margin: 0 0 40px 40px ;
    }

    .rate-top{
        margin-top: 20px;
        border-radius: 20px 20px 10px 10px;
        background: #f1f1f1;
        padding: 10px 20px;
        text-align: center;
    }
    .rate-bottom{
        margin-top: 20px;
        border-radius: 10px 10px 20px 20px;
        background: #f1f1f1;
        display: flex;
        padding: 5px 10px;
        justify-content: space-around;
    }
    .rate-bottom>div{
        width: 50%;
        padding: 5px 10px;
    }

    .solved-info{
        display: flex;
    }

    .solved-item{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 34%;
        line-height: 20px;
    }

    .blank-10{
        height: 10px;
    }

    .hot-ques{
        width: 100%;
        line-height: 20px;
        padding: 10px 20px;
        border-radius: 10px;
        cursor: pointer;
    }
    .hot-ques:hover{
        background: #f1f1f1;
    }
    .hot-ques-pre{
        margin-right: 10px;
    }
    .cvs-container{
        box-sizing: border-box;
        width: 100%;
        margin: 0;
        text-align: center;
    }
    @media screen and (max-width: 500px) {
        .card{
            width: 50%;
            height: 50px;
        }

        .cards-container{
            justify-content: space-around;
            flex-wrap: wrap;
        }

        .m-smart-padding{
            margin: 20px 0;
        }

        .m-rate{
            margin: 20px 10px;
        }

        .cvs-container{
            padding: 0 30px;
        }
    }
</style>