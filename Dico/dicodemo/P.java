package dicodemo; 

public class P implements Comparable  {

	public P(){
	}

	public P(int gh) {
		this.gh = gh;
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
		final P other = (P) obj;
		if (this.gh != other.gh) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(getClass() != other.getClass()) {
			throw new ClassCastException();
		}
		P tmp = (P) other;
		if (!(this.gh == tmp.gh)) {
			return this.gh - tmp.gh;
		}
		return 0;
	}

	@Override
	public String toString() {
		return getClass().getName() +"[gh=" + gh + "]";		
	}

	private int gh;

}
