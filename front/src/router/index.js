import Vue from 'vue'
import Router from 'vue-router'
import Index from "@/components/Index";
import ProblemSet from "@/components/ProblemSet";
import Problem from "@/components/Problem";
import Login from "@/components/Login";
import UserCenter from "@/components/UserCenter";
import ProblemConfig from "@/components/ProblemConfig";

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'index',
            component: Index
        },
        {
            path: '/index',
            name: 'index',
            component: Index
        },
        {
            path: '/problemSet',
            name: 'problemSet',
            component: ProblemSet
        },
        {
            path: '/problemConfig',
            name: 'problemConfig',
            component: ProblemConfig
        },
        {
            path: "/login",
            name: "login",
            component: Login
        },
        {
            path: '/problem/:id',
            name: 'problem',
            component: Problem
        },
        {
            path: '/userCenter',
            name: 'userCenter',
            component: UserCenter
        }
    ]
})
