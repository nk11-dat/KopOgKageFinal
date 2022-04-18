package dat.startcode.model.DTO;

import dat.startcode.model.entities.User;

import java.util.List;

public class UserOrdersDTO {
    User user;
    List<OrderOverviewHeaderAdminDTO> orderOverviewHeaderAdminDTO;

    public UserOrdersDTO(User user, List<OrderOverviewHeaderAdminDTO> orderOverviewHeaderAdminDTO) {
        this.user = user;
        this.orderOverviewHeaderAdminDTO = orderOverviewHeaderAdminDTO;
    }

    public User getUser() {
        return user;
    }

    public List<OrderOverviewHeaderAdminDTO> getOrderOverviewHeaderAdminDTO() {
        return orderOverviewHeaderAdminDTO;
    }

    @Override
    public String toString() {
        return "UserOrdersDTO{" +
                "user=" + user +
                ", orderOverviewHeaderAdminDTO=" + orderOverviewHeaderAdminDTO +
                '}';
    }
}
