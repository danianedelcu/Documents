package Domain;
import java.util.Arrays;
import java.util.List;

public class Document {
    private String name;
    private List<String> keywords;

    private String content;

    public Document(String name, List<String> keywords, String content) {
        this.name = name;
        this.keywords=keywords;
        this.content=content;
    }

    public String getName() {
        return name;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", keywords=" + keywords +
                ", content='" + content + '\'' +
                '}';
    }

}
