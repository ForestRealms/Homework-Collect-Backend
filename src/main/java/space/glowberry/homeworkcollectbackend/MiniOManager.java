package space.glowberry.homeworkcollectbackend;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class MiniOManager {
    @Value("${minio.url}")
    private String url;
    @Value("${minio.username}")
    private String username;
    @Value("${minio.password}")
    private String password;
    @Value("${minio.bucket_name}")
    private String bucketName;

    private MinioClient client;
    private static MiniOManager instance;


    public MiniOManager() {

    }

    public void connect(){
        this.client = MinioClient.builder()
                .endpoint(url)
                .credentials(username, password)
                .build();
    }

    public static MiniOManager getInstance() {
        return Objects.requireNonNullElseGet(instance, MiniOManager::new);
    }

    public MinioClient getClient() {
        return client;
    }

    public boolean bucketExists() throws
                            ServerException,
                            InsufficientDataException,
                            ErrorResponseException,
                            IOException,
                            NoSuchAlgorithmException,
                            InvalidKeyException,
                            InvalidResponseException,
                            XmlParserException,
                            InternalException
    {
        return this.client.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(this.bucketName)
                        .build()
        );
    }

    public void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!bucketExists()){
            this.client.makeBucket(MakeBucketArgs.builder()
                            .bucket(this.bucketName)
                    .build());
        }
    }

    public void putFile() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File file = new File("M:\\Docker\\镜像\\Jupuyter-Lab\\20230915\\Dockerfile");
        FileInputStream stream = new FileInputStream(file);
        this.client.putObject(PutObjectArgs.builder()
                .bucket(this.bucketName)
                .object("Dockerfile.txt")
                .stream(stream, stream.available(), -1)
                .build());

    }

    public String getURL(String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return this.client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(this.bucketName)
                .object(filename)
                        .expiry(7, TimeUnit.DAYS)
                .build());
    }
}
