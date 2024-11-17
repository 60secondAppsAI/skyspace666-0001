import http from "../http-common"; 

class LoyaltyMemberService {
  getAllLoyaltyMembers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/loyaltyMember/loyaltyMembers`, searchDTO);
  }

  get(loyaltyMemberId) {
    return this.getRequest(`/loyaltyMember/${loyaltyMemberId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/loyaltyMember?field=${matchData}`, null);
  }

  addLoyaltyMember(data) {
    return http.post("/loyaltyMember/addLoyaltyMember", data);
  }

  update(data) {
  	return http.post("/loyaltyMember/updateLoyaltyMember", data);
  }
  
  uploadImage(data,loyaltyMemberId) {
  	return http.postForm("/loyaltyMember/uploadImage/"+loyaltyMemberId, data);
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

export default new LoyaltyMemberService();
