package io.tashtabash.parser.onlinesim.number.renderer;


import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;
import io.tashtabash.parser.onlinesim.number.dao.PhoneNumber;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaFxFreeNumberRenderer extends Application implements FreeNumberRenderer {
    private static Map<String, List<String>> data;

    @Override
    public void render(FreeNumbers freeNumbers) {
        JavaFxFreeNumberRenderer.data = new HashMap<>();
        for (var entry: freeNumbers.getCountryToNumbers().entrySet()) {
            List<String> numbers = entry.getValue()
                    .stream()
                    .map(PhoneNumber::getNumber)
                    .toList();

            data.put(entry.getKey().getName(), numbers);
        }

        launch();
    }

    @Override
    public void start(Stage stage) {
        TreeItem<String> root = new TreeItem<>("Free phone numbers");
        root.setExpanded(true);

        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            TreeItem<String> key = new TreeItem<>(entry.getKey());
            key.setExpanded(true);

            for (String value : entry.getValue()) {
                key.getChildren().add(new TreeItem<>(value));
            }

            root.getChildren().add(key);
        }

        TreeView<String> treeView = new TreeView<>(root);

        StackPane layout = new StackPane();
        layout.getChildren().add(treeView);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
