package dicodemo; 

public class Manager extends Person {

	public Manager(){
	super();
	}

	public Manager(int id, String name, Double salary) {
		super(id, name);
		this.salary = salary;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final Manager other = (Manager) obj;
		if (!this.salary.equals(other.salary)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		Manager tmp = (Manager) other;
		if (!this.salary.equals(tmp.salary)) {
			return this.salary.compareTo(tmp.salary);
		}
		return super.compareTo(tmp);
	}

	@Override
	public String toString() {
		return super.toString() +"[salary=" + salary + "]";		
	}

	private Double salary;

}
