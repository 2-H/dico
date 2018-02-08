package dicodemo; 

public class employee extends person {

	public employee(){
	super();
	}

	public employee(int id, String name, String salary) {
		super(id);
		this.name = name;
		this.salary = salary;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final employee other = (employee) obj;
		if (!this.name.equals(other.name)) {
			return false;
		}
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
		employee tmp = (employee) other;
		if (!this.name.equals(tmp.name)) {
			return this.name.compareTo(tmp.name);
		}
		if (!this.salary.equals(tmp.salary)) {
			return this.salary.compareTo(tmp.salary);
		}
		return super.compareTo(tmp);
	}

	@Override
	public String toString() {
		return super.toString() +"[name=" + name + ", salary=" + salary + "]";		
	}

	private String name;
	private String salary;

}
