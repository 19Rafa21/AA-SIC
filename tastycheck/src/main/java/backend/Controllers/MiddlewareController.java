package backend.Controllers;

import backend.Services.JwtService;

import javax.annotation.processing.SupportedSourceVersion;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.Filter;

public class MiddlewareController implements Filter {
	private JwtService jwtService = new JwtService();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		String uri = req.getRequestURI();
		String method = req.getMethod();


		boolean requiresAuth = (
				(uri.startsWith("/tastycheck/review") && method.equals("POST")) ||
				(uri.startsWith("/tastycheck/reply") && method.equals("POST")) ||
				(uri.startsWith("/tastycheck/restaurant") && method.equals("POST")) ||
				(uri.startsWith("/tastycheck/user") && method.equals("PUT"))
		);

		if (requiresAuth){

			Cookie[] cookies = req.getCookies();
			String token = null;

			if (cookies != null){
				for (Cookie c : cookies){
					if ("token".equals(c.getName())){
						token = c.getValue();
						break;
					}
				}
			}

			if (token == null || !jwtService.validateToken(token)) {

				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.setContentType("application/json; charset=UTF-8");
				resp.getWriter().write("{\"message\": \"Acesso não autorizado.\"}");
				return;
			}

			// Extração do userId e role para usar nos endpoints
			String userId = jwtService.extractUserId(token);
			String role = jwtService.extractUserRole(token);

			req.setAttribute("userId", userId);
			req.setAttribute("role", role);
		}

		chain.doFilter(request, response);
	}
}
