package dicodemo; 

public class Manager extends Employee {

	public Manager(){
	super();
	}

	public Manager(int id, String name, Double salary, Double bonus) {
		super(id, name, salary);
		this.bonus = bonus;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final Manager other = (Manager) obj;
		if (!this.bonus.equals(other.bonus)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() +"[bonus=" + bonus + "]";		
	}

	private Double bonus;

}
