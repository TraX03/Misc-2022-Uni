
public class MainApplication {

	public static void main(String[] args) {
		//menu List
		Order menu = new Order();
		
		Customer c1 = new Customer("Cat", "Sonali", "sonali@gmail.com", "12345");
		Customer c2 = new Customer("Rain", "Meiqi", "mq78@gmail.com", "67891");
		
		Merchant m1 = new Merchant("Onigiri", "Nasi Lemak Corner", "nlc@gmail.com", 
				"12345", "Subang Jaya");
		
		//display merchant profile
		m1.displayProfile();
		System.out.println();
		//display customer profile
		c1.displayProfile();
		
		//creating menu items
		Product p1 = new Product("Pasta", "Pasta Mania SS100", "Ham Carbonara & Pepsi", "Meals",
				15.00, 5.00, 4.5, m1); 
		
		Product p2 = new Product("Burger", "Burgertown Grill", "Chicken Burger & Fries", "Meals",
				20.00, 8.00, 4.3, m1); 
		
		//add menu items into menu list
		menu.getProductList().add(p1);
		menu.getProductList().add(p2);
		
		//display the menu and display the item info
		menu.displayMenu();
		menu.displayInfo(p1);
		
		//create first order
		Order o1 = new Order(c1);
		//add item
		o1.addToOrder(p1);
		//display another item info
		menu.displayInfo(p2);
		//add order
		o1.addToOrder(p2);
		//show the order summary
		o1.displayOrder(123);
		
		//customer 1 give feedback to the items they order
		Feedback f1 = new Feedback(o1, o1.getOrderList().get(0), "Nice!", 4.5);
		Feedback f2 = new Feedback(o1, o1.getOrderList().get(1), "Some improvements can be made!", 3.5);
		
		//customer 2 repeat process
		Order o2 = new Order(c2);
		o2.getOrderList().add(p1);
		Feedback f3 = new Feedback(o2, o2.getOrderList().get(0), "Hmm!", 4.5);
		
		//all reviews for the pasta
		Review r1 = new Review();
		r1.getFeedbacks().add(f1);
		r1.getFeedbacks().add(f3);
		
		//all review for the burger
		Review r2 = new Review();
		r2.getFeedbacks().add(f2);
		
		//show the reviews for the pasta
		r1.displayReview(menu, 0);

	}

}
