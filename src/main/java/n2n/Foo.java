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
public class Foo {
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

	// Adding the orphanRemoval here enables the test to pass
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "foo", fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "foo", fetch = FetchType.EAGER, orphanRemoval = true)
    public Collection<FooBar> getFooBars() {
        return fooBars;
    }

    public void setFooBars(Collection<FooBar> fooBars) {
        this.fooBars = fooBars;
    }

    // use this to maintain bidirectional integrity
    public void addBar(Bar bar) {
		FooBar fooBar = new FooBar(this, bar);

        fooBars.add(fooBar);
        bar.getFooBars().add(fooBar);
    }

    // use this to maintain bidirectional integrity
    public void removeBar(Bar bar){
        // I do not want to disclose the code for findFooBarFor(). It works 100%, and is not reloading data from DB
        FooBar fooBar = findFooBarFor(bar, this); 

        fooBars.remove(fooBar);
        bar.getFooBars().remove(fooBar);
    }

	private FooBar findFooBarFor(Bar bar, Foo foo) {
		for (FooBar foobar : bar.getFooBars()) {
			if (foobar.getFoo().getId() == foo.getId())
				return foobar;
		}
		throw new IllegalArgumentException(
				"Matching FooBar not found for " + bar + " + " + foo);
	}

}