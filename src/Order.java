import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String orderId;
    private Date orderDate;
    private Date deliveryDate;
    private String customerName;
    private String deliveryAddress;
    private List<Product> productList;

    /**
	 * 
	 */
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderId, Date orderDate, Date deliveryDate, String customerName, String deliveryAddress) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.productList = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public double calculateOrderTotal() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

	public void updateQuantity(Product product, int quantity) {
		// TODO Auto-generated method stub
		for(int i=0;i<productList.size();i++) {
			if(product.getProductName().equals(productList.get(i).getProductName())) {
				productList.set(i, product);
			}
		}
		
	}
}