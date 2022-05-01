package dev.leandro.erllet.satella.application.lang;

import dev.leandro.erllet.satella.service.LanguageService;
import com.dslplatform.json.DslJson;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

@Log4j2
@Service
class LanguageServiceImpl implements LanguageService {

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<String, String> languages = new HashMap<>();

    @PostConstruct
    @SneakyThrows
    void init() {
        val langResource = resourceLoader.getClassLoader().getResourceAsStream("lang.json");
        if (langResource != null) {
            val dslJson = new DslJson<>();
            val deserialized = dslJson.deserialize(Map.class, langResource);
            languages.putAll(deserialized);
        } else {
            log.warn("Arquivo de mensagens não encontrado em lang.json dentro do plugin");
        }
    }

    @Override
    public Optional<String> getMessage(String key, Object... args) {
        return Optional.ofNullable(languages.get(key))
                .map(message -> args.length > 0 ? String.format(message, args) : message);
    }

    @Override
    public String getItemName(ItemStack item, boolean showAmount) {
        if (item == null) return (showAmount ? "1 " : "") + getItemName(Material.AIR);
        val itemMeta = item.getItemMeta();
        String displayName;
        if (itemMeta != null && isNotBlank(itemMeta.getDisplayName())) {
            displayName = itemMeta.getDisplayName();
        } else {
            val type = item.getType();
            if (itemMeta instanceof PotionMeta) {
                displayName = getPotionName(type, (PotionMeta) itemMeta);
            } else {
                displayName = getItemName(type);
            }
        }
        return showAmount ? String.format("%d %s", item.getAmount(), displayName) : displayName;
    }

    @Override
    public String getItemName(Material type) {
        if (type == null) return getItemName(Material.AIR);
        val prefix = type.isBlock() ? "block" : "item";
        val suffix = ".minecraft." + type.name().toLowerCase();
        return getMessage(prefix + suffix)
                .orElseGet(() -> getMessage((type.isBlock() ? "item" : "block") + suffix)
                        .orElseGet(() -> capitalizeFully(type.name().replaceAll("_", " "))));
    }

    private String getPotionName(Material type, PotionMeta meta) {
        val potionData = meta.getBasePotionData();
        String prefix;
        if (Material.TIPPED_ARROW.equals(type))
            prefix = "item.minecraft.tipped_arrow";
        else if (Material.SPLASH_POTION.equals(type))
            prefix = "item.minecraft.splash_potion";
        else if (potionData.isExtended())
            prefix = "item.minecraft.lingering_potion";
        else
            prefix = "item.minecraft.potion";
        val potionType = potionData.getType();
        String name = switch (potionType) {
            case INSTANT_HEAL -> "healing";
            case INSTANT_DAMAGE -> "harm";
            case SLOWNESS -> "slowness";
            case STRENGTH -> "strength";
            case SPEED -> "swiftness";
            case TURTLE_MASTER -> "turtle_master";
            default -> (potionType.getEffectType() != null ? potionType.getEffectType().getName() : potionType.name()).toLowerCase();
        };
        return getMessage(prefix + ".effect." + name).orElseGet(() -> getItemName(Material.POTION));
    }
}
