import http from "../http-common"; 

class TravelInsuranceService {
  getAllTravelInsurances(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/travelInsurance/travelInsurances`, searchDTO);
  }

  get(travelInsuranceId) {
    return this.getRequest(`/travelInsurance/${travelInsuranceId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/travelInsurance?field=${matchData}`, null);
  }

  addTravelInsurance(data) {
    return http.post("/travelInsurance/addTravelInsurance", data);
  }

  update(data) {
  	return http.post("/travelInsurance/updateTravelInsurance", data);
  }
  
  uploadImage(data,travelInsuranceId) {
  	return http.postForm("/travelInsurance/uploadImage/"+travelInsuranceId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new TravelInsuranceService();
