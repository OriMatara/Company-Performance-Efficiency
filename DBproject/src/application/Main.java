package application;

import javafx.application.Application;
import javafx.stage.Stage;
import application.mvc.controller.ExperimentController;
import application.mvc.model.Experiment;
import application.mvc.view.ExperimentView;
import application.mvc.view.InterfaceExperimentView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		Experiment model = new Experiment();
		InterfaceExperimentView theView = new ExperimentView(primaryStage);
		ExperimentController Controller = new ExperimentController(theView, model);
	
	}

}