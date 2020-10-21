/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';

    //默认工具条配置
    //config.toolbarGroups = [
    //    { name: 'document', groups: ['mode', 'document', 'doctools'] },
    //    { name: 'clipboard', groups: ['clipboard', 'undo'] },
    //    { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
    //    { name: 'forms', groups: ['forms'] },
    //    //'/',
    //    { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
    //    { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] },
    //    { name: 'links', groups: ['links'] },
    //    { name: 'insert', groups: ['insert'] },
    //    //'/',
    //    { name: 'css', groups: ['css'] },
    //    { name: 'colors', groups: ['colors'] },
    //    { name: 'tools', groups: ['tools'] },
    //    { name: 'others', groups: ['others'] },
    //    { name: 'about', groups: ['about'] }
    //];
    config.toolbar = [
        ['Source'], ['Save', 'NewPage', 'Preview', 'Print'], ['Templates'],
        ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord'], ['Undo', 'Redo'],
        ['Find', 'Replace'], ['SelectAll'], ['Scayt'],
        ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
        //'/',
        ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript'], ['CopyFormatting', 'RemoveFormat'],
        ['NumberedList', 'BulletedList'], ['Outdent', 'Indent'], ['Blockquote', 'CreateDiv'], ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'], ['BidiLtr', 'BidiRtl', 'Language'],
        ['Link', 'Unlink', 'Anchor'],
        ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe'],
        //'/',
        ['Styles', 'Format', 'Font', 'FontSize'], ['TextColor', 'BGColor'],
        ['Maximize', 'ShowBlocks']
    ];

    //移除按钮
    config.removeButtons = 'Save,NewPage,Templates,Scayt,Language,Smiley,Styles,Format,About';

    //配置插件
    //config.extraPlugins = "simplebox,codesnippet,uploadfile,diagram,diagramwidget";
    // config.extraPlugins = "codesnippet,uploadfile,diagram";
    // config.uploadfileUploadUrl = "../../pages/epoch-base-module/ckeditor-uploadfile.aspx";
    // config.diagramEditUrl = "../../pages/epoch-web-diagram/diagram-edit.aspx?ckeditor=true";

    //设置文件、图片、Flash上传处理地址
    //config.filebrowserBrowseUrl = "";     启用浏览服务器
    // config.filebrowserUploadUrl = "../../pages/epoch-base-module/ckeditor-upload.aspx?type=file";
    // config.filebrowserImageUploadUrl = "../../pages/epoch-base-module/ckeditor-upload.aspx?type=image";
    // config.filebrowserFlashUploadUrl = "../../pages/epoch-base-module/ckeditor-upload.aspx?type=flash";

    //设置编辑器语言，不设置时根据浏览器自动判断
    //config.language = 'zh-cn';

    //设置皮肤
    //config.skin = "moono-lisa";

    //设置字体，需将config.js另存为utf-8编码格式，否则字体名乱码
    config.font_names = '宋体/宋体, 新宋体;仿宋/仿宋_GB2312, 仿宋;黑体;楷体/楷体_GB2312, 楷体;隶书;微软雅黑;幼圆';

    //设置回车格式
    config.enterMode = CKEDITOR.ENTER_BR;

    //禁用ACF
    config.allowedContent = true;

    //插入/编辑超链接时target默认设置为_blank
    CKEDITOR.on('dialogDefinition', function (ev) {
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
        if (dialogName == 'link') {
            var targetTab = dialogDefinition.getContents('target');
            var targetField = targetTab.get('linkTargetType');
            targetField['default'] = '_blank';
        }
    });

    //设置默认字体及大小
    //CKEDITOR.on('instanceReady', function (ev) {
    //    ev.editor.setData('<span style="font-family:宋体;font-size:14px;">&shy;</span>');
    //});
    //config.font_defaultLabel = "宋体";
    //config.fontSize_defaultLabel = "14px";
};
