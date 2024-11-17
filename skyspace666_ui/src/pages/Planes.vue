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
            <plane-table
            v-if="planes && planes.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:planes="planes"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-planes="getAllPlanes"
             >

            </plane-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PlaneTable from "@/components/PlaneTable";
import PlaneService from "../services/PlaneService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PlaneTable,
  },
  data() {
    return {
      planes: [],
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
    async getAllPlanes(sortBy='planeId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PlaneService.getAllPlanes(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.planes.length) {
					this.planes = response.data.planes;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching planes:", error);
        }
        
      } catch (error) {
        console.error("Error fetching plane details:", error);
      }
    },
  },
  mounted() {
    this.getAllPlanes();
  },
  created() {
    this.$root.$on('searchQueryForPlanesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPlanes();
    })
  }
};
</script>
<style></style>
