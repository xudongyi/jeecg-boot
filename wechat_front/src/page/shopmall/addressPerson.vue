<template>
	<div class="addressPerson">
		<div style="padding: 0px 0px 44px 0px;">
			<div class="addresslist" v-for="(obj,index) in list">
				<div class="top_address">
					{{obj.name}}&nbsp;&nbsp;&nbsp;{{obj.tel}}
				</div>
				<div class="middle">
					{{obj.address}}
				</div>
				<div class="bottom">
					<div class="left_address">
						<span @click="changeMo(index)" class="span" :class="{normal:obj.is_default==2,pick:obj.is_default==1}"></span>
						<span class="address_c">设为默认地址</span>
					</div>
					<span @click="onEdit(index)"><img src="../../images/shopmall/xiugai.png"/></span>
					<span @click="onDelete(index)"><img src="../../images/shopmall/delete.png"/></span>
				</div>
			</div>
		</div>
		<div class="addAdress" @click="onAdd">
			<span>新建地址</span>
		</div>
	</div>
</template>
<script>
	import { getAddress,saveAddress,deleteAddress } from "@/api/shopmall"
	import { Toast } from "vant"
  import { mapGetters } from 'vuex'
  export default {
		data() {
			return {
				addressList: [],
				list: [],
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
			this.initData();

		},
		mounted() {

		},
    computed:{
      ...mapGetters([
        'user'
      ])
    },
		methods: {
			initData() {
				var loadObj = {
					userId: this.user.id
				};
				this.list = [];
				getAddress(loadObj).then(data => {
					this.addressList = data;
					for(var i = 0; i < data.length; i++) {
						var obj = {};
						obj.id = data[i].id;
						obj.name = data[i].userName;
						obj.tel = data[i].mobile;
						obj.address =data[i].unionAddress+data[i].address;
						obj.province = data[i].province;
						obj.county = data[i].area;
						obj.city = data[i].city;
						obj.address_detail = data[i].address;
						obj.area_code = data[i].province;
						obj.is_default = data[i].isDefault;
						this.list.push(obj);

					}
					console.log(this.list)

				})
			},
			changeMo(index) {

				this.loadObj.userId = this.user.id
				this.loadObj.id = this.list[index].id;
				this.loadObj.mobile = this.list[index].tel;
				this.loadObj.userName = this.list[index].name;
				this.loadObj.province = this.list[index].area_code;
				this.loadObj.city = this.list[index].area_code;
				this.loadObj.area = this.list[index].area_code;
				this.loadObj.unionAddress = this.list[index].address.substring(0,this.list[index].address.length-this.list[index].address_detail.length);
				this.loadObj.address = this.list[index].address_detail;
				console.log(this.loadObj.address)
				if(this.list[index].is_default == 1) {
					this.loadObj.isDefault = 2;
				} else {
					this.loadObj.isDefault = 1

				}
				saveAddress(this.loadObj).then(data => {
					if(data) {
						Toast('修改成功!');
						this.initData();
					} else {
						Toast('修改失败!');
					}
				})
			},
			onEdit(index) {
				this.$router.push({
					path: "/addAdress",
					query: {
						target: JSON.stringify(this.list[index]),
						path: "/addressPerson"
					}
				})
			},
			 onAdd() {
        this.$router.push({path: "/addAdress",query:{path:"/addressPerson"}})
      },
			onDelete(index) {
				var loadObj = {
					id: this.list[index].id
				};
				deleteAddress(loadObj).then(data => {
					if(data) {
						Toast('删除成功!');
						this.initData();
					} else {
						Toast('删除失败!');
					}
				})
			},
		},
		components: {}
	}
</script>
<style scoped>
	.addressPerson {
		height: 100%;
	}

	.top_address {
		height: 34px;
		line-height: 34px;
		font-size: 14px;
		text-indent: 20px;
	}

	.middle {
		width: calc(100% - 40px);
		height: 54px;
		font-size: 14px;
		margin-left: 20px;
	}

	.bottom .left_address {
		width: calc(100% - 80px);
		line-height: 38px;
		text-indent: 10px;
		height: 100%;
		float: left;
	}

	.bottom {
		height: 38px;
		border-bottom: 1px solid #EEEEEE;
		border-top: 1px solid #EEEEEE;
	}

	.bottom>span {
		width: 40px;
		display: inline-block;
		height: 38px;
		float: left;
	}

	.bottom>span>img {
		width: 20px;
		height: 20px;
		margin-top: 10px;
		margin-left: 10px;
	}

	.left_address>.span {
		width: 18px;
		height: 18px;
		display: inline-block;
		margin-top: 10px;
		margin-left: 10px;
		background-size: 18px 18px;
	}

	.address_c {
		display: inline-block;
		vertical-align: top;
		width: 140px;
		font-size: 14px;
		height: 100%;
		text-align: left;
	}

	.normal {
		background: url(../../images/shopmall/wxz.png) no-repeat center;
	}

	.pick {
		background: url(../../images/shopmall/xz_address.png) no-repeat center;
	}

	.addAdress {
		position: fixed;
		width: 100%;
		height: 44px;
		color: white;
		line-height: 44px;
		text-align: center;
		bottom: 0px;
		background: #66cc99 url(../../images/shopmall/add.png) no-repeat 60px;
		background-size: 30px 30px;
	}
</style>
