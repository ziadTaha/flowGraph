package control;

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Drawing extends Application {
	int[][] graph = new int[5][5];

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		 primaryStage.setTitle("Drawing Operations Test");
		 Scanner scan = new Scanner(System.in);
		 for (int i =0; i<graph.length;i++)
		 {
			 for (int j=0;j<graph.length;j++)
			 {
				 graph[i][j]=scan.nextInt();
			 }
		 }
		 Group root = drawFlowGraph(graph);
		 Pane drawingPane = new Pane(root);
         ScrollPane pane = new ScrollPane(drawingPane);
		 Scene scene = new Scene(pane, 600, 300);
		 primaryStage.setScene(scene);

	      //Displaying the contents of the stage
	      primaryStage.show();

	}

	private Group drawFlowGraph(int graph[][]) {
		int x;
		int y;
		int c;
		Image forw = new Image( "/icons8-forward-filled-24.png");
		Image back = new Image( "/icons8-back-filled-24.png");
		Group group = new Group();

		for (int i = 0; i < graph.length; i++) {
			x = 60 + i * 100;
			y = 140;
			for (int j = 0; j < graph.length; j++) {
				if (i - j != 1 && i != j) {
					if (graph[i][j] != 0) {
						QuadCurve edge = new QuadCurve();
						ImageView imageView ;
						Text mag = new Text();
						edge.setStartX(x);
						edge.setStartY(y);
						c = ((Math.abs((60+100*i)+(60+100*j))))/2;
						edge.setControlX(c);
						if (i < j) {
							edge.setControlY(140+30*Math.abs(i-j));
							imageView = new ImageView(back);
							imageView.setX(c-12);
							imageView.setY(140+(30*Math.abs(i-j)/2)-12);
							mag.setY(140+(30*Math.abs(i-j)/2));
						} else {
							edge.setControlY(140-30*Math.abs(i-j));
							imageView = new ImageView(forw);
							imageView.setX(c-12);
							imageView.setY(140-(30*Math.abs(i-j)/2)-12);
							mag.setY(140-(30*Math.abs(i-j)/2));
						}
						edge.setEndX(60 + j * 100);
						edge.setEndY(140);
						edge.setStroke(javafx.scene.paint.Color.RED);
						edge.setFill(javafx.scene.paint.Color.TRANSPARENT);

						mag.setText(Integer.toString(graph[i][j]));
						mag.setX(c);

						group.getChildren().add(edge);
						group.getChildren().add(imageView);
						group.getChildren().add(mag);

					}
				} else if (Math.abs(i-j) == 1) {
					if (graph[i][j] != 0) {
						Line edge = new Line();
						Text mag = new Text();
						edge.setStartX(x);
						edge.setStartY(y);
						edge.setEndX(60 + j * 100);
						edge.setEndY(140);
						edge.setStroke(javafx.scene.paint.Color.RED);
						edge.setFill(javafx.scene.paint.Color.RED);
						ImageView imageView = new ImageView(forw);
						imageView.setLayoutX((Math.abs((60+100*i)+(60+100*j)))/2-12);
						imageView.setY(140-12);
						mag.setText(Integer.toString(graph[i][j]));
						mag.setX(((Math.abs((60+100*i)+(60+100*j))))/2);
						mag.setY(y);
					    group.getChildren().add(imageView);
						group.getChildren().add(edge);
						group.getChildren().add(mag);
					}
				} else {
					if (graph[i][j] != 0) {
						Circle edge = new Circle();
						Text mag = new Text();
						edge.setCenterX(x);
						edge.setCenterY(125);
						edge.setRadius(15);
						edge.setStroke(javafx.scene.paint.Color.RED);
						edge.setFill(javafx.scene.paint.Color.TRANSPARENT);
						ImageView imageView = new ImageView(back);
						imageView.setX(x-12);
						imageView.setY(140-42);
						mag.setText(Integer.toString(graph[i][j]));
						mag.setX(((Math.abs((60+100*i)+(60+100*j))))/2);
						mag.setY(y-30);
						group.getChildren().add(edge);
						group.getChildren().add(imageView);
						group.getChildren().add(mag);
					}
				}
			}
		}
		for (int i = 0; i < graph.length; i++) {
			Circle node = new Circle(60 + 100 * i, 140 , 5);
			node.setFill(javafx.scene.paint.Color.BLUE);
			Text num = new Text();
			num.setLayoutX(40 + 100 * i);
			num.setLayoutY(120 );
			num.setText("y" + i);
			num.setFill(javafx.scene.paint.Color.BLACK);
			group.getChildren().add(node);
			group.getChildren().add(num);
		}
		return group;
	}

}
