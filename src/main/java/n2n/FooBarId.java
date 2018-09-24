package n2n;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FooBarId implements Serializable {
	private static final long serialVersionUID = 1L;

	private long fooId;

	private long barId;

	public FooBarId() {
		// TODO Auto-generated constructor stub
	}

	public FooBarId(long fooId, long barId) {
		super();
		this.fooId = fooId;
		this.barId = barId;
	}

	@Column(name = "fooid")
	public long getFooId() {
		return fooId;
	}

	public void setFooId(long fooId) {
		this.fooId = fooId;
	}

	@Column(name = "barid")
	public long getBarId() {
		return barId;
	}

	public void setBarId(long barId) {
		this.barId = barId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (barId ^ (barId >>> 32));
		result = prime * result + (int) (fooId ^ (fooId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FooBarId other = (FooBarId) obj;
		if (barId != other.barId)
			return false;
		if (fooId != other.fooId)
			return false;
		return true;
	}

}
