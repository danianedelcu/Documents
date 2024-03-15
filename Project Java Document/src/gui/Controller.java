package gui;

import Domain.Document;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;

import java.util.Collections;

public class Controller {
    private Service service;
    @FXML
    ListView<Document> documentListView;

    @FXML
    TextField searchTextField;

    @FXML
    TextField updateKeywordsTextField;

    @FXML
    Button updateKeywordsButton;
    public Controller(Service service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        ObservableList<Document> list = FXCollections.observableArrayList();
        list.clear();
        list.addAll(service.showDocumentsSorted()); //adds the documents
        documentListView.setItems(list);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> { // this listener triggers whenever the text in the search text field changes. Inside the listener, it seems to update the list based on the search query provided
            list.clear();
            list.addAll(service.getMatchingItems(newValue));
        });
        updateKeywordsButton.setOnAction( //sets an action event handler for when the "Update Keywords" button is clicked
                actionEvent -> {
                    service.updateDocument(documentListView.getSelectionModel().getSelectedItem().getName(),
                            Collections.singletonList(updateKeywordsTextField.getText()), //face o lista din stringul citit din text property-ul de la textfield
                            //update the keywords of the selected document with the text entered in updateKeywordsTextField, and then refreshes the list based on the current search query.
                            "");
                    list.clear();
                    list.addAll(service.getMatchingItems(searchTextField.getText()));
                }

        );
    }
}
