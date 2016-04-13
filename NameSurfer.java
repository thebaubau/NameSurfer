/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	private JTextField entryName;
	private JButton displayGraph;
	private JButton clearGraph;
	private NameSurferDataBase dataBase;
	private NameSurferGraph graph = new NameSurferGraph();



   /* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);

		JLabel name = new JLabel("Name:");
		entryName = new JTextField(20);
		displayGraph = new JButton("Graph");
		clearGraph = new JButton("Clear");

		add(graph);
		add(name, NORTH);
		add(entryName, NORTH);
		add(displayGraph, NORTH);
		add(clearGraph, NORTH);

		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		System.out.println(graph.getWidth());
		addActionListeners();
	}

   /* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == displayGraph){
			String name = entryName.getText();
			NameSurferEntry entry = dataBase.findEntry(name);
			if (entry != null){
				graph.addEntry(entry);
				graph.update();
			}
		}
		else if (source == clearGraph){
			graph.clear();
		}
	}
}