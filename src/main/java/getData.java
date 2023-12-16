import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class getData {
    public static void main(String[] args) throws IOException {
        int pagesize = 100;
        for (int page = 1; page <= 10; page++) {
            String prex = "https://api.stackexchange.com/2.3/questions?page=";
            String url = prex + page + "&pagesize=" + pagesize + "&todate=1702512000&order=asc&sort=activity&tagged=java&site=stackoverflow";

            try {
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                // 设置请求头，告诉服务器我们接受gzip压缩的数据
                connection.setRequestProperty("Accept-Encoding", "gzip");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();

                    // 检查响应头中是否有gzip编码的标志
                    String contentEncoding = connection.getHeaderField("Content-Encoding");
                    if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                        // 将gzip响应保存为gzip文件
                        FileOutputStream fileOutputStream = new FileOutputStream("result_page_" + page + ".gz");
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, len);
                        }
                        fileOutputStream.close();

                        // 解压缩gzip文件
                        GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream("result_page_" + page + ".gz"));
                        FileOutputStream outputStream = new FileOutputStream("result_page_" + page + ".json");
                        byte[] buf = new byte[1024];
                        int len1;
                        while ((len1 = gzipInputStream.read(buf)) > 0) {
                            outputStream.write(buf, 0, len1);
                        }
                        gzipInputStream.close();
                        outputStream.close();
                    } else {
                        System.out.println("响应不是gzip格式");
                    }
                } else {
                    System.out.println("请求失败，响应码：" + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}