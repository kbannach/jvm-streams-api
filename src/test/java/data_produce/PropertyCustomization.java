package data_produce;

import java.util.function.BiConsumer;
import entities.Customer;

/**
 * used to customize some of the test data
 */
public class PropertyCustomization implements ICustomization {

   private int                          id;
   private BiConsumer<Customer, Object> fun;
   private Object                       arg;

   /**
    * @param id
    *        used to group multiple changes to certain objects
    * @param fun
    *        function to call (invocation target is randomly picked from the
    *        test data)
    * @param arg
    *        argument of {@code fun}
    */
   public PropertyCustomization(int id, BiConsumer<Customer, Object> fun, Object arg) {
      this.id = id;
      this.fun = fun;
      this.arg = arg;
   }

   @Override
   public Customer build(Customer cus) {
      cus.setId(id);
      fun.accept(cus, arg);
      return cus;
   }

   @Override
   public int getId() {
      return id;
   }
}
