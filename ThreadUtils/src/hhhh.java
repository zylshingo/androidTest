import com.shingo.zyl.ThreadUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/8/26.
 */
public class hhhh {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream in = connection.getInputStream();
                String stringFromInput = ThreadUtils.getStringFromInput(in);
                System.out.println(stringFromInput);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
