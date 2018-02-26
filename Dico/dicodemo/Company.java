package dicodemo; 

public class Company implements Comparable  {

	public Company(){
	}

	public Company(Manager manager) {
		this.manager = manager;
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
		final Company other = (Company) obj;
		if (!this.manager.equals(other.manager)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		Company tmp = (Company) other;
		if (!this.manager.equals(tmp.manager)) {
			return this.manager.compareTo(tmp.manager);
		}
		return 0;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[manager=" + manager + "]";		
	}

	private Manager manager;

}
