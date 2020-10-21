//require.js配置
require.config({
    baseUrl: '../../asset/js',
    paths: {
        //'domReady': ['../libs/require-domReady-2.0.1/domReady'],
        'jquery': ['../libs/jquery-1.12.4/jquery-1.12.4.min'],
        'jquery-ui': ['../libs/jquery-ui-1.11.4/jquery-ui.min'],
        'vue': ['../libs/vue-2.6.10/vue'],   //开发环境
        //'vue': ['../libs/vue-2.6.10/vue.min'],   //生产环境
        'layer': ['../libs/layer-v3.1.1/layer'],
        'My97DatePicker': ['../libs/My97DatePicker-4.8/WdatePicker'],
        'jquery-ztree': ['../libs/jquery.ztree-3.5.40/js/jquery.ztree.all'],
        'fuzzysearch': ['../libs/jquery.ztree-3.5.40/fuzzysearch'],
        'jquery-serializejson': ['../libs/jquery.serializeJSON-2.9.0/jquery.serializejson.min'],
        'webuploader': ['../libs/webuploader-0.1.5/js/webuploader.min'],
        'CKEditor': ['../libs/ckeditor-4.11.4/ckeditor'],
        'jquery-qtip': ['../libs/jquery.qtip-3.0.3/jquery.qtip.min'],
        'jsencrypt': ['../libs/crypt/jsencrypt.min'],
        'echarts': ['../libs/echarts-4.2.1/echarts.min'],
        // ie兼容es6语法
        'polyfill': ['../libs/polyfill/polyfill'],
        // 定位引擎
        'Popper': ['../libs/popper-2.0.6/popper-1.16.1'],

    },
    shim: {
        'jquery-ui': {
            deps: ['jquery', 'css!../libs/jquery-ui-1.11.4/jquery-ui.min.css']
        },
        'layer': {
            deps: ['jquery', 'css!../libs/layer-v3.1.1/theme/default/layer.css']
            //, exports: 'layer'
        },
        'My97DatePicker': {
            exports: 'WdatePicker'
        },
        'jquery-ztree': {
            deps: ['jquery'
                //, 'css!../libs/jquery.ztree-3.5.40/css/awesomeStyle/awesome.css'
                //, 'css!../libs/jquery.ztree-3.5.40/css/metroStyle/metroStyle.css'
                , 'css!../libs/jquery.ztree-3.5.40/css/zTreeStyle/zTreeStyle.css'
            ]
        },
        'jquery-serializejson': {
            deps: ['jquery']
        },
        'webuploader': {
            deps: ['jquery', 'css!../libs/webuploader-0.1.5/css/webuploader.css']
        },
        'jquery-qtip': {
            deps: ['jquery', 'css!../libs/jquery.qtip-3.0.3/jquery.qtip.min.css']
        },
        'peac-user-tree': {
            deps: ['css!../../page/process-engine/component-peac.css']
        },
        'peac-users-tree': {
            deps: ['css!../../page/process-engine/component-peac.css']
        },
        'Popper': {
            deps: ['polyfill']
        }
    },
    map: {
        '*': {
            'css': '../libs/require-css-0.1.10/css.min'
        }
    },
    waitSeconds: 0  //修复Load timeout for modules错误
});

//定义公共模块，应用程序中引用后会自动引用对应依赖模块
define("global",
    [
        'css!../css/font_1520246_bci51xc43ts/iconfont',
        'jquery',
        'jquery-ui',
        'vue',
        'layer',
        'My97DatePicker',
        'jquery-ztree',
        'jquery-serializejson',
        'webuploader',
        'CKEditor',
        'jquery-qtip',
        //以上模块根据paths及shim配置加载
        //以下模块根据baseUrl配置自动加载
        'common',                       //公用脚本
        'ajax-global',                  //ajax全局配置
        'new-id',                       //id生成工具
        'utils',                        //工具类
        'component-ckeditor',           //CKEditor富文本编辑器
        'component-uploader',           //上传组件
        'component-files',              //文件查看组件
        'component-files-detail',       //文件查看组件-a标签
        'component-datepicker',         //日期组件
        'component-datetimepicker',     //日期时间组件
        'component-pagination',         //分页组件
        'component-popup-input',        //通用PopupInput组件
        'component-input-number',       //数字选择组件
        'component-connection-box',     //多级关联选择框组件
        'component-popup-uploader',     //弹窗上传组件
        'component-uploader-files',     //弹窗上传组件-表格模式
        'directive-only-num',           //限制输入数字
        'directive-ellipsis',           //显示更多文字
        'directive-hide-more',          //展开显示更多
        'directive-hide-textarea',      //展开显示更多textarea输入框
        'directive-unite-paste',

        'to-uppercase',                        //金额转大写
        'starred-review',                      //星级评分
        'component-checkbox-list',      //Checkbox多选组件
        'component-checkbox-select',      //CheckboxSelect组件
        'component-select-tree',      //下拉树组件
        'component-tree-radio',
        'component-fuzzy-query-select',  //模糊查询

        'file-store-config',  //定义文件存储配置引用

    ]);
