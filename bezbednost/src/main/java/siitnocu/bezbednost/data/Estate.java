package siitnocu.bezbednost.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="ESTATES")
public class Estate {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "estate_type")
	@Enumerated(EnumType.STRING)
	private EstateType estateType;
	
	@Column(name = "address")
    private String address;
	
	
	
	@Column(name = "description", nullable = true)
    private String desciption;
	
	@ManyToMany(mappedBy = "estates", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<User>();

	public Estate() {
		super();
	}

	public Estate(Long id, String address, String desciption, EstateType estateType, Set<User> users) {
		super();
		this.id = id;
		this.address = address;
		this.desciption = desciption;
		this.estateType = estateType;
		this.users = users;
	}
	
	

	public Estate(Long id, String address, String desciption, EstateType estateType) {
		super();
		this.id = id;
		this.address = address;
		this.desciption = desciption;
		this.estateType = estateType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public EstateType getEstateType() {
		return estateType;
	}

	public void setEstateType(EstateType estateType) {
		this.estateType = estateType;
	}
	
	
	
	
	

}
