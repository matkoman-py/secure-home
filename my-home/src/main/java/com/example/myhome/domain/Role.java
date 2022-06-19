package com.example.myhome.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles" , fetch = FetchType.LAZY)
    private Set<User> users;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Role(String name, Set<Privilege> privileges, Set<User> users) {
		super();
		this.name = name;
		this.privileges = privileges;
		this.users = users;
	}

	public Role() {
		super();
	}

	public Collection<? extends GrantedAuthority> getPrivileges() {
		return this.privileges;
	}

}
