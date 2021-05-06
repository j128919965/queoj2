<template>
    <div class="info-form">
        <div class="form-line">
            <div class="form-left">昵称</div>
            <div class="form-right">
                <a-input v-model="nickname" @change="bindChange('nickname')"/>
            </div>
            <div class="form-button" v-if="nickname_req" @click="save('nickname')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">性别</div>
            <div class="form-right"><a-switch v-model="gender" @change="bindChange('gender')" /></div>
            <div class="form-button" v-if="gender_req"  @click="save('gender')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">电话</div>
            <div class="form-right"><a-input v-model="phone" @change="bindChange('phone')"/> </div>
            <div class="form-button" v-if="phone_req" @click="save('phone')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">邮箱</div>
            <div class="form-right"><a-input v-model="email" @change="bindChange('email')"/></div>
            <div class="form-button" v-if="email_req" @click="save('email')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">github</div>
            <div class="form-right"><a-input v-model="github" @change="bindChange('github')"/></div>
            <div class="form-button" v-if="github_req" @click="save('github')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">个人网站</div>
            <div class="form-right"><a-input v-model="website" @change="bindChange('website')"/></div>
            <div class="form-button" v-if="website_req" @click="save('website')">保存</div>
        </div>
        <div class="form-line">
            <div class="form-left">简介</div>
            <div class="form-right"><a-textarea v-model="introduction" @change="bindChange('introduction')" :auto-size="{  maxRows: 3 }" /></div>
            <div class="form-button" v-if="introduction_req" @click="save('introduction')">保存</div>
        </div>
    </div>
</template>

<script>
    import {post} from '@/utils/util';
    import {API_UPDATE_INFO} from '@/utils/urls';
    export default {
        name: "InfoForm",
        data:function () {
            return {
                nickname:"",gender:"",phone:"",email:"",introduction:"",github:"",website:"",
                nickname_req:false,gender_req:false,phone_req:false,email_req:false,introduction_req:false,github_req:false,website_req:false
            }
        },
        mounted:function () {
            let info = this.$store.state.info;
            this.nickname = info.nickname;
            this.gender = info.gender > 0;
            this.phone = info.phone;
            this.email = info.email;
            this.introduction = info.introduction;
            this.github = info.github;
            this.website = info.website;
        },
        methods:{
            bindChange(str){
                this[`${str}_req`] = true;
            },
            async save(str=""){
                let pattern = null;
                let message = "";
                let data = "";
                if (str==='nickname'){
                    data = this.nickname;
                }else if (str==='gender'){
                    data = this.gender?1:0;
                }else if (str==='phone'){
                    pattern=/\d{11}/;
                    data = this.phone;
                    message = '请输入正确的11位手机号'
                }else if (str==='email'){
                    pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                    data = this.email;
                    message = '请输入正确的邮件地址'
                }else if (str==='introduction'){
                    data = this.introduction;
                }else if (str==='github'){
                    data = this.github;
                }else {
                    data = this.website;
                }

                if (pattern){
                    let f = pattern.test(data);
                    if (!f){
                        this.$message.error(message);
                        return;
                    }
                }

                let uid = this.$store.state.info.id;
                let f = await post(`${API_UPDATE_INFO}/${str}`, {data,uid})
                if (f.success){
                    this.$message.success('修改成功');
                    let info = this.$store.state.info;
                    info[str]=data;
                    this.$store.commit("updateInfo",info)
                    this[`${str}_req`] = false;
                }else {
                    this.$message.error(f.message);
                }
            }
        }
    }

</script>

<style scoped>
    .info-form{
        width: 100%;
        max-height: 550px;
        overflow-y: auto;
    }
    .form-line{
        width: 100%;
        padding: 20px 0;
        position: relative;
        display: flex;
    }
    .form-left{
        width: 20%;
    }
    .form-right{
        width: 80%;
    }
    .form-button{
        padding: 2px 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        position: absolute;
        right: 0;
        bottom: -10px;
        cursor: pointer;
        z-index: 2;
    }

</style>