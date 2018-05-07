
public class Tester {

	public static void main(String[] args) {
	Unit person = new Unit(1,2,2,3);
	System.out.println(person.toString());
	person.speedUp(2);
	System.out.println(person.toString());
	person.addLives(4);
	System.out.println(person.toString());
	person.moveXDirection(2);
	System.out.println(person.toString());
	}

}
