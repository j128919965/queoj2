const axios = require("axios");
const qs = require("qs");
const {message} = require('ant-design-vue')
const {API_TOKEN_REFRESH} = require('@/utils/urls')
const store = require('@/store/index').default

const getTokens = ()=>{
    let tokens = JSON.parse(localStorage.getItem("tokens"));
    if (!tokens){
        tokens = {accessToken:null,refreshToken:null}
    }
    return tokens;
}

const isTokenError = async (e)=>{
    console.log(e.data)
    if (!e.data)return ;
    if ( e.data.data===3 ){
        console.log("refreshed")
        await refreshToken();
        return true;
    }else if (e.data.data===4 || e.data.data===1){
        store.commit("logout");
        message.error("登录状态已过期，请重新登录")
    }
    return false;
}

const refreshToken = async ()=>{
    let response = await post(API_TOKEN_REFRESH,{token:getTokens().refreshToken});
    store.commit('updateTokens',response.data)
}

const get = (url="",data={})=>{
    let data_copy = data;
    let accessToken = getTokens().accessToken;
    let headers = {};
    if (accessToken!=null){
        headers.Authorization = accessToken;
    }
    return new Promise(resolve => {
        axios.get(url,{params:data,headers})
            .then(async e=> {
                if (!e.data.success ){
                    let flag = await isTokenError(e);
                    if (flag) {
                        e.data = await get(url, data_copy);
                    }
                }
                resolve(e.data)
            })
            .catch((e)=>{message.error('网络错误，请稍后再试');console.log(e)});
    })
}

const post = (url="",data={})=>{
    let data_copy = data;
    let accessToken = getTokens().accessToken;
    let headers = {'Content-Type':'application/x-www-form-urlencoded'};
    if (accessToken!=null){
        headers.Authorization = accessToken;
    }
    return new Promise(resolve => {
        axios.post(url,qs.stringify(data),{headers})
            .then(async e=> {
                if (!e.data.success ){
                    let flag = await isTokenError(e);
                    if (flag) {
                        e.data = await post(url, data_copy);
                    }
                }
                resolve(e.data)
            })
            .catch((e)=>{message.error('网络错误，请稍后再试');console.log(e)});
    })
}

const put = (url="",data={})=>{
    let data_copy = data;
    let accessToken = getTokens().accessToken;
    let headers = {'Content-Type':'application/x-www-form-urlencoded'};
    if (accessToken!=null){
        headers.Authorization = accessToken;
    }
    return new Promise(resolve => {
        axios.put(url,qs.stringify(data),{headers})
            .then(async e=>{
                if (!e.data.success ){
                    let flag = await isTokenError(e);
                    if (flag) {
                        e.data = await put(url, data_copy);
                    }
                }
                resolve(e.data)
            })
            .catch((e)=>{message.error('网络错误，请稍后再试');console.log(e)});
    })
}

const del = (url="",data={})=>{
    let data_copy = data;
    let accessToken = getTokens().accessToken;
    let headers = {};
    if (accessToken!=null){
        headers.Authorization = accessToken;
    }
    return new Promise(resolve => {
        axios.delete(url,{params:data,headers})
            .then(async e=> {
                if (!e.data.success ){
                    let flag = await isTokenError(e);
                    if (flag) {
                        e.data = await del(url, data_copy);
                    }
                }
                resolve(e.data)
            })
            .catch((e)=>{message.error('网络错误，请稍后再试');console.log(e)});
    })
}
module.exports={
    get,post,put,del
}