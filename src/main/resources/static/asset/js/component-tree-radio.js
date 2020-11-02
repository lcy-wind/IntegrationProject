define(['vue'], function (Vue) {
    return Vue.component('select-tree-radio', {
        template: '<div style="position: relative;">\
                    <div>\
                        <div class="select_container" @click="showTree()">\
                            <span class="select_tree_tags">\
                                <span class="select_tree_tag" v-for="(item,index) in selectedItems" v-if="index<maxTagCount">\
                                    {{getName(item)}}\
                                </span>\
                            </span>\
                            <div class="input_group">\
                                <input type="text" class="form_control" :placeholder="placeholderText" readonly />\
                                <span class="input_group_addon"><i class="icon_zTree"></i></span>\
                            </div>\
                        </div>\
                    <div class="select_tree_content" @mouseleave="hideTree()" v-show="show">\
                    <ul :id="id" class="ztree"></ul>\
                    </div>\
                    </div>\
                </div>',
        props: {
            value: {
                default: function () {
                    return []
                }
            },
            placeholder: {default: '请选择'},
            dataUrl: {required: true},
            idKey: {default: "id"},
            pIdKey: {default: "pId"},
            rootPid: {default: null},
            nameKey: {default: "name"},
            maxTagCount: {default: 999},
        },
        data: function () {
            return {
                id: Math.random().toString(36).substr(2),
                zTree: null,
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
                        enable: true,
                        chkStyle: 'radio',
                        radioType: 'all'
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
            initTree: function () {
                var that = this;
                $.ajax({
                    url: that.url,
                    type: 'get',
                    success: function success(res) {
                        that.nodes = res.data;
                        if (that.nodes != null && that.nodes.length > 0) {
                            that.zTree = $.fn.zTree.init($("#" + that.id), that.setting, that.nodes);
                            var treeNodes = that.zTree.getNodes();
                            var allNodes = that.zTree.transformToArray(treeNodes);
                            $.map(allNodes, function (item) {
                                if (that.value.indexOf(that.getId(item)) > -1) {
                                    that.zTree.checkNode(item, true, true);
                                }
                            });
                            that.selectedItems = that.zTree.getCheckedNodes(true);
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
                });
                that.$emit('input', value);
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
                that.zTree.checkAllNodes(false);
                $.map(allNodes, function (item) {
                    if (val.indexOf(that.getId(item)) > -1) {
                        that.zTree.checkNode(item, true, true);
                    }
                });
                that.selectedItems = that.zTree.getCheckedNodes(true);
            }
        },
        computed: {
            url: function () {
                return this.dataUrl;
            },
            placeholderText: function () {
                return this.selectedItems.length > 0 ? "" : this.placeholder;
            }
        }
    });
});