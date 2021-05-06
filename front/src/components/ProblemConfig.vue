<template>
    <a-layout id="components-layout-demo-custom-trigger">
        <a-layout-sider theam="light" :trigger="null">
            <a-menu theme="light" mode="inline" :default-selected-keys="['1']" style="height: 100%">
                <a-menu-item key="1" @click="changeTab(1)">
                    <a-icon type="book" />
                    <span>题目列表</span>
                </a-menu-item>
                <a-menu-item key="2" @click="changeTab(2)">
                    <a-icon type="video-camera" />
                    <span>新增题目</span>
                </a-menu-item>
            </a-menu>
        </a-layout-sider>
        <a-layout>
            <a-layout-content
               :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }"
            >
                <ProblemForm v-if="tab===2"/>
                <a-row v-else>
                    <a-col :span="24" class="ques-list" v-if="problems.length>0">
                        <a-row class="ques-head">
                            <a-col :span="4">ID</a-col>
                            <a-col :span=" 6">题名</a-col>
                            <a-col :span="4">分数</a-col>
                            <a-col :span="4">难度</a-col>
                            <a-col :span=" 6">操作</a-col>
                        </a-row>
                        <div v-for="p in problems" :key="p.id">
                            <div class="ques-row">
                                <a-row>
                                    <a-col :span="4">{{p.id}}</a-col>
                                    <a-col :span="6">{{p.name}}</a-col>
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
                                    <a-col :span=" 6">
                                        <a-space size="large">
                                            <a-tooltip>
                                                <template slot="title">
                                                    删除
                                                </template>
                                                <a-icon type="delete" @click="del(p.id)"/>
                                            </a-tooltip>
                                            <a-tooltip>
                                                <template slot="title">
                                                    修改
                                                </template>
                                                <a-icon type="edit" @click="show"/>
                                            </a-tooltip>
                                        </a-space>
                                    </a-col>
                                </a-row>
                            </div>
                        </div>
                    </a-col>
                    <a-col :span="24" v-else>
                        <a-empty description="暂无数据" />
                    </a-col>
                </a-row>
            </a-layout-content>
        </a-layout>
    </a-layout>
</template>

<script>
import ProblemForm from "@/components/forms/ProblemForm";
import {get,del} from "@/utils/util";
import {API_PROBLEM} from "@/utils/urls";
export default {
    name: "ProblemConfig",
    components:{ProblemForm},
    data() {
        return {
            collapsed: false,
            tab:1,
            problems:[]
        };
    },
    mounted:async function(){
        await this.getProblemList();
    },
    methods:{
        changeTab(i){
            if (this.tab!==1 && i===1){
                this.getProblemList()
            }
            this.tab = i;
        },
        async getProblemList(){
            let response = await get(API_PROBLEM,{from:0,limit:20});
            this.problems = response.data.list;
            this.total = response.data.total;
        },
        show(){
            console.log("?")
        },
        async del(pid){
            this.$confirm(
                {title:'警告',
                    content:'仅当该题目没有人写过时可以删除成功。是否确定删除？',
                    onOk:async ()=>{
                    let response = await del(API_PROBLEM,{pid})
                    if (!response.success){
                        this.$message.error("该题目不能被删除")
                    }else {
                        this.$message.success("删除成功")
                        this.getProblemList()
                    }},
                    onCancel(){},
                    okText:"确定",
                    cancelText:"取消"
                });

        }
    }
}
</script>

<style scoped>
    #components-layout-demo-custom-trigger{
        height: 100%;
    }
    #components-layout-demo-custom-trigger .trigger {
        font-size: 18px;
        line-height: 64px;
        padding: 0 24px;
        cursor: pointer;
        transition: color 0.3s;
    }

    #components-layout-demo-custom-trigger .trigger:hover {
        color: #1890ff;
    }

    #components-layout-demo-custom-trigger .logo {
        height: 32px;
        background: rgba(255, 255, 255, 0.2);
        margin: 16px;
    }
    .ques-list{
        margin-top: 20px;
        font-size: 15px;
    }
    .ques-head{
        color: black;
        font-size: 16px;
        height: 35px;
        line-height: 35px;
        border-top: 1px solid #f1f1f1;
        text-align: center;
    }
    .ques-row{
        padding: 10px 0;
        text-align: center;
    }

    .ques-row:hover{
        background: rgba(231, 246, 252, 0.78);
    }
    .rate-bottom>div{
        width: 50%;
        padding: 5px 10px;
    }
</style>