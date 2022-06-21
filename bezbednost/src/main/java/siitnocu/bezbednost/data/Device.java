package siitnocu.bezbednost.data;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="DEVICE")
public class Device {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "path")
    private String pathToFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    public Device(Long id, DeviceType type, String ipAddress, String pathToFile, Estate estate) {
        this.id = id;
        this.type = type;
        this.ipAddress = ipAddress;
        this.pathToFile = pathToFile;
        this.estate = estate;
    }

    public Device() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public void writeMessage(String message){
        File csvOutputFile = new File("../my-home/" + getPathToFile()+".csv");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile,true))) {
            List<String[]> dataLines = new ArrayList<>();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = dateFormat.format(new Date());

            dataLines.add(new String[]
                    { message, strDate, getIpAddress() });
            dataLines.stream()
                    .map(elem -> encrypt(convertToCSV(elem)))
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static String encrypt(String plaintext) {
        try {
            // Generate a random 16-byte initialization vector
            byte initVector[] = new byte[16];
            (new Random()).nextBytes(initVector);
            IvParameterSpec iv = new IvParameterSpec(initVector);

            // prep the key
            SecretKeySpec skeySpec = new SecretKeySpec("ThisIsA16ByteKey".getBytes("UTF-8"), "AES");

            // prep the AES Cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // Encode the plaintext as array of Bytes
            byte[] cipherbytes = cipher.doFinal(plaintext.getBytes());

            // Build the output message initVector + cipherbytes -> base64
            byte[] messagebytes = new byte[initVector.length + cipherbytes.length];

            System.arraycopy(initVector, 0, messagebytes, 0, 16);
            System.arraycopy(cipherbytes, 0, messagebytes, 16, cipherbytes.length);

            // Return the cipherbytes as a Base64-encoded string
            return Base64.getEncoder().encodeToString(messagebytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
