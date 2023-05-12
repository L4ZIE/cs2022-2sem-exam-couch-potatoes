package pl.controllers;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DrawingViewController implements Initializable {
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
    GraphicsContext graphicsContext;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void pencilDrawing(ActionEvent event) {
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(txfSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            graphicsContext.fillRect(x, y, size, size);
        });
    }

    public void eraserPressed(ActionEvent event) {
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(txfSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            graphicsContext.clearRect(x, y, size,size);
        });
    }

    public void saveDrawing(ActionEvent event) {
        try {
            WritableImage writableImage = new WritableImage((int) canvas.getHeight(), (int) canvas.getWidth());
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", new File("temp/drawings/lastDrawing.png"));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save Image");
        }
        Platform.exit();
    }

    public void exitPressed(ActionEvent event) {
        Platform.exit();
    }

    public void btnLinePressed(ActionEvent event) {
        //TODO
    }

    public void clearCanvas(ActionEvent event) {
    graphicsContext.getFill();
    graphicsContext.clearRect(0,0, canvas.getHeight(), canvas.getWidth());
    }
}







