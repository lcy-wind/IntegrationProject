//Ajax全局配置
require(['jquery', 'layer'], function ($, layer) {

    $.ajaxSetup({
        dataType: 'JSON',
        cache: false,       //禁止IE9缓存
        dataFilter: function (data, type) {
            //console.log(data);
            //console.log(type);

            //判断Ajax请求返回值
            var result = JSON.parse(data);
            var resultVal = result.success?result.success:result.isSuccess;
            if (!resultVal) {
                failureMessage(result.message);
                top.layer.closeAll('loading');
                if (this.error) {
                    this.error();
                }
                throw new Error('Ajax请求返回值失败。');
            }

            return data;
        }
    });

    //全局Ajax错误处理
    // 设置全局的sessionStorage 记录过期登录弹框的状态 false: 不存在, true: 存在
    if (!sessionStorage.getItem('expiredLoginFlag')) {
        sessionStorage.setItem('expiredLoginFlag', 'false');
    }
    $(document).ajaxError(function (event, xhr, settings, error) {
        //console.log(JSON.stringify(event));
        //console.log(JSON.stringify(xhr));
        //console.log(JSON.stringify(settings));
        //console.log(JSON.stringify(error));
        top.layer.msg('请求失败，请重试。');
        top.layer.closeAll('loading');
        // 过期登录弹框
        var expiredLoginFlag = sessionStorage.getItem('expiredLoginFlag');
        if (xhr.status == '401' && expiredLoginFlag === 'false') {//expiredLoginFlag: 防止弹框多次弹出
            sessionStorage.setItem('expiredLoginFlag', 'true');
            top.layer.open({
                type: 2,
                title: '登录',
                content: '../../page/auth/expired-login.html',
                area: ['500px', '540px'],
                shade: [0.3, '#393D49'],
                shadeClose: true,
                end: function () { // 销毁弹框
                    sessionStorage.setItem('expiredLoginFlag', 'false');
                }
            })
        }
        throw new Error('Ajax请求时发生错误。' + settings.url);
    });

    //全局Ajax成功处理
    $(document).ajaxSuccess(function (event, xhr, options, data) {
        // console.log(JSON.stringify(event));
        // console.log(JSON.stringify(xhr));
        // console.log(JSON.stringify(options));
        // console.log(JSON.stringify(data));

        //如果返回值中包含错误，显示错误信息
        //if (!data.isSuccess) {
        //    failureMessage(data.message);
        //    throw new Error('Ajax请求返回值失败。' + options.url);
        //}
    });

    //失败信息处理方法
    function failureMessage(message) {
        top.layer.msg(message);
    }

    //第一个Ajax请求开始
    $(document).ajaxStart(function () {
        //console.log('ajaxStart');
        //top.layer.load(2, { shade: [0.3, '#3333333'] });
        top.layer.load(0);
    });

    //所有Ajax请求结束
    $(document).ajaxStop(function () {
        //console.log('ajaxStop');
        top.layer.closeAll('loading');
    });
});