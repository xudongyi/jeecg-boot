<template>
  <div>
    <div class="cell-group">
      <div class="cell-group-name">申请人:</div>
      <div class="cell-group-content">
        <van-field v-model="username" placeholder="请输入申请人" />
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">联系方式:</div>
      <div class="cell-group-content">
        <van-field v-model="username" placeholder="请输入联系方式" />
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">性别:</div>
      <div class="cell-group-content">
        <van-radio-group v-model="radio" class="radio-group">
          <van-radio class="inline-radio" name="1">男</van-radio>
          <van-radio class="inline-radio" name="2">女</van-radio>
        </van-radio-group>
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">出生年月:</div>
      <div class="cell-group-content">
        <van-field v-model="birthday" unselectable="on" onfocus="this.blur()" placeholder="请选择时间" readonly @click="pickTime" />
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">身份证:</div>
      <div class="cell-group-content">
        <van-field v-model="identity" placeholder="请输入身份证号" />
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">推荐人:</div>
      <div class="cell-group-content">
        <van-field center unselectable="on" onfocus="this.blur()">
          <van-button @click="scanQr" slot="button" size="small" type="primary">扫码</van-button>
        </van-field>
      </div>
    </div>
    <div class="cell-group">
      <div class="cell-group-name">代理区域:</div>
      <div class="cell-group-content">
        <van-field v-model="area" unselectable="on" onfocus="this.blur()" readonly @click="pickArea" placeholder="请选择代理区域" />
      </div>
    </div>
    <van-datetime-picker v-show="showTime" class="time-class" @confirm="handleConfirm" @cancel="handleCancer" v-model="currentDate" type="datetime" :min-date="minDate" :max-date="maxDate" />
    <van-area class="time-class" v-show="showArea" @confirm="handleAreaConfirm" @cancel="handleAreaCancer" :area-list="areaList" />
  </div>
</template>
<script>
import areaList from "@/plugins/area"
import { getUserList } from '@/api/apply'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      username: "",
      radio: "1",
      birthday: "",
      showTime: false,
      showArea: false,
      minHour: 10,
      maxHour: 20,
      minDate: new Date(),
      maxDate: new Date(2019, 10, 1),
      currentDate: new Date(),
      areaList: areaList,
      area: "",
      areaCode: "",
    }
  },
  components: {},
  created() {
    console.log(this.user.username)
  },
  mounted() {

  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  methods: {
    pickTime: function () {
      this.showArea = false;
      this.showTime = true;
    },
    handleConfirm: function (val) {
      this.showTime = false;
      this.birthday = val.Format("yyyy-MM-dd hh:mm:ss");
    },
    handleCancer: function () {
      this.showTime = false;
    },
    pickArea: function () {
      this.showTime = false;
      this.showArea = true;
    },

    handleAreaConfirm: function (val) {
      if (val[0].name.indexOf("选择") > -1 || val[1].name.indexOf("选择") > -1 || val[2].name.indexOf("选择") > -1) {
        this.swal({
          title: '提示!',
          text: '请选择完成的地址',
          type: 'warning',
          confirmButtonText: '确认'
        })
        return;
      }
      this.area = val[0].name + '-' + val[1].name + '-' + val[2].name;
      this.areaCode = val[0].code + ',' + val[1].code + ',' + val[2].code;
      this.showArea = false;
    },
    handleAreaCancer: function () {
      this.showArea = false;
    },
    scanQr: function () {
      alert("scanQr")
    }
  },
}
</script>
<style scoped>
.cell-group {
  overflow: hidden;
  border-bottom: 1px solid #c1c1c1;
}

.cell-group-name {
  width: 70px;
  height: 44px;
  line-height: 44px;
  float: left;
  text-align: right;
  padding-right: 10px;
}

.cell-group-content {
  width: calc(100% - 80px);
  float: left;
}

.inline-radio {
  display: inline-block;
}

.radio-group {
  margin-top: 8px;
  margin-left: 10px;
}
</style>
