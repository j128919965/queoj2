<template>
    <div class="bars-container">
        <div class="bars-line">
            <ul class="barList">
                <li class="barListItem bar-title" @click="$goRoute('/index')">QueOJ</li>
                <li class="barListItem" @click="$goRoute('/index')">首页</li>
                <li class="barListItem" @click="$goRoute('/problemSet')">题库</li>
                <li class="barListItem" v-if="login && userInfo.role>1" @click="$goRoute('/problemConfig')">管理</li>
            </ul>
            <div class="barList">
                <div v-if="!login" class="barListItem" @click="$goRoute('/login')">
                    登录
                </div>
                <div v-else class="barListItem">
                    <a-dropdown>
                        <a class="ant-dropdown-link icon-and-name" @click="e => e.preventDefault()">
                            {{userInfo.nickname}}<img :src="userInfo.favicon" alt="" id="headImg" class="head-link">
                        </a>
                        <a-menu slot="overlay">
                            <a-menu-item>
                                <a @click="$goRoute('/userCenter')">我的信息</a>
                            </a-menu-item>
                            <a-menu-item>
                                <a @click="logout">退出登录</a>
                            </a-menu-item>
                        </a-menu>
                    </a-dropdown>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "TitleBar",
        computed:{
            login:function () {
                return this.$store.state.login
            },
            userInfo:function (){
                return this.$store.state.info
            }
        },
        methods:{
            logout:function () {
                this.$store.commit("logout")
            }
        }
    }
</script>

<style scoped>
    .icon-and-name>img{
        margin-left: 10px;
    }
    .icon-and-name{
        margin-bottom: 10px;
        color: black;
    }
    .bars-container{
        box-shadow: 0 1px 4px rgba(0,0,0,0.1);
    }
    .bars-line{
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        height: 60px;
        display: flex;
        align-items: stretch;
        justify-content: space-between;
        font-size: 16px;
        padding: 8px 16px;
    //background-color:#242526;
    }
    .barList{
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
        margin-bottom: 0;
        padding: 0;
    }
    .barListItem{
        height: 20px;
        line-height: 20px;
        margin-right: 30px;
        cursor: pointer;
        list-style: none;
        color: rgba(0,0,0,0.65);
    }
    .barListItem>img{
        height: 100%;
    }
    .bar-title{
        font-size: 24px;
        font-weight: bolder;
        letter-spacing: 2px;
    }
    .head-link{
        width: 30px;
        height: 30px;
        border-radius: 15px;
    }
</style>