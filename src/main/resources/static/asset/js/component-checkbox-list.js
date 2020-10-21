//CheckBox多选组件
define(['jquery', 'vue'], function ($, Vue) {
    return Vue.component('checkbox-list', {
        template: '<span>\
                    <template v-if="vertical">\
                        <div class="checkbox" v-if="innerListData != undefined" v-for="(item, index) in innerListData">\
                            <label>\
                                <input type="checkbox" :value="item[valueProp]" v-model="innerValue" @change="checkedChange" :disabled="disabled"\>{{item[textProp]}}\
                            </label>\
                        </div>\
                        <template v-if="innerObjectData != undefined">\
                            <div class="checkbox" v-for="(item, index) in Object.keys(innerObjectData)">\
                                <label>\
                                    <input type="checkbox" :value="item" v-model="innerValue" @change="checkedChange" :disabled="disabled"\>{{innerObjectData[item]}}\
                                </label>\
                            </div>\
                        </template>\
                        <div class="checkbox" v-if="checkAll">\
                           <label>\
                               <input type="checkbox" @change="toggleCheckAll" v-model="checkAllChecked" :disabled="disabled"> 全选\
                           </label>\
                       </div>\
                   </template>\
                   <template v-if="!vertical">\
                        <div class="checkbox" v-if="innerListData != undefined">\
                            <label class="checkbox-inline" v-for="(item, index) in innerListData">\
                                <input type="checkbox" :value="item[valueProp]" v-model="innerValue" @change="checkedChange" :disabled="disabled"\>{{item[textProp]}}\
                            </label>\
                            <label class="checkbox-inline" v-if="checkAll">\
                                <input type="checkbox" @change="toggleCheckAll" v-model="checkAllChecked" :disabled="disabled"> 全选\
                            </label>\
                        </div>\
                        <div class="checkbox" v-if="innerObjectData != undefined">\
                            <label class="checkbox-inline" v-for="(item, index) in Object.keys(innerObjectData)">\
                                <input type="checkbox" :value="item" v-model="innerValue" @change="checkedChange" :disabled="disabled">{{innerObjectData[item]}}\
                            </label>\
                            <label class="checkbox-inline" v-if="checkAll">\
                                <input type="checkbox" @change="toggleCheckAll" v-model="checkAllChecked" :disabled="disabled"> 全选\
                            </label>\
                        </div>\
                   </template>\
                   </span>',
        props: {
            listData: {default: undefined},
            listUrl: {default: undefined},
            textProp: {default: 'text'},
            valueProp: {default: 'value'},
            objectData: {default: undefined},
            objectUrl: {default: undefined},
            vertical: {default: false},
            checkAll: {default: false},
            value: {default: function () { return [] }},
            disabled: false
        },
        data: function () {
            return {
                innerListData: undefined,
                innerObjectData: undefined,
                innerValue: this.value,
                checkAllChecked: false
            }
        },
        watch: {
            listData: function (newValue, oldValue) {
                this.innerListData = newValue;
            },
            listUrl: function (newValue, oldValue) {
                var that = this;
                $.ajax({
                    url: newValue,
                    type: 'GET',
                    success: function (data) {
                        that.innerListData = data.data;
                    }
                });
            },
            objectData: function (newValue, oldValue) {
                this.innerObjectData = newValue;
            },
            objectUrl: function (newValue, oldValue) {
                var that = this;
                $.ajax({
                    url: newValue,
                    type: 'GET',
                    success: function (data) {
                        that.innerObjectData = data.data;
                    }
                });
            },
            value: function (newValue, oldValue) {
                this.innerValue = newValue;
            }
        },
        created: function () {
            var that = this;
            if (this.listData != undefined) {
                this.innerListData = this.listData;
                return;
            }
            if (this.listUrl != undefined) {
                $.ajax({
                    url: that.listUrl,
                    type: 'GET',
                    success: function (data) {
                        that.innerListData = data.data;
                    }
                });
                return;
            }
            if (this.objectData != undefined) {
                this.innerObjectData = this.objectData;
                return;
            }
            if (this.objectUrl != undefined) {
                $.ajax({
                    url: that.objectUrl,
                    type: 'GET',
                    success: function (data) {
                        that.innerObjectData = data.data;
                    }
                });

            }
        },
        methods: {
            checkedChange: function () {
                this.$emit('input', this.innerValue);
                this.$emit('checked-change', this.innerValue);
            },
            toggleCheckAll: function () {
                var that = this;
                if (this.checkAllChecked) {
                    if (this.innerListData != undefined) {
                        this.innerValue = this.innerListData.map(function (item) {
                            return item[that.valueProp];
                        });
                    }
                    if (this.innerObjectData != undefined) {
                        this.innerValue = Object.keys(this.innerObjectData);
                    }
                } else {
                    this.innerValue = [];
                }
                this.checkedChange();
            },
            //取选中项值
            getCheckedValue: function () {
                return this.innerValue;
            },
            //取选中项文本
            getCheckedText: function () {
                if (this.innerListData != undefined) {
                    var t = [];
                    for (var i = 0; i < this.innerListData.length; i++) {
                        if ($.inArray(this.innerListData[i][this.valueProp], this.value) != -1)
                            t.push(this.innerListData[i][this.textProp]);
                    }
                }
                else if (this.innerObjectData != undefined) {
                    for (var i = 0; i < Object.keys(this.innerObjectData).length; i++) {
                        if ($.inArray(Object.keys(this.innerObjectData)[i], this.value) != -1)
                            t.push(this.innerObjectData[Object.keys(this.innerObjectData)[i]]);
                    }
                }
                return t;
            }
        }
    });
});