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
            <travelInsurance-table
            v-if="travelInsurances && travelInsurances.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:travelInsurances="travelInsurances"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-travel-insurances="getAllTravelInsurances"
             >

            </travelInsurance-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import TravelInsuranceTable from "@/components/TravelInsuranceTable";
import TravelInsuranceService from "../services/TravelInsuranceService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    TravelInsuranceTable,
  },
  data() {
    return {
      travelInsurances: [],
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
    async getAllTravelInsurances(sortBy='travelInsuranceId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await TravelInsuranceService.getAllTravelInsurances(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.travelInsurances.length) {
					this.travelInsurances = response.data.travelInsurances;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching travelInsurances:", error);
        }
        
      } catch (error) {
        console.error("Error fetching travelInsurance details:", error);
      }
    },
  },
  mounted() {
    this.getAllTravelInsurances();
  },
  created() {
    this.$root.$on('searchQueryForTravelInsurancesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllTravelInsurances();
    })
  }
};
</script>
<style></style>
