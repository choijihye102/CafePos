package Product_v2;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
//성공 - 비인증결제 : 나이스
public class IamportAPI3 {
	 public static void main(String[] args) {
		 
			/*    String merchantUid2 = "store-e5914df3-46b4-406e-8e4a-6354f508fc34";
		        String merchantUid = "nice_v2.iamport01m"; // 상점 고유 번호
		        int amount = 200; // 결제 금액
		        String cardNumber = "5188310032356509"; // 카드 번호
		        String expiry = "2027-07"; // 카드 유효기간
		        String birth = "921023"; // 카드 유효기간
		        String no = "03"; // 
		        String accessToken = "8d47a89b65e27c9bdf6a11eae3ba70daf8178f85"; // 발급받은 Bearer 토큰

		        try {
		            // HTTP POST 요청
		            HttpResponse<JsonNode> response = Unirest.post( "https://api.iamport.kr/subscribe/payments/onetime")
		                    .header("Content-Type", "application/json")
		                    .header("Authorization", "Bearer " + accessToken)
		                    .body(new JSONObject()
		                           
		                            .put("card_number", cardNumber)
		                            .put("expiry", expiry)
		                            .put("birth", birth)
		                            .put("amount", amount)
		                            .put("merchant_uid", merchantUid2)
		                            .put("pg", merchantUid)
		                    
		                            .put("pwd_2digit", no)
		                            .put("name", "order_one")
		                            .put("buyer_name", "user_one")
		                            .put("buyer_email", "chlwlgp22@naver.com")
		                            .put("pay_method ", "card")
		                      
		                            .put("channelKey", "channel-key-5920898c-eb5a-48eb-8627-11baac145cf7")
		     
		                            .toString())
		                    .asJson();

		            // 응답 처리
		            int statusCode = response.getStatus();
		            System.out.println("Response Code: " + statusCode);

		            // JSON 응답 출력
		            if (response.getBody() != null) {
		                kong.unirest.json.JSONObject jsonResponse = response.getBody().getObject();
		                System.out.println("Response Body: " + jsonResponse.toString(4));

		                // 응답 데이터 파싱
		                int code = jsonResponse.getInt("code");
		                String message = jsonResponse.optString("message", "No message provided");
		                System.out.println("Response Code: " + code);
		                System.out.println("Response Message: " + message);

		                // 성공 응답 데이터 출력
		                kong.unirest.json.JSONObject responseData = jsonResponse.optJSONObject("response");
		                if (responseData != null) {
		                    String impUid = responseData.optString("imp_uid");
		                    String merchantUidResponse = responseData.optString("merchant_uid");
		                    System.out.println("imp_uid: " + impUid);
		                    System.out.println("merchant_uid: " + merchantUidResponse);
		                } else {
		                    System.out.println("No response data available.");
		                }
		            } else {
		                System.out.println("Empty response body.");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		    
		    }*/
	 }
	 public Integer PayCard( int amount, String cardNumber,  String expiry,  String birth, String no) {
		   String merchantUid2 = "store-e5914df3-46b4-406e-8e4a-6354f508fc34";
	       String merchantUid = "nice_v2.iamport01m"; // 상점 고유 번호
	    //    int amount = 200; // 결제 금액
	    //    String cardNumber = "5188310032356509"; // 카드 번호
	    //    String expiry = "2027-07"; // 카드 유효기간
	    //    String birth = "921023"; // 카드 유효기간
	    //   String no = "03"; // 
	        String accessToken = new IamportAPI().getAccessK(); // 발급받은 Bearer 토큰
	        
	        // 결과 받기 
	        int statusCode  =0;;
	        
	        try {
	            // HTTP POST 요청
	            HttpResponse<JsonNode> response = Unirest.post( "https://api.iamport.kr/subscribe/payments/onetime")
	                    .header("Content-Type", "application/json")
	                    .header("Authorization", "Bearer " + accessToken)
	                    .body(new JSONObject()
	                           
	                            .put("card_number", cardNumber)
	                            .put("expiry", expiry)
	                            .put("birth", birth)
	                            .put("amount", amount)
	                            .put("merchant_uid", merchantUid2)
	                            .put("pg", merchantUid)
	                    
	                            .put("pwd_2digit", no)
	                            .put("name", "order_one")
	                            .put("buyer_name", "user_one")
	                            .put("buyer_email", "chlwlgp22@naver.com")
	                            .put("pay_method ", "card")
	                      
	                            .put("channelKey", "channel-key-5920898c-eb5a-48eb-8627-11baac145cf7")
	     
	                            .toString())
	                    .asJson();

	            // 응답 처리
	            statusCode = response.getStatus();
	            System.out.println("Response Code: " + statusCode);

	            // JSON 응답 출력
	            if (response.getBody() != null) {
	                kong.unirest.json.JSONObject jsonResponse = response.getBody().getObject();
	                System.out.println("Response Body: " + jsonResponse.toString(4));

	                // 응답 데이터 파싱
	                int code = jsonResponse.getInt("code");
	                String message = jsonResponse.optString("message", "No message provided");
	                System.out.println("Response Code: " + code);
	                System.out.println("Response Message: " + message);

	                // 성공 응답 데이터 출력
	                kong.unirest.json.JSONObject responseData = jsonResponse.optJSONObject("response");
	                if (responseData != null) {
	                    String impUid = responseData.optString("imp_uid");
	                    String merchantUidResponse = responseData.optString("merchant_uid");
	                    System.out.println("imp_uid: " + impUid);
	                    System.out.println("merchant_uid: " + merchantUidResponse);
	                    return 0;
	                } else {
	                	
	                    System.out.println("No response data available.");
	                    return 1;
	                }
	            } else {
	                System.out.println("Empty response body.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return null;
	        
	     
	    }
	}