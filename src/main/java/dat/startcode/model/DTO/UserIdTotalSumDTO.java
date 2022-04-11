package dat.startcode.model.DTO;

public class UserIdTotalSumDTO {
    private int userId;
    private int totalSum;

    public UserIdTotalSumDTO(int userId, int totalSum) {
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
