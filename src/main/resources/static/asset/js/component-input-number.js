function isValueNumber(value) {
    return (/(^-?[0-9]+\.{1}\d+$)|(^-?[1-9][0-9]*$)|(^-?0{1}$)/).test(value + '');
}

define(['vue'], function (Vue) {
    return Vue.component('input-number', {
        template: '\
        <div class="input_number">\
            <span class="input_number_button input_number_decrease" type="button"\
                @click.stop="handleDown"\
                :disabled="currentValue <= min"><i class="iconfont icon-decrease"></i></span>\
            <span class="input_number_button input_number_increase" type="button"\
                @click.stop="handleUp"\
                :disabled="currentValue >= max"><i class="iconfont icon-increase"></i></span>\
            <input \
                type="text" class="form_control" \
                :value="currentValue"\
                @change="handleChange">\
        </div>',
        props: {
            max: {
                type: Number,
                default: Infinity
            },
            min: {
                type: Number,
                default: -Infinity
            },
            value: {
                type: Number,
                default: 0
            },
            step: {
                type: Number,
                default: 1
            }
        },
        data: function () {
            return {
                currentValue: this.value
            }
        },
        watch: {
            currentValue: function (val) {
                this.$emit('input', val);
                this.$emit('on-change', val);
            },
            value: function (val) {
                this.updateValue(val);
            }
        },
        methods: {
            //加法
            accAdd: function (arg1, arg2) {
                var r1, r2, m;
                try {
                    r1 = arg1.toString().split(".")[1].length
                } catch (e) {
                    r1 = 0
                }
                try {
                    r2 = arg2.toString().split(".")[1].length
                } catch (e) {
                    r2 = 0
                }
                m = Math.pow(10, Math.max(r1, r2));
                return (arg1 * m + arg2 * m) / m
            },
            //减法
            accSubtr: function (arg1, arg2) {
                var r1, r2, m, n;
                try {
                    r1 = arg1.toString().split(".")[1].length
                } catch (e) {
                    r1 = 0
                }
                try {
                    r2 = arg2.toString().split(".")[1].length
                } catch (e) {
                    r2 = 0
                }
                m = Math.pow(10, Math.max(r1, r2));
                //动态控制精度长度
                n = (r1 >= r2) ? r1 : r2;
                return Number(((arg1 * m - arg2 * m) / m).toFixed(n));
            },
            //除法
            accMul: function (arg1, arg2) {
                var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
                try {
                    m += s1.split(".")[1].length
                } catch (e) {
                }
                try {
                    m += s2.split(".")[1].length
                } catch (e) {
                }
                return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
            },
            handleDown: function () {
                if (this.currentValue <= this.min) return;
                this.currentValue = this.accSubtr(this.currentValue, this.step);
            },
            handleUp: function () {
                if (this.currentValue >= this.max) return;
                var value = this.accAdd(this.currentValue, this.step);
                if (value > this.max) return;
                this.currentValue = value;
            },
            updateValue: function (val) {
                var that = this;
                //输入值小于等于最小值，返回最小值
                if (val <= this.min) val = this.min;
                //输入值大于最大值，返回最大值
                else if (val > this.max) {
                    var temp = Math.floor(that.max / val);
                    val = that.accMul(temp, that.step);
                } else {
                    //输入值在合理区间内，返回为(输入值-最小值)%step的最大值且小于等于输入值
                    var temp = Math.floor(that.accSubtr(val, that.min) / that.step);
                    val = that.accAdd(that.min, that.accMul(temp, that.step));
                }
                this.currentValue = val;
            },
            handleChange: function (event) {
                var val = event.target.value.trim();
                var max = this.max;
                var min = this.min;

                if (isValueNumber(val)) {
                    val = Number(val);
                    this.currentValue = val;

                    if (val > max) {
                        this.currentValue = max;
                    } else if (val < min) {
                        this.currentValue = min;
                    }
                } else {
                    event.target.value = this.currentValue;
                }
            }
        },
        mounted: function () {
            this.updateValue(this.value);
        }
    });
});