define(['vue', 'jquery'], function (Vue) {
// 注册一个全局自定义指令 `v-ellipsis`
    return Vue.directive('hideTextarea', {
        // 当被绑定的元素插入到 DOM 中时……
        inserted: function (el, binding, vNode) {
            $(el).css('height','34px').attr('rows','5');
            $(el).on('focus',function(){
                $(el).css('height','auto')
            });
            $(el).on('blur',function(){
                $(el).css('height','34px')
            })
        }
    })
});
