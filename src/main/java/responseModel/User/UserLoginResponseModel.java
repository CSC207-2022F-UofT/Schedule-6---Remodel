package responseModel.User;

public class UserLoginResponseModel {

    private String message;

    public UserLoginResponseModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
