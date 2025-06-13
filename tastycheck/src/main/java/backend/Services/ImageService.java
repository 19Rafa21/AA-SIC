package backend.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ImageService {

	private final Storage storage;
	private final String bucketName;

	public ImageService(InputStream credentialsStream, String bucketName) throws IOException {
		this.bucketName = bucketName;
		StorageOptions storageOptions = StorageOptions.newBuilder()
				.setCredentials(GoogleCredentials.fromStream(credentialsStream))
				.build();

		storage = storageOptions.getService();
	}

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

	public byte[] downloadImage(String objectName) throws IOException {
		Blob blob = storage.get(BlobId.of(bucketName, objectName));
		if (blob == null) {
			throw new FileNotFoundException("Imagem não encontrada.");
		}
		return blob.getContent();
	}

	// Eliminar imagem
	public boolean deleteImage(String objectName) {
		return storage.delete(BlobId.of(bucketName, objectName));
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
