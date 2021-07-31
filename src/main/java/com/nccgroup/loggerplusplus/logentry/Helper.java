package com.nccgroup.loggerplusplus.logentry;

import burp.IParameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Helper {
    static int SIGNATURE_SIZE = 16; // 16 bytes
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static JsonElement parseListToJson(List<IParameter> arr) {
        JsonObject json = new JsonObject();
        Map<String, String> result = new HashMap<String, String>();
        for (IParameter val : arr) {
            json.addProperty(val.getName(), val.getValue());
        }
        return json;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String generateSignature() {
        byte[] b = new byte[Helper.SIGNATURE_SIZE];
        new Random().nextBytes(b);
        return bytesToHex(b);
    }

    // Heuristically determine if a string is non printable
    // If the number of non-ascii character larger than half of length of string, then it is high chance non-printable
    private static boolean isNonPrintableStringHeur(String s) {
        int cnt = 0;
        for (int i = 0; i < s.length(); i += 1) {
            int cur = s.charAt(i);
            if (cur > 0x7F) cnt += 1;
        }
        return cnt * 2 >= s.length();
    }

    public static List<String> removeNonPrintableString(List<String> arr) {
        List<String> result = new ArrayList<String>();
        for (String e: arr) {
            if (!isNonPrintableStringHeur(e)) result.add(e);
        }
        return result;
    }

    // Return array of string after unique
    public static List<String> uniqueArray(List<String> arr) {
        var check = new HashMap<String, Boolean>();
        var result = new ArrayList<String>();
        for (String e: arr) {
            if (check.get(e) == null) {
                check.put(e, true);
                result.add(e);
            }
        }
        return result;
    }

    public static JsonArray toJsonArray(List<String> arr) {
        JsonArray ja = new JsonArray();
        for (String val : arr) {
            ja.add(val);
        }
        return ja;
    }

    public static String hmac(byte[] key, byte[] s) {
        String algorithm = "HmacSHA256";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(s);
            return Hex.encodeHexString(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return "";
        }
    }
}
