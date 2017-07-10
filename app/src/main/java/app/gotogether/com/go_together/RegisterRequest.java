package app.gotogether.com.go_together;

/**
 * Created by seuls on 2017-07-10.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://211.253.9.84/Register.php"; /* 한이음 서버에다 만들기 - 예슬 우분투에 만들어 놓음 */
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String nickname, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("nickname", nickname + "");
        params.put("username", username);
        params.put("password", password);
        }

@Override
public Map<String, String> getParams() {
        return params;
        }
}