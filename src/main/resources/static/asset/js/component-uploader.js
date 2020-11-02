//分页控件
define(['jquery', 'vue', 'webuploader', 'layer'], function ($, Vue, WebUploader, layer) {
    return Vue.component('uploader', {
        props: {
            //file-ids 文件id数组，v-model 实现双向绑定
            value: {
                default: function () {
                    return [];
                }
            },
            //上传按钮文字
            text: {default: '选择文件'},
            //最大上传数量
            maxCount: {default: undefined},
            //允许上传的文件格式
            accept: {default: undefined},
        },
        data: function () {
            return {
                id: undefined,              //控件id
                uploader: undefined,        //WebUploader实例
                filesId: this.value,
                innerFiles: [],             //文件内部变量
            }
        },
        template: '<div class="webuploader_container">\
                <div :id="id" class="picker">{{text}}</div>\
                <div v-for="(file, index) in innerFiles" :id="file.id" class="file_container">\
                    <button type="button" class="btn btn_normal" @click="deleteFile(file, index)">删除</button>\
                    {{file.name}} ({{autoUnitSize(file.size)}})\
                    <div class="upload_progress"><div class="upload_progress_bar"></div></div>\
                </div>\
            </div>',
        created: function () {
            //生成guid，此id用于创建WebUploader实例时关联pick，以保证页面上有多个组件时互相隔离
            this.id = WebUploader.Base.guid("");
        },
        mounted: function () {
            //根据filesId获取文件
            if (this.filesId.length > 0) {
                this.setFiles(this.value);
            }

            this.uploader = WebUploader.create({
                swf: '../../asset/libs/webuploader-0.1.5/js/Uploader.swf',
                server: '/api/file/upload',
                method: 'POST',
                pick: '#' + this.id,
                resize: false,                  //不压缩image, 默认如果是jpeg，文件上传前会进行压缩
                auto: true,                     //选择文件后自动上传
                duplicate: true,                //允许选择重复文件
                fileNumLimit: this.maxCount,    //允许最多文件个数
                accept: this.accept             //限制文件类型
            });
            var that = this;

            this.uploader.on('fileQueued', function (file) {
                that.innerFiles.splice(0, 0, file);  //将上传文件加入集合
                that.getFilesId();
                that.$nextTick(function () {
                    $('#' + file.id + " .upload_progress").css('display', 'inline-block');
                });
            });

            this.uploader.on('uploadProgress', function (file, percentage) {
                //console.log(percentage);
                $('#' + file.id + " .upload_progress_bar").css('width', percentage * 100 + '%');
            });

            this.uploader.on('uploadSuccess', function (file, response) {
                if (!response.isSuccess) {
                    alert(response.message);
                    $('#' + file.id + ' .upload_progress').hide();  //隐藏进度条
                    var index = that.innerFiles.indexOf(file);
                    that.innerFiles.splice(index, 1);               //上从集合中删除文件
                } else {
                    $('#' + file.id + ' .upload_progress').hide();
                    file.id = response.data.id;                     //上传成功时更新文件id
                }
                that.getFilesId();
            });

            this.uploader.on("error", function (type) {
                if (type == "Q_TYPE_DENIED") {
                    layer.msg("只允许上传“" + that.accept.extensions + "”格式文件。");//验证文件格式
                }
                /*else if (type == "Q_EXCEED_SIZE_LIMIT") {
                     layer.msg("文件大小不能超过2M。");                              //验证文件大小
                }*/
                else if (type == "Q_EXCEED_NUM_LIMIT") {
                    layer.msg("最多只允许上传" + that.maxCount + "个文件。");//验证文件数量
                } else {
                    layer.msg("上传文件时发生错误：" + type);
                }
            });

            this.uploader.on('uploadError', function (file, reason) {
                alert('上传文件时发生错误：' + reason);
                $('#' + file.id + ' .upload_progress').hide();  //隐藏进度条
                var index = that.innerFiles.indexOf(file);
                that.innerFiles.splice(index, 1);               //从集合中删除文件
                that.getFilesId();
            });
        },
        computed: {},
        methods: {
            setFiles: function (filesId) {
                var that = this;
                if (filesId.length > 0) {
                    $.ajax({
                        async: false,
                        type: 'POST',
                        url: fileStoreConfig.serverUrl + '/api/query-by-ids',
                        // url: '/api/file/query-by-ids',
                        data: JSON.stringify(filesId),
                        contentType: 'application/json',
                        success: function (data) {
                            if (!data.isSuccess) return;
                            that.innerFiles = data.data;
                        }
                    });
                }
            },
            deleteFile: function (file, index) {
                this.innerFiles.splice(index, 1);
                this.getFilesId();
                //移除组件中文件，否则计算已上传文件数量时有误
                if (this.uploader.getFiles().indexOf(file) != -1)
                    this.uploader.removeFile(file, true);   //移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为true则会从queue中移除
            },
            getFiles: function () {
                return this.innerFiles;
            },
            getFilesId: function () {
                var ids = $.map(this.innerFiles, function (value, index) {
                    return value.id;
                });
                this.$emit('input', ids);
                return ids;
            },
            //根据文件大小自动选择单位显示
            autoUnitSize: function (size) {
                return WebUploader.Base.formatSize(size);
            }
        }
    });
});
