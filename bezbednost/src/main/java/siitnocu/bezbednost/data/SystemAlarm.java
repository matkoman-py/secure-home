package siitnocu.bezbednost.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "system_alarms")
public class SystemAlarm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SystemAlarm() {
    }

    public SystemAlarm(Long id, String message, Date date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }
}
