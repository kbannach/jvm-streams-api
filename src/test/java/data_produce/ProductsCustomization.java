package data_produce;

import entities.Customer;
import entities.Product;

public class ProductsCustomization implements ICustomization {

   private int    id;
   private int    productsCount;
   private double totalPrice;

   public ProductsCustomization(int id, int productsCount, double totalPrice) {
      this.id = id;
      this.productsCount = productsCount;
      this.totalPrice = totalPrice;
   }

   @Override
   public Customer build(Customer cus) {
      double limit = totalPrice, gen;
      for (int i = 1; i < productsCount; i++) {
         gen = GeneratingUtils.generateDoubleInRange(limit);
         cus.addProduct(new Product(i, buildProductName(cus.getName(), i), gen));
         totalPrice -= gen;
      }
      cus.addProduct(new Product(productsCount, buildProductName(cus.getName(), productsCount), totalPrice));
      return cus;
   }

   private String buildProductName(String cusName, int i) {
      return "C:" + cusName + ";Product" + i;
   }

   @Override
   public int getId() {
      return id;
   }
}
