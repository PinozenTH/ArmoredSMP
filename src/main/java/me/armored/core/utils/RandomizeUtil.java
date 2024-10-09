package me.armored.core.utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomizeUtil {

    public static int randomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int randomInt(int number) {
        Random random = new Random();
        return random.nextInt(number);
    }

    public static float randomFloat(float max, float min) {
        Random random = new Random();
        float result = random.nextFloat() * (max - min) + min;
        result = (int) (result * 100) / 100.0f;
        return result;
    }

    public static float randomFloat(float number) {
        Random random = new Random();
        float result = random.nextFloat() * number;
        result = (int) (result * 100) / 100.0f;
        return result;
    }

    public static float random3Float(float number) {
        Random random = new Random();
        float result = random.nextFloat() * number;
        result = (int) (result * 1000) / 1000.0f;
        return result;
    }

    public static Object randomObject(ArrayList arrayList) {
        Random random = new Random();
        return arrayList.get(random.nextInt(arrayList.size()));
    }

    public static Boolean randomPercent(float percent) {
        return random3Float(100) <= percent;
    }
}
