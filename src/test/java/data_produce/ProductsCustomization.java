package data_produce;

import entities.Customer;

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
      // TODO Auto-generated method stub
      return cus;
   }

   @Override
   public int getId() {
      return id;
   }

}
