<template>
    <div class="container usercenter">
        <a-modal v-model="visiable" title="修改信息" on-ok="handleOk">
            <template slot="footer">
                <a-button key="back" @click="closeModal">
                    返回
                </a-button>
            </template>
            <InfoForm/>
        </a-modal>
        <a-row>
            <a-col :span="6" offset="2">
                <div class="m-profile-panel">
                    <div class="panel-heading">
                        <span>我的信息</span>
                    </div>
                    <div class="panel-body margint">
                        <img :src="userInfo.favicon" alt="">
                        <div class="user">
                            <span class="nickname">{{userInfo.nickname}}</span>
                            <span class="username">{{userInfo.username}}</span>
                            <span class="username">全站排名：{{ranking}}</span>
                        </div>
                    </div>
                </div>
                <div class="m-profile-panel">
                    <div class="panel-heading">
                        <span>我的资料</span>
                        <a-button @click="showModal">修改</a-button>
                    </div>
                    <div class="profile-body">
                        <div class="profile-title">个人简介</div>
                        <div class="profile-content">
                            <a-icon type="info" /> {{userInfo.introduction}}
                        </div>
                        <div class="profile-title">电话号码</div>
                        <div class="profile-content">
                            <a-icon type="phone" />{{userInfo.phone}}
                        </div>
                        <div class="profile-title">电子邮件</div>
                        <div class="profile-content">
                            <a-icon type="mail" /> {{userInfo.email}}
                        </div>
                        <div class="profile-title">github</div>
                        <div class="profile-content">
                            <a-icon type="github" /> {{userInfo.github}}
                        </div>
                        <div class="profile-title">个人网站</div>
                        <div class="profile-content">
                            <a-icon type="desktop" />{{userInfo.website}}
                        </div>
                    </div>
                </div>
            </a-col>
            <a-col :span="13" offset="1">
                <div class="m-profile-right">
                    <div class="m-profile-panel">
                        <div class="panel-heading">
                            <span>我的战绩</span>
                        </div>
                        <div class="summitions" v-if="submitions.length>0">
                            <div class="submit-line">
                                <a-col :span="5">
                                    <div class="submit-title">状态</div>
                                </a-col>
                                <a-col :span="5"><div class="submit-title">题目id</div></a-col>
                                <a-col :span="9"><div class="submit-title">题目名称</div></a-col>
                                <a-col :span="5"><div class="submit-title">提交时间</div></a-col>
                            </div>
                            <div v-for="(r,i) in submitions" :key="i" class="submit-line">
                                <a-col :span="5"><div class="submit-content">
                                    <a-tag color="green" v-if="r.state==1">解答成功</a-tag>
                                    <a-tag color="blue" v-else-if="r.state==2">正在判题</a-tag>
                                    <a-tag color="red" v-else-if="r.state==3">答案错误</a-tag>
                                    <a-tag color="orange" v-else-if="r.state==4">运行出错</a-tag>
                                    <a-tag color="blue" v-else-if="r.state==5">编译出错</a-tag>
                                    <a-tag color="orange" v-else>时间过长</a-tag>
                                </div></a-col>
                                <a-col :span="5"><div class="submit-content">{{r.id}}</div></a-col>
                                <a-col :span="9"><div class="submit-content">{{r.name}}</div></a-col>
                                <a-col :span="5"><div class="submit-content">{{r.time}}</div></a-col>
                            </div>
                        </div>
                        <div class="summitions" v-else>
                            <a-empty description="暂无数据" />
                        </div>
                    </div>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import InfoForm from "@/components/forms/InfoForm";
    import {get} from "@/utils/util"
    import {API_USER_RANK,API_PROBLEM_SUBMIT_SIMPLE_RECORD} from "@/utils/urls"
    export default {
        name: "UserCenter",
        data:function(){
            return {
                ranking:1,
                visiable:false,
                submitions:[

                ]
            }
        },
        created:function () {
            if (!this.$store.state.login){
                this.$goRoute("/login");
            }
        },
        mounted:function(){
            this.getRank()
            this.getRecords()
        },
        computed:{
            userInfo:function () {
                return this.$store.state.info;
            }
        },
        methods:{
            showModal() {
                this.visiable=true
            },
            closeModal(){
                this.visiable=false;
            },
            async getRank(){
                let id = this.$store.state.info.id;
                let response = await get(API_USER_RANK,{id})
                this.ranking = response.data
            },
            async getRecords(){
                let id = this.$store.state.info.id;
                let response = await get(API_PROBLEM_SUBMIT_SIMPLE_RECORD,{id})
                this.submitions=response.data;
            }
        },
        components:{
            InfoForm
        }

    }
