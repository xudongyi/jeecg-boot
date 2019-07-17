<template>
  <div>
    <van-tabs v-model="active" @click="onClick">
      <van-tab title="已完成">
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
            <div>被推荐人</div>
            <div>代理/加盟金额</div>
            <div>付款日期</div>
            <div>返利</div>
          </div>
          <div class="line" v-for='ele in already'>
            <div>{{ele.USER_NAME}}</div>
            <div>{{ele.APPLY_FEE}}</div>
            <div>{{new Date(ele.UPDATE_TIME).Format('yyyy-MM-dd hh:mm:ss')}}</div>
            <div>{{ele.BACK_FEE}}</div>
          </div>
        </div>
      </van-tab>
      <van-tab title="未完成">
        <div class="all">
          <div>预估返利</div>
          <div>
            <span>{{todoFee}}</span>元
          </div>
        </div>
        <div class="list">
          <div class="line firstline">
            <div>被推荐人</div>
            <div>代理/加盟金额</div>
            <div>返利</div>
            <div>状态</div>
          </div>
          <div class="line" v-for='ele in noFinish'>
            <div>{{ele.USER_NAME}}</div>
            <div>{{ele.APPLY_FEE}}</div>
            <div>{{ele.BACK_FEE}}</div>
            <div>{{ele.PAY_STATUS | statusFilter(ele.APPLY_STATUS) }}</div>
          </div>
        </div>
      </van-tab>
    </van-tabs>
    <van-datetime-picker v-show="showTime" class="time-class" @confirm="handleConfirm" @cancel="handleCancer"
                         v-model="currentDate" type="year-month" :min-date="minDate" :max-date="maxDate"/>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {recommendBackFinish,recommendBackTODO} from '@/api/center'
  export default {
    data() {
      return {
        active: 0,
        time: new Date().Format("yyyy-MM"),
        showTime: false,
        minDate: new Date(2000, 10, 1),
        maxDate: new Date(),
        currentDate: new Date(),
        already: [],
        noFinish: [],
      }
    },
    computed: {
      ...mapGetters([
        'user'
      ]),
      readyFee:function(){
          let _fee = 0;
          for(let i =0;i<this.already.length;i++){
            _fee+=this.already[i].BACK_FEE;
          }
          return _fee;
      },
      todoFee:function(){
        let _fee = 0;
        for(let i =0;i<this.noFinish.length;i++){
          _fee+=this.noFinish[i].BACK_FEE;
        }
        return _fee;
      }
    },
    created() {
      //已完成的推荐返利
      this.initAlreadyData(this.time);
      this.initTODOData(this.time);
      //未完成的推荐返利
      http.get('api/center/recommendBackTODO.do', {
        params: {
          userId: this.user.id,
        }
      }).then(res => {
        console.log(res);
        this.noFinish = res;
      })
    },
    methods: {
      initAlreadyData(time){
        recommendBackFinish({userId: this.user.id, month: time}).then(data => {
          this.already = data;
        })
      },
      initTODOData(time){
        recommendBackTODO({userId: this.user.id, month: time}).then(data => {
          this.noFinish = data;
        })
      },
      pickTime: function () {
        this.showTime = true;
      },
      handleConfirm(val) {
        this.showTime = false;
        this.time = val.Format("yyyy-MM");
        this.initAlreadyData(this.time);
        this.noFinish(this.time);
      },
      handleCancer() {
        this.showTime = false;
      },
      onClick(index, title) {
        this.showTime = false;
      }
    },
    filters: {
      statusFilter(payStatus, applyStatus){
          let message = payStatus;
          if(applyStatus==1){
              message = "申请待审核"
          }else if(applyStatus==2){
              if(payStatus==1){
                message = "待支付"
              }else if(applyStatus==2){
                message = "待审核"
              }else if(applyStatus==3){
                message = "已通过"
              }else if(applyStatus==4){
                message = "凭证未通过"
              }
          }else if(applyStatus==3){
            message = "申请未通过"
          }
          return message;
      }
    }
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
