
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

              <h5 class="card-title">Payment Methods</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalPaymentMethods = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalPaymentMethods">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add PaymentMethod</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="PaymentMethodId" type="text" placeholder="Enter PaymentMethodId" v-model="paymentMethodToAdd.paymentMethodId"></base-input>
  <base-input label="MethodName" type="text" placeholder="Enter MethodName" v-model="paymentMethodToAdd.methodName"></base-input>
  <base-input label="Details" type="text" placeholder="Enter Details" v-model="paymentMethodToAdd.details"></base-input>
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
            <a-table :columns="columns" :data-source="paymentMethods" :row-key="record => record.PaymentMethodId" :pagination="pagination"
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
            <PaymentMethodPictureView :paymentMethods="paymentMethods" />
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
import PaymentMethodService from "../services/PaymentMethodService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import PaymentMethodPictureView from './PaymentMethodPictureView.vue';


const paymentMethodsColumns = [
  "paymentMethodId",
  "year",
  "date",
  "competitionId",
  "paymentMethodId"
]

export default {
  props: {
    paymentMethods: {
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
    PaymentMethodPictureView
  },

  data() {
    return {
      modalPaymentMethods: false,
        isTableView: true,

      columns: [
        {
          title: 'Payment Method Id',
		dataIndex: 'paymentMethodId',
          visible: true,
          scopedSlots: { customRender: 'paymentMethodId' },
          sorter: true
          //sorter: (a, b) => a.paymentMethodId - b.paymentMethodId,
          //sorter: (a, b) => a.paymentMethodId.localeCompare(b.paymentMethodId),
        },
        {
          title: 'Method Name',
		dataIndex: 'methodName',
          visible: true,
          scopedSlots: { customRender: 'methodName' },
          sorter: true
          //sorter: (a, b) => a.methodName - b.methodName,
          //sorter: (a, b) => a.methodName.localeCompare(b.methodName),
        },
        {
          title: 'Details',
		dataIndex: 'details',
          visible: true,
          scopedSlots: { customRender: 'details' },
          sorter: true
          //sorter: (a, b) => a.details - b.details,
          //sorter: (a, b) => a.details.localeCompare(b.details),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} paymentMethods`,
      },

      paymentMethods: [],
      paymentMethodToAdd: {},

      paymentMethodsTable: {
        columns: [...paymentMethodsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'paymentMethodId',           // Column to sort by
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

    async renderPaymentMethodsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let paymentMethodsTableData = [];
      for (let i = 0; i < this.paymentMethods.length; i++) {
        paymentMethodsTableData.push({
          id: i,
          paymentMethodId: this.paymentMethods[i].paymentMethodId,
          year: this.paymentMethods[i].year,
          date: this.paymentMethods[i].date,
          competitionId: this.paymentMethods[i].competitionId,
          paymentMethodId: this.paymentMethods[i].paymentMethodId,
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

	  this.$emit('get-all-payment-methods',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
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
    	this.$root.$emit('searchQueryForPaymentMethodsChanged', this.searchQuery);
		//this.renderPaymentMethodsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalPaymentMethods = false;

      const currentDate = new Date().getTime();
      this.paymentMethodToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.paymentMethodToAdd);
      console.log(jsonData);
      
      const res = await PaymentMethodService.addPaymentMethod(jsonData);

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
    this.renderPaymentMethodsTable();
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
