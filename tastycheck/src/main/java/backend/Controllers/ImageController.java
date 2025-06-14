package backend.Controllers;

import backend.Services.ImageService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig // para permitir upload multipart/form-data
public class ImageController extends HttpServlet {

	private ImageService imageService;

	@Override
	public void init() throws ServletException {
		String bucketName = "aa-sic-images-storage"; // Nome do bucket

		try {
			// Lê a chave como stream a partir do classpath (resources/keys)
			InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("aa-sic-storage-access.json");
			if (credentialsStream == null) {
				throw new RuntimeException("Chave JSON não encontrada no classpath!");
			}

			imageService = new ImageService(credentialsStream, bucketName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String objectName = request.getParameter("objectName");
		Part filePart = request.getPart("file");

		if (filePart == null || objectName == null || objectName.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta objectName ou file.");
			return;
		}

		try (InputStream inputStream = filePart.getInputStream()) {
			String contentType = filePart.getContentType();
			String publicUrl = imageService.uploadImage(objectName, inputStream, contentType);

			response.setContentType("application/json");
			response.getWriter().println(new Gson().toJson(new UploadResponse(publicUrl)));

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao fazer upload da imagem.");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String objectName = request.getParameter("objectName");

		if (objectName == null || objectName.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "objectName é obrigatório.");
			return;
		}

		try {
			imageService.deleteImage(objectName);

			response.setContentType("application/json");
			response.getWriter().println("{\"status\": \"Imagem removida com sucesso.\"}");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao remover a imagem.");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String objectName = request.getParameter("objectName");

		if (objectName == null || objectName.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "objectName é obrigatório.");
			return;
		}

		try {
			byte[] imageBytes = imageService.downloadImage(objectName);
			response.setContentType("image/png"); // ou image/png
			response.setContentLength(imageBytes.length);
			response.getOutputStream().write(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar imagem.");
		}
	}

	// pequeno DTO de resposta
	private static class UploadResponse {
		public String url;

		public UploadResponse(String url) {
			this.url = url;
		}
	}
}