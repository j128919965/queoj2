<template>
    <div class="m-pro-des-and-ans">
        <div class="half half-small" style="overflow-y:auto;">
            <div class="coding-tool-bar">
                <div class="coding-tool" @click="switchTab(true)">
                    <a-icon type="read" />
                    题目描述
                </div>
                <div class="coding-tool" @click="switchTab(false)">
                    <a-icon type="clock-circle" />
                    提交记录
                </div>
            </div>
            <div class="pro-info-block" v-show="des">
                <div class="pro-title">{{problemInfo.name}}</div>
                <div class="pro-level">
                    难度：
                    <span class="level-name">
                        <a-tag color="red" v-if="problemInfo.level==3">
                                        困难
                                    </a-tag>
                                    <a-tag color="orange" v-else-if="problemInfo.level==2">
                                        中等
                                    </a-tag>
                                    <a-tag color="green" v-else>
                                        简单
                                    </a-tag>
                    </span>
                    分数：
                    <span class="point-text">{{problemInfo.point}}</span>
                </div>
                <div class="pro-discrip" v-html="problemInfo.description">
                </div>
            </div>
            <div class="pro-info-block" id="solved_info_block" v-show="!des">
                <div class="pro-title">提交记录</div>
                <div class="submit-log-table" v-if="login">
                    <div class="submit-log-table" v-if="records.length==0">
                        <a-empty description="暂无数据" />
                    </div>
                    <div class="record-title" v-else>
                        <div class="record">
                            <a-col :span="8">
                                <div class="submit-time">提交时间</div>
                            </a-col>
                            <a-col :span="4">
                                <div class="submit-time">结果</div>
                            </a-col>
                            <a-col :span="6">
                                <div class="submit-time">用时</div>
                            </a-col>
                            <a-col :span="6">
                                <div class="submit-time">内存</div>
                            </a-col>
                        </div>
                    </div>
                    <div v-for="(r,i) in records" :key="i" class="submit-line">
                        <a-col :span="8"><div class="submit-content">{{r.time}}</div></a-col>
                        <a-col :span="4"><div class="submit-content">
                            <a-tag color="green" v-if="r.state==1">解答成功</a-tag>
                            <a-tag color="blue" v-else-if="r.state==2">正在判题</a-tag>
                            <a-tag color="red" v-else-if="r.state==3">答案错误</a-tag>
                            <a-tag color="orange" v-else-if="r.state==4">运行出错</a-tag>
                            <a-tag color="blue" v-else-if="r.state==5">编译出错</a-tag>
                            <a-tag color="orange" v-else>时间过长</a-tag>
                        </div></a-col>
                        <a-col :span="6"><div class="submit-content">{{r.usedTime}}ms</div></a-col>
                        <a-col :span="6"><div class="submit-content">{{r.usedSpace===-1?"NAN":(r.usedSpace/1024).toFixed(2)+"mb"}}</div></a-col>
                    </div>

                </div>
                <div class="submit-log-login" v-else>
                    <a-button @click="$goRoute('/login')">请先登录</a-button>
                </div>
            </div>
        </div>
        <div class="half half-big">
            <div class="coding-tool-bar">
                <a-tooltip placement="bottom">
                    <template slot="title">
                        恢复默认代码
                    </template>
                    <a-icon type="reload" class="coding-tool" @click="defaultCode"/>
                </a-tooltip>
                <a-tooltip placement="bottom">
                    <template slot="title">
                        暂存代码在本地
                    </template>
                    <a-icon type="save" class="coding-tool" @click="saveCode"/>
                </a-tooltip>
                <a-tooltip placement="bottom">
                    <template slot="title">
                        恢复本地暂存的代码
                    </template>
                    <a-icon type="download" class="coding-tool" @click="reloadCode"/>
                </a-tooltip>
<!--                <a-tooltip placement="bottom">-->
<!--                    <template slot="title">-->
<!--                        恢复上次提交的代码-->
<!--                    </template>-->
<!--                    <a-icon type="code" class="coding-tool"/>-->
<!--                </a-tooltip>-->
            </div>
            <div class="coding-area">
                <CodeMirror ref="editor" />
            </div>
            <div class="submit">
                <a-button v-if="!login" @click="$goRoute('/login')">请先登录</a-button>
                <template v-else>
                    <a-button v-if="!pending && problemInfo.id!==-1" @click="submit">提交</a-button>
                    <div class="pending" v-else>pending...</div>
                </template>
            </div>
        </div>
    </div>
</template>

