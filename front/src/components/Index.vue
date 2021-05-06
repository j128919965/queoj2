<template>
    <main class="container">
        <div class="line">
            <div class="title-img">
                <img src="https://sunshine-anime.oss-cn-beijing.aliyuncs.com/queoj.png" alt="logo">
            </div>
        </div>
        <div class="line">
            <h1 class="title">
                Welcome to QueOJ!
            </h1>
            <h3 class="subtitle">如果不是为了找工作，谁会没事刷oj呢</h3>
            <a-space size="large">
                <a-button type="primary" size="large" @click="$goRoute('/problemSet')">
                    立即开始
                </a-button>
                <a href="https://github.com/j128919965" target="_blank">
                    <a-button size="large" >
                        关于我们
                    </a-button>
                </a>
            </a-space>

        </div>
    </main>
</template>

<script>
    export default {
        name: "Index"
    }
</script>

<style scoped>
    .line{
        width: 100%;
        text-align: center;
    }
    .title{
        letter-spacing: 1px;
        font-family: Ubuntu,sans-serif;
        font-size: 56px;
        user-select: none;
        font-weight: 600;
    }
    .subtitle{
        font-size: 30px;
        margin: 50px auto;
    }
    .title-img{
        user-select: none;
        margin: 50px auto;
        display: flex;
        justify-content: center;
    }
</style>