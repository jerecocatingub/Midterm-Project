package project.midterm.com.newsfeed;

/**
 * Created by Jotine on 2/5/2017.
 */

public class News {

    private String author;
    private String title;
    private String description;
    private String url;
    private String image;
    private String published;

    public News() {

    }

    public News(String author, String title, String description, String url, String image, String published) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.image = image;
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getPublished() {
        return published;
    }
}
