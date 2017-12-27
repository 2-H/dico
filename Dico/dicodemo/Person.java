package dicodemo; 

public class Person {

	public Person(){
	}

	public Person(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Person other = (Person) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + "]";		
	}

	private int id;

}
