<template>
  <div>
    <van-radio-group v-model="sex" class="radio-group">
      <van-radio :name="1" class="inline-radio">男</van-radio>
      <van-radio :name="2" class="inline-radio">女</van-radio>
    </van-radio-group>
  </div>
</template>
<script>
  export default{
    data(){
      return {
        sex:1
      }
    },
    created() {
      alert(location.href)
      alert("created")
    },
    mounted() {
      alert("mounted")
    },
    methods: {},
    components: {}
  }
</script>
<style scoped>

</style>
