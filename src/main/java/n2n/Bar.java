package n2n;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bar {
	private long id;

    private Collection<FooBar> fooBars = new HashSet<>();

    // constructor omitted for brevity

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bar", cascade = CascadeType.ALL)
    public Collection<FooBar> getFooBars() {
        return fooBars;
    }

    public void setFooBars(Collection<FooBar> fooBars) {
        this.fooBars = fooBars;
    }
}