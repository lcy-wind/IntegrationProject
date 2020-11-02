//弹窗上传文件
define(['jquery', 'vue', 'webuploader', 'layer'], function ($, Vue, WebUploader, layer) {
    return Vue.component('popup-uploader', {
        props: {
            //file-ids 文件id数组，v-model 实现双向绑定
            value: {
                default: function () {
                    return [];
                }
            },
            //上传按钮文字
            text: {default: '点击上传'},
            //最大上传数量
            maxCount: {default: undefined},
            //最大上传单文件大小
            maxFileSize: {default: undefined},
            //允许上传的文件格式
            accept: {default: undefined},
            //仅显示按钮
            buttonMode: {default: false},
            //按钮样式
            buttonClass: {default: 'btn btn_upload'}
        },
        data: function () {
            return {
                id: undefined,              //控件id
                uploader: undefined,        //WebUploader实例
                filesId: this.value,
                innerFiles: [],             //文件内部变量
                multiple: this.maxCount != 1,//是否多文件上传
                canFile: true,                  //是否返回文件
                innerButtonClass: this.buttonMode ? this.buttonClass : 'input_group_addon',
                layerIndex: ''
            }
        },
        template: '<div class="webuploader_container">\
                        <div class="input_group">\
                            <input type="text" class="form_control" v-show="!buttonMode" v-model="fileNames" readonly>\
                            <span :class="innerButtonClass" title="点击上传" @click="showUpload">{{text}}</span>\
                        </div>\
                        <div :id="\'layer_\'+id" style="display: none;">\
                            <div class="file_wrap" >\
                                <span class="error tooltip" title="一键删除" v-show="hasFile" @click="deleteFile"></span>\
                                <div class="choose_file" v-show="!hasFile" :id="id">\
                                <span >\
                                    <p >请选择上传文件</p>\
                                    <img src="../../asset/images/uploader.png" >\
                                </span>\
                                </div>\
                                <div class="file_container" :id="file.id"  v-for="(file, index) in innerFiles">\
                                    <p :class="fileClass(file.name)">\
                                        {{file.name}} ({{autoUnitSize(file.size)}})</p>\
                                    <div class="upload_progress"><div class="upload_progress_bar"></div></div>\
                                    <i class="iconfont icon-lajitong file_delete" @click="deleteFile(file,index)"></i>\
                                </div>\
                            </div>\
                        </div>\
                    </div>',
        created: function () {
            //生成guid，此id用于创建WebUploader实例时关联pick，以保证页面上有多个组件时互相隔离
            //采用layer前缀
            this.id = WebUploader.Base.guid("");
        },
        mounted: function () {
            //根据filesId获取文件
            // if (this.filesId.length > 0) {
            //     this.setFiles(this.value);
            // }
            this.upload();
        },
        watch: {
            value: {
                handler: function (val, oldVal) {
                    //如果指定了filesId，根据filesId获取文件
                    if (val != null && val.length > 0 && val != oldVal) {
                        if (this.canFile)
                            this.setFiles(this.value);
                    }
                }, immediate: true
            }
        },
        computed: {
            //是否有文件
            hasFile: function () {
                var that = this;
                if (that.uploader)
                    that.uploader.refresh();
                if (that.innerFiles == null || that.innerFiles.length < 1)
                    return false;
                return true;
            },
            fileNames: function () {
                var names = $.map(this.innerFiles, function (value, index) {
                    return value.name;
                });
                return names.join('、');
            }
        },
        methods: {
            upload: function () {
                this.uploader = WebUploader.create({
                    swf: '../../asset/libs/webuploader-0.1.5/js/Uploader.swf',
                    server: fileStoreConfig.serverUrl + '/api/upload',
                    method: 'POST',
                    pick: {id: '#' + this.id, multiple: this.multiple},
                    resize: false,                  //不压缩image, 默认如果是jpeg，文件上传前会进行压缩
                    auto: true,                     //选择文件后自动上传
                    duplicate: true,                //允许选择重复文件
                    fileNumLimit: this.maxCount,    //允许最多文件个数
                    fileSingleSizeLimit: this.maxFileSize,    //验证单个文件大小是否超出限制, 超出则不允许加入队列。
                    accept: this.accept             //限制文件类型
                });
                var that = this;
                if (that.uploader != null) {
                    that.uploader.on('fileQueued', function (file) {
                        that.innerFiles.splice(0, 0, file);  //将上传文件加入集合
                        that.getFilesId();
                        that.$nextTick(function () {
                            $('#' + file.id + " .upload_progress").css('display', 'inline-block');
                        });
                    });

                    that.uploader.on('uploadProgress', function (file, percentage) {
                        $('#' + file.id + " .upload_progress_bar").css('width', percentage * 100 + '%');
                    });

                    that.uploader.on('uploadSuccess', function (file, response) {
                        if (!response.isSuccess) {
                            layer.msg(response.message, {icon: 2});
                            $('#' + file.id + ' .upload_progress').hide();  //隐藏进度条
                            var index = that.innerFiles.indexOf(file);
                            that.innerFiles.splice(index, 1);               //上从集合中删除文件
                            //移除组件中文件，否则计算已上传文件数量时有误
                            if (that.uploader.getFiles().indexOf(file) != -1)
                                that.uploader.removeFile(file, true);   //移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为true则会从queue中移除
                        } else {
                            $('#' + file.id + ' .upload_progress').hide();
                            file.id = response.data.id;                     //上传成功时更新文件id
                            //添加回调方法
                            that.$emit('after-upload', file);
                        }
                        that.getFilesId();
                    });

                    that.uploader.on("error", function (type) {
                        if (type == "Q_TYPE_DENIED") {
                            layer.msg("文件不能为空。");
                            if (typeof that.accept != 'undefined')
                                layer.msg("只允许上传“" + that.accept.extensions + "”格式文件。");//验证文件格式
                        } else if (type == "F_EXCEED_SIZE") {
                            layer.msg("单文件大小不能超过" + that.autoUnitSize(that.maxFileSize));                              //验证文件大小
                        } else if (type == "Q_EXCEED_NUM_LIMIT") {
                            layer.msg("最多只允许上传" + that.maxCount + "个文件。");//验证文件数量
                        } else {
                            layer.msg("上传文件时发生错误：" + type);
                        }
                    });

                    that.uploader.on('uploadError', function (file, reason) {
                        layer.msg('上传文件时发生错误：' + reason);
                        $('#' + file.id + ' .upload_progress').hide();  //隐藏进度条
                        var index = that.innerFiles.indexOf(file);
                        that.innerFiles.splice(index, 1);               //从集合中删除文件
                        //移除组件中文件，否则计算已上传文件数量时有误
                        if (this.uploader.getFiles().indexOf(file) != -1)
                            this.uploader.removeFile(file, true);   //移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为true则会从queue中移除
                        that.getFilesId();
                    });
                }
            },
            setFiles: function (filesId) {
                var that = this;
                if (filesId.length > 0) {
                    $.ajax({
                        async: false,
                        type: 'POST',
                        url: fileStoreConfig.serverUrl + '/api/query-by-ids',
                        data: JSON.stringify(filesId),
                        contentType: 'application/json',
                        success: function (data) {
                            if (!data.isSuccess) return;
                            that.innerFiles = $.map(data.data, function (item, index) {
                                return {name: item.fullname, size: item.contentLength, type: item.contentType, id: item.id};
                            })
                        }
                    });
                }
            },
            closeForm: function () {
                layer.close(this.layerIndex);
            },
            //显示上传文件弹窗
            showUpload: function () {
                var that = this;
                layer.open({
                    type: 1,
                    title: ['上传文件', 'font-weight: bold;background: #fff;margin: 0 10px;padding-left: 2px;'],
                    shadeClose: false,
                    btnAlign: 'c',
                    area: ['435px', '270px'],
                    content: $('#layer_' + that.id),
                    btn: ['完成'],
                    yes: function (index) {
                        layer.close(index);
                    },
                    success: function (layero, index) {
                        that.uploader.refresh();
                    }
                });
            },
            deleteFile: function (file, index) {
                var that = this;
                if (that.hasFile) {
                    //var delFile = that.innerFiles[0];
                    if (index) { // 删除单个文件
                        that.innerFiles.splice(index, 1);
                    } else { // 删除所有文件
                        that.innerFiles = [];
                    }
                    that.getFilesId();
                    that.upload();
                    //移除组件中文件，否则计算已上传文件数量时有误
                    // that.uploader.removeFile(delFile, true);   //移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为true则会从queue中移除
                }
            },
            getFiles: function () {
                return this.innerFiles;
            },
            getFilesId: function () {
                var ids = $.map(this.innerFiles, function (value, index) {
                    return value.id;
                });
                this.canFile = false;
                this.$emit('input', ids);
                return ids;
            },
            //根据文件大小自动选择单位显示
            autoUnitSize: function (size) {
                return WebUploader.Base.formatSize(size);
            },
            //根据文件后缀名判断文件类型
            fileClass: function (filename) {
                console.log('filename',filename);
                //获取最后一个.的位置
                var index = filename.lastIndexOf(".");
                //获取后缀
                var ext = filename.substr(index + 1);
                if (ext.indexOf('doc') > -1)
                    return 'file_word';
                else if (ext.indexOf('xls') > -1)
                    return 'file_excel';
                else if (ext.indexOf('ppt') > -1)
                    return 'file_ppt';
                else if (ext.indexOf('pdf') > -1)
                    return 'file_pdf';
                else if (ext.indexOf('txt') > -1)
                    return 'file_txt';
                else if (['zip', 'rar', '7z'].indexOf(ext) > -1)
                    return 'file_zip';
                else if (['bmp', 'jpg', 'png', 'gif', 'jpeg'].indexOf(ext) > -1)
                    return 'file_png';
                else
                    return 'file_unknow';
            }
        }
    });
});
