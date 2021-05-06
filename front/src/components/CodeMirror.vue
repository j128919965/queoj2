<template>
    <div class="in-coder-panel">
        <textarea ref="textarea"></textarea>
    </div>
</template>

<script type="text/ecmascript-6">
    // 引入全局实例
    import _CodeMirror from 'codemirror'

    // 核心样式
    import 'codemirror/lib/codemirror.css'
    // 引入主题后还需要在 options 中指定主题才会生效
    import 'codemirror/theme/3024-day.css'

    // 需要引入具体的语法高亮库才会有对应的语法高亮效果
    // codemirror 官方其实支持通过 /addon/mode/loadmode.js 和 /mode/meta.js 来实现动态加载对应语法高亮库
    // 但 vue 貌似没有无法在实例初始化后再动态加载对应 JS ，所以此处才把对应的 JS 提前引入
    import 'codemirror/mode/clike/clike'


    // 尝试获取全局实例
    const CodeMirror = window.CodeMirror || _CodeMirror

    export default {
        name: 'CodeMirror',
        props: {
            // 外部传入的内容，用于实现双向绑定
            value: String
        },
        data() {
            return {
                // 内部真实的内容
                code: '',
                // 默认的语法类型
                mode: 'text/x-java',
                // 编辑器实例
                coder: null,
                // 默认配置
                options: {
                    // 缩进格式
                    tabSize: 2,
                    // 主题，对应主题库 JS 需要提前引入
                    theme: '3024-day',
                    // 显示行号
                    lineNumbers: true,
                    line: true
                }
            }
        },
        mounted() {
            // 初始化
            this._initialize()
        },
        methods: {
            // 初始化
            _initialize() {
                // 初始化编辑器实例，传入需要被实例化的文本域对象和默认配置
                this.coder = CodeMirror.fromTextArea(this.$refs.textarea, this.options)
                // 编辑器赋值
                this.coder.setValue(this.value || this.code)

                // 支持双向绑定
                this.coder.on('change', (coder) => {
                    this.code = coder.getValue()

                    if (this.$emit) {
                        this.$emit('input', this.code)
                    }
                })

                // 尝试从父容器获取语法类型
                if (this.language) {
                    // 获取具体的语法类型对象
                    let modeObj = this._getLanguage(this.language)

                    // 判断父容器传入的语法是否被支持
                    if (modeObj) {
                        this.mode = modeObj.label
                    }
                }
            },
            // 获取当前语法类型
            _getLanguage(language) {
                // 在支持的语法类型列表中寻找传入的语法类型
                return this.modes.find((mode) => {
                    // 所有的值都忽略大小写，方便比较
                    let currentLanguage = language.toLowerCase()
                    let currentLabel = mode.label.toLowerCase()
                    let currentValue = mode.value.toLowerCase()

                    // 由于真实值可能不规范，例如 java 的真实值是 x-java ，所以讲 value 和 label 同时和传入语法进行比较
                    return currentLabel === currentLanguage || currentValue === currentLanguage
                })
            },
            getCode(){
                return this.coder.getValue();
            },
            setCode(code){
                this.coder.setValue(code);
            }
        }
    }
</script>

<style>
    .in-coder-panel {
        flex-grow: 1;
        display: flex;
        position: relative;
        text-align: left;
        height: 100%;
    }


    .CodeMirror {
        flex-grow: 1;
        z-index: 1;
        height: 100%;
    }


    .CodeMirror-code {
        line-height: 19px;
    }


    .code-mode-select {
        position: absolute;
        z-index: 2;
        right: 10px;
        top: 10px;
        max-width: 130px;
    }

</style>