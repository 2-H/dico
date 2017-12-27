package dicodemo; 

public class Manager extends Person {

	public Manager(){
	super();
	}

	public Manager(String id, String name, String salary) {
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
	public String toString() {
		return super.toString() +"[salary=" + salary + "]";		
	}

	private String salary;

}
