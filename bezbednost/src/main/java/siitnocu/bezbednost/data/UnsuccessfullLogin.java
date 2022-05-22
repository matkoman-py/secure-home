package siitnocu.bezbednost.data;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UnsuccessfullLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "datetime")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UnsuccessfullLogin() {
    }

    public UnsuccessfullLogin(Integer id, String username, Date date) {
        this.id = id;
        this.username = username;
        this.date = date;
    }
}
