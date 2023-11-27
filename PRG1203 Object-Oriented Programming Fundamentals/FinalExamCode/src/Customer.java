
public class Customer extends User{
	private int points;

	public Customer() {
		super();
	}
	
	public Customer(String image, String name, String email, String pass) {
		super(image, name, email, pass);
		// TODO Auto-generated constructor stub
	}

	public Customer(String image, String name, String email, String pass, int points) {
		super(image, name, email, pass);
		
		this.points = points;	
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	@Override
    public void displayProfile() {
        super.displayProfile();
        
        System.out.println("Log Out");
    }
	
	public void addPoints(int p) {
		this.points += p;
	}
	
	public String toString() {
		return String.format("Customer %s, [points = %s]", super.toString(), points);
	}
}
