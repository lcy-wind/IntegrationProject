//ckeditor组件
define(['vue'], function (Vue) {
    return Vue.component('ckeditor', {
        template: '<div>\
                <textarea :id="id" type="text" class="form-control"></textarea>\
	    </div>',
        props: {
            value: {},
            simple: {default: true}
        },
        data: function () {
            return {
                id: Math.random().toString(36).substr(2),
                ckeditor: null
            }
        },
        mounted: function () {
            var config = '../../asset/libs/ckeditor-4.11.4/config.js';
            if (this.simple && this.simple != "false")
                config = '../../asset/libs/ckeditor-4.11.4/simple-config.js';
            var that = this;
            //暂时解决title显示问题
            window.CKEDITOR.config.title = false;
            this.ckeditor = window.CKEDITOR.replace(this.id, {customConfig: config});
            this.ckeditor.setData(this.value);
            // 监听内容变更事件
            this.ckeditor.on('change', function () {
                that.$emit('input', that.ckeditor.getData());
            })
        },
        watch: {
            value: function () {
                if (this.value !== this.ckeditor.getData()) {
                    this.ckeditor.setData(this.value);
                }
            }
        }
    });
});