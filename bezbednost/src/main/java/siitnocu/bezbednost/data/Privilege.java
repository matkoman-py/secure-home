package siitnocu.bezbednost.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "PRIVILEGE")
public class Privilege implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    private Set<Role> roles;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
