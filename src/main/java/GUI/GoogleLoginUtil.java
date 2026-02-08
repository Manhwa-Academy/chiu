package GUI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.util.List;

public class GoogleLoginUtil {

        public static String login() throws Exception {

                // Tải thông tin client secrets từ credentials.json
                GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                                GsonFactory.getDefaultInstance(),
                                new InputStreamReader(
                                                GoogleLoginUtil.class.getResourceAsStream("/credentials.json")));

                // Tạo đối tượng GoogleAuthorizationCodeFlow để quản lý OAuth
                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                                new NetHttpTransport(),
                                GsonFactory.getDefaultInstance(),
                                clientSecrets,
                                List.of(
                                                "https://www.googleapis.com/auth/userinfo.email",
                                                "https://www.googleapis.com/auth/userinfo.profile"))
                                .setDataStoreFactory(new MemoryDataStoreFactory())
                                .setAccessType("offline")
                                .build();

                // Khởi tạo LocalServerReceiver để xử lý OAuth
                LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                                .setPort(8888)
                                .build();

                // Lấy quyền truy cập người dùng
                Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
                                .authorize("user");

                // Tạo requestFactory để gửi yêu cầu HTTP đến Google API
                HttpRequestFactory requestFactory = new NetHttpTransport()
                                .createRequestFactory(req -> req.getHeaders()
                                                .setAuthorization("Bearer " + credential.getAccessToken()));

                // Gửi yêu cầu lấy thông tin người dùng từ Google
                HttpResponse response = requestFactory
                                .buildGetRequest(new GenericUrl("https://www.googleapis.com/oauth2/v3/userinfo"))
                                .execute();

                // Phân tích kết quả JSON trả về từ Google API
                JsonObject json = JsonParser.parseReader(
                                new InputStreamReader(response.getContent())).getAsJsonObject();

                // Lấy tên, email và URL ảnh đại diện
                String name = json.get("name").getAsString();
                String email = json.get("email").getAsString();
                String pictureUrl = json.get("picture").getAsString(); // Lấy URL ảnh đại diện từ Google

                // Trả về tên, email và URL ảnh đại diện
                return name + "|" + email + "|" + pictureUrl;
        }
}
