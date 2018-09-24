package n2n;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class FooBar {

    private FooBarId id; // embeddable class with foo and bar (only ids)

    private Foo foo;

	private Bar bar;

    // this is why I had to use this helper class (FooBar), 
    // else I could have made a direct @ManyToMany between Foo and Bar
	//private Double additionalInformation; 

	public FooBar() {
		// TODO Auto-generated constructor stub
	}

    public FooBar(Foo foo, Bar bar){
        this.foo = foo;
        this.bar = bar;
		//this.additionalInformation = .... // not important
        this.id = new FooBarId(foo.getId(), bar.getId());
    }

    @EmbeddedId
    public FooBarId getId(){
        return id;
    }

    public void setId(FooBarId id){
        this.id = id;
    }

    @ManyToOne
    @MapsId("foo")
    @JoinColumn(name = "fooid", referencedColumnName = "id")
    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    @ManyToOne
    @MapsId("bar")
    @JoinColumn(name = "barid", referencedColumnName = "id")
    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    // getter, setter for additionalInformation omitted for brevity
}