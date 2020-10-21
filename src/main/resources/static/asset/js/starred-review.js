//CheckBox Select组件
define(['jquery', 'vue'], function ($, Vue) {
    return Vue.component('starred-review', {
        props: {
            bindKey: '',
            value: '',
            readonly: {
                default: false
            }
        },
        template: '<div><div class="procurement">' +
            '            <span class="starlevel" @click="pingFen(1,$event)" :class="{ \'starlevel_active\':this.innerData>0 }"></span>' +
            '            <span class="starlevel" @click="pingFen(2,$event)" :class="{ \'starlevel_active\':this.innerData>1 }"></span>' +
            '            <span class="starlevel" @click="pingFen(3,$event)" :class="{ \'starlevel_active\':this.innerData>2 }"></span>' +
            '            <span class="starlevel" @click="pingFen(4,$event)" :class="{ \'starlevel_active\':this.innerData>3 }"></span>' +
            '            <span class="starlevel" @click="pingFen(5,$event)" :class="{ \'starlevel_active\':this.innerData>4 }"></span>' +
            '        </div></div>',
        data: function () {
            return {
                innerData: this.value
            }
        },
        watch: {
            value: function (newValue) {
                this.innerData = newValue
            }
        },
        created: function () {
        },
        methods: {
            pingFen: function (num, el) {
                if (this.readonly)
                    return false;
                $(el.target).parent().children('.starlevel').removeClass('starlevel_active');
                for (var j = 0; j < num; j++) {
                    $(el.target).parent().children('.starlevel').eq(j).addClass('starlevel_active')
                }
                this.innerData = num;
                this.$emit('input', this.innerData);
            }
        }
    })
});
