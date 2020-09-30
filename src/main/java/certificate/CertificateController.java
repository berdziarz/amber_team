package certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CertificateController {
    private static final String GET_CERTIFICATE = "http://localhost:9999/api/rest/v1/certificate";
    private static final String GET_CERTIFICATE_ALL = GET_CERTIFICATE + "s/all";
    private static final int CODE_200 = 200;


    private ObjectMapper mapper = new ObjectMapper();

    public CertificateResponse getCertificateByIdInQuery(int id) throws IOException, URISyntaxException {
        CertificateResponse certificateResponse = new CertificateResponse();
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE);
        builder.setParameter("id", String.valueOf(id));
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(builder.build());
            System.out.println("\t\tRequest : " + request);
            HttpResponse response = client.execute(request);
            certificateResponse.setCode(response.getStatusLine().getStatusCode());
            if (certificateResponse.isSuccess()) {
                certificateResponse.setCertificates(readCertificate(response));
                String body = mapper.writeValueAsString(certificateResponse.getCertificates());
                System.out.println("\t\tResponse code: " + certificateResponse.getCode());
                System.out.println("\t\tResponse body: " + body);
            } else {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
                System.out.println("\t\tResponse body: " + responseString);
            };
        }
        return certificateResponse;
    }

    public HttpResponse addCertificate(Certificate certificate) throws URISyntaxException, IOException {
        HttpResponse response;
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            StringEntity body = new StringEntity(
                    mapper.writeValueAsString(certificate),
                    ContentType.APPLICATION_JSON);
            HttpPost request = new HttpPost(builder.build());
            request.setEntity(body);
            System.out.println("\t\tRequest : " + request);
            System.out.println("\t\tRequest body: " + mapper.writeValueAsString(certificate));
            response = client.execute(request);
            String responseString;
            if (response.getStatusLine().getStatusCode() == CODE_200) {
                responseString = mapper.writeValueAsString(readCertificate(response));
            } else {
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
            System.out.println("\t\tResponse body: " + responseString);
        }
        return response;
    }
    public HttpResponse changeCertificate(int id, Certificate certificate) throws URISyntaxException, IOException {
        HttpResponse response;
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE + "/" + id);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            StringEntity body = new StringEntity(
                    mapper.writeValueAsString(certificate),
                    ContentType.APPLICATION_JSON);
            HttpPut request = new HttpPut(builder.build());
            request.setEntity(body);
            System.out.println("\t\tRequest : " + request);
            System.out.println("\t\tRequest body: " + mapper.writeValueAsString(certificate));
            response = client.execute(request);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
            System.out.println("\t\tResponse body: " + responseString);
        }
        return response;
    }

    public HttpResponse deleteCertificate(int id) throws URISyntaxException, IOException {
        HttpResponse response;
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE + "/" + id);
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpDelete request = new HttpDelete(builder.build());
            System.out.println("\t\tRequest : " + request);
            response = client.execute(request);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
            System.out.println("\t\tResponse body: " + responseString);
        }
        return response;
    }

    public CertificateResponse getCertificateByIdInPath(int id) throws IOException, URISyntaxException {
        CertificateResponse certificateResponse = new CertificateResponse();
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE + "/" + id);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(builder.build());
            HttpResponse response = client.execute(request);
            System.out.println("\t\tRequest : " + request);
            certificateResponse.setCode(response.getStatusLine().getStatusCode());
            if (certificateResponse.isSuccess()) {
                certificateResponse.setCertificates(readCertificate(response));
                String body = mapper.writeValueAsString(certificateResponse.getCertificates());
                System.out.println("\t\tResponse code: " + certificateResponse.getCode());
                System.out.println("\t\tResponse body: " + body);
            } else {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
                System.out.println("\t\tResponse body: " + responseString);
            }
        }
        return certificateResponse;
    }

    private List<Certificate> readCertificates(HttpResponse response) throws IOException {
        return Arrays.asList(mapper.readValue(response.getEntity().getContent(), Certificate[].class));
    }

    private List<Certificate> readCertificate(HttpResponse response) throws IOException {
            return Collections.singletonList(mapper.readValue(response.getEntity().getContent(), Certificate.class));
    }

    public CertificateResponse getAllCertificate() throws URISyntaxException, IOException {
        CertificateResponse certificateResponse = new CertificateResponse();
        URIBuilder builder = new URIBuilder(GET_CERTIFICATE_ALL);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(builder.build());
            HttpResponse response = client.execute(request);
            System.out.println("\t\tRequest : " + request);
            certificateResponse.setCode(response.getStatusLine().getStatusCode());
            if (certificateResponse.isSuccess()) {
                certificateResponse.setCertificates(readCertificates(response));
                String body = mapper.writeValueAsString(certificateResponse.getCertificates());
                System.out.println("\t\tResponse code: " + certificateResponse.getCode());
                System.out.println("\t\tResponse body: " + body);
            } else {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("\t\tResponse code: " + response.getStatusLine().getStatusCode());
                System.out.println("\t\tResponse body: " + responseString);
            }
        }
        return certificateResponse;
    }
}
