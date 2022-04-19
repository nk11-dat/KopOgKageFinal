package dat.startcode.model.DTO;

public class UserIdAndTotalSumDTO {
    private int userId;
    private int totalSum;

    public UserIdAndTotalSumDTO(int userId, int totalSum) {
        this.userId = userId;
        this.totalSum = totalSum;
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalSum() {
        return totalSum;
    }
}
