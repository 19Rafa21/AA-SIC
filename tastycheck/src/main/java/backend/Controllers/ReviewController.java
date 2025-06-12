package backend.Controllers;

import backend.DTOs.Restaurant.RestaurantDTO;
import backend.DTOs.Review.RegisterReviewDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.DTOs.UserDTO;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Models.User;
import backend.Services.RestaurantService;
import backend.Services.ReviewService;
import backend.Services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import org.orm.PersistentException;

public class ReviewController extends HttpServlet {

	private ReviewService reviewService;

	@Override
	public void init() throws ServletException {
		reviewService = new ReviewService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pathInfo = request.getPathInfo(); // isto vai ser /abc123 ou /abc123/restaurant etc.
		System.out.println("PathInfo: " + pathInfo);

		response.setContentType("application/json");

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				// → LISTA TODAS AS REVIEWS
				List<Review> reviews = reviewService.getAllReviews();
				List<ReviewDTO> dtos = reviews.stream()
						.map(ReviewDTO::new)
						.toList();
				Gson gson = new Gson();
				response.getWriter().println(gson.toJson(dtos));

			} else {
				// Trata paths como /{id} ou /{id}/restaurant ou /{id}/author
				String[] parts = pathInfo.split("/");
				if (parts.length >= 2) {
					String reviewId = parts[1];

					Review review = reviewService.getReviewById(reviewId);
					if (review == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND, "Review não encontrada.");
						return;
					}

					if (parts.length == 2) {
						// → DEVOLVE A REVIEW COM AQUELA ID
						Gson gson = new Gson();
						ReviewDTO reviewDTO = new ReviewDTO(review);
						response.getWriter().println(gson.toJson(reviewDTO));
					} else if (parts.length == 3) {
						String subResource = parts[2];
						Gson gson = new Gson();

						if ("restaurant".equals(subResource)) {
							RestaurantDTO restaurantDTO = new RestaurantDTO(review.getRestaurant());
							response.getWriter().println(gson.toJson(restaurantDTO));
						} else if ("author".equals(subResource)) {
							UserDTO userDTO = new UserDTO(review.getAuthor());
							response.getWriter().println(gson.toJson(userDTO));
						} else {
							response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sub-recurso não encontrado.");
						}
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Gson gson = new Gson();
			RegisterReviewDTO reviewDTO = gson.fromJson(readBodyJson(request), RegisterReviewDTO.class);

			boolean saved = reviewService.registerReview(reviewDTO);
			if (saved) {
				response.setContentType("application/json");
				response.getWriter().println("{\"status\": \"review registado com sucesso\"}");
			}

		} catch (PersistentException | UserException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}

	}

	private String readBodyJson(HttpServletRequest req) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
