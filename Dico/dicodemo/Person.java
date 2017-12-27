package dicodemo; 

public class Person {

	public Person(){
	}

	public Person(int id, int age, String name) {
		this.id = id;
		this.age = age;
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
		if (this.id != other.id) {
			return false;
		}
		if (this.age != other.age) {
			return false;
		}
		if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + ", age=" + age + ", name=" + name + "]";		
	}

	private int id;
	private int age;
	private String name;

}
