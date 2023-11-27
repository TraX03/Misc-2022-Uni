import java.util.ArrayList;
import java.util.Scanner;

public class Order {
	private ArrayList<Product> productList = new ArrayList<>();
	private ArrayList<Product> orderList = new ArrayList<>();
	private Customer cust = new Customer();
	
	Scanner sc = new Scanner(System.in);
	
	public Order() {
		super();
	}

	public Order(ArrayList<Product> productList) {
		super();
		this.productList = productList;
	}
	
	public Order(Customer cust) {
		super();
		this.cust = cust;
	}
	
	public ArrayList<Product> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}


	public ArrayList<Product> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Product> orderList) {
		this.orderList = orderList;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}
	
	public void displayMenu() {
		System.out.printf("\nMenu\n------------");
		for(Product a: productList){
			System.out.println();
			System.out.printf("Image: %s\nTitle: %s\nRating: %.2f\nDescription: %s\nDiscounted Price: %.2f\n"
					+ "Orginal Price: %.2f\n",
					a.getImage(), a.getTitle(), a.getRating(), a.getDescription(), a.getDiscountPrice(),
					a.getOriginPrice());
		}
	}
	
	public void displayInfo(Product item) {
		System.out.println("\nItem Info\n-----------");
		System.out.printf("Image: %s\nTitle: %s\nDescription: %s\nPrice: \nBefore discount = %.2f"
				+ "\nAfter Discount = %.2f\nQuantity: (-) 1 (+)\nCategory: %s\n\n",
				item.getImage(), item.getTitle(), item.getDescription(), item.getDiscountPrice(),
				item.getOriginPrice(), item.getCategory());
	}
	
	public void addToOrder(Product item) {
		System.out.printf("Add to Order\n-----------\nImage: %s\nTitle: %s\nRating: %.2f\nDescription: %s\nPrice:\nBefore discount = %.2f\n"
				+ "After Discount = %.2f",
				item.getImage(), item.getTitle(), item.getRating(), item.getDescription(), item.getDiscountPrice(),
				item.getOriginPrice());
		
		System.out.println("\n\nWHAT IS IN STORE FOR YOU\n"
				+ "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. "
				+ "\nAenean massa. Cum sociis natoque penatibus et magnis dis parturient montes\n");
		
		confirmQuantity(item);
	}
	
	public void confirmQuantity(Product item) {
		int q;
		
		System.out.print("Select Quantity:");
		q = sc.nextInt();
		
		if(q < 0) {
			System.out.println("Quantity cannot be 0 or negative.");
			confirmQuantity(item);
		}
		
		orderList.add(item);
		item.setQuantity(q);
	}
	
	public void displayOrder(int id) {
		System.out.println("\nOrder\n----------\nOrder number: " + id);
		System.out.printf("Customer Details:\n%s\n", cust.toString());
		int total = 0;
		
		for(Product a: orderList){
			System.out.println();
			System.out.printf("Title: %s\nQuantity: %d\nPrice: %.2f\n",
					a.getTitle(), a.getQuantity(), a.getDiscountPrice());
			
			//calculate the total need to pay
			total += a.getQuantity()*a.getDiscountPrice();
		}
		
		//display the total need to pay
		System.out.println("\nTotal Amount: RM" + total);
		
		//add points after each order
		cust.addPoints(total/2);
	}
	
	@Override
	public String toString() {
		return String.format("Order [productList=%s, cust=%s]", productList, cust);
	}
	
	
	
}
