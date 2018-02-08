package dicodemo; 

public class Perosn implements Comparable  {

	public Perosn(){
	}

	public Perosn(int id) {
		this.id = id;
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
		final Perosn other = (Perosn) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		Perosn tmp = (Perosn) other;
		if (!(this.id == tmp.id)) {
			return this.id - tmp.id;
		}
		return 0;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + "]";		
	}

	private int id;

}
