//弹出选择单一来源场景
define(['jquery', 'vue', 'component-popup-input'], function ($, Vue) {
    return Vue.component('select-single-source-type', {
        props: {
            value: {default: undefined},
            text: {default: ''},
            popupTitle: {default: '&nbsp;'}
        },
        data: function () {
            return {
                innerText: this.text,
                innerValue: this.value, //选中id
                dataList: [
                    {
                        value: 1,
                        text: '除原供应商外，其他供应商不具备提供相关设备扩容、升级能力及各类服务的，或使用其他供应商影响功能服务配套的采购',
                        require: '采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明），' +
                            '如有可研文件对于唯一性情况的技术论证、项目建议书对于唯一性情况的说明、设计文件对唯一性情况的相关表述或出具的专项证明（需设计单位盖章）需提供'
                    },
                    {
                        value: 2,
                        text: '满足配套使用要求的备品备件、配件辅件等的采购',
                        require: '采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要' +
                            '（对唯一性情况的说明）'
                    },
                    {
                        value: 3, text: '在用系统后续应用开发、在用软件升级（为连续性开发或升级需求，供应商不可替代）的采购',
                        require: '采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明），如有可研文件对于唯一性情况的技术论证、' +
                            '项目建议书对于唯一性情况的说明、设计文件对唯一性情况的相关表述或出具的专项证明（需设计单位盖章）需提供'
                    },
                    {
                        value: 4, text: '基于技术方案或市场垄断，只能从唯一供应商或代理商采购的软硬件购置',
                        require: '采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明），如有可研文件对于唯一性情况的技术论证、' +
                            '项目建议书对于唯一性情况的说明、设计文件对唯一性情况的相关表述或出具的专项证明（需设计单位盖章）需提供'
                    },
                    {
                        value: 5, text: '唯一性媒体、赞助、会展服务合作',
                        require: '供应商的资质文件（与唯一性情况相关）、采购需求文件(对唯一性情况的说明）、' +
                            '经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明'
                    },
                    {
                        value: 6, text: '集团客户项目中需从唯一供应商处采购的工程、货物、服务等（包括客户指定、业务置换等）',
                        require: '客户方（法人）出具的指定供应商说明函（需加盖客户方法人章或法定代表人授权的签字）、采购需求文件(对唯一性情况的说明）、' +
                            '经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明）'
                    },
                    {
                        value: 7,
                        text: '政府、行政部门指定供应商的采购或虽未指定供应商，但是指定的产品（品牌、型号、性能指标等）、特定服务等经论证仅唯一供应商可提供的采购',
                        require: '政府行政部门或公共事业单位出具的正式文件或其他证明文件（需盖章）、采购需求文件(对唯一性情况的说明）、' +
                            '经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明）'
                    },
                    {
                        value: 8,
                        text: '涉及国家安全、国家秘密，由于保密要求仅唯一供应商可提供的采购',
                        require: '采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明）'
                    },
                    {
                        value: 9,
                        text: '需要采用不可替代的知识产权、专有技术的采购',
                        require: '知识产权相关证书材料、采购需求文件(对唯一性情况的说明）、经公司分管领导审批的立项签报或公司分管领导主持的会议纪要（对唯一性情况的说明）'
                    },
                    {
                        value: 10,
                        text: '发生抢险救灾不可预见的紧急情况，不能从其他供应商处采购的',
                        require: '采购部门对于抢险救灾不可预见的紧急情况采购的详细说明或采购需求文件(对唯一性情况的说明）;'
                    },
                    {
                        value: 12,
                        text: '特殊场景（须谨慎使用“特殊场景”，使用时应充分考虑采用单一来源采购方式理由的合法合规性，避免过度使用造成的风险）',
                        require: '采购方案中描述采用单一来源采购方式的理由和供应商，并按照如下要求进行审批：1．采购方案的预估采购金额达到“三重一大”标准的采购项目：按照“三重一大”相关规定对采购方案进行决策。' +
                            '2．采购方案的预估采购金额未达到“三重一大”标准的采购项目：采购方案由公司总经理签批，或由总经理主持的办公会议决策。'
                    }
                ]//待选项
            }
        },
        template: '<popup-input popup-url="/pages/popup/select-single-source-type.html" :text="innerText" :value.sync="innerValue"\
            :popup-title="popupTitle" @popup-change="popupChange"></popup-input>',
        created: function () {
            this.getText();
        },
        watch: {
            value: function (val) {
                var that = this;
                if (val) {
                    for (var i = 0; i < that.dataList.length; i++) {
                        var item = that.dataList[i];
                        if (item.value == that.value) {
                            that.innerText = item.text;
                        }
                    }
                } else
                    that.innerText = '';
            }
        },
        methods: {
            popupChange: function (users, text) {
                this.$emit('update:value', this.innerValue);
                this.$emit('popup-change', this.innerValue, this.innerText);
            },
            getText: function () {
                var that = this;
                if (that.value) {
                    for (var i = 0; i < that.dataList.length; i++) {
                        var item = that.dataList[i];
                        if (item.value == that.value) {
                            that.innerText = item.text;
                        }
                    }
                } else
                    that.innerText = '';
            }
        }
    });
});
