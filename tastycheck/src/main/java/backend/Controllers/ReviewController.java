package backend.Controllers;

import backend.DTOs.Reply.ReplyDTO;
import backend.DTOs.Restaurant.RestaurantDTO;
import backend.DTOs.Review.RegisterReviewDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.DTOs.Review.UpdateReviewDTO;
import backend.DTOs.UserDTO;
import backend.Exceptions.UnauthorizedException;
import backend.Exceptions.UserException;
import backend.Models.Review;
import backend.Services.ImageService;
import backend.Services.RestaurantService;
import backend.Services.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backend.Utils.HttpRequestUtils;
import com.google.gson.Gson;
import org.orm.PersistentException;

@MultipartConfig
public class ReviewController extends HttpServlet {

	private ReviewService reviewService;
	private RestaurantService restaurantService;
	private ImageService imageService;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		try {
			reviewService = new ReviewService();
			restaurantService = new RestaurantService();
			imageService = new ImageService();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pathInfo = request.getPathInfo();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				List<Review> reviews = reviewService.getAllReviews();
				List<ReviewDTO> dtos = reviews.stream()
						.map(ReviewDTO::new)
						.toList();
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
						ReviewDTO reviewDTO = new ReviewDTO(review);
						response.getWriter().println(gson.toJson(reviewDTO));
					} else if (parts.length == 3) {
						String subResource = parts[2];

						if ("restaurant".equals(subResource)) {
							RestaurantDTO restaurantDTO = new RestaurantDTO(review.getRestaurant());
							response.getWriter().println(gson.toJson(restaurantDTO));
						} else if ("author".equals(subResource)) {
							UserDTO userDTO = new UserDTO(review.getAuthor());
							response.getWriter().println(gson.toJson(userDTO));
						} else if ("replies".equals(subResource)) {
							List<ReplyDTO> replyDTOs = review.getReplies().stream()
									.map(ReplyDTO::new)
									.toList();

							response.getWriter().println(gson.toJson(replyDTOs));
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
		String userId = (String) request.getAttribute("userId");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		try {
			// 1. Lê o campo JSON
			String reviewJson = request.getParameter("review");
			RegisterReviewDTO reviewDTO = gson.fromJson(reviewJson, RegisterReviewDTO.class);

			// 2. Verifica se o utilizador é o dono do restaurante
			String restaurantId = reviewDTO.getRestaurantId();
			if (restaurantService.isOwnerOfRestaurant(userId, restaurantId)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Não pode fazer review do seu próprio restaurante.");
				return;
			}

			// 3. Processa imagens se existirem
			List<String> uploadedReviewImages = new ArrayList<>();
			for (Part part : request.getParts()) {
				if (part.getName().equals("reviewImages") &&
						part.getSize() > 0 &&
						part.getContentType().startsWith("image/")) {

					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					try (InputStream contentStream = part.getInputStream()) {
						if (contentStream != null) {
							String uploadedPath = imageService.uploadImage(fileName, contentStream);
							uploadedReviewImages.add(uploadedPath);
						}
					} catch (IOException e) {
						throw new UnauthorizedException("Erro ao fazer upload da imagem: " + e.getMessage());
					}
				}
			}

			// 4. Anexa as imagens à review (se houver)
			reviewDTO.setImagesReview(uploadedReviewImages);

			// 5. Regista a review
			boolean saved = reviewService.registerReview(reviewDTO);
			if (saved) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println("{\"status\": \"review registado com sucesso\"}");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao guardar a review.");
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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		String pathInfo = request.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da review é obrigatório na URL.");
			return;
		}

		String id = pathInfo.substring(1);

		try {
			String reviewJson = request.getParameter("review");

			UpdateReviewDTO reviewDTO = gson.fromJson(reviewJson, UpdateReviewDTO.class);

			Collection<Part> reviewPart = request.getParts().stream()
					.filter(p -> p.getName().equals("reviewImages"))
					.toList();

			if (!reviewPart.isEmpty()){
				List<String> uploadedReviewImages = new ArrayList<>();
				for (Part part : reviewPart){
					if (part.getSize() > 0 && part.getContentType().startsWith("image/")){
						String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
						InputStream contentStream = part.getInputStream();
						if (contentStream != null){
							try {
								String uploadedPath = imageService.uploadImage(filename, contentStream);
								uploadedReviewImages.add(uploadedPath);
							} catch (IOException e){
								throw new UnauthorizedException("Error while adding review image: " + e.getMessage());
							}
						}
					}
				}
				reviewDTO.setReviewImages(uploadedReviewImages);
			} else {

				Review review = reviewService.getReviewById(id);
				List<String> reviewsImages = new ArrayList<>(review.getImagesReview());
				for (String i : reviewsImages){
					imageService.deleteImage(i);
				}
			}

			if ((reviewDTO.getText() == null || reviewDTO.getText().isEmpty()) && reviewDTO.getRating() == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhum campo fornecido para atualizar.");
				return;
			}

			boolean updated = reviewService.updateReview(id, reviewDTO);
			if (updated){
				response.getWriter().println("{\"status\": \"Review atualizada com sucesso.\"}");
			} else {
				response.getWriter().println("{\"status\": \"Erro ao atualizar a Review.\"}");
			}

		} catch (IOException | PersistentException e){
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
}
