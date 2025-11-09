package Main.Interactables;

import java.awt.Color;
import java.awt.Graphics;

// Draws a visual indicator for the exit inside the door area.
public class Exit {

	/**
	 * Draw a thin white rectangle inside the door bounds to indicate the exit.
	 * @param g Graphics context
	 * @param x door x
	 * @param y door y
	 * @param w door width
	 * @param h door height
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		Color prev = g.getColor();
		g.setColor(Color.WHITE);

		// Draw a thin horizontal rectangle (like a slot) centered in the door.
		int rectW = Math.max(8, w - 20);
		int rectH = 6; // thin
		int rectX = x + (w - rectW) / 2;
		int rectY = y + (h - rectH) / 2;

		g.fillRect(rectX, rectY, rectW, rectH);

		g.setColor(prev);
	}

}
