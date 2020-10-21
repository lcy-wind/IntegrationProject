// 下拉框模糊查询
define(['jquery', 'vue', 'Popper'], function ($, Vue, Popper) {
    Vue.directive('clickoutside', {
        bind: function (el, binding, vnode) {
            function documentHandler(e) {
                if (!el.contains(e.target) &&binding.expression) {
                    binding.value(e);
                }
            }
            el.__vueClickOutside__ = documentHandler;
            document.addEventListener('click', documentHandler);
        },
        undind: function (el, binding) {
            document.removeEventListener('click', el.__vueClickOutside__);
            delete el.__vueClickOutside__;
        }
    })
    return Vue.component('fuzzy-query-select', {
        props: {
            fuzzyList: {default: function () { return [] }},   // 所有下拉数据 数据格式[{key: '', value: ''}]
            defaultText: {default: '请选择'},   // 默认值
            queryKey: {default: ''},            // 查询结果key
            disabled: {default: false}          // 禁用
        },
        template: '<div class="fuzzy_query" v-clickoutside="hideSelect">\
                        <input type="text" class="form_control" v-model="fuzzyValue" aria-describedby="tooltip"\
                        @keyup="resetPosition" @focus="resetPosition" :placeholder="defaultText" :disabled="disabled">\
                        <!--<i class="iconfont icon-arrow-down"></i>-->\
                        <div v-show="queryShow"  role="tooltip" class="query_content_container"><ul class="query_content" >\
                            <li class="nowrap" v-show="queryList.length == 0">没有找到匹配项</li>\
                            <li class="nowrap" v-for="item in queryList" @click="selectItem(item)">{{item.value}}</li>\
                        </ul></div>\
                    </div>',
        data: function () {
            return {
                queryShow: false,
                fuzzyKey: this.queryKey,
                fuzzyValue: '',
                isWatch: true,
                popperInstance: null
            }
        },
        computed: {
            queryList: function () { // 模糊查询后的数据
                var that = this;
                var list = [];
                list = this.fuzzyList.filter(function (item) {
                    return item.value.indexOf(that.fuzzyValue) > -1;
                    // return (item.value + '').indexOf(that.fuzzyValue) > -1;
                });
                return list
            }
        },
        watch: {
            'queryKey':  {
                immediate: true,
                handler: function (val) {
                    var item = this.fuzzyList.filter(function (item) {
                        return item.key == val;
                    });
                    if (item.length > 0) {
                        this.fuzzyValue = item[0].value;
                    } else {
                        this.fuzzyValue = '';
                        this.fuzzyKey = '';
                        this.$emit("update:queryKey", '');
                    }
                }
            }
        },
        mounted: function () {
        },
        methods: {
            getValue: function () { // 父组件获取下拉框的value值
                return this.fuzzyValue;
            },
            selectItem: function (item) { // 点击选择事件
                this.queryShow = false;
                if (this.popperInstance) { // 销毁popper实例
                    this.popperInstance.destroy();
                    this.popperInstance = null;
                }
                this.fuzzyValue = item.value;
                this.fuzzyKey = item.key;
                this.$emit("update:queryKey", item.key);
                this.$emit("fuzzy-change", item.key, item.value);
            },
            hideSelect: function () { // 点击组件之外的地方, 隐藏组件
                var that = this;
                if (!this.queryShow) {
                    return
                }
                this.queryShow = false;
                if (this.popperInstance) { // 销毁popper实例
                    this.popperInstance.destroy();
                    this.popperInstance = null;
                }
                var list = this.fuzzyList.filter(function (item) {
                    return item.value == that.fuzzyValue;
                });
                if (list.length == 1) {// 输入的值完全匹配, 默认选中
                    this.$emit("update:queryKey", list[0].key);
                    this.$emit("fuzzy-change", list[0].key, list[0].value);
                } else { // 清空
                    this.fuzzyValue = '';
                    this.$emit("update:queryKey", '');
                    this.$emit("fuzzy-change", '', '');
                }
            },
            resetPosition: function (event) { // 固定位置
                var $this = $(event.target);
                this.queryShow = true;
                var inputSelect = $this[0];
                var selectContent = $this.siblings('.query_content_container')[0];
                this.popperInstance = null;
                this.popperInstance = new Popper(inputSelect, selectContent, { // 创建popper实例
                    placement: 'bottom-start',
                    modifiers: {
                        offset: {
                            offset: '0, 0'
                        },
                        preventOverflow: { // 溢出显示的优先顺序
                            priority: ['bottom'],
                            boundariesElement: 'window'
                        }
                    }
                })
            }
        }
    })
});