</script>

<style scoped>
    .usercenter{
        margin-top: 20px;
    }

    .profile-body{
        display: flex;
        flex-direction: column;
        padding-left: 20px;
        padding-bottom: 20px;
    }

    .profile-content{
        width: 100%;
        font-size: 13px;
    }
    .profile-content>i{
        font-size: 16px;
        margin-right: 10px;
        color: #1890ff;
    }
    .profile-title{
        width: 100%;
        font-size: 16px;
        font-weight: bold;
        margin: 15px 0;
    }

    .summitions{
        display: flex;
        flex-direction: column;
    }

    .submit-title{
        text-align: center;
        width: 100%;
        height: 30px;
        line-height: 30px;
        font-size: 18px;
    }

    .submit-line{
        display: flex;
        -webkit-box-align: center;
        align-items: center;
        -webkit-box-pack: justify;
        justify-content: space-between;
        margin-bottom: 1px;
        width: 100%;
        padding: 13px 16px;
        box-shadow: 0px 1px 2px rgba(0,0,0, 0.1),0px 2px 8px rgba(0,0,0, 0.08);
        border-radius: 8px;
        background-color: rgba(255,255,255,1);
    }

    .submit-content{
        text-align: center;
        width: 100%;
        height: 25px;
    }

    .nickname{
        font-size: 24px;
    }

    .username{
        font-size: 12px;
        font-weight: lighter;
    }
    .m-profile-panel {
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-bottom: 2.0rem;
        box-shadow: 0 2px 6px 0 rgba(0,0,0,.12);
    }

    .m-profile-panel>.panel-heading {
        padding: 0.5rem 1rem;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #ddd;
        background-color: #F5F5F5;
    }

    .m-profile-panel>.panel-heading>span {
        font-size: 1.0rem;
    }

    .m-profile-panel>.panel-heading>a {
        text-decoration: none;
    }

    .m-profile-panel>.panel-heading>a:hover {
        background-color: #DDD;
        color: #000;
    }

    .m-profile-panel>.panel-body {
        display: flex;
        padding-bottom: 20px;
    }

    .m-profile-panel>.panel-body>.user {
        display: flex;
        flex-direction: column;
    }

    .m-profile-panel>.panel-body>.user>.ranking>img {
        margin-right: 0.3rem;
        width: 18px;
        height: 18px;
    }

    .m-profile-panel>.panel-body>.user>.social>img {
        width: 32px;
        height: 32px;
    }

    .m-profile-panel>.panel-body>img {
        width: 80px;
        height: 80px;
        border-radius: 5px;
        margin: 0 1.2rem;
    }

    .m-profile-panel>.panel-body>ul {
        width: 100%;
        padding-inline-start: 0px;
        margin-block-end: 0px;
    }

    .m-profile-panel>.panel-body>ul>svg {
        margin-top: 2.0rem;
    }

    .m-profile-panel>.panel-body>ul>li {
        display: flex;
        justify-content: space-between;
        padding: 0.5rem 1rem;
        border-bottom: 1px solid #ddd;
    }

    .m-profile-panel>.panel-body>ul>li>span:last-child {
        background-color: #5cb85c;
        color: #FFF;
        font-size: 0.8rem;
        font-weight: bold;
        min-width: 10px;
        padding: 0px 5px;
        border-radius: 10px;
    }

    .m-profile-panel>.panel-body>ul>li>span>img {
        width: 18px;
        height: 18px;
        margin-top: -0.2rem;
        margin-right: 0.3rem;
    }

    .margint {
        margin-top: 1.0rem;
    }
</style>