const SERVER = "http://localhost:8888/oj"

module.exports={
    API_LOGIN:SERVER+'/user/login',
    API_USER_REGISTER:SERVER+'/user/register',
    API_TOKEN_REFRESH:SERVER+'/user/refresh',
    API_USER_RANK:SERVER+'/user/rank',
    API_USER_COUNT:SERVER+'/user/count',
    API_PROBLEM:SERVER+'/problem',
    API_PROBLEM_TOTAL:SERVER+'/problem/total',
    API_PROBLEM_LIKE:SERVER+'/problem/like',
    API_PROBLEM_HOT:SERVER+'/problem/hot',
    API_PROBLEM_SUBMIT:SERVER+'/submit',
    API_PROBLEM_SUBMIT_RECORD:SERVER+'/submit',
    API_PROBLEM_SUBMIT_SIMPLE_RECORD:SERVER+'/submit/simple',
    API_PROBLEM_SUBMIT_STATE:SERVER+'/submit/state',
    API_UPDATE_INFO:SERVER+'/info'
}