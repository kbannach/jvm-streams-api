package data_produce;

import java.util.function.BiConsumer;
import entities.Customer;

public enum CustomerFunctions {
      setPhoneNo((obj, param) -> obj.setPhoneNo((String) param)),
      setEmail((obj, param) -> obj.setEmail((String) param)),
      setName((obj, param) -> obj.setName((String) param)),
      setTaxId((obj, param) -> obj.setTaxId((String) param));

   private CustomerFunctions(BiConsumer<Customer, Object> function) {
      this.function = function;
   }

   private final BiConsumer<Customer, Object> function;

   public BiConsumer<Customer, Object> getFunction() {
      return function;
   }
}
