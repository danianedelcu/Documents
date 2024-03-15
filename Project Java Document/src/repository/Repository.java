package repository;

import Domain.Document;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;

    private List<Document> documents = new ArrayList<>();

    public Repository() {
        // Initialize the repository by fetching data from the database
        fetchDocumentsFromDatabase();
    }

    private void fetchDocumentsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Document";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String[] keywords = new String[]{resultSet.getString("keywords")};
                String content = resultSet.getString("content");

                Document document = new Document(name, Arrays.asList(keywords), content);
                documents.add(document);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Document document) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "INSERT INTO Document (String name, String[] keywords, String content) " +
                    "VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, document.getName());
                preparedStatement.setString(2, document.getKeywords().toString());
                preparedStatement.setString(3, document.getContent());

                preparedStatement.executeUpdate();
            }

            documents.add(document);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDocument(String documentName, List<String> documentKeywords, String documentContent) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "UPDATE Document SET keywords = ?,content = ? WHERE name = ? ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, documentName);
                preparedStatement.setString(2, documentKeywords.toString());
                preparedStatement.setString(3, documentContent);

                preparedStatement.executeUpdate();
            }

            documents.forEach(
                    document -> {
                        if (Objects.equals(document.getName(), documentName)) {
                            document.setKeywords(documentKeywords);
                            document.setContent(documentContent);
                        }
                    }
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        if (conn == null)
            openConnection();
        return conn;
    }

    private static void openConnection()
    {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection()
    {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Document> getAll()
    {
        return documents;
    }



}
