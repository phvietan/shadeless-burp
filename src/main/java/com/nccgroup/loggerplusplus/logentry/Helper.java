package com.nccgroup.loggerplusplus.logentry;

import com.google.gson.JsonArray;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Helper {
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
