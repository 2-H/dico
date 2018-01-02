package dicodemo; 

public class CTO extends Manager {

	public CTO(){
	super();
	}

	public CTO(int id, String name, Double salary, int yearsOfExperience) {
		super(id, name, salary);
		this.yearsOfExperience = yearsOfExperience;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final CTO other = (CTO) obj;
		if (this.yearsOfExperience != other.yearsOfExperience) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() +"[yearsOfExperience=" + yearsOfExperience + "]";		
	}

	private int yearsOfExperience;

}
