//日期选择控件
define(['jquery','vue', 'My97DatePicker'], function ($, Vue) {

    //console.log(WdatePicker);
    Vue.prototype.WdatePicker = WdatePicker;    //必须为Vue注册WdatePicker

    return Vue.component('date-picker', {
        props: {
            placeholder: '',
            value: '',
            disabled: {default: false}
        },
        template: '<div class="input_group">\
                       <input type="text" class="form_control" :placeholder="placeholder"\
                       @click="WdatePicker({onpicked:changeData, oncleared:changeData})"\
                       @blur="changeData($event)" @keyup="changeData($event)"\
                       v-bind:value="value" v-on:input="$emit(\'input\', $event.target.value)" :disabled="disabled">\
                       <span class="input_group_addon" @click="calender($event)"><i class="iconfont icon-calendar"></i></span>\
                   </div>',
        methods: {
            changeData: function (e) {
                if (e.el) {
                    this.$emit('input', e.el.value);
                } else {
                    this.$emit('input', e.target.value);
                }
            },
            calender: function (event) {
                if (this.disabled) {
                    return
                }
                $(event.target).parents('.input_group').find('input').trigger('click');
            }
            /*,
            //改用Vue.prototype方式
            WdatePicker: function (M, d) {
                //Vue调试模式下无法弹出日期选择，需再包装一层
                WdatePicker(M, d);
            }*/
        }
    });
});