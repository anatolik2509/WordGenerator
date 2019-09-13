
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Controller {
    @FXML Button generate;

    @FXML Button install;

    @FXML
    TextField word;

    @FXML ChoiceBox <String> choiceBox;

    @FXML ProgressBar progressBar;

    @FXML public void initialize(){
        choiceBox.setValue("rus_words");
        ObservableList<String>list=FXCollections.observableArrayList("rus_words","russian_city","norvegy_city");
        choiceBox.setItems(list);
    }


    @FXML public void install(){
        Generator.load("src/word_packs/"+choiceBox.getValue()+".txt");
        progressBar.setVisible(true);
        progressBar.setVisible(false);
    }

    @FXML public void generate(){
        word.setText(Generator.generateWord());
    }





}
