define(['vue'], function (Vue) {
    Vue.directive('clickoutside', {
        bind: function (el, binding, vnode) {
            function documentHandler(e) {
                if (el.contains(e.target)) {
                    return false;
                }
                if (binding.expression) {
                    binding.value(e);
                }
            }

            el.__vueClickOutside__ = documentHandler;
            document.addEventListener('click', documentHandler);
        },
        undind: function (el, binding) {
            document.removeEventListener('click', el__vueClickOutside__);
            delete el.__vueClickOutside__;
        }
    })
    return Vue.component('select-tree', {
        template: '<div style="position: relative;" v-clickoutside="hideTree">\
                    <div>\
                        <div class="select_container"  @click="showTree()">\
                            <span class="select_tree_tags">\
                                <span class="select_tree_tag" v-for="(item,index) in selectedItems" v-if="index<maxTagCount">\
                                    {{getName(item)}}\
                                    <i v-if="multi==true&&isdelete==true" class="icon_delete select_tree_remove" @click.stop="deleteItem(item)"></i>\
                                </span>\
                                <span class="select_tree_tag_num" v-if="showPlus!=null">{{showPlus}}</span>\
                            </span>\
                            <div class="input_group">\
                                <input type="text" class="form_control" :placeholder="placeholderText" readonly />\
                                <span class="input_group_addon"><i class="icon_zTree"></i></span>\
                            </div>\
                        </div>\
                    <div class="select_tree_content" v-show="show">\
                    <ul :id="id" class="ztree"></ul>\
                    </div>\
                    </div>\
                </div>',

        // <i v-if="multi==true" class="icon_delete select_tree_remove" @click.stop="deleteItem(item)"></i>
        props: {
            value: {
                default: function () {
                    return []
                }
            },
            multi: {default: true},
            placeholder: {default: '请选择'},
            dataUrl: {required: true},
            radioType: {default: 'all'},
            idKey: {default: "id"},
            pIdKey: {default: "pId"},
            rootPid: {default: null},
            nameKey: {default: "name"},
            maxTagCount: {default: 999},
            maxChooseNum: {default: -1},
            checkType: {default: 0},
            isdelete: {default: false}
        },
        data: function () {
            return {
                id: Math.random().toString(36).substr(2),
                zTree: null,
                showPlus: null,
                show: false,
                selectedItems: [],
                setting: {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: this.idKey,
                            pIdKey: this.pIdKey,
                            rootPId: this.rootPid
                        },
                        key: {
                            name: this.nameKey
                        }
                    },
                    check: {
                        nocheckInherit: false,
                        enable: true,
                        chkStyle: this.multi == true ? "checkbox" : "radio",
                        radioType: this.radioType,
                        chkboxType: this.checkboxSettingArr()

                    },
                    callback: {
                        onCheck: this.zTreeOnCheck,
                        beforeClick: this.zTreeBeforeClick
                    }
                },
                nodes: []
            }
        },
        mounted: function () {
            this.initTree();
        },
        methods: {
            checkboxSettingArr: function () {
                // 0：被勾选时,取消勾选时关联父
                // 1：被勾选时,取消勾选时关联子
                // 2：被勾选时,取消勾选时
                var arr = [{"Y": "p", "N": "p"}, {"Y": "s", "N": "s"}, {"Y": "ps", "N": "ps"}];
                return arr[this.checkType]
            },
            initTree: function () {
                var that = this;
                $.ajax({
                    url: that.dataUrl,
                    type: 'post',
                    success: function success(res) {
                        if (typeof res.data == 'string') {
                            that.nodes = JSON.parse(res.data);
                        } else {
                            that.nodes = res.data;
                        }
                        if (that.nodes != null && that.nodes.length > 0) {
                            that.zTree = $.fn.zTree.init($("#" + that.id), that.setting, that.nodes);
                            var treeNodes = that.zTree.getNodes();
                            var allNodes = that.zTree.transformToArray(treeNodes)
                            $.map(allNodes, function (item) {
                                if (that.multi == false) {
                                    if (that.value == (that.getId(item))) {
                                        that.zTree.expandNode(item.getParentNode(), true);
                                        that.zTree.checkNode(item, true, true);
                                    }
                                } else {
                                    if (that.value.indexOf(that.getId(item)) > -1) {
                                        that.zTree.expandNode(item.getParentNode(), true);
                                        that.zTree.checkNode(item, true, true);
                                    }
                                }
                            })
                            that.selectedItems = that.zTree.getCheckedNodes(true);
                            if (that.value.length <= that.maxTagCount) {
                                that.showPlus = null;
                            } else {
                                var count = that.value.length - that.maxTagCount;
                                that.showPlus = "+" + count;
                            }
                        }
                    }
                });
            },
            showTree: function () {
                this.show = !this.show;
            },
            hideTree: function () {
                this.show = false;
            },
            zTreeBeforeClick: function (treeId, treeNode) {
                this.zTree.checkNode(treeNode, !treeNode.checked, true, true);
                return false;
            },
            zTreeOnCheck: function (event, treeId, treeNode) {
                var that = this;
                var items = that.zTree.getCheckedNodes(true);
                var value = $.map(items, function (item) {
                    return that.getId(item)
                })
                if (that.multi)
                    that.$emit('input', value);
                else
                    that.$emit('input', value[0]);
            },
            deleteItem: function (node) {
                var that = this;
                that.zTree.checkNode(node, false, true);
                var Items = that.zTree.getCheckedNodes(true);
                var value = $.map(Items, function (item) {
                    return that.getId(item)
                })
                if (that.multi)
                    that.$emit('input', value);
                else
                    that.$emit('input', value[0]);
            },
            otherDeleteItem: function () {
                var that = this;
                if (that.selectedItems.length > that.maxChooseNum) {
                    that.deleteItem(that.selectedItems[0])
                }
            },
            getName: function (item) {
                var key = this.nameKey;
                return item[key];
            },
            getId: function (item) {
                var key = this.idKey;
                return item[key];
            }
        },
        watch: {
            value: function (val) {
                var that = this;
                //取到根节点
                var treeNodes = that.zTree.getNodes();
                //取到所有节点
                var allNodes = that.zTree.transformToArray(treeNodes);
                that.zTree.checkAllNodes(false)
                $.map(allNodes, function (item) {
                    if (that.multi == false) {
                        if (val == (that.getId(item))) {
                            that.zTree.checkNode(item, true, true);
                        }
                    } else {
                        if (val.indexOf(that.getId(item)) > -1) {
                            that.zTree.checkNode(item, true, true);
                        }
                    }
                })
                that.selectedItems = that.zTree.getCheckedNodes(true);

                if (val.length <= that.maxTagCount) {
                    that.showPlus = null;
                } else {
                    var count = val.length - that.maxTagCount;
                    that.showPlus = "+" + count;
                }
                if (that.maxChooseNum != -1) {
                    that.otherDeleteItem()
                }
            },
            dataUrl: function (val) {
                this.initTree();
            }
        },
        computed: {
            placeholderText: function () {
                return this.selectedItems.length > 0 ? "" : this.placeholder;
            }
        }
    });
});