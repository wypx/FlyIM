package me.smart.mylibrary.utils.encoder;

import java.util.Random;

public class RandomUtils
{
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new AssertionError();
    }

    //随机字符串
    public static String getRandomNumbersAndLetters(int length) 
    {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    //得到固定长度的和数字混合随机字符串
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    //得到一个固定长度的随机字符串，它是大小写字母混合
    public static String getRandomLetters(int length) 
    {
        return getRandom(LETTERS, length);
    }

    //得到一个固定长度的随机字符串，它是大写字母混合
    public static String getRandomCapitalLetters(int length) 
    {
        return getRandom(CAPITAL_LETTERS, length);
    }

   
    //得到一个固定长度的随机字符串，它是小写字母混合
    public static String getRandomLowerCaseLetters(int length)
    {
        return getRandom(LOWER_CASE_LETTERS, length);
    }
    
    public static boolean isEmpty(CharSequence str) 
    {
        return (str == null || str.length() == 0);
    }
    //得到一个固定长度的随机字符串，它在source字符的混合 
    public static String getRandom(String source, int length) 
    {
        return isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    //得到一个固定长度的随机字符串，字符它在sourceChar的混合物
    public static String getRandom(char[] sourceChar, int length) 
    {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) 
        {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) 
        {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    //0-MAX的随机数
    public static int getRandom(int max) 
    {
        return getRandom(0, max);
    }

    // min-max 的随机数
    public static int getRandom(int min, int max) 
    {
        if (min > max) 
        {
            return 0;
        }
        if (min == max) 
        {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }
    //洗牌算法，随机permutes使用随机的默认源指定数组
    public static boolean shuffle(Object[] objArray)
    {
        if (null == objArray ) 
        {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }

    //洗牌算法，随机permutes指定数组
    public static boolean shuffle(Object[] objArray, int shuffleCount) 
    {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) 
        {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) 
        {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

   //洗牌算法，随机permutes使用随机的默认源指定的int数组
    public static int[] shuffle(int[] intArray) 
    {
        if (null == intArray) 
        {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

   //洗牌算法，随机permutes指定的int数组
    public static int[] shuffle(int[] intArray, int shuffleCount) 
    {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) 
        {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) 
        {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }
}
