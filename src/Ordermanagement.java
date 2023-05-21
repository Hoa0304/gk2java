import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ordermanagement {
    private List<Order> orderList;
    private Order order;
    

    public Ordermanagement() {
        orderList = new ArrayList<>();
        order = new Order();
        
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
    }

    public void updateOrder(Order order) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(order.getOrderId())) {
                orderList.set(i, order);
                break;
            }
        }
    }

	public Product findProductById(String productId) {
		// TODO Auto-generated method stub
		Product pro = null;
		for(int i=0;i<order.getProductList().size();i++) {
			if(order.getProductList().get(i).getProductId()==productId) {
				String productIdd = order.getProductList().get(i).getProductId();
				String productName = order.getProductList().get(i).getProductName();
				double price = order.getProductList().get(i).getPrice();
				int quantity = order.getProductList().get(i).getQuantity();
				 pro = new Product(productIdd, productName, price, quantity);
			}
			
		}
		return pro;
	}

	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		for(int i=0;i<order.getProductList().size();i++) {
			if(product.getProductId()==order.getProductList().get(i).getProductId()) {
				order.getProductList().remove(i);
			}
		}
	}

	public Order findOrderById(String orderId) {
		// TODO Auto-generated method stub
		Order x = null;
		for(int i=0;i<orderList.size();i++) {
			if(orderList.get(i).getOrderId()==orderId) {
			      String odid = orderList.get(i).getOrderId();
			      Date dateod = orderList.get(i).getOrderDate();
			      Date deliveryd = orderList.get(i).getDeliveryDate();
			      String ctmname = orderList.get(i).getCustomerName();
			      String address = orderList.get(i).getDeliveryAddress();
			      x = new Order(orderId, dateod, deliveryd, ctmname, address);
			}
		}
		return x;
	}

	public Product[] getProductList() {
		// TODO Auto-generated method stub
		return getProductList();
	}

	
}