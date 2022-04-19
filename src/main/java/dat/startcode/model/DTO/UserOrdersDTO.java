package dat.startcode.model.DTO;

import dat.startcode.model.entities.User;

import java.util.List;

public class UserOrdersDTO {
    User user;
    List<OrderHeaderAdminDTO> orderHeaderAdminDTO;

    public UserOrdersDTO(User user, List<OrderHeaderAdminDTO> orderHeaderAdminDTO) {
        this.user = user;
        this.orderHeaderAdminDTO = orderHeaderAdminDTO;
    }

    public User getUser() {
        return user;
    }

    public List<OrderHeaderAdminDTO> getOrderOverviewHeaderAdminDTO() {
        return orderHeaderAdminDTO;
    }

    @Override
    public String toString() {
        return "UserOrdersDTO{" +
                "user=" + user +
                ", orderOverviewHeaderAdminDTO=" + orderHeaderAdminDTO +
                '}';
    }
}
