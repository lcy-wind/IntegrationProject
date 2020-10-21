define(['vue', 'jquery'], function (Vue) {
// 注册一个全局自定义指令 `v-ellipsis`
    return Vue.directive('hideMore', {
        // 当被绑定的元素插入到 DOM 中时……
        inserted: function (el, binding, vNode) {
            var isshow = binding.value==undefined?true:binding.value;

            var menuDown = '展开<span class="glyphicon glyphicon-menu-down"></span>';

            var menuUp = '收起<span class="glyphicon glyphicon-menu-up"></span>';

            function htmlFtn(ahtml,istoggle) {
                return '<p data-toggle="'+istoggle+'" class="text-center hide_more" style="font-weight: bold;color: rgb(66, 169, 250);cursor: pointer; line-height: 34px; margin-bottom: 0px;">'+ahtml+'</p>';
            }
            function gethideMoreHtmlFtn(num){
                $(el).append(htmlFtn(menuDown,0));
                for(var i=num;i<$(el).find(".row").length;i++){
                    $(el).find(".row").eq(i).hide()
                }
            }
            function isShowFtn(isshow) {
                for(var i=typeof isshow == "number"?isshow:3;i<$(el).find(".row").length;i++){
                    isshow?$(el).find(".row").eq(i).slideDown():$(el).find(".row").eq(i).slideUp()
                }
            }

            if(typeof isshow == "boolean"){
                if(isshow){
                    gethideMoreHtmlFtn(3);
                }else{
                    $(el).append(htmlFtn(menuUp,1))
                }
            }else if(typeof isshow == "number"){
                gethideMoreHtmlFtn(isshow);
            }


            $(el).find('.hide_more').click(function () {
                if($(this).attr('data-toggle')==0){
                    $(this).attr('data-toggle','1').html(menuUp);
                    isShowFtn(true);

                }else{
                    $(this).attr('data-toggle','0').html(menuDown);
                    isShowFtn(false);
                }
            });

            // $(el).find('p').each(function(){
            //     if($(this).text()==''){
            //         $(this).html('-')
            //     }
            // })
        }
    })
});
