<template>
  <div class="go_cash">
    <van-address-list
      v-model="chosenAddressId"
      :list="list"
      @add="onAdd"
      @edit="onEdit"
      @select="onPick"
    />
  </div>
</template>
<script>
	import {getAddress} from "@/api/shopmall"
  import { mapGetters } from 'vuex'
  export default{
    data(){
      return {
        chosenAddressId: '',
        activeIndex:0,
        list: [],
        addressList:[],
        totalNum:0,
        totalPrice:0

      }
    },
    created() {
    	var loadObj={
    		userId:this.user.id
    	};
      this.totalNum = this.$route.query.totalNum;
      this.totalPrice = this.$route.query.totalPrice;
      //请求
			getAddress(loadObj).then(data=>{
				this.addressList=data;
				for(var i=0;i<data.length;i++){
					var obj={};
					obj.id=data[i].id;
					obj.name=data[i].userName;
					obj.tel=data[i].mobile;
					obj.address=data[i].unionAddress+data[i].address;
					obj.province=data[i].province;
					obj.county=data[i].area;
					obj.city=data[i].city;
					obj.address_detail=data[i].address;
					obj.area_code=data[i].province;
					obj.is_default=	data[i].isDefault;
					this.list.push(obj);
					//判断之前是否选中
					if(this.$store.state.storeData.address==undefined||this.$store.state.storeData.address.id==undefined){

						if(data[i].isDefault==1){
							this.chosenAddressId = this.list[i].id;
						}else{
							this.chosenAddressId = this.list[0].id;
						}
					}else{
						this.chosenAddressId = this.$store.state.storeData.address.id;
					}



				}


			})

    },
    mounted() {

    },
    computed:{
      ...mapGetters([
        'user'
      ])
    },
    methods: {
      onAdd() {
        this.$router.push({path: "/addAdress",query:{path:"/address"}})
      },
      onEdit(item, index) {
        this.$router.push({path: "/addAdress",query:{target:JSON.stringify(this.list[index]),path:"/address"}})
      },
      onPick(item, index){
      	this.activeIndex=index;
      	this.$store.commit("updateAddress", this.addressList[index]);
      	this.$router.replace({path:"/goCash",query:{totalNum:this.totalNum,totalPrice:this.totalPrice}})

      }


    },
    components: {}
  }
</script>
<style scoped>
  .go_cash {
    height: 100%;
  }
</style>

