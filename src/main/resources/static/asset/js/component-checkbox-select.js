//CheckBox Select组件
define(['jquery', 'vue'], function ($, Vue) {
    return Vue.component('checkbox-select', {
        template: '<div class="dropdown">\
            <input type="text" class="form-control checkbox-select-text" data-toggle="dropdown"\
                :value="selectedText.join(textSeparator)" :placeholder="placeholder" readonly :disabled="disabled">\
            <ul class="dropdown-menu checkbox-menu">\
                <li v-for="(item, index) in innerListData">\
                    <label>\
                        <input type="checkbox" :value="item.value" v-model="innerValue" @change="selectedChange">\
                        {{item.text}}\
                    </label>\
                </li>\
            </ul>\
        </div>',
        props: {
            listData: {default: undefined},
            listUrl: {default: undefined},
            textProp: {default: 'text'},
            valueProp: {default: 'value'},
            objectData: {default: undefined},
            objectUrl: {default: undefined},
            value: {default: function () { return [] }},
            textSeparator: {default: '，'},
            disabled: {default: false},
            placeholder: {default: ''}
        }
        ,
        data: function () {
            return {
                innerListData: [],
                innerValue: this.value
            }
        },
        computed: {
            selectedText: function () {
                var t = [];
                for (var i = 0; i < this.innerListData.length; i++) {
                    if ($.inArray(this.innerListData[i].value, this.innerValue) != -1)
                        t.push(this.innerListData[i].text);
                }
                return t;
            }
        },
        watch: {
            listData: function (newValue, oldValue) {
                this.convertListData(newValue);
            },
            listUrl: function (newValue, oldValue) {
                this.convertListUrl(newValue);
            },
            objectData: function (newValue, oldValue) {
                this.convertObjectData(newValue);
            },
            objectUrl: function (newValue, oldValue) {
                this.convertObjectUrl(newValue);
            },
            value: function (newValue, oldValue) {
                this.innerValue = newValue;
            }
        },
        created: function () {
            if (this.listData != undefined) {
                this.convertListData(this.listData);
                return;
            }
            if (this.listUrl != undefined) {
                this.convertListUrl(this.listUrl);
                return;
            }
            if (this.objectData != undefined) {
                this.convertObjectData(this.objectData);
                return;
            }
            if (this.objectUrl != undefined) {
                this.convertObjectUrl(this.objectUrl);

            }
        },
        methods: {
            convertListData: function (data) {
                var that = this;
                this.innerListData = $.map(data, function (item, index) {
                    return {'text': item[that.textProp], 'value': item[that.valueProp]}
                });
            },
            convertListUrl: function (url) {
                var that = this;
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (data) {
                        that.convertListData(data.data);
                    }
                });

            },
            convertObjectData: function (data) {
                this.innerListData = $.map(data, function (value, key) {
                    return {'text': value, 'value': key}
                });
            },
            convertObjectUrl: function (url) {
                var that = this;
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (data) {
                        that.convertObjectData(data.data);
                    }
                });
            },
            selectedChange: function () {
                this.$emit('input', this.innerValue);
                this.$emit('selected-change', this.innerValue);
            },
            //取选中项值
            getSelectedValue: function () {
                return this.innerValue;
            },
            //取选中项文本
            getSelectedText: function () {
                return this.selectedText;
            }
        }
    });
});