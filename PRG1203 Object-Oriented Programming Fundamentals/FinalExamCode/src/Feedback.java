
public class Feedback {
	private Order orders;
	private Product item;
	private String feedback;
	private double rating;
	
	public Feedback() {
		super();
	}

	public Feedback(Order orders, Product item, String feedback, double rating) {
		super();
		this.orders = orders;
		this.item = item;
		this.feedback = feedback;
		this.rating = rating;
	}

	public Order getOrder() {
		return orders;
	}

	public void setOrder(Order orders) {
		this.orders = orders;
	}

	public Product getItem() {
		return item;
	}

	public void setItem(Product item) {
		this.item = item;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return String.format("Feedback [orders=%s, feedback=%s, rating=%s]", orders, feedback, rating);
	}
	
}
