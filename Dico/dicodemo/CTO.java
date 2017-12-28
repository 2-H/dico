package dicodemo; 

public class CTO extends Manager {

	public CTO(){
	super();
	}

	public CTO(int id, String name, Double salary, int experience) {
		super(id, name, salary);
		this.experience = experience;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final CTO other = (CTO) obj;
		if (this.experience != other.experience) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() +"[experience=" + experience + "]";		
	}

	private int experience;

}
