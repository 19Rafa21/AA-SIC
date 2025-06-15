package backend.Controllers;

import backend.DTOs.UserDTO;
import backend.Exceptions.UnauthorizedException;
import backend.Exceptions.UserException;
import backend.Services.AuthenticationService;
import backend.Utils.HttpRequestUtils;
import com.google.gson.Gson;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;
@MultipartConfig
public class AuthenticationController extends HttpServlet {

	private AuthenticationService authService;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		try {
			authService = new AuthenticationService();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.gson = new Gson();
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
			} else {
				String[] parts = pathInfo.split("/");
				String action = parts[1];

				if ("login".equals(action)) {
					try {
						UserDTO user = gson.fromJson(HttpRequestUtils.readBodyJson(req), UserDTO.class);
						String email = user.getEmail();
						String password = user.getPassword();

						Map<String, String> resultado = loginProcess(resp, email, password);
						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().write(gson.toJson(resultado));
					} catch (UnauthorizedException e) {
						e.printStackTrace();
						resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						resp.getWriter().write("{\"message\": \"Unauthorized: " + e.getMessage() + "\"}");
					} catch (Exception e){
						e.printStackTrace();
						resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						resp.getWriter().write("{\"message\": \"Error: " + e.getMessage() + "\"}");
					}

				} else if ("register".equals(action)) {
					try {
						String userJson = req.getParameter("user");

						Part file = req.getPart("file");

						boolean imageExist = file != null && file.getSize() > 0;

						if (imageExist){
							String originalFileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();

							InputStream fileContent = file.getInputStream();

							String contentType = file.getContentType();
							if (!contentType.startsWith("image/")){
								resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								resp.getWriter().write("{\"message\": Ficheiro não é uma imagem \"}");
							}

							UserDTO user = gson.fromJson(userJson, UserDTO.class);
							String email = user.getEmail();
							String password = user.getPassword();

							boolean saved = authService.registerWithImage(user, originalFileName, fileContent);
							if (saved) {
								Map<String, String> resultado = loginProcess(resp, email, password);
								resp.setStatus(HttpServletResponse.SC_OK);
								resp.getWriter().write(gson.toJson(resultado));
							}
						} else {

							UserDTO user = gson.fromJson(userJson, UserDTO.class);
							String email = user.getEmail();
							String password = user.getPassword();

							boolean saved = authService.register(user);
							if (saved) {
								Map<String, String> resultado = loginProcess(resp, email, password);
								resp.setStatus(HttpServletResponse.SC_OK);
								resp.getWriter().write(gson.toJson(resultado));
							}
						}




					} catch (UnauthorizedException e) {
						e.printStackTrace();
						resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						resp.getWriter().write("{\"message\": \"Unauthorized: " + e.getMessage() + "\"}");
					} catch (Exception e){
						e.printStackTrace();
						resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						resp.getWriter().write("{\"message\": \"Error: " + e.getMessage() + "\"}");
					}
				} else {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
				}
			}
		} catch (IOException e){
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addCookie(authService.deleteCookie());

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write("{\"message\": \"OK: Logout bem sucedido!\"}");
	}

	private Map<String, String> loginProcess(HttpServletResponse resp, String email, String password) throws PersistentException, UserException {
		String token = authService.login(email, password);

		resp.addCookie(authService.createCookie(email, password));
		String userId = authService.getUserIdFromToken(token);
		String userName = authService.getUserNameFromToken(token);


		return Map.of("token", token,
					"id", userId,
					"userName", userName,
					"email", email,
					"message", "Login bem sucedido com o " + email);
	}
}
