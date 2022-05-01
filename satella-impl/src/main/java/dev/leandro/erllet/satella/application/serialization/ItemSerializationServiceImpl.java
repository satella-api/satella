package dev.leandro.erllet.satella.application.serialization;


import dev.leandro.erllet.satella.service.ItemSerializationService;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class ItemSerializationServiceImpl implements ItemSerializationService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public Stream<ItemStack> deSerialize(String serializedItems) {
        return Arrays.stream(serializedItems.split(";"))
                .filter(StringUtils::isNotBlank)
                .map(serialized -> conversionService.convert(serialized, ItemStack.class));
    }

    @Override
    public ItemStack deSerializeSingle(String serialized) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack item = (ItemStack) dataInput.readObject();
            dataInput.close();
            return item.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String serialize(ItemStack[] items) {
        return Arrays.stream(items)
                .filter(Objects::nonNull)
                .filter(item -> item.getType() != Material.AIR)
                .map(item -> conversionService.convert(item, String.class))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(";"));
    }

    @Override
    public String serialize(List<ItemStack> items) {
        return serialize(items.toArray(new ItemStack[0]));
    }

    @Override
    public String serialize(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(item.clone());

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception ignored) {
            return null;
        }
    }

}
