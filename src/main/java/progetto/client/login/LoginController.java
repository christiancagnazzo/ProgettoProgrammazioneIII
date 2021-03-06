package progetto.client.login;

import progetto.client.home.HomeModel;
import progetto.client.home.MailListController;
import progetto.client.home.MailViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;


public class LoginController {
    private static final String PATH = "src/main/java/progetto/server/mailbox/";

    @FXML
    private TextField email_account;

    @FXML
    private Button btn;

    @FXML
    private void login() throws IOException {
        if (!"".equals(email_account.getText()) &&
                Pattern.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}",email_account.getText()) &&
                Files.exists(Path.of(PATH + email_account.getText()))) {

            Stage st = (Stage) btn.getScene().getWindow();
            st.close();

            BorderPane root = new BorderPane();
            HomeModel homemodel = new HomeModel(email_account.getText());

            FXMLLoader mailListLoader = new FXMLLoader(getClass().getClassLoader().getResource("mailList.fxml"));
            root.setLeft(mailListLoader.load());
            MailListController listmailcontroller = mailListLoader.getController();

            FXMLLoader mailViewLoader = new FXMLLoader(getClass().getClassLoader().getResource("mailView.fxml"));
            root.setRight(mailViewLoader.load());
            MailViewController viewmailcontroller = mailViewLoader.getController();

            Stage stage = new Stage();

            listmailcontroller.initModel(homemodel, stage);
            viewmailcontroller.initModel(homemodel);

            stage.setTitle("Unito Mail");
            stage.setMaximized(true);

            Scene s = new Scene(root);
            s.getStylesheets().add(Login.class.getResource("/css.css").toExternalForm());

            stage.setScene(s);
            stage.show();
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
    }
}
