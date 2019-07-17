<template>
    <div>
        <img src="../../images/shopmall/load.png" alt="">
        <div>您的{{typeName}}正在审核，请耐心等待!</div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            typeName: ''
        }
    },
    created() {
        //判断类型 1 代理 2 加盟 3 付款
        var type = this.$route.query.type;
        if (type == 1) {
            this.typeName = "代理申请";
        } else if (type == 2) {
            this.typeName = "加盟申请";
        } else if (type == 3) {
            this.typeName = "付款凭证";
        }
    },
}
</script>

<style scoped>
img {
  display: block;
  margin: 30px auto;
}
div {
  text-align: center;
}
</style>