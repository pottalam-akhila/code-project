import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class javafxmysql2 extends Application {

    // MySQL connection details
    String url = "jdbc:mysql://localhost:3306/sys";  // replace your_database
    String user = "root"; // your MySQL username
    String password = "Akhila@7496"; // your MySQL password

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label message = new Label("SQL CRUD Operations (MySQL DB)");
        Button createBtn = new Button("Create Table");
        Button insertBtn = new Button("Insert Values");
        Button updateBtn = new Button("Update Table");
        Button deleteBtn = new Button("Delete Table");
        Button truncateBtn = new Button("Truncate Table");
        Button dropBtn = new Button("Drop Table");
        Button selectBtn = new Button("Select Records");

        createBtn.setOnAction(e -> showCreateTableWindow());
        insertBtn.setOnAction(e -> showInsertWindow());
        updateBtn.setOnAction(e -> showUpdateWindow());
        deleteBtn.setOnAction(e -> showSimpleTableActionWindow("Delete", "DELETE FROM "));
        truncateBtn.setOnAction(e -> showSimpleTableActionWindow("Truncate", "TRUNCATE TABLE "));
        dropBtn.setOnAction(e -> showSimpleTableActionWindow("Drop", "DROP TABLE "));
        selectBtn.setOnAction(e -> showSelectWindow());

        VBox root = new VBox(15, message, createBtn, insertBtn, updateBtn, deleteBtn, truncateBtn, dropBtn, selectBtn);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Scene scene = new Scene(root, 400, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX MySQL DB Manager");
        primaryStage.show();
    }

    private Connection getConnection() throws SQLException {
        // Load MySQL driver (optional since JDBC 4.0+)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "MySQL JDBC Driver not found.");
        }
        return DriverManager.getConnection(url, user, password);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    public static class RowData {
        SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
        List<SimpleStringProperty> values;

        RowData(List<SimpleStringProperty> values) {
            this.values = values;
        }
    }

    private List<String> getPrimaryKeyColumns(Connection conn, String tableName) throws SQLException {
        List<String> pkCols = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet pkRs = metaData.getPrimaryKeys(null, null, tableName)) {
            while (pkRs.next()) {
                pkCols.add(pkRs.getString("COLUMN_NAME"));
            }
        }
        return pkCols;
    }

    private void showSelectWindow() {
        Stage stage = new Stage();
        TextField tableField = new TextField();
        Button loadBtn = new Button("Load Table");

        VBox layout = new VBox(10, new Label("Enter Table Name:"), tableField, loadBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        stage.setScene(new Scene(layout, 300, 200));
        stage.setTitle("Select Records");
        stage.show();

        loadBtn.setOnAction(ev -> {
            String tableName = tableField.getText().trim();
            if (tableName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Table name is required.");
                return;
            }

            Stage selectStage = new Stage();
            VBox contentLayout = new VBox(10);
            contentLayout.setStyle("-fx-padding: 10;");

            TableView<RowData> tableView = new TableView<>();
            tableView.setEditable(true);
            Button deleteSelectedBtn = new Button("Delete Selected Rows");
            contentLayout.getChildren().addAll(new ScrollPane(tableView), deleteSelectedBtn);

            List<String> columnNames = new ArrayList<>();
            int[] columnCount = {0};

            ObservableList<RowData> data = FXCollections.observableArrayList();

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                ResultSet rs = stmt.executeQuery("SELECT * FROM `" + tableName + "` LIMIT 1000");  // MySQL limit example
                ResultSetMetaData meta = rs.getMetaData();
                columnCount[0] = meta.getColumnCount();

                TableColumn<RowData, Boolean> selectCol = new TableColumn<>("Select");
                selectCol.setCellValueFactory(param -> param.getValue().selected);
                selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
                tableView.getColumns().add(selectCol);

                for (int i = 1; i <= columnCount[0]; i++) {
                    String colName = meta.getColumnName(i);
                    columnNames.add(colName);
                    final int colIndex = i - 1;
                    TableColumn<RowData, String> col = new TableColumn<>(colName);
                    col.setCellValueFactory(cellData -> cellData.getValue().values.get(colIndex));
                    tableView.getColumns().add(col);
                }

                while (rs.next()) {
                    List<SimpleStringProperty> rowValues = new ArrayList<>();
                    for (int i = 1; i <= columnCount[0]; i++) {
                        rowValues.add(new SimpleStringProperty(rs.getString(i)));
                    }
                    data.add(new RowData(rowValues));
                }

                tableView.setItems(data);

                List<String> pkColumns = getPrimaryKeyColumns(conn, tableName);

                deleteSelectedBtn.setOnAction(e -> {
                    List<RowData> selectedRows = new ArrayList<>();
                    for (RowData row : data) {
                        if (row.selected.get()) selectedRows.add(row);
                    }

                    if (selectedRows.isEmpty()) {
                        showAlert(Alert.AlertType.WARNING, "No rows selected.");
                        return;
                    }

                    if (pkColumns.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "No primary key found for the table. Cannot safely delete rows.");
                        return;
                    }

                    try (Connection delConn = getConnection();
                         Statement delStmt = delConn.createStatement()) {

                        int deletedCount = 0;

                        for (RowData row : selectedRows) {
                            StringBuilder where = new StringBuilder();
                            for (int i = 0; i < columnCount[0]; i++) {
                                String column = columnNames.get(i);
                                if (!pkColumns.contains(column)) continue;

                                String value = row.values.get(i).get();

                                if (where.length() > 0) where.append(" AND ");

                                if (value == null || value.equalsIgnoreCase("null") || value.isEmpty()) {
                                    where.append("`").append(column).append("` IS NULL");
                                } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                                    where.append("`").append(column).append("`=").append(value);
                                } else {
                                    where.append("`").append(column).append("`='").append(value.replace("'", "''")).append("'");
                                }
                            }

                            if (where.length() == 0) continue;

                            String delQuery = "DELETE FROM `" + tableName + "` WHERE " + where;
                            int affected = delStmt.executeUpdate(delQuery);
                            if (affected > 0) deletedCount++;
                        }

                        showAlert(Alert.AlertType.INFORMATION, deletedCount + " row(s) deleted.");
                        selectStage.close();

                    } catch (SQLException ex) {
                        showAlert(Alert.AlertType.ERROR, ex.getMessage());
                    }
                });

            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }

            selectStage.setScene(new Scene(contentLayout, 800, 600));
            selectStage.setTitle("Table: " + tableName);
            selectStage.show();
        });
    }

    private void showCreateTableWindow() {
        Stage stage = new Stage();
        TextField tableNameField = new TextField();
        TextField fieldCountField = new TextField();
        Button nextBtn = new Button("Next");

        VBox layout = new VBox(10, new Label("Table Name:"), tableNameField,
                new Label("Number of Fields:"), fieldCountField, nextBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        stage.setScene(new Scene(layout, 300, 250));
        stage.setTitle("Create Table");
        stage.show();

        nextBtn.setOnAction(e -> {
            String tableName = tableNameField.getText().trim();
            int count;
            try {
                count = Integer.parseInt(fieldCountField.getText().trim());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid field count.");
                return;
            }
            showFieldCountWindow(stage, tableName, count);
        });
    }

    private void showFieldCountWindow(Stage parent, String tableName, int count) {
        Stage stage = new Stage();
        VBox fieldsBox = new VBox(10);
        List<TextField> names = new ArrayList<>();
        List<TextField> types = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            TextField name = new TextField();
            TextField type = new TextField();
            names.add(name);
            types.add(type);
            fieldsBox.getChildren().add(new HBox(10, new Label("Field " + (i + 1) + ":"), name, type));
        }

        Button createBtn = new Button("Create Table");
        fieldsBox.getChildren().add(createBtn);
        fieldsBox.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(fieldsBox, 400, 300));
        stage.setTitle("Field Details");
        stage.show();

        createBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("CREATE TABLE `").append(tableName).append("` (");
            for (int i = 0; i < count; i++) {
                sb.append("`").append(names.get(i).getText()).append("` ").append(types.get(i).getText());
                if (i < count - 1) sb.append(", ");
            }
            sb.append(")");
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sb.toString());
                showAlert(Alert.AlertType.INFORMATION, "Table created successfully.");
                stage.close();
                parent.close();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });
    }

    private void showInsertWindow() {
        Stage stage = new Stage();
        TextField tableField = new TextField();
        Button loadBtn = new Button("Load Table Structure");

        VBox layout = new VBox(10, new Label("Table Name:"), tableField, loadBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment
