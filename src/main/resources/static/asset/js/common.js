//通用脚本
require(['jquery', 'layer', 'jquery-qtip'], function ($, layer) {

    //解决IE8、IE9不支持console问题
    window.console = window.console || (function () {
        var c = {};
        c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
            = c.clear = c.exception = c.trace = c.assert = function () {
        };
        return c;
    })();

    //为a链接添加标签打开支持
    //$(function () {
    //判断是否在框架页中
    var w = self;
    if (self != top) w = top;
    $(document).on('click', 'a[tab]', function (e) {
        //先加载common.js，main.html页面的mainApp还未生成，故要在放在内部判断
        if (w.mainApp) {
            if (e.ctrlKey) {
                return;
            }
            var title = $(this).attr('tabTitle');
            if (title == undefined)
                title = $(this).text();
            var tabId = $(this).attr('tabId');
            var url = $(this).attr('href');
            w.mainApp.openTab(title, url, tabId);
            e.preventDefault();
        }
    });
    //});

    //checkbox-menu弹出层点击时阻止事件冒泡
    $(document).on('click', '.checkbox-menu', function (e) {
        e.stopPropagation();
    });

    //添加了fixed样式的page_title在页面滚动时固定在头部
    $(window).scroll(function () {
        //当页面中只有一个page_title时才触发此效果
        if ($('.page_title').length == 1) {
            var top = $('.page_title').offset().top;
            if ($(window).scrollTop() >= top) {
                $('.page_title.fixed').addClass('page_title_fixed');
            }
            if ($(window).scrollTop() == 0) {
                $('.page_title.fixed').removeClass('page_title_fixed');
            }
        }
    });

    //Javascript方式无法为Vue组件内添加tooltip图标，改用css方式添加
    //$('.tooltip_icon').append(' <span class="glyphicon glyphicon-info-sign" style="color: #3498db;"></span>');
    //设置qtip的z-index在最上层，否则在layer弹出层中无法显示
    $.fn.qtip.zindex = 2147483647;
    //qtip提示
    $(document).on('mouseover', '[title]', function(event) {
        $(this).qtip({
            overwrite: false,
            show: {
                event: event.type,
                ready: true
            },
            position: {
                //viewport: $(window),
                my: 'bottom left',
                at: 'top left'
            },
            style: {
                classes: 'qtip-bootstrap'
            }
        }, event);
    });

    // 统一添加required * 样式
    /*$(document).find('.required').each(function () {
        $(this).append('<i>*</i>')
    });*/
    /*layer 拓展css样式*/
    layer.config({
        extend: 'myskin/style.css',
        shade: [0]
    });
});

