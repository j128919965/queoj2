import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        login:false,
        tokens:{},
        info: {}
    },
    mutations: {
        login(state,data){
            if (data.info.favicon==null){
                data.info.favicon = 'https://assets.leetcode-cn.com/aliyun-lc-upload/default_avatar.png?x-oss-process=image%2Fresize%2Ch_60%2Cw_60'
            }
            state.login=true;
            state.tokens=data.tokens;
            state.info=data.info;
            localStorage.setItem("tokens",JSON.stringify(data.token))
            localStorage.setItem("login",true)
            localStorage.setItem("info",JSON.stringify(data.info))
        },
        logout(state){
            console.log("log out")
            state.login=false;
            state.tokens={}
            state.info={}
            localStorage.removeItem("tokens")
            localStorage.removeItem("login")
            localStorage.removeItem("info")
        },
        open(state){
            let login = localStorage.getItem("login");
            if (login){
                state.login=true;
                state.info=JSON.parse(localStorage.getItem("info"));
                state.tokens=JSON.parse(localStorage.getItem("tokens"));
            }
        },
        updateInfo(state,info){
            localStorage.removeItem("info");
            state.info=info;
            localStorage.setItem("info",JSON.stringify(info))
        },
        updateTokens(state,tokens){
            localStorage.removeItem("tokens")
            state.tokens=tokens;
            localStorage.setItem("tokens",JSON.stringify(tokens))
        }
    }
})