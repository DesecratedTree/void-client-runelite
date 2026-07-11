package net.runelite.client.plugins.itemhighlights;

import com.GameClient;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
class ItemHighlightsOverlay extends Overlay
{
	private final GameClient client;
	private final ItemHighlightsPlugin plugin;
	private final ItemHighlightsConfig config;

	@Inject
	private ItemHighlightsOverlay(GameClient client, ItemHighlightsPlugin plugin, ItemHighlightsConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		graphics.setFont(FontManager.getRunescapeSmallFont());
		int itemCount = 0;
		int highlightedCount = 0;
		int renderedCount = 0;

		for (GameClient.GroundItemInfo item : client.getGroundItems())
		{
			itemCount++;
			if (!plugin.isHighlighted(item))
			{
				continue;
			}

			highlightedCount++;
			if (renderItem(graphics, item))
			{
				renderedCount++;
			}
		}

		plugin.recordRenderStats(itemCount, highlightedCount, renderedCount);
		if (config.debugOverlay())
		{
			renderDebug(graphics);
		}

		return null;
	}

	private boolean renderItem(Graphics2D graphics, GameClient.GroundItemInfo item)
	{
		LocalPoint localPoint = new LocalPoint(item.getLocalX(), item.getLocalY());
		BasicStroke stroke = new BasicStroke(Math.max(1.0f, (float) config.borderWidth()));
		boolean rendered = false;

		if (config.highlightTile())
		{
			rendered |= renderTile(graphics, localPoint, item.getPlane(), config.highlightColor(), config.fillColor(), stroke, config.tileOutline());
		}

		if (config.highlightTrueTile())
		{
			rendered |= renderTile(graphics, localPoint, item.getPlane(), config.trueTileColor(), config.trueTileFillColor(), stroke, config.trueTileOutline());
		}

		if (config.drawNames())
		{
			String label = formatItemName(item);
			Point textLocation = Perspective.getCanvasTextLocation(client, graphics, localPoint, label, 70);
			if (textLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, textLocation, label, config.highlightColor());
				rendered = true;
			}
		}

		return rendered;
	}

	private boolean renderTile(Graphics2D graphics, LocalPoint localPoint, int plane, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		Polygon tile = Perspective.getCanvasTilePoly(client, localPoint);
		if (tile == null)
		{
			return false;
		}

		Color originalColor = graphics.getColor();
		java.awt.Stroke originalStroke = graphics.getStroke();
		graphics.setColor(fill);
		graphics.fill(tile);
		if (outline)
		{
			graphics.setColor(border);
			graphics.setStroke(stroke);
			graphics.draw(tile);
		}
		graphics.setStroke(originalStroke);
		graphics.setColor(originalColor);
		return true;
	}

	private String formatItemName(GameClient.GroundItemInfo item)
	{
		if (!config.showQuantity() || item.getQuantity() <= 1)
		{
			return item.getName();
		}

		return item.getName() + " x" + item.getQuantity();
	}

	private void renderDebug(Graphics2D graphics)
	{
		String line = plugin.getDebugLine();
		String configLine = "Item Highlights config: names=" + plugin.getHighlights().size()
			+ " tile=" + config.highlightTile()
			+ " trueTile=" + config.highlightTrueTile()
			+ " namesOnGround=" + config.drawNames();
		int x = 8;
		int y = 22;
		int width = Math.max(graphics.getFontMetrics().stringWidth(line), graphics.getFontMetrics().stringWidth(configLine)) + 12;
		int height = graphics.getFontMetrics().getHeight() * 2 + 10;

		graphics.setColor(new Color(0, 0, 0, 180));
		graphics.fillRect(x - 4, y - graphics.getFontMetrics().getAscent() - 4, width, height);
		graphics.setColor(Color.YELLOW);
		graphics.drawString(line, x, y);
		graphics.drawString(configLine, x, y + graphics.getFontMetrics().getHeight());
	}
}
