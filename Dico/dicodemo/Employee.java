package dicodemo; 

public class Employee extends Person {

	public Employee(){
	super();
	}

	public Employee(int id, int age, String name, Double salary) {
		super(id, age, name);
		this.salary = salary;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final Employee other = (Employee) obj;
		if (!this.salary.equals(other.salary)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() +"[salary=" + salary + "]";		
	}

	private Double salary;

}
