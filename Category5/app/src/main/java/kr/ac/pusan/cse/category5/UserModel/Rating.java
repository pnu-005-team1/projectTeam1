package kr.ac.pusan.cse.category5.UserModel;

public class Rating {
    private String userName;
    private String restaurantId;
    private String rateValue;
    private String comment;

    public Rating() {
    }

    public Rating(String userName, String restaurantId, String rateValue, String comment) {
        this.userName = userName;
        this.restaurantId = restaurantId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
       this.userName = userName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
