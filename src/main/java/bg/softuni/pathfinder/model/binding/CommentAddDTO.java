package bg.softuni.pathfinder.model.binding;


public class CommentAddDTO {

    private String textContent;

    public CommentAddDTO() {
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentAddDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }
}
