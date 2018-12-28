package com.ingran.util;

public class PassWordGenerator {

    public static String NUMEROS = "0123456789";

    public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    public static String ESPECIALES = "ñÑ";

    public static String getPinNumber() {
        return getPassword(NUMEROS, 4);
    }

    public static String getPassword() {
        return getPassword(8);
    }

    public static String getPassword(int length) {
        return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }

    public static String getPassword(String key, int length) {
        String pswd = "";

        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }

        return pswd;
    }
    
//    public static void main(String[] args) {
//        System.out.println(PassWordGenerator.getPinNumber());
//        System.out.println(PassWordGenerator.getPassword());
//        System.out.println(PassWordGenerator.getPassword(10));
//        System.out.println(PassWordGenerator.getPassword(NUMEROS, 6));
//        System.out.println(PassWordGenerator.getPassword(MAYUSCULAS, 6));
//        System.out.println(PassWordGenerator.getPassword(MINUSCULAS, 6));
//        System.out.println(PassWordGenerator.getPassword(ESPECIALES, 6));
//    }
}
