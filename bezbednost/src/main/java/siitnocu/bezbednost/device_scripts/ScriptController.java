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
        dev.writeMessage("Door opened for 3 seconds");
        dev.writeMessage("smece***1");

        return "Success";
    }

    @PostMapping("/attackState/door/{pathToFile}")
    public String attackStateDoor(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Door opened for 10 seconds");
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
        dev.writeMessage("Failed to open doorlock 3 times");
        return "Success";
    }

    @PostMapping("/normalState/doorlock/{pathToFile}")
    public String normalStateDoorlock(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Failed to open doorlock 1 times");
        return "Success";
    }

    @PostMapping("/normalState/dishwasher/{pathToFile}")
    public String normalStateDishwasher(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Dishwasher water level 30 cm");
        return "Success";
    }

    @PostMapping("/attackState/dishwasher/{pathToFile}")
    public String attackStateDishwasher(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Dishwasher water level 60 cm");
        return "Success";
    }

    @PostMapping("/normalState/refrigerator/{pathToFile}")
    public String normalStateRefrigerator(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Refrigerator temprature 5");
        return "Success";
    }

    @PostMapping("/attackState/refrigerator/{pathToFile}")
    public String attackStateRefrigerator(@PathVariable String pathToFile) throws InterruptedException {
        Optional<Device> device = deviceRepository.findByPathToFile(pathToFile);
        Device dev = device.get();
        dev.writeMessage("Refrigerator temprature 15");
        return "Success";
    }
}
