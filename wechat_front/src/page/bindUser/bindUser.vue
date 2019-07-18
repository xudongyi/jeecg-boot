<template>
  <div>
    <van-cell-group>
      <van-field
        left-icon="manager-o"
        :value="username"
        required
        label="户号"
        placeholder="请输入16位户号"
      />
      <van-field
        left-icon="phone-circle-o"
        :value="mobile"
        required
        label="手机号"
        placeholder="请输入手机号"
      />
      <van-field
        left-icon="chat-o"
        :value="sms"
        required
        center
        label="短信验证码"
        placeholder="请输入短信验证码"
        use-button-slot
      >
        <van-button slot="button" size="small" type="primary">发送验证码</van-button>
      </van-field>
    </van-cell-group>
    <div class="confirm-button">
      <van-button type="primary" size="large">确认绑定</van-button>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    data() {
      return {
        username: "",
        mobile: "",
        sms: "",
      }
    },
    components: {},
    created() {

    },
    mounted() {

    },
    computed: {
      ...mapGetters([
        'user'
      ])
    },
    methods: {
       bindNumber:function(){

       }
    },
  }
</script>

<style scoped>
.confirm-button{
  width: 80%;
  margin: auto;
  margin-top: 30px;
}
</style>
