
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Planes</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalPlanes = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalPlanes">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Plane</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="PlaneId" type="text" placeholder="Enter PlaneId" v-model="planeToAdd.planeId"></base-input>
  <base-input label="Model" type="text" placeholder="Enter Model" v-model="planeToAdd.model"></base-input>
  <base-input label="Capacity" type="text" placeholder="Enter Capacity" v-model="planeToAdd.capacity"></base-input>
  <base-input label="Manufacturer" type="text" placeholder="Enter Manufacturer" v-model="planeToAdd.manufacturer"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="planes" :row-key="record => record.PlaneId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <PlanePictureView :planes="planes" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import PlaneService from "../services/PlaneService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import PlanePictureView from './PlanePictureView.vue';


const planesColumns = [
  "planeId",
  "year",
  "date",
  "competitionId",
  "planeId"
]

export default {
  props: {
    planes: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    PlanePictureView
  },

  data() {
    return {
      modalPlanes: false,
        isTableView: true,

      columns: [
        {
          title: 'Plane Id',
		dataIndex: 'planeId',
          visible: true,
          scopedSlots: { customRender: 'planeId' },
          sorter: true
          //sorter: (a, b) => a.planeId - b.planeId,
          //sorter: (a, b) => a.planeId.localeCompare(b.planeId),
        },
        {
          title: 'Model',
		dataIndex: 'model',
          visible: true,
          scopedSlots: { customRender: 'model' },
          sorter: true
          //sorter: (a, b) => a.model - b.model,
          //sorter: (a, b) => a.model.localeCompare(b.model),
        },
        {
          title: 'Capacity',
		dataIndex: 'capacity',
          visible: true,
          scopedSlots: { customRender: 'capacity' },
          sorter: true
          //sorter: (a, b) => a.capacity - b.capacity,
          //sorter: (a, b) => a.capacity.localeCompare(b.capacity),
        },
        {
          title: 'Manufacturer',
		dataIndex: 'manufacturer',
          visible: true,
          scopedSlots: { customRender: 'manufacturer' },
          sorter: true
          //sorter: (a, b) => a.manufacturer - b.manufacturer,
          //sorter: (a, b) => a.manufacturer.localeCompare(b.manufacturer),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} planes`,
      },

      planes: [],
      planeToAdd: {},

      planesTable: {
        columns: [...planesColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'planeId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderPlanesTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let planesTableData = [];
      for (let i = 0; i < this.planes.length; i++) {
        planesTableData.push({
          id: i,
          planeId: this.planes[i].planeId,
          year: this.planes[i].year,
          date: this.planes[i].date,
          competitionId: this.planes[i].competitionId,
          planeId: this.planes[i].planeId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-planes',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToFlightDetail(id) {
      this.$router.push({ name: 'FlightDetail', params: { flightId: id.toString() }})
    },
    routingToPlaneDetail(id) {
      this.$router.push({ name: 'PlaneDetail', params: { planeId: id.toString() }})
    },
    routingToPilotDetail(id) {
      this.$router.push({ name: 'PilotDetail', params: { pilotId: id.toString() }})
    },
    routingToPassengerDetail(id) {
      this.$router.push({ name: 'PassengerDetail', params: { passengerId: id.toString() }})
    },
    routingToBookingDetail(id) {
      this.$router.push({ name: 'BookingDetail', params: { bookingId: id.toString() }})
    },
    routingToTicketDetail(id) {
      this.$router.push({ name: 'TicketDetail', params: { ticketId: id.toString() }})
    },
    routingToPaymentMethodDetail(id) {
      this.$router.push({ name: 'PaymentMethodDetail', params: { paymentMethodId: id.toString() }})
    },
    routingToPaymentDetail(id) {
      this.$router.push({ name: 'PaymentDetail', params: { paymentId: id.toString() }})
    },
    routingToAirportDetail(id) {
      this.$router.push({ name: 'AirportDetail', params: { airportId: id.toString() }})
    },
    routingToTerminalDetail(id) {
      this.$router.push({ name: 'TerminalDetail', params: { terminalId: id.toString() }})
    },
    routingToGateDetail(id) {
      this.$router.push({ name: 'GateDetail', params: { gateId: id.toString() }})
    },
    routingToBaggageDetail(id) {
      this.$router.push({ name: 'BaggageDetail', params: { baggageId: id.toString() }})
    },
    routingToCrewDetail(id) {
      this.$router.push({ name: 'CrewDetail', params: { crewId: id.toString() }})
    },
    routingToCrewAssignmentDetail(id) {
      this.$router.push({ name: 'CrewAssignmentDetail', params: { crewAssignmentId: id.toString() }})
    },
    routingToLoyaltyProgramDetail(id) {
      this.$router.push({ name: 'LoyaltyProgramDetail', params: { loyaltyProgramId: id.toString() }})
    },
    routingToLoyaltyMemberDetail(id) {
      this.$router.push({ name: 'LoyaltyMemberDetail', params: { loyaltyMemberId: id.toString() }})
    },
    routingToInsuranceDetail(id) {
      this.$router.push({ name: 'InsuranceDetail', params: { insuranceId: id.toString() }})
    },
    routingToTravelInsuranceDetail(id) {
      this.$router.push({ name: 'TravelInsuranceDetail', params: { travelInsuranceId: id.toString() }})
    },
    routingToMealDetail(id) {
      this.$router.push({ name: 'MealDetail', params: { mealId: id.toString() }})
    },
    routingToInFlightMealDetail(id) {
      this.$router.push({ name: 'InFlightMealDetail', params: { inFlightMealId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForPlanesChanged', this.searchQuery);
		//this.renderPlanesTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalPlanes = false;

      const currentDate = new Date().getTime();
      this.planeToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.planeToAdd);
      console.log(jsonData);
      
      const res = await PlaneService.addPlane(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderPlanesTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
