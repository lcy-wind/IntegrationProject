//上传文件显示组件
define(['vue'], function (Vue) {
    return Vue.component('files-detail', {
        props: {
            files: {
                default: function () {
                    return [];
                }
            },
            filesId: {
                default: function () {
                    return [];
                }
            },
        },
        template: ' <div class="pointer">\
                        <a v-if="innerFiles.length!=0" v-for="(item ,index) in innerFiles" @click="downloadFile(item)" :title="item.fullname">{{item.fullname}} ({{autoUnitSize(item.contentLength)}})<br></a>\
                    </div> ',
        data: function () {
            return {
                innerFiles: this.files,     //文件内部变量
                innerFilesId: this.filesId,     //文件内部变量
            }
        },
        created: function () {
        },
        mounted: function () {

        },
        watch: {
            innerFilesId: {
                handler: function (val, oldVal) {
                    //如果指定了filesId，根据filesId获取文件
                    if (val != null && val.length > 0 && val != oldVal) {
                        this.queryFile(val);
                    }
                }, immediate: true
            }
        },

        methods: {
            //模拟表单下载文件
            downloadFile: function (item) {
                var url = fileStoreConfig.serverUrl + "/api/download/" + item.id;
                var fileName = item.fullname;
                var form = $("<form></form>").attr("action", url).attr("method", "GET");
                form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
                form.appendTo('body').submit().remove();
            },
            //根据文件大小自动选择单位显示
            autoUnitSize: function (size) {
                if (size < 1024)
                    return size + ' B';

                if (size >= 1024 && size < 1024 * 1024)
                    return (size / 1024).toFixed(2) + ' KB';

                if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024)
                    return (size / (1024 * 1024)).toFixed(2) + ' MB';

                return (size / (1024 * 1024 * 1024)).toFixed(2) + ' GB';
                //1024 * 1024 * 1024 > int.MaxValue
            },
            queryFile: function (filesId) {
                var that = this;
                $.ajax({
                    type: 'POST',
                    url: fileStoreConfig.serverUrl + '/api/query-by-ids',
                    data: JSON.stringify(filesId),
                    contentType: 'application/json',
                    success: function (res) {
                        if ((typeof res != 'undefined') && (null != res) && (0 !== res.length))
                            that.innerFiles = res.data;
                    }
                });
            },
        }
    });
});
