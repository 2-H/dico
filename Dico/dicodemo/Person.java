package dicodemo; 

public class Person implements Comparable  {

	public Person(){
	}

	public Person(int id, String name) {
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
		if (this.id != other.id) {
			return false;
		}
		if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		Person tmp = (Person) other;
		if (!(this.id == tmp.id)) {
			return this.id - tmp.id;
		}
		if (!this.name.equals(tmp.name)) {
			return this.name.compareTo(tmp.name);
		}
		return 0;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + ", name=" + name + "]";		
	}

	private int id;
	private String name;

}
