package certificate;

import java.util.List;

public class CertificateResponse {
    private static final int CODE_200 = 200;

    private int code;
    private List<Certificate> certificates;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public boolean isSuccess() {
        return code == CODE_200;
    }

}
