import java.util.ArrayList;

public class Review {
	private ArrayList<Feedback> feedbackList = new ArrayList<>();

	public Review() {
		super();
	}


	public Review(ArrayList<Feedback> feedbacks) {
		super();
		this.feedbackList = feedbacks;
	}


	public ArrayList<Feedback> getFeedbacks() {
		return feedbackList;
	}


	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbackList = feedbacks;
	}


	public void displayReview(Order menu, int index) {
		System.out.printf("\n\nReviews for %s \n------------", menu.getProductList().get(index).getTitle());
		for(Feedback a: feedbackList){
			System.out.println();
			System.out.printf("Name: %s\nRating: %.2f\nFeedback: %s\n",
					a.getOrder().getCust().getName(), a.getRating(), a.getFeedback());
		}
	}
	
	@Override
	public String toString() {
		return String.format("Review [feedbacks=%s]", feedbackList);
	}

}
