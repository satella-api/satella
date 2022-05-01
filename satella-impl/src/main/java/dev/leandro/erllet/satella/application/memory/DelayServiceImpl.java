package dev.leandro.erllet.satella.application.memory;

import dev.leandro.erllet.satella.exception.DelayHitException;
import dev.leandro.erllet.satella.service.DelayService;
import dev.leandro.erllet.satella.service.MemoryService;
import lombok.NonNull;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
class DelayServiceImpl implements DelayService {

    @Autowired
    private MemoryService memoryService;

    @Override
    public void assertDelay(@NonNull CommandSender sender, @NonNull String key, long delay,
                            @NonNull TimeUnit delayUnit) {
        val realKey = getDelayKey(sender, key);
        val now = System.currentTimeMillis();
        if (memoryService.containsKey(realKey)) {
            val lastDelay = memoryService.get(realKey, Long.class);
            if (now - lastDelay <= delayUnit.toMillis(delay)) {
                throw new DelayHitException("Aguarde para executar esta ação novamente");
            }
        }
        memoryService.set(realKey, now, delay, delayUnit);
    }

    @Override
    public void resetDelay(@NonNull CommandSender sender, @NonNull String key) {
        memoryService.remove(getDelayKey(sender, key));
    }

    @Override
    public boolean isInDelay(@NonNull CommandSender sender, @NonNull String key) {
        return memoryService.containsKey(getDelayKey(sender, key));
    }

    private String getDelayKey(CommandSender sender, String key) {
        return String.join(":", "delay", sender.getName().toLowerCase(), key);
    }
}
