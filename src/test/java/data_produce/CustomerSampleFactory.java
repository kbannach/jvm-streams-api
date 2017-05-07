package data_produce;

import java.math.BigDecimal;

public enum CustomerSampleFactory {
   ;

   public static ICustomization getSample(int id, int productsCount, double totalPrize) {
      return new ProductsCustomization(id, productsCount, new BigDecimal(totalPrize));
   }

   public static ICustomization getSample(int id, CustomerFunctions setter, Object arg) {
      return new PropertyCustomization(id, setter.getFunction(), arg);
   }
}
