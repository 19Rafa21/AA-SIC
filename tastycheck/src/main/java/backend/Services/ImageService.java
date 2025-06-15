package backend.Services;

import backend.Exceptions.UnauthorizedException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ImageService {

	private final Storage storage;
	private static final String bucketName = "aa-sic-images-storage";

	public ImageService() throws IOException {
		InputStream credentials = getClass().getClassLoader().getResourceAsStream("aa-sic-storage-access.json");
		StorageOptions storageOptions = StorageOptions.newBuilder()
				.setCredentials(GoogleCredentials.fromStream(credentials))
				.build();

		this.storage = storageOptions.getService();
	}

	/*public ImageService(InputStream credentialsStream, String bucketName) throws IOException {
		this.bucketName = bucketName;
		StorageOptions storageOptions = StorageOptions.newBuilder()
				.setCredentials(GoogleCredentials.fromStream(credentialsStream))
				.build();

		storage = storageOptions.getService();
	}
	*/

	//public String uloadUserProfileImage(String foldername, String objectName, MultipartFile file)

	// Upload de imagem
	public String uploadImage(String objectName, InputStream inputStream, String contentType) throws IOException {
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
				.setContentType(contentType)
				.build();

		storage.create(blobInfo, inputStream);

		// URL de acesso público (se o objeto for público depois)
		return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
	}

	public String uploadImage(String fileName, InputStream fileContent) throws IOException {

		String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
		String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
		String uniqueFileName = baseName + "_" + UUID.randomUUID() + "." + extension;

		BlobId blobId = BlobId.of(bucketName, uniqueFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
				.setContentType("image/" + extension)
				.build();

		try {
			byte[] bytes = fileContent.readAllBytes();
			storage.create(blobInfo, bytes);
			return uniqueFileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String replaceImage(String fileName, InputStream fileContent, String oldFileName){
		try {
			String newFileName = uploadImage(fileName, fileContent);
			deleteImage(oldFileName);
			return newFileName;
		} catch (IOException e) {
			throw new RuntimeException("Erro ao substituir imagem: " + fileName, e);
		}
	}

	public byte[] downloadImage(String objectName) throws IOException {
		Blob blob = storage.get(BlobId.of(bucketName, objectName));
		if (blob == null) {
			throw new FileNotFoundException("Imagem não encontrada.");
		}
		return blob.getContent();
	}

	// Eliminar imagem
	public void deleteImage(String objectName) {
		BlobId blobId = BlobId.of(bucketName, objectName);
		try {
			boolean deleted = storage.delete(blobId);
			if (!deleted){
				throw new FileNotFoundException("Image not found in Storage.");
			}
		} catch (StorageException | FileNotFoundException e){
			throw new RuntimeException("Error deleting image: " + e.getMessage());
		}
	}


	public String generateImageUrl(String objectName) {
		return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
	}

	public String generateSignedUrl(String objectName) {
		BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();

		URL signedUrl = storage.signUrl(
				blobInfo,
				15, // duração da URL em minutos → podes ajustar (ex: 15 min)
				TimeUnit.MINUTES,
				Storage.SignUrlOption.withV4Signature() // V4 signature é mais segura
		);

		return signedUrl.toString();
	}
}
