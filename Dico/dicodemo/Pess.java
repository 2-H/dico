package dicodemo; 

public class Pess implements Comparable  {

	public Pess(){
	}

	public Pess(int aasa) {
		this.aasa = aasa;
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
		final Pess other = (Pess) obj;
		if (this.aasa != other.aasa) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		Pess tmp = (Pess) other;
		if (!(this.aasa == tmp.aasa)) {
			return this.aasa - tmp.aasa;
		}
		return 0;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[aasa=" + aasa + "]";		
	}

	private int aasa;

}
