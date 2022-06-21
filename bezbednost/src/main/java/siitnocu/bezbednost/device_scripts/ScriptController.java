package siitnocu.bezbednost.device_scripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import siitnocu.bezbednost.data.Device;
import siitnocu.bezbednost.dto.EstateDTO;
import siitnocu.bezbednost.repositories.DeviceRepository;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(value = "/api/scripts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Transactional
public class ScriptController {

    @Autowired
    private DeviceRepository deviceRepository;



    @PostMapping("/normalState/door/{pathToFile}")
    public String normalStateDoor(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Door opened");
        dev.writeMessage("Door closed");
        Thread.sleep(3000);
        dev.writeMessage("Door opened");
        dev.writeMessage("Door closed");
        return "Success";
    }

    @PostMapping("/attackState/door/{pathToFile}")
    public String attackStateDoor(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Door opened");
        dev.writeMessage("Door closed");
        Thread.sleep(500);
        dev.writeMessage("Door opened");
        dev.writeMessage("Door closed");
        Thread.sleep(500);
        dev.writeMessage("Door opened");
        dev.writeMessage("Door closed");
        return "Success";
    }

    @PostMapping("/normalState/airConditioner/{pathToFile}")
    public String normalStateAirConditioner(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Temp set to 23");
        dev.writeMessage("Temp set to 21");
        return "Success";
    }

    @PostMapping("/attackState/airConditioner/{pathToFile}")
    public String attackStateAirConditioner(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Temp set to 10");
        return "Success";
    }

    @PostMapping("/attackState/doorlock/{pathToFile}")
    public String attackStateDoorlock(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Failed to open doorlock");
        dev.writeMessage("Failed to open doorlock");
        dev.writeMessage("Failed to open doorlock");
        return "Success";
    }

    @PostMapping("/normalState/doorlock/{pathToFile}")
    public String normalStateDoorlock(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Opened doorlock");
        dev.writeMessage("Locked doorlock");
        dev.writeMessage("Opened doorlock");
        dev.writeMessage("Locked doorlock");

        return "Success";
    }
}
