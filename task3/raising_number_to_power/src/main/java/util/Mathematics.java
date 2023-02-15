package util;

public class Mathematics {
    public static double pow(double number, int pow){
        Double result = 1.0;

        while (pow > 0){
            if (pow % 2 ==1){
                result *= number;
            }
            pow >>= 1;
            number *= number;
        }
        return result;
    }
}
