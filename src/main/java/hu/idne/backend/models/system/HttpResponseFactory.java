package hu.idne.backend.models.system;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

public class HttpResponseFactory {

    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final String CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE = "attachment; filename=%s";
    private static final String CONTENT_DISPOSITION_HEADER_INLINE_VALUE = "inline; filename=%s";
    private static final String MEDIA_TYPE_APPLICATION = "application";
    private static final String MEDIA_TYPE_TXT = "txt";

    private HttpResponseFactory() {
    }

    public static HttpEntity<byte[]> makeCsvHttpResponse(String namePrefix, byte[] body) {
        String name = String.format("%s.csv", namePrefix);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "csv"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        header.setContentLength(body.length);
        return new HttpEntity<>(body, header);
    }

    public static HttpEntity<byte[]> makeExcelHttpResponse(String namePrefix, byte[] body) {
        String name = String.format("%s_%s.xls", namePrefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "vnd.ms-excel"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_INLINE_VALUE, name));
        header.setContentLength(body.length);
        return new HttpEntity<>(body, header);
    }

    public static HttpEntity<byte[]> makePdfHttpResponse(String namePrefix, byte[] body) {
        String name = String.format("%s_%s.pdf", namePrefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "pdf"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        header.setContentLength(body.length);
        return new HttpEntity<>(body, header);
    }

    public static HttpEntity<byte[]> makeZipHttpResponse(String namePrefix, byte[] body) {
        String name = String.format("%s_%s.zip", namePrefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "zip"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        header.setContentLength(body.length);
        return new HttpEntity<>(body, header);
    }

    public static HttpEntity<byte[]> makePdfInlineHttpResponse(String namePrefix, byte[] body) {
        String name = String.format("%s_%s.pdf", namePrefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "pdf"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_INLINE_VALUE, name));
        header.setContentLength(body.length);
        return new HttpEntity<>(body, header);
    }

    public static HttpEntity<String> makJsonlHttpResponse(String prefix, String rawjson) {
        String name = String.format("%s_%s.json", prefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "json"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        return new HttpEntity<>(rawjson, header);
    }

    public static HttpEntity<byte[]> makePictureHttpResponse(String name, byte[] binary) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_APPLICATION, "octet-stream"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        header.setContentLength(binary.length);
        return new HttpEntity<>(binary, header);
    }

    public static HttpEntity<byte[]> makeTxtHttpResponse(String prefix, byte[] binary) {
        String name = String.format("%s_%s.txt", prefix, LocalDateTime.now().toString());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MEDIA_TYPE_TXT, "plain"));
        header.set(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_HEADER_ATTACHMENT_VALUE, name));
        header.setContentLength(binary.length);
        return new HttpEntity<>(binary, header);
    }
}
