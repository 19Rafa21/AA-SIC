package backend.Controllers;

import backend.DTOs.Restaurant.RestaurantDTO;
import backend.DTOs.Review.RegisterReviewDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.DTOs.Review.UpdateReviewDTO;
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

		String pathInfo = request.getPathInfo();
		//System.out.println("PathInfo: " + pathInfo);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				List<Review> reviews = reviewService.getAllReviews();
				List<ReviewDTO> dtos = reviews.stream()
						.map(ReviewDTO::new)
						.toList();
				Gson gson = new Gson();
				response.getWriter().println(gson.toJson(dtos));

			} else {
				String[] parts = pathInfo.split("/");
				if (parts.length >= 2) {
					String reviewId = parts[1];

					Review review = reviewService.getReviewById(reviewId);
					if (review == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND, "Review não encontrada.");
						return;
					}

					if (parts.length == 2) {
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
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().println("{\"status\": \"review registado com sucesso\"}");
			}

		} catch (PersistentException | UserException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da review é obrigatório na URL.");
			return;
		}

		String id = pathInfo.substring(1);

		try {
			Gson gson = new Gson();
			UpdateReviewDTO updateDTO = gson.fromJson(readBodyJson(request), UpdateReviewDTO.class);

			if ((updateDTO.getText() == null || updateDTO.getText().isEmpty()) && updateDTO.getRating() == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhum campo fornecido para atualizar.");
				return;
			}

			reviewService.updateReview(id, updateDTO);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().println("{\"status\": \"Review atualizada com sucesso.\"}");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da review não especificado.");
				return;
			}

			String reviewId = pathInfo.split("/")[1];

			Review review = reviewService.getReviewById(reviewId);
			if (review == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Review não encontrada.");
				return;
			}

			boolean deleted = reviewService.deleteReview(review);
			if (deleted) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().println("{\"status\": \"review removida com sucesso\"}");
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().println("{\"status\": \"Erro ao remover a review\"}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
		}
	}

	private String readBodyJson(HttpServletRequest req) throws IOException {
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
