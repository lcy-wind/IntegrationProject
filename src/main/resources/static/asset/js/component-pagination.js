//分页组件
define(['vue'], function (Vue) {
    return Vue.component('pagination', {
        props: {
            pageSizeOptions: {
                default: function () {
                    return [10, 20, 30, 50]
                }
            },                                     //每页显示数可选项
            totalCount: {required: true},            //所有记录数
            pageSize: {default: 10},                //每页显示记录数
            pageIndex: {default: 1},                //页码
            rangeCount: {default: 7},               //页码导航按钮数量
            showInfo: {default: true},              //是否显示分页信息
            sizeSelect: {default: true},            //是否显示每页显示数选择框
            manualInput: {default: true},            //是否显示页码输入框

        },
        data: function () {
            return {
                innerPageIndex: this.pageIndex,     //内部当前页码
                innerPageSize: this.pageSize,       //内部每页显示数
                inputPageIndex: ''                  //手工输入的页码
            }
        },
        template: '<div class="pagination_container">\
                        <div style="float: left;" v-if="showInfo">\
                             第<span> {{innerPageIndex}} / {{pageCount}} </span>页，\
                             每页\
                             <select v-if="sizeSelect" v-model="innerPageSize" @change="goto(innerPageIndex, innerPageSize)" class="form_control"\
                                style="padding: 0px; width: 44px; display: inline-block;">\
                                <option v-for="item in pageSizeOptions">{{item}}</option>\
                              </select>\
                              <span v-if="!sizeSelect"> {{innerPageSize}} </span>\
                              条，共<span> {{totalCount}} </span>条\
                        </div>\
                        <div style="float: right;">\
                            <button type="button" class="btn arrow" @click="goto(1, innerPageSize)" :disabled="!(innerPageIndex != 1)"><i class="iconfont icon-top-right"></i></button>\
                            <button type="button" class="btn arrow" @click="goto(innerPageIndex - 1, innerPageSize)" :disabled="!(innerPageIndex > 1)"><i class="iconfont icon-right"></i></button>\
                            <button type="button" class="btn" @click="goto(minRangeIndex + i - 1, innerPageSize)"\
                              :class="{ \'btn_primary\': innerPageIndex == minRangeIndex + i - 1 }"\
                              v-for="i in maxRangeIndex - minRangeIndex + 1">{{minRangeIndex + i - 1}}</button>\
                            <button type="button" class="btn arrow" @click="goto(innerPageIndex + 1, innerPageSize)" :disabled="!(innerPageIndex < pageCount)"><i class="iconfont icon-left"></i></button>\
                            <button type="button" class="btn arrow" @click="goto(pageCount, innerPageSize)" :disabled="!(innerPageIndex < pageCount)"><i class="iconfont icon-top-left"></i></button>\
                            <input type="text" class="form_control float_left" style="width: 48px; display: inline;margin: 0 7px;" v-model="inputPageIndex" v-if="manualInput">\
                            <button type="button" class="btn btn_primary" @click="gotoInput(inputPageIndex, innerPageSize)" v-if="manualInput">GO</button>\
                        </div>\
                        <div style="clear: both;"></div>\
                    </div>',
        created: function () {
            this.setPageSize();
        },
        watch: {
            pageIndex: function (newPageIndex, oldPageIndex) {
                this.innerPageIndex = newPageIndex;
            },
            pageSize: function (newPageSize, oldPageSize) {
                this.setPageSize();
                this.innerPageSize = newPageSize;
                if (this.innerPageIndex > this.pageCount)
                    this.innerPageIndex = this.pageCount;
            },
            totalCount: function (newTotalCount, oldTotalCount) {
                if (this.innerPageIndex > this.pageCount)
                    this.innerPageIndex = this.pageCount;
                if (this.innerPageIndex == 0)
                    this.innerPageIndex = 1;
            }
        },
        computed: {
            pageCount: function () {
                var pageCount = Math.ceil(this.totalCount / this.innerPageSize);
                if (isNaN(pageCount))
                    return 1;
                return pageCount;
            },
            minRangeIndex: function () {
                //按固定区间显示导航按钮
                /*var stub = Math.floor((this.innerPageIndex - 1) / 10) * 10;
                if (isNaN(stub))
                    return 1;
                return stub + 1;*/
                var pageCount = this.pageCount;
                if (pageCount <= this.rangeCount)
                    return 1;

                //按滑动区间显示导航按钮
                //var halfRangeCount = Math.floor(this.rangeCount / 2);        //页码偶数时中数在后
                var halfRangeCount = Math.ceil(this.rangeCount / 2) - 1;    //页码偶数时中数在前
                if (pageCount - this.innerPageIndex < halfRangeCount)
                    return pageCount - this.rangeCount + 1;

                if (this.innerPageIndex - halfRangeCount > 0)
                    return this.innerPageIndex - halfRangeCount;

                return 1;
            },
            maxRangeIndex: function () {
                //按固定区间显示导航按钮
                /*var stub = Math.floor((this.innerPageIndex - 1) / 10) * 10;
                if (isNaN(stub))
                    return 1;
                if (stub + 10 > this.pageCount)
                    return this.pageCount;
                else
                    return stub + 10;*/

                //按滑动区间显示导航按钮
                var pageCount = this.pageCount;
                if (pageCount <= this.rangeCount)
                    return pageCount;

                var minRangeIndex = this.minRangeIndex;
                if (minRangeIndex + this.rangeCount - 1 < pageCount)
                    return minRangeIndex + this.rangeCount - 1;

                return pageCount;
            }
        },
        methods: {
            setPageSize: function () {
                //判断指定的每页记录数是否在选项中，如不在加入到选项中
                if (this.pageSizeOptions.indexOf(this.pageSize) === -1) {
                    for (var i = 0; i < this.pageSizeOptions.length; i++) {
                        var item = this.pageSizeOptions[i];
                        if (this.pageSize === item)
                            break;
                        if (this.pageSize < item) {
                            this.pageSizeOptions.splice(i, 0, this.pageSize);
                            break;
                        }
                    }
                    if (this.pageSizeOptions.indexOf(this.pageSize) == -1)
                        this.pageSizeOptions.splice(this.pageSizeOptions.length, 0, this.pageSize);
                }
            },
            gotoInput: function (pageIndex, pageSize) {
                var index = parseInt(pageIndex);
                if (isNaN(index)) {
                    alert('请输入正确的页码。');
                    return;
                }
                this.goto(Math.round(index), pageSize);
            },
            goto: function (pageIndex, pageSize) {
                pageIndex = parseInt(pageIndex);
                pageSize = parseInt(pageSize);
                /*console.log("before page change:pageIndex=" + this.pageIndex + ",pageSize=" + this.pageSize +
                    "innerPageIndex=" + this.innerPageIndex + ",innerPageSize=" + this.innerPageSize +
                    "pageIndex=" + pageIndex + ",pageSize=" + pageSize);*/

                if (pageIndex < 1) pageIndex = 1;
                if (pageIndex > this.pageCount) pageIndex = this.pageCount;
                this.innerPageIndex = pageIndex;
                this.innerPageSize = pageSize;
                this.inputPageIndex = '';

                this.$emit('page-change', this.innerPageIndex, this.innerPageSize);
                /*console.log("after page change:pageIndex=" + this.pageIndex + ",pageSize=" + this.pageSize +
                    "innerPageIndex=" + this.innerPageIndex + ",innerPageSize=" + this.innerPageSize +
                    "pageIndex=" + pageIndex + ",pageSize=" + pageSize);*/
                /*console.log("range:" + this.minRangeIndex + "-" + this.maxRangeIndex);*/
            },
            getPageIndex: function () {
                return this.innerPageIndex;
            },
            getPageSize: function () {
                return this.innerPageSize;
            }
        }
    });
});
