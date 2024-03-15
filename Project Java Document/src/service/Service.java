package service;

import Domain.Document;
import repository.Repository;

import java.util.Comparator;
import java.util.List;

public class Service {
    private Repository repository;

    public Service(Repository repo) {
        this.repository = repo;
    }

    public void add(Document d)
    {
        repository.add(d);
    }

    public List<Document> getAll()
    {
        return repository.getAll();
    }

    //1.Visualize all documents in a list. The list will display the name,
    //and the keywords for each document, sorted by document name.
    public List<Document> showDocumentsSorted()
    {
        List<Document> documentList = getAll();
        List<Document> result = documentList.stream()
                .sorted(Comparator.comparing(Document::getName))
                .toList();

        return result;

    }

    //2.Search documents. The user inputs the text in a text field,
    //and the list is refreshed, containing only the documents whose name
    //or keyword match the searched text
    public List<Document> getMatchingItems(String searchToken) {
        return showDocumentsSorted()
                .stream()
                .filter(document -> document.getName().contains(searchToken) ||
                        isSearchTokenContainedInKeywords(searchToken, document))
                .toList();
    }

    private static boolean isSearchTokenContainedInKeywords(String searchToken, Document document) {
        return document.getKeywords()
                .stream()
                .anyMatch(keyword -> keyword.contains(searchToken));
    }

    public List<Document> getInitialList() {
        return repository.getAll();
    }


    public void updateDocument(String name, List<String> keyword, String content) {
        repository.updateDocument(name,keyword,content);
    }
}
