package net.runelite.client.plugins.inventorygrid;

import com.GameClient;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.WidgetID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.MouseListener;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.util.AsyncBufferedImage;

class InventoryGridOverlay extends Overlay implements MouseListener
{
	private static final int INVENTORY_SIZE = 28;
	private static final int DISTANCE_TO_ACTIVATE_HOVER = 5;
	private static final int INVENTORY_COLUMNS = 4;
	private static final int INVENTORY_SLOT_SIZE = 32;
	private static final int INVENTORY_SLOT_X_STEP = 42;
	private static final int INVENTORY_SLOT_Y_STEP = 36;
	private static final int INVENTORY_RIGHT_INSET = 202;
	private static final int INVENTORY_BOTTOM_INSET = 290;
	private static final Object FALLBACK_INVENTORY_WIDGET = new Object();

	private final InventoryGridConfig config;
	private final GameClient client;
	private final ItemManager itemManager;
	private final OverlayManager overlayManager;

	private final List<WidgetItem> lastInventoryItems = new ArrayList<>();

	private Point mousePoint;
	private Point initialMousePoint;
	private WidgetItem draggedItem;
	private boolean mouseDown;
	private boolean hoverActive;

	@Inject
	InventoryGridOverlay(
		InventoryGridPlugin plugin,
		InventoryGridConfig config,
		GameClient client,
		ItemManager itemManager,
		OverlayManager overlayManager)
	{
		super(plugin);
		this.config = config;
		this.client = client;
		this.itemManager = itemManager;
		this.overlayManager = overlayManager;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
		setPriority(OverlayPriority.HIGH);
		drawAfterInterface(WidgetID.FIXED_VIEWPORT_GROUP_ID);
		drawAfterInterface(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID);
		drawAfterInterface(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		List<WidgetItem> inventoryItems = findInventoryItems(overlayManager.getWidgetItems(), mousePoint);
		if (inventoryItems.isEmpty())
		{
			inventoryItems = buildFallbackInventoryItems();
		}

		if (!inventoryItems.isEmpty())
		{
			lastInventoryItems.clear();
			lastInventoryItems.addAll(inventoryItems);
		}

		if (!mouseDown || mousePoint == null || initialMousePoint == null)
		{
			return null;
		}

		if (!hoverActive && initialMousePoint.distance(mousePoint) < DISTANCE_TO_ACTIVATE_HOVER)
		{
			return null;
		}
		hoverActive = true;

		if (inventoryItems.isEmpty())
		{
			inventoryItems = new ArrayList<>(lastInventoryItems);
		}
		if (inventoryItems.size() < INVENTORY_SIZE)
		{
			return null;
		}

		Rectangle initialBounds = draggedItem.getCanvasBounds(false);
		for (int i = 0; i < INVENTORY_SIZE; i++)
		{
			WidgetItem targetItem = inventoryItems.get(i);
			Rectangle bounds = targetItem.getCanvasBounds(false);
			if (bounds == null)
			{
				continue;
			}

			boolean inBounds = bounds.contains(mousePoint);
			if (config.showItem() && inBounds && draggedItem != null)
			{
				drawItem(graphics, bounds, draggedItem);
				drawItem(graphics, initialBounds, targetItem);
			}

			if (config.showHighlight() && inBounds)
			{
				graphics.setColor(config.highlightColor());
				graphics.fill(bounds);
			}
			else if (config.showGrid())
			{
				graphics.setColor(config.gridColor());
				graphics.fill(bounds);
			}
		}

		return null;
	}

	@Override
	public MouseEvent mouseClicked(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mousePressed(MouseEvent mouseEvent)
	{
		mousePoint = mouseEvent.getPoint();
		initialMousePoint = mouseEvent.getPoint();
		mouseDown = true;
		hoverActive = false;
		draggedItem = findItemAt(lastInventoryItems, mousePoint);
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseReleased(MouseEvent mouseEvent)
	{
		resetDrag();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseEntered(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseExited(MouseEvent mouseEvent)
	{
		resetDrag();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseDragged(MouseEvent mouseEvent)
	{
		mousePoint = mouseEvent.getPoint();
		if (draggedItem == null)
		{
			draggedItem = findItemAt(lastInventoryItems, initialMousePoint);
		}
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseMoved(MouseEvent mouseEvent)
	{
		mousePoint = mouseEvent.getPoint();
		return mouseEvent;
	}

	void resetDrag()
	{
		mouseDown = false;
		hoverActive = false;
		mousePoint = null;
		initialMousePoint = null;
		draggedItem = null;
	}

	private static WidgetItem findItemAt(List<WidgetItem> items, Point point)
	{
		if (point == null)
		{
			return null;
		}

		for (WidgetItem item : items)
		{
			Rectangle bounds = item.getCanvasBounds(false);
			if (bounds != null && bounds.contains(point) && item.getId() != -1)
			{
				return item;
			}
		}
		return null;
	}

	private static List<WidgetItem> findInventoryItems(Iterable<WidgetItem> widgetItems, Point preferredPoint)
	{
		Map<Object, List<WidgetItem>> byWidget = new HashMap<>();
		for (WidgetItem widgetItem : widgetItems)
		{
			Rectangle bounds = widgetItem.getCanvasBounds(false);
			if (bounds == null || bounds.width <= 0 || bounds.height <= 0)
			{
				continue;
			}

			byWidget.computeIfAbsent(widgetItem.getWidget(), key -> new ArrayList<>()).add(widgetItem);
		}

		List<WidgetItem> best = new ArrayList<>();
		for (List<WidgetItem> candidate : byWidget.values())
		{
			candidate.sort(Comparator.comparingInt(WidgetItem::getIndex));
			if (!looksLikeInventory(candidate))
			{
				continue;
			}

			List<WidgetItem> normalized = new ArrayList<>(candidate.subList(0, INVENTORY_SIZE));
			if (preferredPoint != null && contains(normalized, preferredPoint))
			{
				return normalized;
			}
			if (best.isEmpty())
			{
				best = normalized;
			}
		}
		return best;
	}

	private List<WidgetItem> buildFallbackInventoryItems()
	{
		Dimension dimensions = client.getRealDimensions();
		int canvasWidth = dimensions == null || dimensions.width <= 0 ? client.getCanvasWidth() : dimensions.width;
		int canvasHeight = dimensions == null || dimensions.height <= 0 ? client.getCanvasHeight() : dimensions.height;
		if (canvasWidth <= 0 || canvasHeight <= 0)
		{
			return new ArrayList<>();
		}

		int startX = Math.max(0, canvasWidth - INVENTORY_RIGHT_INSET);
		int startY = Math.max(0, canvasHeight - INVENTORY_BOTTOM_INSET);
		List<WidgetItem> items = new ArrayList<>(INVENTORY_SIZE);
		for (int i = 0; i < INVENTORY_SIZE; i++)
		{
			int column = i % INVENTORY_COLUMNS;
			int row = i / INVENTORY_COLUMNS;
			Rectangle bounds = new Rectangle(
				startX + column * INVENTORY_SLOT_X_STEP,
				startY + row * INVENTORY_SLOT_Y_STEP,
				INVENTORY_SLOT_SIZE,
				INVENTORY_SLOT_SIZE);
			items.add(new WidgetItem(-1, 0, i, bounds, FALLBACK_INVENTORY_WIDGET, null));
		}
		return items;
	}

	private static boolean contains(List<WidgetItem> items, Point point)
	{
		for (WidgetItem item : items)
		{
			Rectangle bounds = item.getCanvasBounds(false);
			if (bounds != null && bounds.contains(point))
			{
				return true;
			}
		}
		return false;
	}

	private static boolean looksLikeInventory(List<WidgetItem> items)
	{
		if (items.size() < INVENTORY_SIZE)
		{
			return false;
		}

		for (int i = 0; i < INVENTORY_SIZE; i++)
		{
			WidgetItem item = items.get(i);
			Rectangle bounds = item.getCanvasBounds(false);
			if (item.getIndex() != i || bounds == null || bounds.width < 20 || bounds.height < 20)
			{
				return false;
			}
		}
		return true;
	}

	private void drawItem(Graphics2D graphics, Rectangle bounds, WidgetItem item)
	{
		if (bounds == null || item == null || item.getId() == -1)
		{
			return;
		}

		AsyncBufferedImage image = getItemImage(item);
		if (image == null)
		{
			return;
		}

		Composite previousComposite = graphics.getComposite();
		graphics.setComposite(AlphaComposite.SrcOver.derive(0.3f));
		graphics.drawImage((BufferedImage) image, bounds.x, bounds.y, null);
		graphics.setComposite(previousComposite);
	}

	private AsyncBufferedImage getItemImage(WidgetItem item)
	{
		try
		{
			return itemManager.getImage(item.getId(), item.getQuantity(), false);
		}
		catch (RuntimeException ex)
		{
			return null;
		}
	}
}
