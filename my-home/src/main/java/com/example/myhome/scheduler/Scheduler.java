package com.example.myhome.scheduler;

import com.example.myhome.domain.Device;
import com.example.myhome.domain.Message;
import com.example.myhome.repository.DeviceRepository;
import com.example.myhome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
public class Scheduler {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private TaskScheduler executor;

    @EventListener(ApplicationReadyEvent.class)
    public void setUpTasks() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader scanner = new BufferedReader(new FileReader(new File("config.csv")))) {
            String line;
            while ((line = scanner.readLine()) != null) {
                records.add(getRecordFromLine(line));
            }
        }

        for (List<String> s:records) {
            System.out.println(s);

            Optional<Device> device = deviceRepository.findByPathToFile(s.get(0));
            Device dev = device.get();
            Integer period = Integer.parseInt(s.get(1));

            //kreirati task za svaki
            Runnable task  = () -> {
                try {
                    readMessagesWriteToMongo(dev.getPathToFile(), dev, s.get(2));
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            };

            executor.scheduleWithFixedDelay(task, period*1000);

        }
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private void readMessagesWriteToMongo(String path, Device dev, String regex) throws IOException, ParseException {
        List<List<String>> records = new ArrayList<>();
        try(BufferedReader scanner = new BufferedReader(new FileReader(new File(path+".csv")))){
            String line;
            while ((line = scanner.readLine()) != null) {
                records.add(getRecordFromLine(decrypt(line)));
            }
        }

        for (List<String> s:records) {
            Matcher matcher = Pattern.compile(regex).matcher(s.get(0));
            if(!matcher.matches()){
                System.out.println("USAO SAM OVDE NE PROLAZI");
                continue;
            }

            Message message = new Message();
            message.setMessage(s.get(0));
            message.setDevice(dev);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=dateFormat.parse(s.get(1));
            message.setDate(date);
            mongoTemplate.insert(message, "Messages");
        }

        new PrintWriter(path+".csv").close();
    }

    public static String decrypt(String ciphertext) {
        try {
            byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

            byte[] initVector = Arrays.copyOfRange(cipherbytes,0,16);

            byte[] messagebytes = Arrays.copyOfRange(cipherbytes,16,cipherbytes.length);

            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec("ThisIsA16ByteKey".getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            // Convert the ciphertext Base64-encoded String back to bytes, and
            // then decrypt
            byte[] byte_array = cipher.doFinal(messagebytes);

            // Return plaintext as String
            return new String(byte_array, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
