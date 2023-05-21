import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderManagementMainProgram {
    private Ordermanagement ordermanagement;
    private Order order;
    private Product product;

    public OrderManagementMainProgram() {
        ordermanagement = new Ordermanagement();
        order = new Order();
        product = new Product();
    }

    public void run() throws OrderManagementMainProgram.InvalidProductQuantityException, OrderManagementMainProgram.InvalidProductPriceException, OrderManagementMainProgram.InsufficientProductQuantityException, OrderManagementMainProgram.InvalidOrderDateException, SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    removeProduct();
                    break;
                case 4:
                    addOrder();
                    break;
                case 5:
                    updateOrder();
                    break;
                case 6:
                    removeOrder();
                    break;
                case 7:
                    displayProductList();
                    break;
                case 8:
                    displayOrderList();
                    break;
                case 9:
                    return;
                case 10: 
                	writefile(order.getProductList(), ordermanagement.getOrderList());break;
                case 11:
                	readfile();break;
                case 12: order.calculateOrderTotal();break;
                case 13: search();break;
                case 14: sortt();break;
                case 15: getAllOrders();getAllProducts();break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    private void search() throws OrderManagementMainProgram.InvalidOrderDateException {
    	Scanner scanner = new Scanner(System.in);
    	String orderId = scanner.nextLine();
    	String cusname = scanner.nextLine();
    	 String orderDateStr = scanner.nextLine();
         Date orderDate = null;
         try {
             orderDate = Order.DATE_FORMAT.parse(orderDateStr);
         } catch (java.text.ParseException e) {
             throw new InvalidOrderDateException();
         }
         
         String deliveryDatefm = scanner.nextLine();
         Date deliveryDate = null;
         try {
 			deliveryDate = Order.DATE_FORMAT.parse(deliveryDatefm);
 		} catch (Exception e) {
 			// TODO: handle exception
 			throw new InvalidOrderDateException();
 		}
    	List<Order> orders = ordermanagement.getOrderList();
    	  List<Order> searchResults = new ArrayList<>();
          for (Order order : orders) {
              if ((orderId == null || orderId.equals(order.getOrderId()))
                      && (cusname == null || cusname.equals(order.getCustomerName()))
                      && (orderDate == null || orderDate.equals(order.getOrderDate()))
                      && (deliveryDate == null || deliveryDate.equals(order.getDeliveryDate()))) {
                  searchResults.add(order);
              }
          }
          for(Order os : searchResults) {
        	  System.out.println(os.getOrderId());
          }
      
    	
    	
    }
    private void readfile() {
    	try (FileInputStream fis =new FileInputStream("export.txt");ObjectInputStream ois = new ObjectInputStream(fis)){
    		List<Product> produclist = (List<Product>) ois.readObject();
    		List<Order> oderlist = (List<Order>) ois.readObject();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    private void writefile(List<Product> productlist,List<Order> orderlist) {
    	try (FileOutputStream fos = new FileOutputStream("export.txt");ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(productlist);
			oos.writeObject(orderlist);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    private void displayMenu() {
        System.out.println("1. Add product");
        System.out.println("2. Update product");
        System.out.println("3. Remove product");
        System.out.println("4. Add order");
        System.out.println("5. Update order");
        System.out.println("6. Remove order");
        System.out.println("7. Display product list");
        System.out.println("8. Display order list");
        System.out.println("9. Exit");
        System.out.println("10. Write file");
        System.out.println("11. Read file");
        System.out.println("12. Total price");
        System.out.println("13 Search");
        System.out.println("14 Sort");
        System.out.println("15 ReadFromDB");
        System.out.print("Your choice: ");
    }
    private void sortt() {
    	List<Order> orders = ordermanagement.getOrderList();
    	Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });
    }
    private void addProduct() throws OrderManagementMainProgram.InvalidProductQuantityException, OrderManagementMainProgram.InvalidProductPriceException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product price: ");
        double productPrice = scanner.nextDouble();
        if (productPrice <= 0) {
            throw new InvalidProductPriceException();
        }
        System.out.print("Enter product quantity: ");
        int productQuantity = scanner.nextInt();
        if (productQuantity <= 0) {
            throw new InvalidProductQuantityException();
        }

        try {
            Product product = new Product(productId, productName, productPrice, productQuantity);
            order.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        Product product = ordermanagement.findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.print("Enter new product name (or press Enter to skip): ");
        String productName = scanner.nextLine();
        if (!productName.isEmpty()) {
            product.setProductName(productName);
        }
        System.out.print("Enter new product price (or press Enter to skip): ");
        String productPriceStr = scanner.nextLine();
        if (!productPriceStr.isEmpty()) {
            try {
                double productPrice = Double.parseDouble(productPriceStr);
                product.setPrice(productPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid product price!");
            }
        }
        System.out.print("Enter new product quantity (or press Enter to skip): ");
        String productQuantityStr = scanner.nextLine();
        if (!productQuantityStr.isEmpty()) {
            try {
                int productQuantity = Integer.parseInt(productQuantityStr);
                product.setQuantity(productQuantity);
            } catch (NumberFormatException e) {
                System.out.println("Invalid product quantity!");
            }
        }
        System.out.println("Product updated successfully!");
    }

    private void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        Product product = ordermanagement.findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        ordermanagement.removeProduct(product);
        System.out.println("Product removed successfully!");
    }

    private void addOrder() throws OrderManagementMainProgram.InsufficientProductQuantityException, OrderManagementMainProgram.InvalidOrderDateException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter order date (yyyy-mm-dd): ");
        String orderDateStr = scanner.nextLine();
        Date orderDate = null;
        try {
            orderDate = Order.DATE_FORMAT.parse(orderDateStr);
        } catch (java.text.ParseException e) {
            throw new InvalidOrderDateException();
        }
        System.out.println("Enter delivery date");
        String deliveryDate = scanner.nextLine();
        Date deliveryDatefm = null;
        try {
			deliveryDatefm = Order.DATE_FORMAT.parse(deliveryDate);
		} catch (Exception e) {
			// TODO: handle exception
			throw new InvalidOrderDateException();
		}
        System.out.println("Enter delivery address");
        String dadd = scanner.nextLine();
        Order order = new Order(orderId, orderDate,deliveryDatefm,customerName,dadd);

        while (true) {
            System.out.print("Enter product ID (or press Enter to finish): ");
            String productId = scanner.nextLine();
            if (productId.isEmpty()) {
                break;
            }
            Product product = ordermanagement.findProductById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                continue;
            }
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            if (quantity > product.getQuantity()) {
                throw new InsufficientProductQuantityException(product, quantity);
            }
            try {
                order.addProduct(product);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            ordermanagement.addOrder(order);
            System.out.println("Order added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        Order order = ordermanagement.findOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        System.out.print("Enter new customer name (or press Enter to skip): ");
        String customerName = scanner.nextLine();
        if (!customerName.isEmpty()) {
            order.setCustomerName(customerName);
        }
        System.out.print("Enter new order date (yyyy-mm-dd) (or press Enter to skip): ");
        String orderDateStr = scanner.nextLine();
        if (!orderDateStr.isEmpty()) {
            try {
                Date orderDate = Order.DATE_FORMAT.parse(orderDateStr);
                order.setOrderDate(orderDate);
            } catch (java.text.ParseException e) {
                System.out.println("Invalid order date format!");
            }
        }

        while (true) {
            System.out.print("Enter product ID (or press Enter to finish): ");
            String productId = scanner.nextLine();
            if (productId.isEmpty()) {
                break;
            }
            Product product = ordermanagement.findProductById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                continue;
            }
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            try {
                order.updateQuantity(product, quantity);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Order updated successfully!");
    }

    private void removeOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        Order order = ordermanagement.findOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        ordermanagement.removeOrder(order);
        System.out.println("Order removed successfully!");
    }

    private void displayProductList() {
        System.out.println("Product list:");
        for (Product product : ordermanagement.getProductList()) {
            System.out.println(product);
        }
    }

    private void displayOrderList() {
        System.out.println("Order list:");
        for (Order order : ordermanagement.getOrderList()) {
            System.out.println(order);
            System.out.println("Product list:");
            for (Product orderItem : order.getProductList()) {
                System.out.println("- " + orderItem.getProductName() + " x " + orderItem.getQuantity());
            }
            System.out.println("Total: " + order.calculateOrderTotal());
        }
    }
    public class InvalidProductPriceException extends Exception {
        public InvalidProductPriceException() {
            super("Invalid product price!");
        }
    }

    public class InvalidProductQuantityException extends Exception {
        public InvalidProductQuantityException() {
            super("Invalid product quantity!");
        }
    }

    public class InvalidOrderDateException extends Exception {
        public InvalidOrderDateException() {
            super("Invalid order date!");
        }
    }

    public class InsufficientProductQuantityException extends Exception {
        public InsufficientProductQuantityException(Product product, int requestedQuantity) {
            super("Insufficient product quantity: " + product.getProductName() + " (quantity in stock: " + product.getQuantity() + ", requested quantity: " + requestedQuantity + ")");
        }
    }
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_database";
        String user = "root";
        String password = "my_password";
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                products.add(new Product(id, name, price,quantity));
            }
        }
        return products;
    }

    public static List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String customerName = resultSet.getString("customer_name");
                String deliveryAddress = resultSet.getString("delivery_address");
                Date orderDate = resultSet.getDate("order_date");
                Date deliveryDate = resultSet.getDate("delivery_date");
                
                orders.add(new Order(id,orderDate,deliveryDate,customerName,deliveryAddress));
            }
        }
        return orders;
    }

    public static List<Product> getProductListForOrder(int orderId) throws SQLException {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT products.* FROM order_products " +
                     "JOIN products ON order_products.product_id = products.id " +
                     "WHERE order_products.order_id = ?")) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    productList.add(new Product(id, name, price,quantity));
                }
            }
        }
        return productList;
    }
   

    public static void main(String[] args) throws OrderManagementMainProgram.InvalidProductQuantityException, OrderManagementMainProgram.InvalidProductPriceException, OrderManagementMainProgram.InsufficientProductQuantityException, OrderManagementMainProgram.InvalidOrderDateException, SQLException {
        OrderManagementMainProgram program = new OrderManagementMainProgram();
        program.run();
    }
}