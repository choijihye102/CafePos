package Product_v2;
import java.net.CacheResponse;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
//성공 - 토큰받기
public class IamportAPI {
	public String getAccessK() {
		String accessToken= null;
		
		  // Step 1: Get Access Token
        String impKey = "3124425248517814";  // Replace with your imp_key
        String impSecret = "hKeQqCaMdovEnERlxQenYuggrdxk5MTg1zGhyfzGSYYXO3o723CydCk36eLegg1Eh5YH471mfDJUwXmX";  // Replace with your imp_secret
        
        HttpResponse<JsonNode> tokenResponse = Unirest.post("https://api.iamport.kr/users/getToken")
            .header("Content-Type", "application/json")
            .body("{\"imp_key\":\"" + impKey + "\",\"imp_secret\":\"" + impSecret + "\"}")
            .asJson();

        if (tokenResponse.getStatus() == 200) {
            accessToken = tokenResponse.getBody().getObject().getJSONObject("response").getString("access_token");
            System.out.println("Access Token: " + accessToken);
            
            // Step 2: Use Access Token to Get Certification Number
            String impUid = "your_imp_uid";  // Replace with the user's imp_uid or other identifier
            
            HttpResponse<JsonNode> certificationResponse = Unirest.get("https://api.iamport.kr/certifications/" + impUid)
                .header("Authorization", accessToken)
                .asJson();

            if (certificationResponse.getStatus() == 200) {
                System.out.println("Certification Info: " + certificationResponse.getBody().toString());
            } else {
                System.out.println("Failed to get certification info: " + certificationResponse.getBody().toString());
            }
        } else {
            System.out.println("Failed to get access token: " + tokenResponse.getBody().toString());
        }
        
		return accessToken;
	}
/*    public static void main(String[] args) {
        // Step 1: Get Access Token
        String impKey = "3124425248517814";  // Replace with your imp_key
        String impSecret = "hKeQqCaMdovEnERlxQenYuggrdxk5MTg1zGhyfzGSYYXO3o723CydCk36eLegg1Eh5YH471mfDJUwXmX";  // Replace with your imp_secret
        
        HttpResponse<JsonNode> tokenResponse = Unirest.post("https://api.iamport.kr/users/getToken")
            .header("Content-Type", "application/json")
            .body("{\"imp_key\":\"" + impKey + "\",\"imp_secret\":\"" + impSecret + "\"}")
            .asJson();

        if (tokenResponse.getStatus() == 200) {
            String accessToken = tokenResponse.getBody().getObject().getJSONObject("response").getString("access_token");
            System.out.println("Access Token: " + accessToken);
            
            // Step 2: Use Access Token to Get Certification Number
            String impUid = "your_imp_uid";  // Replace with the user's imp_uid or other identifier
            
            HttpResponse<JsonNode> certificationResponse = Unirest.get("https://api.iamport.kr/certifications/" + impUid)
                .header("Authorization", accessToken)
                .asJson();

            if (certificationResponse.getStatus() == 200) {
                System.out.println("Certification Info: " + certificationResponse.getBody().toString());
            } else {
                System.out.println("Failed to get certification info: " + certificationResponse.getBody().toString());
            }
        } else {
            System.out.println("Failed to get access token: " + tokenResponse.getBody().toString());
        }
    }*/
}
