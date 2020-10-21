//多级关联选择框组件
define(['vue'], function (Vue) {
    return Vue.component('connection-box', {
        props: {
            'popinputdataleve1': {},
            'popinputdata': {},
            'placeholder': {default: '请选择采购目录'}
        },
        data: function () {
            return {
                popinputDataleve2: [],
                popinputDataleve3: [],
                popinputDataleve4: [],
                name1: '',
                name2: '',
                name3: '',
                name4: '',
                show: false
            }
        },
        template:
            '<div style="position: relative">' +
            '         <input type="text" @click="choseData" class="form_control" autocomplete="off" v-model="popinputdata.name" :placeholder="placeholder">' +
            '<div class="pupinput_select" v-show="show" @mouseleave="show=false">' +
            '         <div class="select_tree" >' +
            '             <div class="popinputDataleve1" v-for="(item,index) in popinputdataleve1" @mouseenter="showTree2(item.children,item.name,index)">{{item.name}} ' +
            '                 <img src="../../images/xiala.png" alt="">' +
            '             </div>' +
            '         </div>' +
            '         <div class="select_tree select_tree2" >' +
            '             <div  class="popinputDataleve2" v-for="(item,index) in popinputDataleve2" @mouseenter="showTree3(item.children,item.name,index)">{{item.name}}' +
            '                 <img src="../../images/xiala.png" alt="">' +
            '             </div>' +
            '         </div>' +
            '         <div class="select_tree select_tree3">' +
            '             <div  class="popinputDataleve3" v-for="(item,index) in popinputDataleve3" v-if="!item.children" @click="choseVal(item,index)" >{{item.name}}' +
            '             </div>' +
            '             <div  class="popinputDataleve3" v-for="(item,index) in popinputDataleve3"  v-if="item.children" @mouseenter="showTree4(item,item.name,index)">{{item.name}}' +
            '             </div>' +
            '         </div>' +
            '         <div class="select_tree select_tree4">' +
            '             <div  class="popinputDataleve4" v-for="(item,index) in popinputDataleve4" @click="choseVal(item,index)" @mouseenter="showTree5(item,index)">{{item.name}}' +
            '             </div>' +
            '         </div>' +
            '     </div>' +
            '     </div>',
        created: function () {
        },
        methods: {
            choseData: function () {
                this.show = true
            },
            showTree2: function (data, name, index, e) {//关联框第一级菜鼠标移入击事件 获取第二级数据
                this.name1 = name + '/';
                $('.popinputDataleve1').removeClass('pupinput_active');
                $('.popinputDataleve1').eq(index).addClass('pupinput_active');
                this.popinputDataleve2 = data;
                this.popinputDataleve3 = this.popinputDataleve2[0].children;
                if (this.popinputDataleve3[0].children) {
                    $('.pupinput_select').width('841px')
                } else {
                    $('.pupinput_select').width('631px')
                }
                this.popinputDataleve4 = this.popinputDataleve3[0].children;
            },
            showTree3: function (data, name, index, e) {//关联框第二级菜鼠标移入击事件 获取第三级数据
                this.name2 = name + '/';
                $('.popinputDataleve2').removeClass('pupinput_active');
                $('.popinputDataleve2').eq(index).addClass('pupinput_active');
                this.popinputDataleve3 = data;
                this.popinputDataleve4 = this.popinputDataleve3[0].children;
            },
            showTree4: function (data, name, index, e) {//关联框第三级菜鼠标移入击事件 绑定input框值
                if (data.children) {
                    $('.pupinput_select').width('841px')
                } else {
                    $('.pupinput_select').width('631px')
                }
                this.name3 = name + '/';
                $('.popinputDataleve3').removeClass('pupinput_active');
                $('.popinputDataleve3').eq(index).addClass('pupinput_active');
                this.popinputDataleve4 = data.children;
            },
            showTree5: function (data, index, e) {//关联框第4级菜鼠标移入击事件 绑定input框值
                $('.popinputDataleve4').removeClass('pupinput_active');
                $('.popinputDataleve4').eq(index).addClass('pupinput_active');
            },
            choseVal: function (item) {//点击最后级绑定数据
                this.name4 = item.name;
                this.popinputdata.name = this.name1 + this.name2 + this.name3 + this.name4;
                this.popinputdata.id = item.number;
                //添加回调方法
                this.$emit('popup-change', item);
                this.show = false
            },

        }
    });
});
