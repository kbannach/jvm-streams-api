package data_produce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import entities.Customer;
import entities.Product;

public class ProductsCustomization implements ICustomization {

   private int        id;
   private int        productsCount;
   private BigDecimal totalPrice;

   public ProductsCustomization(int id, int productsCount, BigDecimal totalPrice) {
      this.id = id;
      this.productsCount = productsCount;
      this.totalPrice = totalPrice;
   }

   @Override
   public Customer build(Customer cus) {
      cus.setId(id);
      BigDecimal gen;
      for (int i = 1; i < productsCount; i++) {
         gen = GeneratingUtils.generateDoubleInRange(totalPrice);
         cus.addProduct(new Product(i, buildProductName(cus.getName(), i), gen.doubleValue()));
         totalPrice = totalPrice.subtract(gen).setScale(2, RoundingMode.HALF_UP);
      }
      cus.addProduct(new Product(productsCount, buildProductName(cus.getName(), productsCount), totalPrice.doubleValue()));
      return cus;
   }

   private String buildProductName(String cusName, int number) {
      return "C:" + cusName + ";Product" + number;
   }

   @Override
   public int getId() {
      return id;
   }
}
