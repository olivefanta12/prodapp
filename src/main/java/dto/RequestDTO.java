package dto;

import java.util.Map;

public class RequestDTO {
    private String method;
    private Map<String, Object> queryString;
    private Map<String, Object> body;

    public RequestDTO(){
    }


    public RequestDTO(String method, Map<String, Object> queryString, Map<String, Object> body) {
        this.method = method;
        this.queryString = queryString;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getQueryString() {
        return queryString;
    }

    public void setQueryString(Map<String, Object> queryString) {
        this.queryString = queryString;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
