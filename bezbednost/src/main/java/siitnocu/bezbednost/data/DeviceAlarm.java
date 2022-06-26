package siitnocu.bezbednost.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_alarms")
public class DeviceAlarm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public DeviceAlarm() {
    }

    public DeviceAlarm(Long id, String message, Date date, Device device) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.device = device;
    }
}
