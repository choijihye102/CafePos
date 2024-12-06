
package Product_v2;

public class UserDTO {
	
	    private static UserDTO instance;
	    private String loggedInUserName;

	    // 싱글톤 패턴
	    private UserDTO() {
	    }

	    public static UserDTO getInstance() {
	        if (instance == null) {
	            instance = new UserDTO();
	        }
	        return instance;
	    }

	    public String getLoggedInUserName() {
	        return loggedInUserName;
	    }

	    public void setLoggedInUserName(String loggedInUserName) {
	        this.loggedInUserName = loggedInUserName;
	    }
	}