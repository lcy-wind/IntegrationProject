define(['vue', 'jquery'], function (Vue) {
    var trigger = function (el, type) {
        var e = document.createEvent('HTMLEvents');
        e.initEvent(type, true, true);
        $(el)[0].dispatchEvent(e);
    };

    return Vue.directive('onlyNum',{
        bind: function (el, binding, vnode) {
            var ele = el.tagName === 'INPUT' ? el : el.querySelector('input');
            ele.oninput = function() {

                var val = ele.value;
                var oldValue = ele.value;
                val = val.replace(/[^\d.]/g, ''); // 清除“数字”和“.”以外的字符
                val = val.replace(/\.{2,}/g, '.');// 只保留第一个. 清除多余的
                val = val.replace('.', '$#$');
                val = val.replace(/\./g, '');
                val = val.replace('$#$', '.');


                var num = binding.value==undefined?0:binding.value;
                if(num==3){
                    val = val.replace(/^(\-)*(\d+)\.(\d\d\d).*$/, '$1$2.$3'); //只能输入三位小数
                }else if(num==4){
                    val = val.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/, '$1$2.$3'); //只能输入四位小数
                }else{
                    val = val.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两位小数
                }

                if (val.indexOf('.') < 0 && val != '') {
                    // 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                    val = parseFloat(val);
                }

                ele.value = val;
                if (oldValue != val) {
                    trigger(ele,'input')
                }
            }
        }
    })
});