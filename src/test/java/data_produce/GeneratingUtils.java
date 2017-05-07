package data_produce;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
   public static double generateDoubleInRange(double limit) {
      BigDecimal tmp = new BigDecimal(rand.nextDouble() * limit);
      tmp = tmp.setScale(2, RoundingMode.HALF_UP);
      return tmp.doubleValue() + 0.1;
   }
}
