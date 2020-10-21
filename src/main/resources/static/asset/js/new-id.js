//id生成工具
define(['jquery'], function ($) {

    //生成id的接口地址
    //var newIdApi = '../../demos/json/new-id-sample.json';
    var newIdApi = '/api/new-id';

    //调用接口生成Id，同步
    function ajaxSync() {
        var id = '';
        $.ajax({
            url: newIdApi,
            type: 'GET',
            async: false,       //同步调用
            success: function (data) {
                id = data.data;
            }
        });
        return id;
    }

    //调用接口生成Id，异步，通过回调方法返回
    function ajaxCallback(callback) {
        $.ajax({
            url: newIdApi,
            type: 'GET',
            success: function (data) {
                callback(data.data.id);
            }
        });
    }

    //调用接口生成Id，异步，包装成Promise
    function ajaxPromise() {
        var dfd = $.Deferred();
        $.ajax({
            url: newIdApi,
            type: 'GET',
            success: function (data) {
                dfd.resolve(data.data.id);
            }
        });
        return dfd.promise();
    }

    //生成随机Id，仅用于测试
    function test() {
        //模拟延时
        // var now = new Date().getTime();
        // while (new Date().getTime() < now + 1000) { }

        //生成随机Id，仅用于测试
        return Math.floor(100000000 + Math.random() * 900000000);
    }

    return {
        ajaxSync: ajaxSync,
        ajaxCallback: ajaxCallback,
        ajaxPromise: ajaxPromise,
        test: test
    }

});
