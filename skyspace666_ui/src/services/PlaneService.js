import http from "../http-common"; 

class PlaneService {
  getAllPlanes(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/plane/planes`, searchDTO);
  }

  get(planeId) {
    return this.getRequest(`/plane/${planeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/plane?field=${matchData}`, null);
  }

  addPlane(data) {
    return http.post("/plane/addPlane", data);
  }

  update(data) {
  	return http.post("/plane/updatePlane", data);
  }
  
  uploadImage(data,planeId) {
  	return http.postForm("/plane/uploadImage/"+planeId, data);
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

export default new PlaneService();
