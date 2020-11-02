/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';

    //默认工具条配置
    config.toolbar = [
        ['Bold', 'Italic', 'Underline', 'Strike', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', 'Table'],
        ['Format', 'Font', 'FontSize', 'TextColor', 'BGColor', 'Maximize']
    ];

    //设置字体，需将config.js另存为utf-8编码格式，否则字体名乱码
    config.font_names = '宋体/宋体, 新宋体;仿宋/仿宋_GB2312, 仿宋;黑体;楷体/楷体_GB2312, 楷体;隶书;微软雅黑;幼圆';
    //设置回车格式
    config.enterMode = CKEDITOR.ENTER_BR;
    //禁用ACF
    config.allowedContent = true;
};
