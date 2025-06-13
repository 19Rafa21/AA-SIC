package backend.Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestUtils {

	public static String readBodyJson(HttpServletRequest req) throws IOException, IOException {
		req.setCharacterEncoding("UTF-8");
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
