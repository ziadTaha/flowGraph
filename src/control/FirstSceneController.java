package control;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FirstSceneController {
	@FXML
	private Button select_button;
	@FXML
	private TextArea pathsAreaText;
	@FXML
	private TextField number_field;
	@FXML
	private TextArea loops;
	@FXML
	private TextArea gainTextArea;
	@FXML
	private TextArea non_touchArea;

	private Graph graph;
	private Path path;
	private Loop loop;

	@FXML
	private Button draw_button;

	private static int[][] content;
	@FXML
    private Button operate;

    @FXML
    private TextField end;

    @FXML
    private TextField begin;

	@FXML
	void toFillArrayScene(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			int n = Integer.valueOf(number_field.getText());
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);

			TextField[][] fields = new TextField[n][n];
			for (int i = 0; i < n; i++) {

				Text row = new Text();
				row.setText(i + ":");
				grid.add(row, 0, i + 1);
				for (int j = 0; j < n; j++) {
					if (i == 0) {
						Text col = new Text();
						col.setText(j + ":");
						grid.add(col, j + 1, 0);
					}
					fields[i][j] = new TextField();
					grid.add(fields[i][j], j + 1, i + 1);
				}
			}
			Button solve = new Button("solve");

			solve.setMinWidth(100);
			solve.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {


						content = takeContents(fields);
						try {
							Parent root = FXMLLoader.load(getClass().getResource("InOutScene.fxml"));
							Scene scene = new Scene(root);
							stage.setScene(scene);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


				}
			});
			grid.add(solve, n, n + 1);
			Scene scene = new Scene(grid, 200 + 100 * n, 200 + 30 * n);
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("input Error");
			alert.setHeaderText(null);
			alert.setContentText("input error, check your input!!");
			alert.showAndWait();
		}

	}

	int[][] takeContents(TextField[][] a) {
		int[][] b = new int[a.length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {

				Pattern pattern = Pattern.compile("^(\\s*?([\\-]?(([0-9]*)([.][0-9]+)?))\\s*?)$");
				Matcher matcher = pattern.matcher(a[i][j].getText());
				if (matcher.find()) {
					if (matcher.group(1).equals("")) {
						b[i][j] = 0;
					} else {
						b[i][j] = Integer.parseInt(matcher.group(1));
					}
				} else {
					throw new RuntimeException();
				}
			}
		}
		return b;
	}

	private Group drawFlowGraph(int graph[][]) {
		int x;
		int y;
		int c;
		Image forw = new Image("/icons8-forward-filled-24.png");
		Image back = new Image("/icons8-back-filled-24.png");
		Group group = new Group();
		for (int i = 0 ; i < graph.length ; i++) {
			for (int j = i ; j < graph.length ; j++) {
				int temp = graph[i][j];
				graph[i][j] = graph[j][i];
				graph[j][i] = temp;
			}
		}

		for (int i = 0; i < graph.length; i++) {
			x = 60 + i * 100;
			y = 140;
			for (int j = 0; j < graph.length; j++) {
				if (i - j != 1 && i != j) {
					if (graph[i][j] != 0) {
						QuadCurve edge = new QuadCurve();
						ImageView imageView;
						Text mag = new Text();
						edge.setStartX(x);
						edge.setStartY(y);
						c = ((Math.abs((60 + 100 * i) + (60 + 100 * j)))) / 2;
						edge.setControlX(c);
						if (i < j) {
							edge.setControlY(140 + 30 * Math.abs(i - j));
							imageView = new ImageView(back);
							imageView.setX(c - 12);
							imageView.setY(140 + (30 * Math.abs(i - j) / 2) - 12);
							mag.setY(140 + (30 * Math.abs(i - j) / 2));
						} else {
							edge.setControlY(140 - 30 * Math.abs(i - j));
							imageView = new ImageView(forw);
							imageView.setX(c - 12);
							imageView.setY(140 - (30 * Math.abs(i - j) / 2) - 12);
							mag.setY(140 - (30 * Math.abs(i - j) / 2));
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
				} else if (Math.abs(i - j) == 1) {
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
						imageView.setLayoutX((Math.abs((60 + 100 * i) + (60 + 100 * j))) / 2 - 12);
						imageView.setY(140 - 12);
						mag.setText(Integer.toString(graph[i][j]));
						mag.setX(((Math.abs((60 + 100 * i) + (60 + 100 * j)))) / 2);
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
						imageView.setX(x - 12);
						imageView.setY(140 - 42);
						mag.setText(Integer.toString(graph[i][j]));
						mag.setX(((Math.abs((60 + 100 * i) + (60 + 100 * j)))) / 2);
						mag.setY(y - 30);
						group.getChildren().add(edge);
						group.getChildren().add(imageView);
						group.getChildren().add(mag);
					}
				}
			}
		}
		for (int i = 0; i < graph.length; i++) {
			Circle node = new Circle(60 + 100 * i, 140, 5);
			node.setFill(javafx.scene.paint.Color.BLUE);
			Text num = new Text();
			num.setLayoutX(40 + 100 * i);
			num.setLayoutY(120);
			num.setText("y" + i);
			num.setFill(javafx.scene.paint.Color.BLACK);
			group.getChildren().add(node);
			group.getChildren().add(num);
		}
		return group;
	}

	public void showGraphScene(ActionEvent event) {
		Stage primaryStage = new Stage();
		System.out.println(content);
		Group root = drawFlowGraph(content);
		Pane drawingPane = new Pane(root);
		ScrollPane pane = new ScrollPane(drawingPane);
		Scene scene = new Scene(pane, 600, 300);

		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();
	}

	public void changeTextLoops(String s) {
		loops.setText(s);
		loops.setEditable(false);
	}

	public void changeTextNon(String s) {
		non_touchArea.setText(s);
		non_touchArea.setEditable(false);
	}

	public void changeTextPath(String s) {
		pathsAreaText.setText(s);
		pathsAreaText.setEditable(false);
	}

	public void changeTextGain(String s) {
		gainTextArea.setText(s);
		gainTextArea.setEditable(false);
	}


	    @FXML
	    void operate(ActionEvent event) {

	    	URL location = getClass().getResource("Main_scene.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = null;
			try {
				root = fxmlLoader.load(location.openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FirstSceneController controller = fxmlLoader.getController();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			String looptext = "";
			graph = new Graph(content);
			loop = new Loop(graph);
			loop.GetAllLoops();
			LinkedList<Integer> LoopG = loop.GainOfLoops();
			loop.Nontouching();
			int Delta = loop.Delta();
            for (int i = 0; i < loop.Getpaths().size(); i++) {
                looptext += i + ") ";
                looptext += loop.Getpaths().get(i).toString();
                looptext += "   Gain = " + LoopG.get(i);
                looptext += "\n";
            }
            looptext += "Delta = " + Delta;
			controller.changeTextLoops(looptext);
			looptext = "";
			for (int i = 0; i < loop.getNonTouch().size(); i++) {
				String non = loop.getNonTouch().get(i);
				for (int j = 0; j < non.length(); j++) {
					looptext += "L" + non.charAt(j) + "  ";
				}
				looptext += "\n";
				looptext += " ";
			}
			controller.changeTextNon(looptext);
			path = new Path(graph, loop);
			try
			{
		    if(Integer.parseInt(begin.getText())>=content.length||Integer.parseInt(end.getText())>=content.length)
		    {
		    	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("input Error");
				alert.setHeaderText(null);
				alert.setContentText("input error, check your input!! out of bounds");
				alert.showAndWait();
		    }
		    else
		    {
			path.getpaths(Integer.parseInt(begin.getText()), Integer.parseInt(end.getText()));
			path.Getpaths();
			LinkedList<Integer> Gainp = path.GainOfPahts();
			Double over = path.overAll();
			looptext = "";
			for (int i = 0; i < path.Getpaths().size(); i++) {
				looptext += path.Getpaths().get(i).toString() + "  " + "Gain = ";
				looptext += Gainp.get(i) + "  " + "Delta = ";
				looptext += path.getDelTaK().get(i) + "\n";
			}
			controller.changeTextPath(looptext);
			controller.changeTextGain(over.toString());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			}
			}
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("input Error");
				alert.setHeaderText(null);
				alert.setContentText("input error, check your input!!");
				alert.showAndWait();
			}
	    }
}
