package com.shingo.zyl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ThreadUtils {

    public static String getStringFromInput(InputStream in) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int c = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((c = in.read(buffer)) != -1) {
                bos.write(buffer,0,c);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bos.toByteArray());
    }
}
