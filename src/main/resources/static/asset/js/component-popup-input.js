//弹窗选择组件
define(['vue', 'layer'], function (Vue, layer) {
    return Vue.component('popup-input', {
        props: {
            rows: {default: 1},
            text: {default: ''},
            value: {default: undefined},
            popupUrl: {require: true},
            popupTitle: {default: '&nbsp;'},
            popupWidth: {default: '85%'},
            popupHeight: {default: '80%'},
            buttonMode: {default: false},
            buttonText: {default: '选择'},
            buttonClass: {default: 'btn_normal'},
            disabled: {default: false},
            beforePopup: {type: Function, default: undefined}, //弹出前事件
            afterPopup: {type: Function, default: undefined}, //关闭选择页面前事件
        },
        data: function () {
            return {
                innerText: this.text,
                innerValue: this.value
            }
        },
       template: '<span>\
                    <div class="input_group" v-if="!buttonMode">\
                       <input type="text" class="form_control popup_input" :value="innerText" @click="popup" v-if="rows == 1" readonly :disabled="disabled">\
                       <span class="input_group_addon" @click="popup" v-if="rows == 1"><i class="icon_search_grey"></i></span>\
                    </div>\
                    <button type="button" :class="\'btn \'+ buttonClass" @click="popup" v-if="buttonMode">{{buttonText}}</button>\
                  </span>',
        created: function () {
        },
        watch: {
            text: function (newValue, oldValue) {
                this.innerText = newValue;
            },
            value: function (newValue, oldValue) {
                this.innerValue = newValue;
            }
        },
        computed: {
            //获取在文本框中显示的值
            /*innerText: function () {
                if(this.innerValue) {
                    if (this.innerValue.text)
                        return this.innerValue.text;
                }
                return '';
            }*/
        },
        methods: {
            popup: function () {
                if (this.disabled)
                    return;

                if (this.beforePopup != undefined) {
                    if (!this.beforePopup())
                        return;
                }

                //打开窗口前注册方法
                window.getPopupInputText = this.getText;
                window.getPopupInputValue = this.getValue;

                var that = this;
                var options = {
                    type: 2,
                    area: undefined,
                    title: that.popupTitle,
                    btn: ['确定', '清除', '取消'],
                    content: that.popupUrl,
                    success: function (layero, index) {
                        //以下方法在打开窗口中第一次有可能获取不到，改为注册父窗口方法，可能是layer的success方法和vue加载顺序混乱引起
                        /*
                        //var body = layer.getChildFrame('body', index);
                        var iframeWindow = window[layero.find('iframe')[0]['name']];
                        //console.log(iframeWindow);

                        iframeWindow.popupInputText = that.innerText;
                        if (that.innerValue == undefined)
                            iframeWindow.popupInputValue = undefined;
                        else
                            iframeWindow.popupInputValue = JSON.parse(JSON.stringify(that.innerValue));  //深拷贝数据
                        */
                    },
                    //确定按钮
                    yes: function (index, layero) {
                        var iframeWindow = window[layero.find('iframe')[0]['name']];        //top
                        var value = iframeWindow.returnPopupInputValue();
                        //console.log(layero.find('iframe')[0]['name']);

                        //关闭弹窗前子页面事件
                        if(iframeWindow.beforePopupReturn)
                        {
                            if(!iframeWindow.beforePopupReturn())
                                return;
                        }

                        if (that.afterPopup != undefined) {
                            if (!that.afterPopup(value))
                                return;
                        }

                        //console.log(iframeWindow);
                        if (iframeWindow.returnPopupInputText)
                            that.innerText = iframeWindow.returnPopupInputText();

                        //var value = iframeWindow.returnPopupInputValue();
                        //console.log(iframeWindow.returnPopupInputValue);
                        //console.log(value);

                        that.$emit("update:text", that.innerText);
                        that.innerValue = value;
                        that.$emit("update:value", value);
                        //that.$emit('input', value);          //支持v-model

                        that.$emit('popup-change', value, that.innerText);
                        layer.close(index);     //top
                    },
                    //清除按钮
                    btn2: function (index, layero) {
                        that.innerText = '';
                        that.$emit("update:text", '');
                        that.innerValue = undefined;
                        that.$emit("update:value", undefined);
                        //that.$emit('input', undefined);            //支持v-model

                        that.$emit('popup-change', undefined, '');
                    }
                };

                if (that.popupWidth != '') {
                    if (that.popupHeight != '') {
                        options.area = [that.popupWidth, that.popupHeight];
                    } else {
                        options.area = that.popupWidth;
                    }
                }

                var layerIndex = layer.open(options);   //top
                //console.log(layerIndex);
            },
            getValue: function () {
                return this.innerValue;
            },
            getText: function () {
                return this.innerText;
            }
        }
    });
});
