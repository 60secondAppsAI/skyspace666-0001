<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <loyaltyMember-table
            v-if="loyaltyMembers && loyaltyMembers.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:loyaltyMembers="loyaltyMembers"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-loyalty-members="getAllLoyaltyMembers"
             >

            </loyaltyMember-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import LoyaltyMemberTable from "@/components/LoyaltyMemberTable";
import LoyaltyMemberService from "../services/LoyaltyMemberService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    LoyaltyMemberTable,
  },
  data() {
    return {
      loyaltyMembers: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllLoyaltyMembers(sortBy='loyaltyMemberId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await LoyaltyMemberService.getAllLoyaltyMembers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.loyaltyMembers.length) {
					this.loyaltyMembers = response.data.loyaltyMembers;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching loyaltyMembers:", error);
        }
        
      } catch (error) {
        console.error("Error fetching loyaltyMember details:", error);
      }
    },
  },
  mounted() {
    this.getAllLoyaltyMembers();
  },
  created() {
    this.$root.$on('searchQueryForLoyaltyMembersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllLoyaltyMembers();
    })
  }
};
</script>
<style></style>
