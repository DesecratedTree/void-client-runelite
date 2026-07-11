package net.runelite.client.plugins.inventorygrid;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public final class InventoryGridCanvasOverlay
{
	private static final int INVENTORY_SIZE = 28;
	private static final int INVENTORY_COLUMNS = 4;
	private static final int SLOT_SIZE = 32;
	private static final int SLOT_X_STEP = 42;
	private static final int SLOT_Y_STEP = 36;
	private static final int RIGHT_INSET = 202;
	private static final int BOTTOM_INSET = 290;
	private static final int DRAG_DISTANCE = 5;
	private static final Color GRID_COLOR = new Color(255, 255, 255, 45);
	private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 255, 95);

	private static boolean installed;
	private static boolean mouseDown;
	private static Point mousePoint;
	private static Point initialMousePoint;
	private static Component currentCanvas;

	private InventoryGridCanvasOverlay()
	{
	}

	public static void paint(Graphics graphics, Component canvas)
	{
		install();
		currentCanvas = canvas;
		if (!(graphics instanceof Graphics2D) || canvas == null || !mouseDown || mousePoint == null || initialMousePoint == null)
		{
			return;
		}

		Rectangle inventoryBounds = inventoryBounds(canvas);
		if (!inventoryBounds.contains(initialMousePoint) || initialMousePoint.distance(mousePoint) < DRAG_DISTANCE)
		{
			return;
		}

		Graphics2D graphics2D = (Graphics2D) graphics.create();
		try
		{
			for (int i = 0; i < INVENTORY_SIZE; i++)
			{
				Rectangle slot = slotBounds(inventoryBounds, i);
				graphics2D.setColor(slot.contains(mousePoint) ? HIGHLIGHT_COLOR : GRID_COLOR);
				graphics2D.fill(slot);
			}
		}
		finally
		{
			graphics2D.dispose();
		}
	}

	private static synchronized void install()
	{
		if (installed)
		{
			return;
		}

		Toolkit.getDefaultToolkit().addAWTEventListener(new InventoryGridMouseTracker(),
			AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		installed = true;
	}

	private static Rectangle inventoryBounds(Component canvas)
	{
		int width = Math.max(1, canvas.getWidth());
		int height = Math.max(1, canvas.getHeight());
		return new Rectangle(
			Math.max(0, width - RIGHT_INSET),
			Math.max(0, height - BOTTOM_INSET),
			(INVENTORY_COLUMNS - 1) * SLOT_X_STEP + SLOT_SIZE,
			((INVENTORY_SIZE - 1) / INVENTORY_COLUMNS) * SLOT_Y_STEP + SLOT_SIZE);
	}

	private static Rectangle slotBounds(Rectangle inventoryBounds, int index)
	{
		int column = index % INVENTORY_COLUMNS;
		int row = index / INVENTORY_COLUMNS;
		return new Rectangle(
			inventoryBounds.x + column * SLOT_X_STEP,
			inventoryBounds.y + row * SLOT_Y_STEP,
			SLOT_SIZE,
			SLOT_SIZE);
	}

	private static final class InventoryGridMouseTracker implements AWTEventListener
	{
		@Override
		public void eventDispatched(AWTEvent event)
		{
			if (!(event instanceof MouseEvent))
			{
				return;
			}

			MouseEvent mouseEvent = (MouseEvent) event;
			if (!(mouseEvent.getSource() instanceof Component))
			{
				return;
			}

			Component canvas = currentCanvas;
			if (canvas == null)
			{
				return;
			}

			Component source = (Component) mouseEvent.getSource();
			Point point = SwingUtilities.convertPoint(source, mouseEvent.getPoint(), canvas);
			switch (mouseEvent.getID())
			{
				case MouseEvent.MOUSE_PRESSED:
					if (SwingUtilities.isLeftMouseButton(mouseEvent))
					{
						mouseDown = true;
						mousePoint = point;
						initialMousePoint = point;
					}
					break;
				case MouseEvent.MOUSE_DRAGGED:
				case MouseEvent.MOUSE_MOVED:
					mousePoint = point;
					break;
				case MouseEvent.MOUSE_RELEASED:
				case MouseEvent.MOUSE_EXITED:
					mouseDown = false;
					mousePoint = null;
					initialMousePoint = null;
					break;
				default:
					break;
			}
		}
	}
}
