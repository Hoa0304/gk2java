
public class Product implements IProduct {
   private String productId;
   private String productName;
   private double price;
   private int quantity;
	/**
 * @param productId
 * @param productName
 * @param price
 * @param quantity
 */
public Product(String productId, String productName, double price, int quantity) {
	this.productId = productId;
	this.productName = productName;
	this.price = price;
	this.quantity = quantity;
}
	public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
	/**
 * 
 */
public Product() {
	super();
	// TODO Auto-generated constructor stub
}
	@Override
	public double calculatePriceForUser() {
		// TODO Auto-generated method stub
		return price * 1.1;
	}

}
