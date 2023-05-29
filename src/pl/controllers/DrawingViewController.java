package pl.controllers;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DrawingViewController implements Initializable {
    @FXML
    public Button btnPencil, btnEraser, btnLine;
    @FXML
    public AnchorPane anpMain;
    @FXML
    public ImageView imvExistingDrawing;
    @FXML
    private TextField txfSize;
    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Boolean haveHandler;
    private EventHandler<MouseEvent> mouseHandler;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(Double.parseDouble(txfSize.getText()));
        haveHandler = false;
        if(ProjectViewController.getDrawingLocation() != null){
            imvExistingDrawing.setImage(new Image(ProjectViewController.getDrawingLocation()));
        }
        //anpMain.setOnMousePressed(this::handleMousePressed);
        //anpMain.setOnMouseDragged(this::handleMouseDragged);

    }
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    public void pencilDrawing() {
        Color c = Color.web("#000000");
        canvas.getGraphicsContext2D().setStroke(c);
        drawStuff();
        haveHandler = true;
        btnPencil.setDisable(true);
        btnLine.setDisable(false);
        btnEraser.setDisable(false);
    }

    private void drawStuff() {
        if (mouseHandler != null)
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);

        if (!haveHandler) {
            mouseHandler = event -> {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
            };
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);

            mouseHandler = event -> {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            };
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseHandler);
        }
    }

    public void eraserPressed() {
        drawStuff();
        Color c = Color.web("#17263a");//change later to getBackgroundColor
        canvas.getGraphicsContext2D().setStroke(c);
        btnPencil.setDisable(false);
        btnLine.setDisable(false);
        btnEraser.setDisable(true);

    }

    public void saveDrawing(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        try {
            WritableImage writableImage = new WritableImage(
                    (int) graphicsContext.getCanvas().getWidth(),
                    (int) graphicsContext.getCanvas().getHeight());

            SnapshotParameters params = new SnapshotParameters();
            params.setViewport(new javafx.geometry.Rectangle2D(15, 50,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
            anpMain.snapshot(params, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
            fileChooser.setInitialFileName("LocationDrawing");
            File file = fileChooser.showSaveDialog(null);
            if (file != null){
                ImageIO.write(renderedImage, "png", file);
                ProjectViewController.setDrawingLocation(file.getAbsolutePath());
                closeWindow(event);
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save Image");
        }
    }

    public void btnLinePressed() {
        btnLine.setDisable(true);
        btnPencil.setDisable(false);
        btnEraser.setDisable(false);

        Color c = Color.web("#000000");
        canvas.getGraphicsContext2D().setStroke(c);

        if (mouseHandler != null)
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseHandler);
        haveHandler = false;

        final Boolean[] startSet = {false};
        Point start = new Point(0, 0);
        Point end = new Point(0, 0);

        mouseHandler = event -> {
            if (!startSet[0]) {
                start.setLocation(event.getX(), event.getY());
                startSet[0] = true;
                graphicsContext.strokeLine(start.getX(), start.getY(), start.getX(), start.getY());
            } else {
                end.setLocation(event.getX(), event.getY());
                startSet[0] = false;
            }

            if (!startSet[0])
                canvas.getGraphicsContext2D().strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        };
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
    }

    public void minBtnPressed(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeBtnPressed(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private void closeWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void txfSizeKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            setDrawingSize();
    }

    private void setDrawingSize() {
        canvas.getGraphicsContext2D().setLineWidth(Double.parseDouble(txfSize.getText()));
    }

    public void btnSetSizePressed() {
        setDrawingSize();
    }
}







