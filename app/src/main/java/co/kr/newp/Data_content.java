package co.kr.newp;

public class Data_content {
    String num;
    String writer;
    String date;
    String title;
    String place;
    String pic;
    String text;
    String choice;
    String reply;


    public Data_content(String num, String writer, String date, String title,
                        String place, String pic, String text, String choice, String reply) {
        super();
        this.num = num;
        this.writer = writer;
        this.date = date;
        this.title = title;
        this.place = place;
        this.pic = pic;
        this.text = text;
        this.choice = choice;
        this.reply = reply;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Data_content(String num, String writer, String date, String title,
                        String place, String pic, String text) {
        super();
        this.num = num;
        this.writer = writer;
        this.date = date;
        this.title = title;
        this.place = place;
        this.pic = pic;
        this.text = text;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
