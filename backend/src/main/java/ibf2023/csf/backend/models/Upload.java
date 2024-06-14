package ibf2023.csf.backend.models;

public class Upload {
    private String _id;
    private String date;
    private String title;
    private String comments;
    private String url;

    public Upload() {}

    public Upload(String date, String title, String comments, String url) {
        this.date = date;
        this.title = title;
        this.comments = comments;
        this.url = url;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Upload [_id=" + _id + ", date=" + date + ", title=" + title + ", comments=" + comments + ", url=" + url
                + "]";
    }

}
