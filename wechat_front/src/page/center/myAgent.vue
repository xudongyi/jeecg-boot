<template>
  <div class="myContainer">
    <table>
      <thead>
      <td>日期</td>
      <td>类型</td>
      <td>区域</td>
      <td>品牌</td>
      </thead>
      <tbody>
      <tr v-for="ele in list">
        <td>{{new Date(ele.CREATE_TIME).Format('yyyy-MM-dd')}}</td>
        <td>{{ele.TYPE==1?'代理':'加盟'}}</td>
        <td>{{ele.AREA}}</td>
        <td>{{ele.BRAND_NAME}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {myAgentAndJiameng} from '@/api/center'
  export default {
    data() {
      return {
          list:[]
      }
    },
    created() {
      //已加盟的信息
      myAgentAndJiameng({userId:this.user.id}).then(data=>{
        this.list=data;
      })
    },
    computed: {
      ...mapGetters([
        'user'
      ]),
    },
    methods: {
      name() {

      }
    },
  }
</script>

<style scoped>
  .myContainer {
    padding-top: 30px;
  }

  table {
    width: 95%;
    margin: 0 auto;
    border-collapse: collapse;
    table-layout: fixed;
  }

  thead td {
    color: white;
    background: rgb(68, 221, 175);
  }

  td {
    border: 1px solid #dddddd;
    text-align: center;
    height: 30px;
    line-height: 30px;
    font-size: 13px;
    width: 25%;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
</style>
