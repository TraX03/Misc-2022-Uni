
public class Merchant extends User{
	private String location;

	public Merchant() {
		super();
	}

	public Merchant(String image, String name, String email, String pass, String location) {
		super(image, name, email, pass);
		
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
    public void displayProfile() {
        super.displayProfile();
        System.out.printf("Location: %s\n", getLocation());
        
        System.out.println("Log Out");
    }
	
	@Override
	public String toString() {
		return String.format("Merchant [location=%s]", location);
	}
	
}
