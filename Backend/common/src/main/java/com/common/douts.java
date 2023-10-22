package com.common;

import java.util.Arrays;

public class douts {

    public static void main(String[] args) {
        String[] allowedString = new String[]{"/auth/login","auth/registration"};
        Arrays.stream(allowedString).anyMatch(e->{
            Boolean result = e.contains("/auth/login");
            System.out.println(result);
            return result;
        });

    }
}
