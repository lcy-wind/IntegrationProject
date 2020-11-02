define(['vue', 'jquery'], function (Vue) {
// 注册一个全局自定义指令 `v-ellipsis`
    return Vue.directive('ellipsis', {
        // 当被绑定的元素插入到 DOM 中时……
        inserted: function (el, binding, vNode) {
            var ellipsisWidth = binding.value==undefined?0:binding.value;
            if(ellipsisWidth!=0){
                $(el).css('width',ellipsisWidth).addClass("ellipsis").attr('title',$(el).text())
            }else{
                $(el).addClass("ellipsis").attr('title',$(el).text())
            }
        }
    })
});
