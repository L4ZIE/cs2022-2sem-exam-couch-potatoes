package pl.controllers;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DrawingViewController implements Initializable {
    public AnchorPane anpMain;
    @FXML
    private TextField txfSize;
    @FXML
    private Canvas canvas;
    @FXML
    private Button
            btnClear,
            btnPencil,
            btnEraser,
            btnLine,
            btnCircle,
            btnSquare,
            btnSave,
            btnExit;
    private GraphicsContext graphicsContext;
    private Boolean haveHandler;
    private EventHandler<MouseEvent> mouseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        haveHandler = false;
    }

    public void pencilDrawing(ActionEvent event) {
        Color c = Color.web("#000000");
        canvas.getGraphicsContext2D().setStroke(c);
        drawStuff();
        haveHandler = true;
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

    public void eraserPressed(ActionEvent event) {
        Color c = Color.web("#17263a");//change later to getBackgroundColor
        canvas.getGraphicsContext2D().setStroke(c);
    }

    public void saveDrawing(ActionEvent event) {
        try {
            WritableImage writableImage = new WritableImage(
                    (int) graphicsContext.getCanvas().getWidth(),
                    (int) graphicsContext.getCanvas().getHeight());

            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", new File("temp/drawings/lastDrawing.png"));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save Image");
        }
        closeWindow(event);
    }

    public void btnLinePressed(ActionEvent e) {
        //TODO
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
            } else {
                end.setLocation(event.getX(), event.getY());
                startSet[0] = false;
            }

            if (!startSet[0])
                canvas.getGraphicsContext2D().strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        };
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
    }

    public void clearCanvas(ActionEvent event) {
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

    public void btnSetSizePressed(ActionEvent actionEvent) {
        setDrawingSize();
    }
}







