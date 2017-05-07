package data_produce;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import entities.Customer;

public enum DataProducer {
   ;

   /**
    * generates {@code count} customers with no products
    */
   public static List<Customer> getTestData(int count) {
      return getTestData(count, false);
   }

   private static List<Customer> getTestData(int count, boolean internal) {
      return IntStream.rangeClosed(1, count).mapToObj((i) -> new Customer(internal ? -1 : i, "Customer" + i)).collect(Collectors.toList());
   }

   /**
    * @see PropertyCustomization
    */
   public static List<Customer> getTestData(int count, ICustomization... customization) {
      return processSamples(getTestData(count, true), Arrays.asList(customization));
   }

   private static List<Customer> processSamples(List<Customer> testData, List<ICustomization> list) {
      Map<Integer, Customer> map = new HashMap<>();
      Set<Customer> ret = new HashSet<>(testData.size());

      // perform customization on chosen objects
      list.stream().forEach(s -> {
         Customer cust = map.get(s.getId());
         cust = s.build(cust != null ? cust : GeneratingUtils.drawOne(testData));
         ret.add(cust);
         map.put(s.getId(), cust);
      });

      // assign ids to uncustomized objects
      int id = GeneratingUtils.getMax(map.keySet());
      for (Customer obj : testData) {
         obj.setId(++id);
      }

      // copy the rest of the data
      ret.addAll(testData);
      return Arrays.asList(ret.toArray(new Customer[]{}));
   }
}
