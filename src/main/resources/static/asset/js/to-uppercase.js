//CheckBox Select组件
define(['jquery', 'vue'], function ($, Vue) {
    return Vue.component('to-uppercase', {
        props: {
            popinputdataleve1:{}
        },
        template:'<tr><td>{{popinputdataleve1.label}}</td>\n' +
            '                    <td>\n' +
            '                        <div class="input_group">\n' +
            '                            <input class="form_control" v-model="popinputdataleve1.money" @keyup="getKeyup()"  maxlength="15" oninput="value=value.replace(/[^\\d.]/g,\'\')"  >\n' +
            '                            <span class="input_group_addon"><i>元</i></span>\n' +
            '                        </div>\n' +
            '                    </td>\n' +
            '                    <td>大写</td>\n' +
            '                    <td><p class="detail_line upperCase">{{popinputdataleve1.upperMoney}}</p></td>\n' +
            '                </tr>',
        data: function () {
            return {
                popInputData:{
                    label:'',
                    money:'',
                    upperMoney:''
                }
            }
        },
        computed: {

        },
        watch: {

        },
        created: function () {
            this.popinputdataleve1.upperMoney = this.changDaxie(this.popinputdataleve1.money)
        },
        methods: {
            getKeyup:function () {
                this.popinputdataleve1.upperMoney = this.changDaxie(this.popinputdataleve1.money)
            },
            changDaxie:function (money){
                var cnNums = ["零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"]; //汉字的数字
                var cnIntRadice = ["", "拾", "佰", "仟"]; //基本单位
                var cnIntUnits = ["", "万", "亿", "兆"]; //对应整数部分扩展单位
                var cnDecUnits = ["角", "分", "毫", "厘"]; //对应小数部分单位
                var cnInteger = "整"; //整数金额时后面跟的字符
                var cnIntLast = "元"; //整型完以后的单位
                var maxNum = 999999999999999.9999; //最大处理的数字
                var IntegerNum; //金额整数部分
                var DecimalNum; //金额小数部分
                var ChineseStr = ""; //输出的中文金额字符串
                var parts; //分离金额后用的数组，预定义
                var Symbol="";//正负值标记
                if (money == "") {
                    return "";
                }
                money = parseFloat(money);
                if (money >= maxNum) {
                    // alert('超出最大处理数字');
                    return "";
                }
                if (money == 0) {
                    ChineseStr = cnNums[0] + cnIntLast + cnInteger;
                    return ChineseStr;
                }
                if(money<0)
                {
                    money=-money;
                    Symbol="负 ";
                }
                money = money.toString(); //转换为字符串
                if (money.indexOf(".") == -1) {
                    IntegerNum = money;
                    DecimalNum = '';
                } else {
                    parts = money.split(".");
                    IntegerNum = parts[0];
                    DecimalNum = parts[1].substr(0, 4);
                }
                if (parseInt(IntegerNum, 10) > 0) { //获取整型部分转换
                    var zeroCount = 0;
                    var IntLen = IntegerNum.length;
                    for (var i = 0; i < IntLen; i++) {
                        var n = IntegerNum.substr(i, 1);
                        var p = IntLen - i - 1;
                        var q = p / 4;
                        var m = p % 4;
                        if (n == "0") {
                            zeroCount++;
                        }
                        else {
                            if (zeroCount > 0) {
                                ChineseStr += cnNums[0];
                            }
                            zeroCount = 0; //归零
                            ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                        }
                        if (m == 0 && zeroCount < 4) {
                            ChineseStr += cnIntUnits[q];
                        }
                    }
                    ChineseStr += cnIntLast;
                    //整型部分处理完毕
                }
                if (DecimalNum != '') { //小数部分
                    var decLen = DecimalNum.length;
                    for (var i = 0; i < decLen; i++) {
                        var n = DecimalNum.substr(i, 1);
                        if (n != '0') {
                            ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
                        }
                    }
                }
                if (ChineseStr == '') {
                    ChineseStr += cnNums[0] + cnIntLast + cnInteger;
                } else if (DecimalNum == '') {
                    ChineseStr += cnInteger;
                }
                ChineseStr = Symbol +ChineseStr;

                return ChineseStr;
            }
        }
    })
});
