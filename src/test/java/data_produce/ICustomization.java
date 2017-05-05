package data_produce;

import entities.Customer;

public interface ICustomization {

   public int getId();

   public Customer build(Customer cus);

}
