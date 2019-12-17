package co.kr.newp;

public class Data_reply {
    String id;
    String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Data_reply(String id, String text) {
        super();
        this.id = id;
        this.text = text;
    }


}
