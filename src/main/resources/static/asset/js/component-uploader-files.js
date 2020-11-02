//弹窗上传文件
define(['jquery', 'vue', 'layer'], function ($, Vue, layer) {
    return Vue.component('uploader-files', {
        props: {
            //file-ids 文件id数组，v-model 实现双向绑定
            value: {
                default: function () {
                    return [];
                }
            },
            //上传按钮文字
            text: {default: '上传'},
            //最大上传数量
            maxCount: {default: undefined},
            //最大上传单文件大小
            maxFileSize: {default: undefined},
            //允许上传的文件格式
            accept: {default: undefined}
        },
        data: function () {
            return {
                id: Math.random().toString(36).substr(2),              //控件id
                filesId: this.value,
                innerFiles: [],             //文件内部变量
                canQueryFile: true,         //请求文件
                //按钮样式
                buttonClass: 'btn btn_upload'
            }
        },
        template: '<span class="display_inherit">\
                         <table class="table_list table_striped table_hover text_center">\
                            <thead>\
                            <tr>\
                                <th>文件名</th>\
                                <th>上传者</th>\
                                <th>大小</th>\
                                <th>上传时间</th>\
                                <th class="text_center">\
                                    <popup-uploader :button-mode="true" :button-class="\'clickable \'+buttonClass"\
                                                    :text="text" :max-count="maxCount" :max-file-size="maxFileSize" \
                                                    :accept="accept" ref="files" @after-upload="uploaded"></popup-uploader>\
                                </th>\
                            </tr>\
                            </thead>\
                            <tbody>\
                            <tr v-if="innerFiles && innerFiles.length == 0">\
                                <td colspan="5">暂无数据</td>\
                            </tr>\
                            <tr v-for="(item, index) in innerFiles">\
                                <td>{{item.fullname}}</td>\
                                <td>{{item.applyUsername}}</td>\
                                <td>{{autoUnitSize(item.contentLength)}}</td>\
                                <td>{{item.saveTime}}</td>\
                                <td><i title="删除" class="iconfont tooltip icon-delete" type="danger" @click="deleteFile(index)"></i></td>\
                            </tr>\
                            </tbody>\
                        </table>\
                    </span>',
        created: function () {
        },
        mounted: function () {

        },
        watch: {
            value: {
                handler: function (val, oldVal) {
                    //如果指定了filesId，根据filesId获取文件
                    if (val != null && val.length > 0 && val != oldVal) {
                        if (this.canQueryFile)
                            this.setFiles(val);
                    }
                }, immediate: true
            }
        },
        computed: {},
        methods: {
            //上传组件完成回调事件
            uploaded: function (item) {
                this.$emit('after-upload', item);
                this.innerFiles.push({
                    id: item.id,
                    name: item.fullname,
                    size: item.contentLength,
                    applyUsername: item.applyUsername,
                    uploadTime: item.uploadTime
                });
                this.$refs.files.deleteFile();
                this.$refs.files.closeForm();
                this.getFilesId();
            },
            setFiles: function (filesId) {
                var that = this;
                if (filesId.length > 0) {
                    $.ajax({
                        async: false,
                        type: 'GET',
                        url: fileStoreConfig.serverUrl + '/api/file/query-by-ids',
                        data: {ids: filesId},
                        success: function (data) {
                            that.innerFiles = data.data;
                            /*that.innerFiles = $.map(data.data, function (item, index) {
                                return {
                                    name: item.fullname,
                                    size: item.contentLength,
                                    type: item.contentType,
                                    id: item.id
                                }
                            })*/
                        }
                    });
                }
            },
            deleteFile: function (index) {
                this.innerFiles.splice(index, 1);
                this.getFilesId();
            },
            getFiles: function () {
                return this.innerFiles;
            },
            getFilesId: function () {
                var ids = $.map(this.innerFiles, function (value, index) {
                    return value.id;
                })
                this.canQueryFile = false;
                this.$emit('input', ids);
                return ids;
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
            }
        }
    });
});
