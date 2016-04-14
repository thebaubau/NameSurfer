/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import com.sun.javafx.sg.prism.NGCircle;

import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

	private ArrayList<NameSurferEntry> namesDisplayed;


	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		namesDisplayed = new ArrayList<>();
	}
	
	public void addGraphBackground(){
		double decadeSeparator = getWidth() / NDECADES;
		double margin = getHeight() - GRAPH_MARGIN_SIZE;
		int decade = START_DECADE;
		Font font = new Font("Serif", Font.ITALIC, 14);

		for (int i = 1; i <= NDECADES; i++){
			GLine vertical = new GLine(decadeSeparator * i, 0, decadeSeparator * i, getHeight());
			vertical.setColor(Color.LIGHT_GRAY);
			GLabel label = new GLabel(Integer.toString(decade), decadeSeparator * (i - 1) + 3, getHeight() - 7);
			label.setFont(font);
			label.setColor(Color.DARK_GRAY);

			add(label);
			add(vertical);
			decade += 10;
		}

		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(new GLine(0, margin, getWidth(), margin));
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		namesDisplayed.clear();
		update();
	}
	
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		namesDisplayed.add(entry);
	}

	/**
	 * Removes an entry from the list and graph
     */
	public void removeEntry(NameSurferEntry entry){
		namesDisplayed.remove(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		addGraphBackground();
		if (namesDisplayed.size() >= 0) {
			for (int i = 0; i < namesDisplayed.size(); i++) {
				drawGraphEntry(i, namesDisplayed.get(i));
			}
		}
	}

	private void drawGraphEntry(int entryNumber, NameSurferEntry entry) {
		double decadeSeparator = getWidth() / 12;
		double radius = 2;
		Font font = new Font("Serif", Font.BOLD, 14);

		for (int i = 0; i < NDECADES - 1; i++) {
			int startRank = entry.getRank(i);
			int nextRank  = entry.getRank(i + 1);

			// Calculating the X coordinate for all graph objects
			double x0 = decadeSeparator * i;
			double x1 = decadeSeparator * (i + 1);

			// Creating the lines, rank labels, names and points at the corresponding coordinates on the graph
			GLabel rankLabel = new GLabel(Integer.toString(startRank), x0 + 3, calculatePosY(startRank) - 3);
			GLabel lastLabel = new GLabel(Integer.toString(nextRank), x1 + 3, calculatePosY(nextRank) - 3);
			GLine line       = new GLine(x0, calculatePosY(startRank), x1, calculatePosY(nextRank));
			GOval marker     = new GOval(x1 - radius, calculatePosY(nextRank) - radius, radius * 2, radius * 2);
			GLabel name      = new GLabel(entry.getName(), 3, calculatePosY(entry.getRank(0)) + 18);

			name.setFont(font);
			marker.setFilled(true);

			// Changing colors for lines and labels
			if (entryNumber % 4 == 0) {
				line.setColor(Color.black);
				rankLabel.setColor(Color.black);
				lastLabel.setColor(Color.black);
				name.setColor(Color.black);
				marker.setColor(Color.black);
			}
			else if (entryNumber % 4 == 1) {
				line.setColor(Color.RED);
				rankLabel.setColor(Color.RED);
				lastLabel.setColor(Color.RED);
				name.setColor(Color.RED);
				marker.setColor(Color.RED);
			}
			else if (entryNumber % 4 == 2) {
				line.setColor(Color.blue);
				rankLabel.setColor(Color.blue);
				lastLabel.setColor(Color.blue);
				name.setColor(Color.blue);
				marker.setColor(Color.blue);
			}
			else if (entryNumber % 4 == 3) {
				line.setColor(Color.MAGENTA);
				rankLabel.setColor(Color.MAGENTA);
				lastLabel.setColor(Color.MAGENTA);
				name.setColor(Color.MAGENTA);
				marker.setColor(Color.MAGENTA);
			}
			add(line);
			add(rankLabel);
			add(lastLabel);
			add(name);
			add(marker);
		}
	}

	private double calculatePosY(int rank){
		double marginDifference = 2 * GRAPH_MARGIN_SIZE;
		double posY;
		if (rank != 0){
			posY = GRAPH_MARGIN_SIZE + (getHeight() - marginDifference) * rank / MAX_RANK;
		}
		else {
			posY = getHeight() - GRAPH_MARGIN_SIZE;
		}
		return posY;
	}
}
