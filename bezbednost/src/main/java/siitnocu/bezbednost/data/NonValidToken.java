package siitnocu.bezbednost.data;

import javax.persistence.*;

@Entity
public class NonValidToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token", columnDefinition="text")
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NonValidToken() {
    }

    public NonValidToken(Integer id, String token) {
        this.id = id;
        this.token = token;
    }
}
