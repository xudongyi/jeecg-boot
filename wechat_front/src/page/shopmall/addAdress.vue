<template>
	<div class="addAdress">
		<van-address-edit  :area-list="areaList" :address-info="addressInfo" show-search-result :search-result="searchResult" @save="onSave" @delete="onDelete" @change-detail="onChangeDetail" />
	</div>
</template>
<script>
	import areaList from "@/plugins/area"
	import { Toast } from 'vant';
	import { saveAddress,deleteAddress } from "@/api/shopmall"
  import { mapGetters } from 'vuex'
  export default {
		data() {
			return {
				areaList: areaList,
				searchResult: [],
				addressInfo: {},
				loadObj: {
					userId: "",
					userName: "",
					mobile: "",
					province: "",
					city: "",
					area: "",
					unionAddress: "",
					address: "",
					isDefault: 2

				}
			}
		},
		created() {
			
			this.loadObj.userId=this.user.id;
			if(this.$route.query.target!=undefined){
				var target=JSON.parse(this.$route.query.target);
				this.loadObj.id = target.id;
				console.log(target)
				this.addressInfo=target;
			}else{
				this.addressInfo={};
			}


		},
		mounted() {
			//修改
			var a=document.getElementsByClassName("van-field__control")[1];
			console.log(a)
			a.setAttribute("placeholder","输入手机号码")
		},
    computed:{
      ...mapGetters([
        'user'
      ])
    },
		methods: {
			onSave(content) {
				console.log(content)
				this.loadObj.mobile = content.tel;
				this.loadObj.userName = content.name;
				this.loadObj.province = content.area_code;
				this.loadObj.city = content.area_code;
				this.loadObj.area = content.area_code;
				this.loadObj.unionAddress = content.province + content.city + content.county;
				this.loadObj.address = content.address_detail;
				console.log(this.loadObj.address)
				saveAddress(this.loadObj).then(data => {
					if(data){
						Toast('保存成功!');
						this.$router.replace({path:this.$route.query.path})
					}else{
						Toast('保存失败!');
					}
				})
			},
			onDelete() {
				var loadObj={
					id:this.loadObj.id
				};
				deleteAddress(loadObj).then(data=>{
					if(data){
						Toast('删除成功!');
						this.$router.replace({path:this.$route.query.path})
					}else{
						Toast('删除失败!');
					}
				})
			},
			onChangeDetail() {
				console.log(this.searchResult)
			}

		},
		components: {}
	}
</script>
<style scoped>

</style>
