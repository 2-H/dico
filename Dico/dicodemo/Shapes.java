package dicodemo; 

public class Shapes {

	public Shapes(){
	}

	public Shapes(int id) {
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
		final Shapes other = (Shapes) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[id=" + id + "]";		
	}

	private int id;

}
