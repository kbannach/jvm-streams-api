package tests;

import java.util.function.BiConsumer;
import entities.Customer;

/**
 * used to customize some of the test data
 */
public class SampleCustomization {

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
   public SampleCustomization(int id, BiConsumer<Customer, Object> fun, Object arg) {
      this.id = id;
      this.fun = fun;
      this.arg = arg;
   }

   public Customer build(Customer cus) {
      fun.accept(cus, arg);
      return cus;
   }

   public int getId() {
      return id;
   }
}
