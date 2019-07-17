<template>
  <div>

  </div>
</template>
<script>
  export default{
    data(){
      return {}
    },
    created() {
      let type = this.$route.query.type ? this.$route.query.type : 1;
      this.$router.replace({ path: '/routerTest'+type, query: { type: type } });
    },
    mounted() {

    },
    methods: {},
    components: {}
  }
</script>
<style scoped>

</style>
