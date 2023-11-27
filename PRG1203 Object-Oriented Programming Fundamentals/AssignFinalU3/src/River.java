import java.util.ArrayList;
import java.util.Random;

public class River {
	private ArrayList<Element> eList = new ArrayList<Element>(100);
	private Random r = new Random();
	
	//constructors
	public River() {
		//print all spaces
		for (int j=0; j<100 ; j++) {
			Empty e = new Empty();
			eList.add(e);
		}

		//generate 40 all four elements except empty, randomly positioned
		for (int i = 0; i < 40; i++) {
			int type = r.nextInt(4); //generate a random number between 0 and 3 (inclusive)

			switch (type) {
			case 0:
				Current c = new Current();
				eList.set(c.getPosition(), c);
				break;
			case 1:
				Trap t = new Trap();
				eList.set(t.getPosition(), t);
				break;
			case 2:
				Multiplier m = new Multiplier();
				eList.set(m.getPosition(), m);
				break;
			default:
				Frozen f = new Frozen();
				eList.set(f.getPosition(), f);
				break;
			}
		}
	}

	public River(ArrayList<Element> eList) {
		this.eList = eList;
	}

	//setter getter
	public ArrayList<Element> geteList() {
		return eList;
	}

	public void seteList(ArrayList<Element> eList) {
		this.eList = eList;
	}
	
	//to input element into their respective positions in the river
	public void setRiverElement(int position, Element element) {
		eList.set(position, element);
	}
	//to print out the river
	public void printRiver() {
		for (Element e: eList) {
			System.out.print(e.getSymbol()+" ");
		}
		System.out.println();		
	}
	
	
}

	
	
