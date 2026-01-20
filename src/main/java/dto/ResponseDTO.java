package dto;

public class ResponseDTO<T> {
    private String msg;
    private T body;

    public ResponseDTO(){
    }

    public ResponseDTO(String msg, T body) {
        this.msg = msg;
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
