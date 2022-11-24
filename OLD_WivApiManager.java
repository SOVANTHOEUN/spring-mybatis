package biztrip.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;
import jex.log.JexLogFactory;
import jex.log.JexLogger;

public class WivApiManager {
	private static final JexLogger LOG = JexLogFactory.getLogger(WivApiManager.class);
	
	String ERR_CODE	= null;
	String ERR_MSG	= null;
	public WivApiManager() {}
	
	public Map<String, String> call_WivPropertyEnalbedIds(){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		
		String targetUrl  = "/properties/enabledIds";
		try{
			result = wivApi.sendWivApi(targetUrl, "GET", "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public Map<String, String> call_WivPropertyContents(String propertyId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String strPropId  = "";
		
		if(propertyId != null && !propertyId.equals("")){
			strPropId = propertyId;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Id is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties/"+strPropId;
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}	
	public Map<String, String> call_WivPropertyPagination(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String pageParam  = "";
		String sizeParam  = "";
		
		try {
			if(!paramObj.isNull("page") && !paramObj.get("page").equals("")) {
				pageParam += "page="+Integer.parseInt(paramObj.get("page").toString());
			}else {
				pageParam += "page="+0;
			}
			
			if(!paramObj.isNull("size") && !paramObj.get("size").equals("")) {
				sizeParam = "size="+Integer.parseInt(paramObj.get("size").toString());
			}else {
				sizeParam = "size="+0;
			}
			
			if(nullCheck) {
				ERR_CODE = "";
				ERR_MSG	 = "Property Id is required.";
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/properties?"+pageParam +"&"+ sizeParam;
				try{
					result = wivApi.sendWivApi(targetUrl, "GET", "");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public Map<String, String> call_WivAvailabilityRooms(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = "";
		
		String propertyId		= "";
		String checkInParam		= "";
		String checkOutParam	= "";
		String adultParam		= "";
		String childParam		= "";
		
		try {
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				errMsg	= "checkIn param is required.";
				errCode = "9990";
				nullCheck = true;
			}else {
				checkInParam = "&checkIn="+paramObj.get("checkIn");
			}
			
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				errMsg	= "checkOut param is required.";
				errCode = "9991";
				nullCheck = true;
			}else {
				checkOutParam = "&checkOut="+paramObj.get("checkOut");
			}
			
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				errMsg	= "propertyId param is required.";
				errCode = "9992";
				nullCheck = true;
			}else {
				propertyId = paramObj.get("propertyId").toString();
			}
			
			if(!paramObj.isNull("adult") && !paramObj.get("adult").equals("")) {
				adultParam = "adult="+paramObj.get("adult");
			}else {
				adultParam = "";
			}
			
			if(!paramObj.isNull("child") && !paramObj.get("child").equals("")) {
				childParam = "&child="+paramObj.get("child");
			}else {
				childParam = "";
			}
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/properties/"+propertyId+"/roomtypes?" + adultParam + checkInParam + checkOutParam + childParam;
				try{
					result = wivApi.sendWivApi(targetUrl, "GET", "");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public Map<String, String> getAllRoomTypeId(String propertyId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String strPropId  = "";
		
		if(propertyId != null && !propertyId.equals("")){
			strPropId = propertyId;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Id is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties/"+strPropId+"/roomtypes/all";
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	public Map<String, String> call_WivAvailabilityRoomsTypeId(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = "";
		 
		String adultsParam		= ""; 
		String checkInDateParam	= "";
		String checkOutDateParam= ""; 
		String childrenParam	= "";
		String propertyId			= "";
		String rateTypeId		= "";
		String roomTypeId		= "";
		
		try {
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				errMsg	= "checkInDate param is required.";
				errCode = "9990";
				nullCheck = true;
			}else {
				checkInDateParam = paramObj.get("checkIn").toString();
			}
			
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				errMsg	= "checkOutDate param is required.";
				errCode = "9991";
				nullCheck = true;
			}else {
				checkOutDateParam = paramObj.get("checkOut").toString();
			}
			
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				errMsg	= "propertyId param is required.";
				errCode = "9992";
				nullCheck = true;
			}else {
				propertyId		 = paramObj.get("propertyId").toString(); 
			}
			
			if(paramObj.isNull("rateTypeId") || paramObj.get("rateTypeId").equals("")) {
				errMsg	= "rateTypeId param is required.";
				errCode = "9993";
				nullCheck = true;
			}else {
				rateTypeId = paramObj.get("rateTypeId").toString();
			}
			
			if(paramObj.isNull("roomTypeId") || paramObj.get("roomTypeId").equals("")) {
				errMsg	= "roomTypeId param is required.";
				errCode = "9994";
				nullCheck = true;
			}else {
				roomTypeId = paramObj.get("roomTypeId").toString();
			}
			
			if(paramObj.isNull("adult") || paramObj.get("adult").equals("")) {
				errMsg	= "adult param is required.";
				errCode = "9995";
				nullCheck = true;
			}else {
				adultsParam = paramObj.get("adult").toString();
			}
			
			if(paramObj.isNull("child") || paramObj.get("child").equals("")) {
				errMsg	= "child param is required.";
				errCode = "9996";
				nullCheck = true;
			}else {
				childrenParam = paramObj.get("child").toString();
			} 
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/properties/"+propertyId+"/roomtypes/"+ roomTypeId + "?adult="+adultsParam+"&checkIn="+ checkInDateParam +"&checkOut="+ checkOutDateParam +"&child="+ childrenParam +"&rateTypeId="+ rateTypeId;
				try{
					result = wivApi.sendWivApi(targetUrl, "GET", "");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> call_WivPropertyRoomsTypeId(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = "";
		 
		String adultsParam		= "";
		String checkInDateParam	= "";
		String checkOutDateParam= "";
		String childrenParam	= "";
		String propertyId		= "";
		String rateTypeId		= "";
		String roomTypeId		= "";
		
		try {
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				errMsg	= "checkInDate param is required.";
				errCode = "9990";
				nullCheck = true;
			}else {
				checkInDateParam = paramObj.get("checkIn").toString();
			}
			
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				errMsg	= "checkOutDate param is required.";
				errCode = "9991";
				nullCheck = true;
			}else {
				checkOutDateParam = paramObj.get("checkOut").toString();
			}
			
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				errMsg	= "propertyId param is required.";
				errCode = "9992";
				nullCheck = true;
			}else {
				propertyId		 = paramObj.get("propertyId").toString(); 
			}
			
			if(paramObj.isNull("rateTypeId") || paramObj.get("rateTypeId").equals("")) {
				errMsg	= "rateTypeId param is required.";
				errCode = "9993";
				nullCheck = true;
			}else {
				rateTypeId = paramObj.get("rateTypeId").toString();
			}
			
			if(paramObj.isNull("roomTypeId") || paramObj.get("roomTypeId").equals("")) {
				errMsg	= "roomTypeId param is required.";
				errCode = "9994";
				nullCheck = true;
			}else {
				roomTypeId = paramObj.get("roomTypeId").toString();
			}
			
			if(paramObj.isNull("adult") || paramObj.get("adult").equals("")) {
				errMsg	= "adult param is required.";
				errCode = "9995";
				nullCheck = true;
			}else {
				adultsParam = paramObj.get("adult").toString();
			}
			
			if(paramObj.isNull("child") || paramObj.get("child").equals("")) {
				errMsg	= "child param is required.";
				errCode = "9996";
				nullCheck = true;
			}else {
				childrenParam = paramObj.get("child").toString();
			} 
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else { 
				String targetUrl = "/properties/"+propertyId+"/roomtypes/"+ roomTypeId +"/ratetype/"+ rateTypeId+"/inventory?adult="+adultsParam+"&checkIn="+ checkInDateParam +"&checkOut="+ checkOutDateParam +"&child="+ childrenParam;
				try{
					result = wivApi.sendWivApi(targetUrl, "GET", "");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> getPropertyReview(String propertyId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String strPropId  = "";
		
		if(propertyId != null && !propertyId.equals("")){
			strPropId = propertyId;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Id is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties/reviews/byPropertyId?propertyIds="+strPropId;
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Map<String, String> submitReview(JSONArray parameter) throws Exception{
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = "";
		
		try { 
			if(parameter != null && parameter.length()>0) {
				 nullCheck = false;
			}else {
				errMsg 	= "propertyId param is required.";
				errCode = "9992";
				nullCheck = true;
			}
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/properties/reviews/byPropertyId";
				try{				
					result = wivApi.sendWivApi(targetUrl, "POST",parameter.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public Map<String, String> getLastReview(String last_date){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi		= new WivApiCall();
		String lastDateUpdate	= "";
		
		if(last_date != null || !last_date.equals("")){
			lastDateUpdate = last_date;
		}else{
			lastDateUpdate = "1970-01-01";
		}
		
		String targetUrl  = "/properties/reviews/changes?lastDate="+lastDateUpdate;
		try{
			result = wivApi.sendWivApi(targetUrl, "GET", "");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> getPropertyContent(Integer page,Integer size){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		Integer pageId  = 0;
		Integer sizeId  = 0;
		
		if(page != 0 || size!=0){
			pageId = page;
			sizeId = size;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Content is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties?page="+pageId+"&size="+sizeId;
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Map<String, String> getPropertyId(Integer propertyId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		Integer strPropId  = 0;
		
		if(propertyId != 0){
			strPropId = propertyId;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Id is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties/"+strPropId;
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	public Map<String, String> getPropertyChangeAll(String lastDate){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		String lastD  = "";
		if(lastDate != null && !lastDate.equals("")){
			lastD = lastDate;
		}else{
			lastD = "";
		}
		String targetUrl  = "/properties/changes/all?lastDate="+lastD;
		try{
			result = wivApi.sendWivApi(targetUrl, "GET", "");
		}catch(Exception e){
				e.printStackTrace();
		}
		return result;
	}
	public Map<String, String> getPropertyDetailByPropertyId(String propertyId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String strPropId  = "";
		
		if(propertyId != null && !propertyId.equals("")){
			strPropId = propertyId;
		}else{
			nullCheck = true;
		}
		
		if(nullCheck) {
			ERR_CODE = "";
			ERR_MSG	 = "Property Detail By PropertyId is required.";
			result.put("RESULT_CODE",	ERR_CODE);
			result.put("RESULT_MSG",	ERR_MSG);
		}else {
			String targetUrl  = "/properties/details/byPropertyIds?propertyIds="+strPropId;
			try{
				result = wivApi.sendWivApi(targetUrl, "GET", "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	public Map<String, String> getPropertyEnabledId(){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
			String targetUrl  = "/properties/enabledIds";
			try{
				result = wivApi.sendWivApi(targetUrl, "GET","");
			}catch(Exception e){
				e.printStackTrace();
			}
		return result;
	}
	public Map<String, String> wivGetReservation(String reservationId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try {   
			if(reservationId == null && reservationId.equals("")) {
				errMsg	= "reservationId param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/"+reservationId;
				try{ 
					result = wivApi.sendWivApi(targetUrl, "GET", reservationId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> wivGetReservationAndCancel(String reservationId){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try {   
			if(reservationId == null && reservationId.equals("")) {
				errMsg	= "reservationId param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/"+reservationId+"/cancelFee";
				try{ 
					result = wivApi.sendWivApi(targetUrl, "GET", reservationId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> wivCreateBooking(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try { 
			
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				errMsg	= "propertyId param is required.";
				errCode = "9989";
				nullCheck = true;
			}
			
			if(paramObj.isNull("orderNo") || paramObj.get("orderNo").equals("")) {
				errMsg	= "orderNo param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserName") || paramObj.get("reservationUserName").equals("")) {
				errMsg	= "reservationUserName param is required.";
				errCode = "9991";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserPhone") || paramObj.get("reservationUserPhone").equals("")) {
				errMsg	= "reservationUserPhone param is required.";
				errCode = "9992";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserEmail") || paramObj.get("reservationUserEmail").equals("")) {
				errMsg	= "reservationUserEmail param is required.";
				errCode = "9993";
				nullCheck = true;
			}
			
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				errMsg	= "checkIn param is required.";
				errCode = "9994";
				nullCheck = true;
			}
			
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				errMsg	= "checkOut param is required.";
				errCode = "9995";
				nullCheck = true;
			}
			
			if(paramObj.isNull("rateList") || paramObj.get("rateList").equals("")) {
				errMsg	= "rateList param is required.";
				errCode = "9996";
				nullCheck = true;
			} 
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/create";
				try{
					result = wivApi.sendWivApi(targetUrl, "POST", paramObj.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> wivCreateBookingDirect(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try { 
			
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				errMsg	= "propertyId param is required.";
				errCode = "9989";
				nullCheck = true;
			}
			
			if(paramObj.isNull("orderNo") || paramObj.get("orderNo").equals("")) {
				errMsg	= "orderNo param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserName") || paramObj.get("reservationUserName").equals("")) {
				errMsg	= "reservationUserName param is required.";
				errCode = "9991";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserPhone") || paramObj.get("reservationUserPhone").equals("")) {
				errMsg	= "reservationUserPhone param is required.";
				errCode = "9992";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationUserEmail") || paramObj.get("reservationUserEmail").equals("")) {
				errMsg	= "reservationUserEmail param is required.";
				errCode = "9993";
				nullCheck = true;
			}
			
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				errMsg	= "checkIn param is required.";
				errCode = "9994";
				nullCheck = true;
			}
			
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				errMsg	= "checkOut param is required.";
				errCode = "9995";
				nullCheck = true;
			}
			
			if(paramObj.isNull("rateList") || paramObj.get("rateList").equals("")) {
				errMsg	= "rateList param is required.";
				errCode = "9996";
				nullCheck = true;
			} 
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/create/direct";
				try{
					result = wivApi.sendWivApi(targetUrl, "POST", paramObj.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> wivCancelReservation(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try { 
			
			if(paramObj.isNull("contextId") || paramObj.get("contextId").equals("")) {
				errMsg	= "contextId param is required.";
				errCode = "9989";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationId") || paramObj.get("reservationId").equals("")) {
				errMsg	= "reservationId param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(paramObj.isNull("totalCancelFee") || paramObj.get("totalCancelFee").equals("")) {
				errMsg	= "totalCancelFee param is required.";
				errCode = "9991";
				nullCheck = true;
			}
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/cancel";
				try{
					result = wivApi.sendWivApi(targetUrl, "POST", paramObj.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, String> wivConfirmReservation(JSONObject paramObj){
		Map<String,String> result = new HashMap();
		WivApiCall wivApi = new WivApiCall();
		boolean nullCheck = false;
		String errMsg	  = "";
		String errCode	  = ""; 
		
		try { 
			
			if(paramObj.isNull("contextId") || paramObj.get("contextId").equals("")) {
				errMsg	= "contextId param is required.";
				errCode = "9989";
				nullCheck = true;
			}
			
			if(paramObj.isNull("reservationId") || paramObj.get("reservationId").equals("")) {
				errMsg	= "reservationId param is required.";
				errCode = "9990";
				nullCheck = true;
			}
			
			if(paramObj.isNull("totalPrice") || paramObj.get("totalPrice").equals("")) {
				errMsg	= "totalPrice param is required.";
				errCode = "9991";
				nullCheck = true;
			} 
			
			if(nullCheck) {
				ERR_CODE = errCode;
				ERR_MSG	 = errMsg;
				result.put("RESULT_CODE",	ERR_CODE);
				result.put("RESULT_MSG",	ERR_MSG);
			}else {
				String targetUrl  = "/reservation/confirm";
				try{
					result = wivApi.sendWivApi(targetUrl, "POST", paramObj.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
