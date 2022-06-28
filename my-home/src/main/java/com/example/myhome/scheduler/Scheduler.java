package com.example.myhome.scheduler;

import com.example.myhome.domain.*;
import com.example.myhome.repository.DeviceAlarmRepository;
import com.example.myhome.repository.DeviceRepository;
import com.example.myhome.repository.MessageRepository;
import com.example.myhome.service.WebSocketService;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
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
import java.util.stream.Collectors;

@Component
@Transactional
public class Scheduler {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceAlarmRepository deviceAlarmRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private TaskScheduler executor;

//    @Autowired
//    private KieContainer kieContainer;

    @Autowired
    private WebSocketService webSocketService;

    private final KieServices kieServices = KieServices.Factory.get();

    public KieContainer getKieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules.drl"));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

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

    private List<String> getRecordFromLine(String line) throws IOException {
        String[] split = line.split(",");
        List<String> values = new ArrayList<String>(Arrays.stream(split).collect(Collectors.toList()));
        return values;
    }

    private void readMessagesWriteToMongo(String path, Device dev, String regex) throws IOException, ParseException {

        System.out.println("USAO SAM U TASK: "+ path);
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

            if(path.startsWith("doorlock")){
                String[] split = s.get(0).split(" ");
                Integer seconds = Integer.parseInt(split[4]);

                KieSession kieSession = getKieContainer().newKieSession();
                DroolsDTO fdRequest = new DroolsDTO();
                fdRequest.setDeviceType(path);
                fdRequest.setValue(seconds);
                kieSession.insert(fdRequest);
                kieSession.fireAllRules();
                kieSession.dispose();
                System.out.println("USAO SAM OVDE NE doorlock dto: " + Arrays.toString(split) + " is alarm: " + fdRequest.isAlarm());

                if(fdRequest.isAlarm()){
                    DeviceAlarm alarm = new DeviceAlarm();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=dateFormat.parse(s.get(1));
                    alarm.setDate(date);
                    alarm.setMessage(s.get(0));
                    alarm.setDevice(dev);
                    deviceAlarmRepository.save(alarm);

                    webSocketService.sendMessage("alarm", "Alarm happened on: " + dev.getPathToFile());

                }
            }
            else if(path.startsWith("refrigerator")){
                String[] split = s.get(0).split(" ");
                Integer seconds = Integer.parseInt(split[2]);

                KieSession kieSession = getKieContainer().newKieSession();
                DroolsDTO fdRequest = new DroolsDTO();
                fdRequest.setDeviceType(path);
                fdRequest.setValue(seconds);
                kieSession.insert(fdRequest);
                kieSession.fireAllRules();
                kieSession.dispose();
                System.out.println("USAO SAM OVDE NE refrigerator dto: " + Arrays.toString(split) + " is alarm: " + fdRequest.isAlarm());

                if(fdRequest.isAlarm()){
                    DeviceAlarm alarm = new DeviceAlarm();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=dateFormat.parse(s.get(1));
                    alarm.setDate(date);
                    alarm.setMessage(s.get(0));
                    alarm.setDevice(dev);
                    deviceAlarmRepository.save(alarm);

                    webSocketService.sendMessage("alarm", "Alarm happened on: " + dev.getPathToFile());

                }
            }
            else if(path.startsWith("airconditioner")){
                String[] split = s.get(0).split(" ");
                Integer seconds = Integer.parseInt(split[3]);

                KieSession kieSession = getKieContainer().newKieSession();
                DroolsDTO fdRequest = new DroolsDTO();
                fdRequest.setDeviceType(path);
                fdRequest.setValue(seconds);
                kieSession.insert(fdRequest);
                kieSession.fireAllRules();
                kieSession.dispose();
                System.out.println("USAO SAM OVDE NE airconditioner dto: " + Arrays.toString(split) + " is alarm: " + fdRequest.isAlarm());

                if(fdRequest.isAlarm()){
                    DeviceAlarm alarm = new DeviceAlarm();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=dateFormat.parse(s.get(1));
                    alarm.setDate(date);
                    alarm.setMessage(s.get(0));
                    alarm.setDevice(dev);
                    deviceAlarmRepository.save(alarm);

                    webSocketService.sendMessage("alarm", "Alarm happened on: " + dev.getPathToFile());


                }
            }
            else if(path.startsWith("dishwasher")){
                String[] split = s.get(0).split(" ");
                Integer seconds = Integer.parseInt(split[3]);

                KieSession kieSession = getKieContainer().newKieSession();
                DroolsDTO fdRequest = new DroolsDTO();
                fdRequest.setDeviceType(path);
                fdRequest.setValue(seconds);
                kieSession.insert(fdRequest);
                kieSession.fireAllRules();
                kieSession.dispose();
                System.out.println("USAO SAM OVDE NE dishwasher dto: " + Arrays.toString(split) + " is alarm: " + fdRequest.isAlarm());

                if(fdRequest.isAlarm()){
                    DeviceAlarm alarm = new DeviceAlarm();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=dateFormat.parse(s.get(1));
                    alarm.setDate(date);
                    alarm.setMessage(s.get(0));
                    alarm.setDevice(dev);
                    deviceAlarmRepository.save(alarm);

                    webSocketService.sendMessage("alarm", "Alarm happened on: " + dev.getPathToFile());

                }
            }
            else if(path.startsWith("door")){
                String[] split = s.get(0).split(" ");
                Integer seconds = Integer.parseInt(split[3]);

                KieSession kieSession = getKieContainer().newKieSession();
                DroolsDTO fdRequest = new DroolsDTO();
                fdRequest.setDeviceType(path);
                fdRequest.setValue(seconds);
                kieSession.insert(fdRequest);
                kieSession.fireAllRules();
                kieSession.dispose();
                System.out.println("USAO SAM OVDE NE door dto: " + path + Arrays.toString(split) + " is alarm: " + fdRequest.isAlarm());

                if(fdRequest.isAlarm()){
                    DeviceAlarm alarm = new DeviceAlarm();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=dateFormat.parse(s.get(1));
                    alarm.setDate(date);
                    alarm.setMessage(s.get(0));
                    alarm.setDevice(dev);
                    deviceAlarmRepository.save(alarm);

                    webSocketService.sendMessage("alarm", "Alarm happened on: " + dev.getPathToFile());


                }
            }

            Message message = new Message();
            message.setMessage(s.get(0));
            message.setDevice(dev.getPathToFile());
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
