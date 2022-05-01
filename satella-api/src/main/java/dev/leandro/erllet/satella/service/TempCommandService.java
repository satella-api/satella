package dev.leandro.erllet.satella.service;

import dev.alangomes.springspigot.util.scheduler.SchedulerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class TempCommandService {

    @Getter
    private HashMap<UUID, Runnable> actions = new HashMap<>();

    @Autowired
    private SchedulerService schedulerService;

    public String createTempCommand(Runnable r, Integer expires) {
        final UUID uuid = UUID.randomUUID();
        actions.put(uuid, r);
        if (expires > 0) {
            schedulerService.scheduleSyncDelayedTask(() -> actions.remove(uuid), expires);
        }
        return "/chataction " + uuid.toString();

    }

    public String createTempCommand(Runnable r) {
        return createTempCommand(r, 1);
    }
}
