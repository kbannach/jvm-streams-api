package data_produce;

import java.util.function.BiConsumer;
import entities.Customer;

public enum CustomerMethodFactory {
   ;

   public static BiConsumer<Customer, Object> setPhoneNo() {
      return (obj, param) -> obj.setPhoneNo((String) param);
   }

   public static BiConsumer<Customer, Object> setEmail() {
      return (obj, param) -> obj.setEmail((String) param);
   }

   public static BiConsumer<Customer, Object> setName() {
      return (obj, param) -> obj.setName((String) param);
   }

   public static BiConsumer<Customer, Object> setTaxId() {
      return (obj, param) -> obj.setTaxId((String) param);
   }
}
