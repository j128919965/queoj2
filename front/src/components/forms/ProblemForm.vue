<template>
    <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
        <a-form-item label="题目名称">
            <a-input
                v-decorator="['name', { rules: [{ required: true, message: '请输入题目名称!' }] }]"
            />
        </a-form-item>
        <a-form-item label="分数">
            <a-input-number
                v-decorator="['point', { rules: [{ required: true, message: '请输入题目分数！' }] }]"
            />
        </a-form-item>
        <a-form-item label="难度">
            <a-select
                v-decorator="[
                  'level',
                  { rules: [{ required: true, message: '请选择题目难度!' }] },
                ]"
                placeholder="请选择"
            >
                <a-select-option value="1">
                    简单
                </a-select-option>
                <a-select-option value="2">
                    中等
                </a-select-option>
                <a-select-option value="3">
                    困难
                </a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="题目描述">
            <a-textarea
                placeholder="请输入题目描述，可以使用html语法"
                v-decorator="['description', { rules: [{ required: true, message: '请输入题目名称!' }] }]"
            />
        </a-form-item>
        <a-form-item label="默认代码">
            <a-icon type="info-circle" @click="showCodeTip" class="m-form-tip"/>
            <a-textarea
                    placeholder="请输入默认代码"
                    v-decorator="['defaultCode', { rules: [{ required: true, message: '请输入默认代码!' }] }]"
            />
        </a-form-item>
        <a-form-item label="测试用例">
            <a-icon type="info-circle" @click="showPendTip" class="m-form-tip"/>
            <a-textarea
                    placeholder="请输入测试用例"
                    v-decorator="['pendingCode', { rules: [{ required: true, message: '请输入测试用例!' }] }]"
            />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
            <a-button type="primary" html-type="submit">
                提交
            </a-button>
        </a-form-item>
    </a-form>
</template>

<script>
    import {put} from '@/utils/util'
    import {API_PROBLEM} from '@/utils/urls'
    export default {
        name: "ProblemForm",
        data() {
            return {
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            };
        },
        methods: {
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields(async (err, values) => {
                    if (!err) {
                        let response = await put(API_PROBLEM,values);
                        if (!response.success){
                            this.$message.error("提交失败")
                        }else {
                            this.$message.success("提交成功")
                        }
                    }
                });
            },
            showCodeTip(){
                this.$notification.open({
                    message: `代码模板`,
                    description:
                        '请输入形如"int get(int p1,int p2)"的方法名，不要加大括号、分号。不要有任何多余的空格。',
                    placement:'topLeft',
                });
            },
            showPendTip(){
                this.$notification.open({
                    message: `测试用例`,
                    description:
                        '每个参数占一行，返回结果占一行。使用例如：“ 1 \\n 2 \\n 3 ” （此处使用“\\n”代表换行）',
                    placement:'topLeft',
                });
            }
        },

    }
</script>

<style scoped>
.m-form-tip{
    position: absolute;
    z-index: 1;
    left: -15px;
    font-size: 15px;
}
</style>