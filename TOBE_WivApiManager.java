package biztrip.api;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;
import jex.log.JexLogFactory;
import jex.log.JexLogger;

/**
 * 여기어때 모든 Api 서비스관리 클래스
 */
public class WivApiManager {
	public static final JexLogger LOG = JexLogFactory.getLogger(WivApiManager.class);
	public static final String REQUIRED_DATA_ERROR     = "1001";
	public static final String UNDEFINE_ERROR          = "9999";
	public static final String REQUIRED_DATA_ERROR_MSG = "필수값이 누락되었습니다.";
	public static final String UNDEFINE_ERROR_MSG      = "처리 중 오류가 발생하였습니다. 잠시 후 이용하시기 바랍니다.";
	
	public WivApiManager() {  }
	
	/**
	 * @숙소정보API: 숙소 전체 조회 - 페이징 적용(최대 1000건)
	 * @param : 없음
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivPropertyPagination(){
		return call_WivPropertyPagination(null);
	}
	
	/**
	 * @숙소정보API: 숙소 전체 조회 - 페이징 적용(최대 1000건)
	 * @param : pagingObj
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivPropertyPagination(JSONObject pagingObj){
		Map<String,String> responseObject = new HashMap();
		StringBuilder reqParam = new StringBuilder();
		String concatParam     = "&";
		
		try {
			if(pagingObj != null) {
				// page 옵션
				if(!pagingObj.isNull("page") && !pagingObj.get("page").equals("")) {
					reqParam.append("page="+Integer.parseInt(pagingObj.get("page").toString()));
				} else {
					reqParam.append("page="+0);
				}
				
				// size 옵션
				if(!pagingObj.isNull("size") && !pagingObj.get("size").equals("")) {
					reqParam.append(concatParam);
					reqParam.append("size="+Integer.parseInt(pagingObj.get("size").toString()));
				} else {
					reqParam.append("size="+0);
				}
			}
			
			WivApiCall wivApi = new WivApiCall();
			String pathParam  = "/properties?"+reqParam;
			responseObject    = wivApi.callApi(pathParam, "GET", "");
		} catch (Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @숙소정보API: 숙소 상세정보 조회
	 * @param : propertyId
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivPropertyContents(String propertyId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String strPropId    = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try{
			// propertyId 검증
			if(propertyId != null && !propertyId.equals("")){
				strPropId = propertyId;
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+strPropId;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @숙소정보API: 변경된 숙소 조회
	 * @param : lastDate
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> getPropertyChangeAll(String lastDate){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String dateParam    = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try{
			// lastDate 검증
			if(lastDate != null && !lastDate.equals("")){
				dateParam = lastDate;
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "lastDate(검색 기준일자 2019-03-01 12:00:00) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck){
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/changes/all?lastDate="+dateParam;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @숙소정보API: 숙소 상셍정보 조회 - 여러건 최대 200건
	 * @param : propertyId
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> getPropertyDetailByPropertyId(String propertyId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String strPropId    = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try{
			// propertyId 검증
			if(propertyId != null && !propertyId.equals("")){
				strPropId = propertyId;
			}else{
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/details/byPropertyIds?propertyIds="+strPropId;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @숙소정보API: Enabled 상태인 전체 숙소 id 리스트 조회
	 * @param : 없음
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivPropertyEnabledIds(){
		Map<String,String> responseObject = new HashMap();
		
		try{
			WivApiCall wivApi = new WivApiCall();
			String pathParam  = "/properties/enabledIds";
			responseObject    = wivApi.callApi(pathParam, "GET", "");
		} catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약가능객실API: 예약가능객실 조회
	 * @param : paramObj
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivAvailabilityRooms(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		StringBuilder reqParam = new StringBuilder();
		boolean nullCheck   = false;
		String concatParam  = "&";
		String propertyId   = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(체크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append("checkIn=" +paramObj.get("checkIn"));
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(제크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append("checkOut=" +paramObj.get("checkOut"));
			}
			
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				propertyId = paramObj.get("propertyId").toString();
			}
			
			// adult 검증
			if(!paramObj.isNull("adult") && !paramObj.get("adult").equals("")) {
				reqParam.append(concatParam);
				reqParam.append("adult=" +paramObj.get("adult"));
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "adult(성인 수) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// child 옵션
			if(!paramObj.isNull("child") && !paramObj.get("child").equals("")) {
				reqParam.append(concatParam);
				reqParam.append("child=" +paramObj.get("child"));
			} else {
				reqParam.append("");
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+propertyId+"/roomtypes?" + reqParam;
				responseObject    = wivApi.callApi(pathParam, "POST", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch (Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약가능객실API: 객실 상세 조회
	 * @param : paramObj
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivAvailabilityRoomsTypeId(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		StringBuilder reqParam = new StringBuilder();
		boolean nullCheck   = false;
		String concatParam  = "&";
		String propertyId   = "";
		String roomTypeId   = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(체크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(paramObj.get("checkIn").toString());
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(제크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("checkOut").toString());
			}
			
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				propertyId = paramObj.get("propertyId").toString();
			}
			
			// roomTypeId 검증
			if(paramObj.isNull("roomTypeId") || paramObj.get("roomTypeId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "roomTypeId(객실ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				roomTypeId = paramObj.get("roomTypeId").toString();
			}
			
			// rateTypeId 검증
			if(paramObj.isNull("rateTypeId") || paramObj.get("rateTypeId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "rateTypeId(객실 요금제 ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("rateTypeId").toString());
			}
			
			// adult 검증
			if(paramObj.isNull("adult") || paramObj.get("adult").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "adult(성인 수) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("adult").toString());
			}
			
			// child 옵션
			if(paramObj.isNull("child") || paramObj.get("child").equals("")) {
				reqParam.append("");
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("child").toString());
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+propertyId+"/roomtypes/"+roomTypeId+"?"+ reqParam;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약가능객실API: 전체 객실 조회
	 * @param : propertyId
	 * @method: GET
	 * @return: responseObject: 응답 오브젝트
	 */
	public Map<String, String> getAllRoomTypeId(String propertyId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String strPropId    = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try{
			// propertyId 검증
			if(propertyId != null && !propertyId.equals("")){
				strPropId = propertyId;
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+strPropId+"/roomtypes/all";
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약가능객실API: 예약가능객실 조회(with 상세)
	 * @param : paramObj
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivAvailabilityRoomsWtihDetails(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		StringBuilder reqParam = new StringBuilder();
		boolean nullCheck   = false;
		String concatParam  = "&";
		String propertyId   = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(체크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append("checkIn=" +paramObj.get("checkIn"));
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(제크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append("checkOut=" +paramObj.get("checkOut"));
			}
			
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				propertyId = paramObj.get("propertyId").toString();
			}
			
			// adult 검증
			if(!paramObj.isNull("adult") && !paramObj.get("adult").equals("")) {
				reqParam.append(concatParam);
				reqParam.append("adult=" +paramObj.get("adult"));
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "adult(성인 수) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// child 옵션
			if(!paramObj.isNull("child") && !paramObj.get("child").equals("")) {
				reqParam.append(concatParam);
				reqParam.append("child=" +paramObj.get("child"));
			} else {
				reqParam.append("");
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+propertyId+"/roomtypesWithDetail?" + reqParam;
				responseObject    = wivApi.callApi(pathParam, "POST", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch (Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	
	
	/**
	 * @예약정보API: 예약 조회
	 * @param : reservationId 예약번호
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivGetReservation(String reservationId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// reservationId 검증
			if(reservationId == null && reservationId.equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationId(예약번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/"+reservationId;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약정보API: 취소 수수료 조회(cancelFee 조회)
	 * @param : reservationId 예약번호
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivGetReservationAndCancel(String reservationId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// reservationId 검증
			if(reservationId == null && reservationId.equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationId(예약번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/"+reservationId+"/cancelFee";
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
			
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약정보API: 예약취소
	 * @method: POST
	 * @param : paramObj
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivCancelReservation(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// contextId 검증
			if(paramObj.isNull("contextId") || paramObj.get("contextId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "contextId(기본 예약 Unique ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationId 검증
			if(paramObj.isNull("reservationId") || paramObj.get("reservationId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationId(예약번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// totalCancelFee 검증
			if(paramObj.isNull("totalCancelFee") || paramObj.get("totalCancelFee").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "totalCancelFee(총 취소 수수료 금액) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/cancel";
				responseObject    = wivApi.callApi(pathParam, "POST", paramObj.toString());
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약정보API: 가예약 확정
	 * @method: POST
	 * @param : paramObj
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivConfirmReservation(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// contextId 검증
			if(paramObj.isNull("contextId") || paramObj.get("contextId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "contextId(기본 예약 Unique ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationId 검증
			if(paramObj.isNull("reservationId") || paramObj.get("reservationId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationId(예약번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// totalPrice 검증
			if(paramObj.isNull("totalPrice") || paramObj.get("totalPrice").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "totalPrice(지물가격) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/confirm";
				responseObject    = wivApi.callApi(pathParam, "POST", paramObj.toString());
			}else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약정보API: 가예약 생성 (쓰지 않을 것 같다)
	 * @method: POST
	 * @param : paramObj
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivCreateBooking(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(여기어때 상품 번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// orderNo 검증
			if(paramObj.isNull("orderNo") || paramObj.get("orderNo").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "orderNo(채절사 주문번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserName 검증
			if(paramObj.isNull("reservationUserName") || paramObj.get("reservationUserName").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserName(예약자 명) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserPhone 검증
			if(paramObj.isNull("reservationUserPhone") || paramObj.get("reservationUserPhone").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserPhone(예약자 연락처: 010-1234-1234) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserEmail 검증
			if(paramObj.isNull("reservationUserEmail") || paramObj.get("reservationUserEmail").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserEmail(예약자 이메일: test@gmail.com) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(채크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(채크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// rateList 검증
			if(paramObj.isNull("rateList") || paramObj.get("rateList").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "rateList(예약객실정보) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/create";
				responseObject    = wivApi.callApi(pathParam, "POST", paramObj.toString());
			}else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
			
		}catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @예약정보API: 바로예약(즉시예약) - 가장 중요한 API
	 * @method: POST
	 * @param : paramObj
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> wivCreateBookingDirect(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(여기어때 상품 번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// orderNo 검증
			if(paramObj.isNull("orderNo") || paramObj.get("orderNo").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "orderNo(채널사 주문번호) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserName 검증
			if(paramObj.isNull("reservationUserName") || paramObj.get("reservationUserName").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserName(예약자 명) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserPhone 검증
			if(paramObj.isNull("reservationUserPhone") || paramObj.get("reservationUserPhone").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserPhone(예약자 연락처: 010-1234-1234) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// reservationUserEmail 검증
			if(paramObj.isNull("reservationUserEmail") || paramObj.get("reservationUserEmail").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "reservationUserEmail(예약자 이메일: test@gmail.com) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(채크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(채크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			// rateList 검증
			if(paramObj.isNull("rateList") || paramObj.get("rateList").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "rateList(예약객실정보) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/reservation/create/direct";
				responseObject    = wivApi.callApi(pathParam, "POST", paramObj.toString());
			}else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @재고API: 개실 상세 인벤토리 조회
	 * @param : paramObj
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> call_WivPropertyRoomsTypeId(JSONObject paramObj){
		Map<String,String> responseObject = new HashMap();
		StringBuilder reqParam = new StringBuilder();
		boolean nullCheck   = false;
		String concatParam  = "&";
		String propertyId   = "";
		String roomTypeId   = "";
		String rateTypeId   = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try {
			// checkIn 검증
			if(paramObj.isNull("checkIn") || paramObj.get("checkIn").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkIn(체크인 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(paramObj.get("checkIn").toString());
			}
			
			// checkOut 검증
			if(paramObj.isNull("checkOut") || paramObj.get("checkOut").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "checkOut(제크아웃 일자: yyyy-MM-dd) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("checkOut").toString());
			}
			
			// propertyId 검증
			if(paramObj.isNull("propertyId") || paramObj.get("propertyId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				propertyId = paramObj.get("propertyId").toString();
			}
			
			// roomTypeId 검증
			if(paramObj.isNull("roomTypeId") || paramObj.get("roomTypeId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "roomTypeId(객실ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				roomTypeId = paramObj.get("roomTypeId").toString();
			}
			
			// rateTypeId 검증
			if(paramObj.isNull("rateTypeId") || paramObj.get("rateTypeId").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "rateTypeId(객실 요금제 ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				rateTypeId = paramObj.get("rateTypeId").toString();
			}
			
			// adult 검증
			if(paramObj.isNull("adult") || paramObj.get("adult").equals("")) {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "adult(성인 수) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("adult").toString());
			}
			
			// child 옵션
			if(paramObj.isNull("child") || paramObj.get("child").equals("")) {
				reqParam.append("");
			} else {
				reqParam.append(concatParam);
				reqParam.append(paramObj.get("child").toString());
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/"+propertyId+"/roomtypes/"+roomTypeId+"/ratetype/"+rateTypeId+"/inventory?"+reqParam;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		} catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	// 호텔 리뷰 API 부분
	/**
	 * @호텔리뷰API: 숙소 리뷰정보 조회
	 * @param : propertyId
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> getPropertyReview(String propertyId){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String strPropId    = "";
		String responseCode = "";
		String responseMsg  = "";
		
		try{
			// propertyId 검증
			if(propertyId != null && !propertyId.equals("")){
				strPropId = propertyId;
			}else{
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/reviews/byPropertyId?propertyIds="+strPropId;
				responseObject    = wivApi.callApi(pathParam, "GET", "");
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
			
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @호텔리뷰API: 숙소 리뷰정보 등록
	 * @method: POST
	 * @param : parameter
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> submitReview(JSONArray parameter){
		Map<String,String> responseObject = new HashMap();
		boolean nullCheck   = false;
		String responseCode = "";
		String responseMsg  = "";
		
		try { 
			// propertyId 배열 검증
			if(parameter != null && parameter.length()>0) {
				 nullCheck = false;
			} else {
				responseCode = REQUIRED_DATA_ERROR;
				responseMsg  = "propertyId(숙소ID) " +REQUIRED_DATA_ERROR_MSG;
				nullCheck    = true;
			}
			
			if(!nullCheck) {
				WivApiCall wivApi = new WivApiCall();
				String pathParam  = "/properties/reviews/byPropertyId";
				responseObject    = wivApi.callApi(pathParam, "POST", parameter.toString());
			} else {
				responseObject.put("RESULT_CODE", responseCode);
				responseObject.put("RESULT_MSG" , responseMsg);
			}
		}catch(Exception e) {
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	/**
	 * @호텔리뷰API: 변경된 숙소리뷰 조회
	 * @param : last_date
	 * @method: GET
	 * @return: responseObject 응답 오브젝트
	 */
	public Map<String, String> getLastReview(String last_date){
		Map<String,String> responseObject = new HashMap();
		String lastDateUpdate = "";
		
		try{
			// lastDate 옵션
			if(last_date != null || !last_date.equals("")){
				lastDateUpdate = last_date;
			} else {
				lastDateUpdate = "1970-01-01";
			}
			
			WivApiCall wivApi = new WivApiCall();
			String pathParam  = "/properties/reviews/changes?lastDate="+lastDateUpdate;
			responseObject    = wivApi.callApi(pathParam, "GET", "");
		}catch(Exception e){
			responseObject.put("RESULT_CODE", UNDEFINE_ERROR);
			responseObject.put("RESULT_MSG" , UNDEFINE_ERROR_MSG);
			e.printStackTrace();
		}
		
		return responseObject;
	}
	
	
}
