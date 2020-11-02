define(['vue', 'jquery'], function (Vue) {
// 注册一个全局自定义指令 `v-add-data`
    var trigger = function (el, type) {
        var e = document.createEvent('HTMLEvents');
        e.initEvent(type, true, true);
        $(el)[0].dispatchEvent(e);
    };
    return Vue.directive('unitePaste', {
        // 当被绑定的元素插入到 DOM 中时……
        inserted: function (el, binding, vNode) {
            var customizeEvent = binding.arg;
            var className = binding.value.className;
            $(el).addClass(className);

            $(el).on(customizeEvent,function () {
                var tableRowIndex = el.parentElement.parentElement.rowIndex-2;
                var eventType = binding.value.eventType==undefined?0:binding.value.eventType;
                if(eventType==0){
                    var val = $(el).val();
                    var checkList = binding.value.returnftn();

                    if(checkList.indexOf(tableRowIndex)!=-1){
                        for(var i=0;i<checkList.length;i++){
                            var oldValue = $('.'+className).eq(checkList[i]).val();
                            $('.'+className).eq(checkList[i]).val($(el).val());

                            if (oldValue != val) {
                                trigger($('.'+className).eq(checkList[i]),'input');
                            }
                        }
                    }
                }
            })
        }
    })
});