<script>
    import CodeMirror from "@/components/CodeMirror";
    import {get,put} from "@/utils/util";
    import {API_PROBLEM,API_PROBLEM_SUBMIT,API_PROBLEM_SUBMIT_RECORD,API_PROBLEM_SUBMIT_STATE} from "@/utils/urls";
    export default {
        name: "Problem",
        data:function() {
            return {
                code:"",
                pending:false,
                des:true,
                problemInfo:{
                    id:-1,
                    name:"加载中，请稍候",
                    point:10,
                    level:1,
                    defaultCode:"",
                    description:"加载中，请稍候",
                },
                lastCode:"",
                records:[],
                timer:null
            };
        },
        computed:{
            login:function () {
                return this.$store.state.login
            }
        },
        components:{
            CodeMirror
        },
        mounted:function(){
            this.getProblemInfo();
        },
        beforeDestroy:function(){
            if (this.timer){
                clearInterval(this.timer);
                console.log("exit")
            }
        },
        methods:{
            switchTab:function (des) {
                this.des=des;
            },
            async getProblemInfo(){
                let pid = this.$route.params.id;
                let response = await get(`${API_PROBLEM}/${pid}`);
                if (!response.success){
                    this.$message.error("题目不存在！三秒后跳转到题库");
                    setTimeout(()=>this.$goRoute('/problemSet'),3000);
                    return ;
                }
                this.problemInfo = response.data;
                this.getRecords();
                this.defaultCode()
            },
            async submit() {
                this.pending = true;
                let editor = this.$refs.editor;
                let code = editor.getCode();
                let uid = this.$store.state.info.id;
                let record = {uid:uid, pid: this.problemInfo.id, code:code};
                console.log(record)
                let response = await put(API_PROBLEM_SUBMIT, record);
                if (!response.success){
                    this.pending = false;
                    this.$message.error("题目提交失败");
                    return;
                }
                this.$message.success("提交成功，正在判题中")
                this.timer = setInterval(()=>{this.getRecordState(response.data)},1500);
            },
            async getRecords(){
                let uid = this.$store.state.info.id;
                let pid = this.problemInfo.id;
                let response = await get(API_PROBLEM_SUBMIT_RECORD,{uid,pid});
                this.records = response.data;
            },
            async getRecordState(record_id){
                let response = await get(API_PROBLEM_SUBMIT_STATE,{id:record_id});
                if (response.data!==2){
                    console.log(response.data)
                    this.records.push(response.data)
                    this.$message.success("判题成功，请查看结果")
                    this.des = false;
                    clearInterval(this.timer)
                    this.pending = false;
                }

            },
            saveCode(){
                if (this.problemInfo.id!==-1){
                    let editor = this.$refs.editor;
                    let code = editor.getCode();
                    localStorage.setItem(`${this.problemInfo.id}_code`,code);
                    this.$message.success("保存成功");
                }
            },
            reloadCode(){
                if (this.problemInfo.id!==-1){
                    let editor = this.$refs.editor;
                    let code = localStorage.getItem(`${this.problemInfo.id}_code`);
                    editor.setCode(code);
                    this.$message.success("读取成功");
                }
            },
            defaultCode(){
                let editor = this.$refs.editor;
                editor.setCode(this.problemInfo.defaultCode);
            }

        }
    }
</script>

<style scoped>

    .submit-line{
        display: flex;
        -webkit-box-align: center;
        align-items: center;
        -webkit-box-pack: justify;
        justify-content: space-between;
        margin-bottom: 1px;
        width: 100%;
        padding: 13px;
        box-shadow: 0px 1px 2px rgba(0,0,0, 0.1),0px 2px 8px rgba(0,0,0, 0.08);
        border-radius: 8px;
        background-color: rgba(255,255,255,1);
    }
    .submit-content{
        text-align: center;
        width: 100%;
        height: 25px;
    }
    .m-pro-des-and-ans{
        box-sizing: border-box;
        width: 100%;
        height: 100%;
        display: flex;
        flex-wrap: nowrap;
        justify-content: space-around;
    }

    .half{
        height: 100%;
        max-height: 100%;
        border: 2px solid #f1f1f1;
        display: flex;
        flex-direction: column;
    }

    .half-small{
        width: 45%;
    }

    .half-big{
        width: 55%;
    }

    .coding-tool-bar{
        display: flex;
        justify-content: space-around;
        padding: 10px 0;
        background: #fafafa;
        user-select: none;
    }

    .coding-tool{
        cursor: pointer;
        user-select: none;
    }

    .pro-title{
        padding: 10px 20px;
        font-size: 25px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
    }

    .pro-level{
        color: black;
        padding: 10px 20px;
        margin-bottom: 20px;
        border-bottom:  1px solid #f1f1f1;
    }

    .level-name{
        margin-right: 30px;
    }

    .pro-discrip{
        font-size: 16px;
        padding: 20px;
        line-height: 20px;
        letter-spacing: 1px;
    }
    .pro-discrip>blockquote{
        padding: 10px;
        display: block;
        width: 100%;
        background: #fafafa;
        margin: 10px 0;
    }

    .submit-log-table{
        width: 100%;
        padding: 15px;
    }

    .record-title{
        font-size: 18px;
        margin-bottom: 20px;
        height: 50px;
    }

    .record-container{
        width: 100%;
    }

    .record{
        padding: 10px 0;
        text-align: center;
    }

    .submit{
        display: flex;
        justify-content: center;
        font-size: 30px;
        align-items: center;
        padding: 15px;
    }
    .submit>.m-btn{
        font-size: 20px;
        width: 120px;
    }
    .coding-area{
        width: 100%;
        height: 100%;
        max-height: 90%;
    }
    .pending{
        color: var(--success);
    }

    .submit-login{
        display: none;
    }

    .submit-log-login{
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 40px;
        text-align: center;
    }
</style>