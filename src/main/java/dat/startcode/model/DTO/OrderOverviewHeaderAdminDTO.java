package dat.startcode.model.DTO;

import java.util.Date;
import java.util.List;

public class OrderOverviewHeaderAdminDTO
{
 private int orderId;
 private String costumerUsername;
 private int totalsum;
 private boolean status;
 private Date date;
 private List<OrderItemOverviewAdminDTO> orderItem;

 public OrderOverviewHeaderAdminDTO(int orderId, String costumerUsername, int totalsum, boolean status, Date date, List<OrderItemOverviewAdminDTO> orderItem) {
  this.orderId = orderId;
  this.costumerUsername = costumerUsername;
  this.totalsum = totalsum;
  this.status = status;
  this.date = date;
  this.orderItem = orderItem;
 }

 public int getOrderId() {
  return orderId;
 }

 public String getCostumerUsername()
 {
  return costumerUsername;
 }

 public void setCostumerUsername(String costumerUsername)
 {
  this.costumerUsername = costumerUsername;
 }

 public int getTotalsum()
 {
  return totalsum;
 }

 public void setTotalsum(int totalsum)
 {
  this.totalsum = totalsum;
 }

 public boolean isStatus() {
  return status;
 }

 public void setStatus(boolean status) {
  this.status = status;
 }

 public Date getDate()
 {
  return date;
 }

 public void setDate(Date date)
 {
  this.date = date;
 }

 public void setOrderId(int orderId) {
  this.orderId = orderId;
 }

 public List<OrderItemOverviewAdminDTO> getOrderItem() {
  return orderItem;
 }

 public void setOrderItem(List<OrderItemOverviewAdminDTO> orderItem) {
  this.orderItem = orderItem;
 }

 @Override
 public String toString() {
  return "OrderOverviewHeaderAdminDTO{" +
          "orderId=" + orderId +
          ", costumerUsername='" + costumerUsername + '\'' +
          ", totalsum=" + totalsum +
          ", status=" + status +
          ", date=" + date +
          ", orderItem=" + orderItem +
          '}';
 }
}
