package data_produce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public enum GeneratingUtils {
   ;

   static {
      rand = new Random();
   }

   private static Random rand;

   /**
    * draws one random element from {@code l}
    */
   public static <T> T drawOne(List<T> l) {
      return l.remove(rand.nextInt(l.size()));
   }

   /**
    * @return random double between 0.1 (inclusive) and {@code limit}
    *         (exclusive)
    */
   public static BigDecimal generateDoubleInRange(BigDecimal totalPrice) {
      BigDecimal tmp = new BigDecimal(rand.nextDouble()).multiply(totalPrice);
      tmp = tmp.setScale(2, RoundingMode.HALF_UP);
      return tmp.add(new BigDecimal(0.1));
   }

   public static int getMax(Collection<Integer> col) {
      return col.stream().mapToInt((i) -> i).max().orElse(0);
   }
}
