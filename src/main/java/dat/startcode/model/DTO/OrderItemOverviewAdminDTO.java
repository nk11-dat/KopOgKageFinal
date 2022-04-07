package dat.startcode.model.DTO;

import java.util.Date;

public class OrderItemOverviewAdminDTO
{
 private int quantity;
 private String flavor;

 public OrderItemOverviewAdminDTO(int quantity, String flavor) {
  this.quantity = quantity;
  this.flavor = flavor;
 }

 public int getQuantity() {
  return quantity;
 }

 public void setQuantity(int quantity) {
  this.quantity = quantity;
 }

 public String getFlavor() {
  return flavor;
 }

 public void setFlavor(String flavor) {
  this.flavor = flavor;
 }

 @Override
 public String toString() {
  return "OrderItemOverviewIAdminDTO{" +
          "quantity=" + quantity +
          ", flavor='" + flavor + '\'' +
          '}';
 }
}
