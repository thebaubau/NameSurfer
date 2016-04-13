/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
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
		double decadeSeparator = getWidth() / 12;
		double margin = getHeight() - GRAPH_MARGIN_SIZE;
		int decade = START_DECADE;

		for (int i = 1; i <= NDECADES; i++){
			add(new GLine(decadeSeparator * i, 0, decadeSeparator * i, getHeight()));
			add(new GLabel(Integer.toString(decade), decadeSeparator * (i - 1), getHeight() - 10));
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
	}
	
	
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		namesDisplayed.add(entry);
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
				NameSurferEntry entry = namesDisplayed.get(i);
				drawGraphEntry(i, entry);
			}
		}
	}

	private void drawGraphEntry(int entryNumber, NameSurferEntry entry) {
		String name = entry.getName();

		for (int i = 0; i < NDECADES; i++) {
			int startRank = entry.getRank(i);
			int nextRank  = entry.getRank(i + 1);

			double decadeSeparator = getWidth() / NDECADES;
			double marginDifference = getHeight() - GRAPH_MARGIN_SIZE;
			double x1 = decadeSeparator * i;
			double x2 = decadeSeparator * (i + 1);
			double y1 = 0;
			double y2 = 0;

			if (startRank != 0 && nextRank != 0) {
				y1 = GRAPH_MARGIN_SIZE + (marginDifference * 2) * startRank / MAX_RANK;
				y2 = GRAPH_MARGIN_SIZE + (marginDifference * 2) * nextRank / MAX_RANK;
			}
			else if (startRank == 0 && nextRank == 0) {
				y1 = marginDifference;
				y2 = marginDifference;
			}
			else if (startRank == 0){
				y1 = marginDifference;
				y2 = GRAPH_MARGIN_SIZE + (marginDifference * 2) * nextRank / MAX_RANK;
			}
			else if (nextRank == 0) {
				y1 = GRAPH_MARGIN_SIZE + (marginDifference * 2) * startRank / MAX_RANK;
				y2 = marginDifference;
			}

			GLine line = new GLine(x1, y1, x2, y2);
			if (entryNumber % 4 == 1) {
				line.setColor(Color.RED);
			}
			else if (entryNumber % 4 == 2) {
				line.setColor(Color.BLUE);
			}
			else if (entryNumber % 4 == 3) {
				line.setColor(Color.MAGENTA);
			}

			add(line);
		}
	}
}
