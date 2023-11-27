
public class User {
	private String image, name, email, password;

	public User() {
		super();
	}

	public User(String image, String name, String email, String password) {
		super();
		this.image = image;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void displayProfile() {
		System.out.printf("Profile\n----------------\n"
				+ "Image: %s\nName: %s\nEmail Address: %s\npassword: %s\n", getImage(), 
				getName(), getEmail(), getPassword());
	}
	
	@Override
	public String toString() {
		return String.format("User [name=%s, email=%s, password=%s]", name, email, password);
	}
	
	
}
