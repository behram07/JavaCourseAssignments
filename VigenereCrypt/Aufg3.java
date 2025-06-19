import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Aufg3 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(10);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);

        VBox rightPane = new VBox(10);
        rightPane.setPadding(new Insets(0, 0, 0, 10));

        Label keyLabel = new Label("Schlüsselwort:");
        TextField keyField = new TextField();
        keyField.setPromptText("z.b. geheim");

        Button encryptButton = new Button("Verschülüsselung");
        encryptButton.setOnAction(e -> {
            String input = textArea.getText();
            String key = keyField.getText();

            if(key.isEmpty()){
                showAlert("Anahtar eksik!", "Lütfen bir anahtar kelime girin");
                return;
            }
            String encrypted = vigenereEncrypt(input, key);
            textArea.setText(encrypted);
        });

        Button decryptButton = new Button("Entschlüsselung");
        decryptButton.setOnAction(e -> {
            String input = textArea.getText();
            String key = keyField.getText();

            if(key.isEmpty()){
                showAlert("Anahtar eksik!", "Lütfen bir anahtar kelime girin.");
                return;
            }
            String decrypted = vigenereDecrypt(input, key);
            textArea.setText(decrypted);
        });

        encryptButton.setMaxWidth(Double.MAX_VALUE);
        decryptButton.setMaxWidth(Double.MAX_VALUE);

        rightPane.getChildren().addAll(keyLabel, keyField, encryptButton, decryptButton);
        grid.add(rightPane, 1, 0);
        grid.add(textArea, 0, 0);


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        Button btnOpen = new Button("Öffnen");

        btnOpen.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Textdatei öffnen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if(selectedFile != null) {
                try {
                    String content = Files.readString(selectedFile.toPath());
                    textArea.setText(content);
                } catch(IOException ex) {
                    showAlert("Datei konnte nicht gelesen werden!", ex.getMessage());
                }
            }
        });
        Button btnSave = new Button("Speichern");

        btnSave.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Datei speichern");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File fileToSave = fileChooser.showSaveDialog(primaryStage);
            if(fileToSave != null){
                try {
                    String content = textArea.getText();
                    Files.writeString(fileToSave.toPath(), content);
                } catch (IOException ex){
                    showAlert("Datei konnte nicht gespeichert werden!", ex.getMessage());
                }
            }

        });

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        HBox bottomBar = new HBox(10, btnOpen, spacer1, btnSave);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setPrefHeight(50);
        grid.add(bottomBar, 0, 1, 2, 1);

        Scene scene = new Scene(grid, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Textver- und Entschlüsselung");
        primaryStage.show();
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String vigenereEncrypt(String text, String key){
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        int keyIndex = 0;

        for(char ch: text.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z'){
                int m = ch - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int c = (m + k) % 26;
                result.append((char) (c + 'A'));
                keyIndex++;
            } else{
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String vigenereDecrypt(String text, String key){
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        int keyIndex = 0;

        for(char ch: text.toUpperCase().toCharArray()){
            if(ch >= 'A' && ch <= 'Z'){
                int c = ch - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int m = (c - k + 26) % 26;
                result.append((char) (m + 'A'));
                keyIndex++;
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
