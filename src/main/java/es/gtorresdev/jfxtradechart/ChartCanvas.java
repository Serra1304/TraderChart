package es.gtorresdev.jfxtradechart;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChartCanvas extends Pane {
    private static final double TOP_SECTION_HEIGHT = 30;
    private static final double SIDE_SECTION_WIDTH = 100;
    private static final double BOTTOM_SECTION_HEIGHT = 50;

    private static final double CANVAS_MARGIN = 10;

    private boolean topSectionVisible;
    private boolean sideSectionVisible;
    private boolean bottomSectionVisible;

    private final Canvas canvas;
    private final GraphicsContext graphContext;
    private final Grid grid;

    public ChartCanvas() {
        topSectionVisible = true;
        sideSectionVisible = true;
        bottomSectionVisible = true;

        canvas = new Canvas();
        canvas.setLayoutX(CANVAS_MARGIN);
        canvas.setLayoutY(CANVAS_MARGIN);

        graphContext = canvas.getGraphicsContext2D();
        graphContext.setLineWidth(1);

        grid = new Grid(graphContext);

        this.setMinHeight(150);
        this.getChildren().add(canvas);
        this.widthProperty().addListener((obs, oldWidth, newWidth) -> resizeCanvas());
        this.heightProperty().addListener((obs, oldHeight, newHeight) -> resizeCanvas());
    }

    private void resizeCanvas() {
        canvas.setWidth(getWidth() - CANVAS_MARGIN * 2);
        canvas.setHeight(getHeight() - CANVAS_MARGIN * 2);
        redraw();
    }

    private void redraw() {
        graphContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawSections();
    }

    private void drawSections() {
        graphContext.setLineDashes(0);

        if (topSectionVisible) {
            drawTopSection();
        }

        if (sideSectionVisible) {
            drawSideSection();
        }

        if (bottomSectionVisible) {
            drawBottomSection();
        }

        drawCentralSection();
    }

    private void drawTopSection() {
        double width = canvas.getWidth();
        double height = topSectionVisible ? TOP_SECTION_HEIGHT : 0;

        //graphContext.strokeRect(0, 0, width, height);
        //graphContext.strokeText("Sección Superior", 10, 20);
    }

    private void drawSideSection() {
        double sideSectionWidth = sideSectionVisible ? SIDE_SECTION_WIDTH : 0;
        double topSectionHeight = topSectionVisible ? TOP_SECTION_HEIGHT : 0;
        double bottomSectionHeight = bottomSectionVisible ? BOTTOM_SECTION_HEIGHT : 0;

        double layoutX = canvas.getWidth() - sideSectionWidth;
        double layoutY = topSectionHeight;
        double width = sideSectionWidth;
        double height = canvas.getHeight() - topSectionHeight - bottomSectionHeight;

        graphContext.setStroke(Color.GRAY);
        for (double i = height; i >= 0; i -= 32) {
            graphContext.strokeLine(layoutX, layoutY + i, layoutX + 5, layoutY + i);
        }
    }

    private void drawBottomSection() {
        double sideSectionWidth = sideSectionVisible ? SIDE_SECTION_WIDTH : 0;
        double bottomSectionHeight = bottomSectionVisible ? BOTTOM_SECTION_HEIGHT : 0;

        double layoutY = canvas.getHeight() - bottomSectionHeight;
        double width = canvas.getWidth() - sideSectionWidth;

        graphContext.setStroke(Color.GRAY);

        for (double x = 0.5; x <= width; x += 64) {
            graphContext.strokeLine(x, layoutY, x, layoutY +5);
        }
    }

    private void drawCentralSection() {
        double sideSectionWidth = sideSectionVisible ? SIDE_SECTION_WIDTH : 0;
        double bottomSectionHeight = bottomSectionVisible ? BOTTOM_SECTION_HEIGHT : 0;

        double startX = 0.5;
        double startY = topSectionVisible ? TOP_SECTION_HEIGHT - 0.5 : 0.5;
        double width = canvas.getWidth() - sideSectionWidth;
        double height = canvas.getHeight() - startY - bottomSectionHeight - 0.5;

        graphContext.setStroke(Color.GRAY);
        graphContext.strokeRect(startX, startY, width, height);

        grid.setBounds(new BoundingBox(startX, startY, width, height));
        grid.setColor(Color.BLUE);
    }
}
