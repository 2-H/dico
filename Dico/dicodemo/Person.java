package dicodemo; 

public class Person {

	public Person(){
	}

	public Person(String id, String name) {
		this.id = id;
		this.name = name;
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
		if (!this.id.equals(other.id)) {
			return false;
		}
		if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + ", name=" + name + "]";		
	}

	private String id;
	private String name;

}
