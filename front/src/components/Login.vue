<template>
    <div :class="is_login_page?'login-container':'login-container-large'">
        <div class="login-title">
            QUEOJ
        </div>
        <form method="post" class="login-form" v-if="is_login_page">
            <a-input size="large" name="username" v-model="username" placeholder="用户名" />
            <span :style="!username_required?'color:#FaFaFa;':''">请输入用户名</span>
            <a-input size="large" name="password" v-model="password" type="password" placeholder="密码"/>
            <span :style="!password_required?'color:#FaFaFa;':''">请输入密码</span>
            <a-button size="large" @click="postLogin" class="m-btn">登录</a-button>
        </form>
        <form method="post" class="login-form-large" v-else>
            <a-input size="large" name="username" v-model="username" placeholder="用户名" />
            <span :style="!username_required?'color:#FaFaFa;':''">请输入用户名</span>
            <a-input size="large" name="password" v-model="password" type="password" placeholder="密码"/>
            <span :style="!password_required?'color:#FaFaFa;':''">请输入密码</span>
            <a-input size="large" v-model="pswd_re" type="password" placeholder="再次输入密码"/>
            <span :style="!not_e?'color:#FaFaFa;':''">两次输入不一致</span>
            <a-button size="large" @click="postRegister" class="m-btn">注册</a-button>
        </form>
        <span v-if="is_login_page" @click="is_login_page=false" class="login-switch">没有账号？</span>
        <span v-else @click="is_login_page=true" class="login-switch">已有账号？</span>
    </div>
</template>

<script>
    import {API_LOGIN,API_USER_REGISTER} from '../utils/urls'
    import {post,put} from '../utils/util'
    export default {
        name: "Login",
        data:function () {
            return{
                is_login_page:true,
                username:"",
                password:"",
                pswd_re:"",
                username_required:false,
                password_required:false,
                not_e:false

            }
        },
        created:function(){
            if (this.$store.state.login){
                this.$goRoute('/userCenter')
            }
        },
        methods:{
            async postLogin(){
                let f = false;
                if (this.username==null || this.username.trim()===""){
                    this.username_required = true;
                    f=true;
                }else {
                    this.username_required = false;
                }
                if (this.password==null || this.password.trim()===""){
                    this.password_required = true;
                    f=true;
                }else {
                    this.password_required = false;
                }
                if (f)return;
                let e = await post(API_LOGIN,{username: this.username,password: this.password});
                if (e.success){
                    this.$message.success("登录成功")
                    let d=e.data;
                    this.$store.commit("login",d)
                    this.$router.go(-1)
                }else {
                    this.$message.error("用户名或密码错误")
                }
            },
            async postRegister(){
                let f = false;
                if (this.username==null || this.username.trim()===""){
                    this.username_required = true;
                    f=true;
                }else {
                    this.username_required = false;
                }
                if (this.password==null || this.password.trim()===""){
                    this.password_required = true;
                    f=true;
                }else {
                    this.password_required = false;
                }
                if (this.pswd_re==null || this.pswd_re!=this.password){
                    this.not_e=true;
                    f=true;
                }else {
                    this.not_e=false;
                }
                if (f)return;
                let e = await put(API_USER_REGISTER,{username: this.username,password: this.password});
                if (e.success){
                    this.$message.success("注册成功,请登录")
                    this.is_login_page=true;
                }else {
                    this.$message.error("用户名已存在")
                }
            }
        }
    }
</script>

<style scoped>
    .login-switch{
        cursor: pointer;
        color: gray;
        font-size: 10px;
        margin-top: 10px;
    }
    .login-container{
        background-color: #FaFaFa;
        margin: 100px auto;
        border-radius: 3px;
        text-align: center;
        box-shadow: 0 2px 6px 0 rgba(0,0,0,.12);
        width: 400px;
        padding: 20px;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        height: 300px;
    }
    .login-container-large{
        background-color: #FaFaFa;
        margin: 100px auto;
        border-radius: 3px;
        text-align: center;
        box-shadow: 0 2px 6px 0 rgba(0,0,0,.12);
        width: 400px;
        padding: 20px;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        height: 360px;
    }
    .login-title{
        font-size: 48px;
    }
    .login-form{
        height: 170px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .login-form-large{
        height: 230px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .m-btn{
        color: #fff;
        background-color: #545b62;
        border-color: #4e555b
    }
</style>