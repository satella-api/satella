package dev.leandro.erllet.satella.application.serialization;


import dev.leandro.erllet.satella.service.RomanNumberService;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

@Service
public class RomanNumberServiceImpl implements RomanNumberService {

    private final static TreeMap<Integer, String> map = new TreeMap<>();


    @PostConstruct
    private void init() {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    @Override
    public String toRoman(int number) {
        val floorKey = map.floorKey(number);
        return number == floorKey ? map.get(number) : map.get(floorKey) + toRoman(number - floorKey);
    }
}
