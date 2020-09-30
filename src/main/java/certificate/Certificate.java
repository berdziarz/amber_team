package certificate;


import com.fasterxml.jackson.annotation.JsonInclude;

public class Certificate {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;
    private String organization;
    private String name;
    private String period;
    private String trade;

    public Certificate(String organization, String name, String period, String trade) {
        this.organization = organization;
        this.name = name;
        this.period = period;
        this.trade = trade;
    }

    public Certificate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}
