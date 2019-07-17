<template>
  <div>
    <div class="picker">
      <input v-model="time" unselectable="on" class="input" type="text" readonly="true" placeholder="请选择时间"
             @click="pickTime">
    </div>
    <div class="all">
      <div>可结返利</div>
      <div>
        <span>{{readyFee}}</span>元
      </div>

    </div>
    <div class="list">
      <div class="line firstline">
        <div>加盟商</div>
        <div>采购金额</div>
        <div>付款日期</div>
        <div>返利</div>
      </div>
      <div class="line" v-for='ele in list'>
        <div>{{ele.USER_NAME}}</div>
        <div>{{ele.TOTAL_FEE}}</div>
        <div>{{ele.PAY_TIME?new Date(ele.PAY_TIME).Format('yyyy-MM-dd hh:mm:ss'):''}}</div>
        <div>{{ele.BACK_FEE}}</div>
      </div>
    </div>
    <van-datetime-picker v-show="showTime" class="time-class" @confirm="handleConfirm" @cancel="handleCancer"
                         v-model="currentDate" type="year-month" :min-date="minDate" :max-date="maxDate"/>

  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {agentBack} from '@/api/center'
  export default {
    data() {
      return {
        time: new Date().Format("yyyy-MM"),
        showTime: false,
        minDate: new Date(2000, 10, 1),
        maxDate: new Date(),
        currentDate: new Date(),
        list: [],
      }
    },
    created() {
      this.initData(this.time);
    },
    methods: {
      initData(time){
        //代理返利
        agentBack({userId: this.user.id}).then(data=>{
          this.list = data;
        })
      },
      pickTime: function () {
        this.showArea = false;
        this.showTime = true;
      },
      handleConfirm(val) {
        this.showTime = false;
        this.time = val.Format("yyyy-MM");
        this.initData(this.time);
      },
      handleCancer() {
        this.showTime = false;
      }
    },
    computed: {
      ...mapGetters([
        'user'
      ]),
      readyFee:function(){
        let _fee = 0;
        for(let i =0;i<this.list.length;i++){
          _fee+=this.list[i].BACK_FEE;
        }
        return _fee;
      }
    },
  }
</script>

<style scoped>
  .picker {
    height: 40px;
  }

  .picker input {
    height: 40px;
    border: 0;
    margin: 0;
    text-align: center;
    color: white;
    width: 100%;
    background: rgb(68, 221, 175);
  }

  ::-webkit-input-placeholder {
    /* WebKit browsers */
    color: white;
  }

  :-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    color: white;
  }

  ::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    color: white;
  }

  :-ms-input-placeholder {
    /* Internet Explorer 10+ */
    color: white;
  }

  .all {
    display: flex;
    height: 80px;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding: 0 15px;
  }

  .all span {
    font-size: 40px;
    color: orange;
    margin-right: 10px;
  }

  .line {
    overflow: hidden;
    border-bottom: 1px solid #dddddd;
  }

  .line > div {
    font-size: 13px;
    text-align: center;
    float: left;
    width: 25%;
    height: 40px;
    line-height: 40px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }

  .firstline > div {
    background: rgb(68, 221, 175);
    color: white;
  }
</style>
