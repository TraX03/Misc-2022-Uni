
public class Product {
	private String image, title, description, category;
	private double originPrice, discountPrice, rating;
	private int quantity = 0;
	private Merchant merch;
	
	public Product() {
		super();
	}

	public Product(String image, String title, String description, String category, double originPrice,
			double discountPrice, double rating, Merchant merch) {
		super();
		this.image = image;
		this.title = title;
		this.description = description;
		this.category = category;
		this.originPrice = originPrice;
		this.discountPrice = discountPrice;
		this.rating = rating;
		this.merch = merch;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(double originPrice) {
		this.originPrice = originPrice;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Merchant getMerch() {
		return merch;
	}

	public void setMerch(Merchant merch) {
		this.merch = merch;
	}

	@Override
	public String toString() {
		return String.format(
				"Product [image=%s, title=%s, description=%s, category=%s, originPrice=%s, discountPrice=%s, rating=%s, quantity=%s, merch=%s]",
				image, title, description, category, originPrice, discountPrice, rating, quantity, merch);
	}
}
