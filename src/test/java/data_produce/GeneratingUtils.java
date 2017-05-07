package data_produce;

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
}
