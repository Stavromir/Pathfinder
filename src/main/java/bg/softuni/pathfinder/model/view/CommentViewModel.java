package bg.softuni.pathfinder.model.view;

public class CommentViewModel {

    private Long id;
    private String textContent;
    private String authorFullName;
    private String created;

    public CommentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentViewModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public CommentViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public CommentViewModel setCreated(String created) {
        this.created = created;
        return this;
    }
}
