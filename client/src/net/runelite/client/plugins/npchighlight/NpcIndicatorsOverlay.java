package net.runelite.client.plugins.npchighlight;

import com.GameClient;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
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
class NpcIndicatorsOverlay extends Overlay
{
	private final GameClient client;
	private final NpcIndicatorsPlugin plugin;
	private final NpcIndicatorsConfig config;

	@Inject
	private NpcIndicatorsOverlay(GameClient client, NpcIndicatorsPlugin plugin, NpcIndicatorsConfig config)
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
		int npcCount = 0;
		int highlightedCount = 0;
		int renderedCount = 0;

		for (GameClient.NpcInfo npc : client.getNpcs())
		{
			npcCount++;
			if (!plugin.isHighlighted(npc))
			{
				continue;
			}

			highlightedCount++;
			if (renderNpc(graphics, npc))
			{
				renderedCount++;
			}
		}

		plugin.recordRenderStats(npcCount, highlightedCount, renderedCount);
		if (config.debugOverlay())
		{
			renderDebug(graphics);
		}

		return null;
	}

	private boolean renderNpc(Graphics2D graphics, GameClient.NpcInfo npc)
	{
		Color border = config.highlightColor();
		Color fill = config.fillColor();
		Color hullBorder = config.hullColor();
		Color hullFill = config.hullFillColor();
		Color trueTileBorder = config.trueTileColor();
		Color trueTileFill = config.trueTileFillColor();
		BasicStroke stroke = new BasicStroke(Math.max(1.0f, (float) config.borderWidth()));
		LocalPoint localPoint = new LocalPoint(npc.getLocalX(), npc.getLocalY());
		LocalPoint trueLocalPoint = new LocalPoint(npc.getTrueLocalX(), npc.getTrueLocalY());
		boolean drew = false;
		int plane = client.getPlane();

		boolean rendered = false;

		if (config.highlightHull() || config.highlightOutline())
		{
			rendered |= renderNpcHull(graphics, npc, localPoint, plane, hullBorder, hullFill, stroke, config.hullOutline());
		}

		if (config.highlightTile() || config.highlightTrueTile())
		{
			if (config.highlightTile())
			{
				rendered |= renderTileArea(graphics, localPoint, plane, npc.getSize(), border, fill, stroke, config.tileOutline());
			}
			if (config.highlightTrueTile())
			{
				rendered |= renderTileArea(graphics, trueLocalPoint, plane, npc.getSize(), trueTileBorder, trueTileFill, stroke, config.trueTileOutline());
			}
		}

		if (config.highlightSouthWestTile() || config.highlightSouthWestTrueTile())
		{
			if (config.highlightSouthWestTile())
			{
				rendered |= renderTileArea(graphics, southWestTile(localPoint, npc.getSize()), plane, 1, border, fill, stroke, config.southWestTileOutline());
			}
			if (config.highlightSouthWestTrueTile())
			{
				rendered |= renderTileArea(graphics, southWestTile(trueLocalPoint, npc.getSize()), plane, 1, trueTileBorder, trueTileFill, stroke, config.southWestTrueTileOutline());
			}
		}

		if (!rendered)
		{
			rendered = renderFallbackMarker(graphics, localPoint, plane, border, fill, stroke);
		}
		drew |= rendered;

		if (config.drawNames())
		{
			String name = formatNpcName(npc);
			Point textLocation = Perspective.getCanvasTextLocation(client, graphics, localPoint, name, npc.getHeight() + 125);
			if (textLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, textLocation, name, getNameColor(npc, border));
				drew = true;
			}
		}

		return drew;
	}

	private boolean renderNpcHull(Graphics2D graphics, GameClient.NpcInfo npc, LocalPoint localPoint, int plane, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		GameClient.NpcHullInfo hull = client.getNpcHull(npc.getIndex());
		if (hull != null && renderNpcHullHook(graphics, hull, border, fill, stroke, outline))
		{
			return true;
		}

		return renderNpcHullFallback(graphics, localPoint, plane, npc.getHeight(), npc.getSize(), border, fill, stroke, outline);
	}

	private boolean renderNpcHullHook(Graphics2D graphics, GameClient.NpcHullInfo hull, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		GameClient.HullSegment[] segments = hull.getSegments();
		if (segments == null || segments.length == 0)
		{
			return false;
		}

		Area area = new Area();
		for (GameClient.HullSegment segment : segments)
		{
			int radius = Math.max(2, segment.getRadius());
			BasicStroke segmentStroke = new BasicStroke(radius * 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			area.add(new Area(segmentStroke.createStrokedShape(new Line2D.Double(
				segment.getX1(),
				segment.getY1(),
				segment.getX2(),
				segment.getY2()))));
		}

		renderShape(graphics, area, border, fill, stroke, outline);
		return true;
	}

	private boolean renderNpcHullFallback(Graphics2D graphics, LocalPoint localPoint, int plane, int height, int size, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		Point base = Perspective.localToCanvas(client, localPoint, plane, 0);
		Point top = Perspective.localToCanvas(client, localPoint, plane, Math.max(64, height));
		if (base == null || top == null)
		{
			return renderTileArea(graphics, localPoint, plane, size, border, fill, stroke, outline);
		}

		int hullHeight = Math.max(48, Math.abs(base.getY() - top.getY()));
		int hullWidth = Math.max(24, Math.max(size, 1) * 18 + hullHeight / 4);
		int hullTop = Math.min(base.getY(), top.getY());
		Shape hull = new RoundRectangle2D.Double(
			base.getX() - hullWidth / 2.0,
			hullTop,
			hullWidth,
			hullHeight,
			Math.max(12, hullWidth / 2.0),
			Math.max(12, hullWidth / 2.0));
		renderShape(graphics, hull, border, fill, stroke, outline);
		return true;
	}

	private boolean renderTileArea(Graphics2D graphics, LocalPoint localPoint, int plane, int size, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		Polygon tile = Perspective.getCanvasTileAreaPoly(client, localPoint, Math.max(1, size), Math.max(1, size), plane, 0);
		if (tile != null)
		{
			renderShape(graphics, tile, border, fill, stroke, outline);
			return true;
		}

		return false;
	}

	private boolean renderFallbackMarker(Graphics2D graphics, LocalPoint localPoint, int plane, Color border, Color fill, BasicStroke stroke)
	{
		Point point = Perspective.localToCanvas(client, localPoint, plane, 0);
		if (point == null)
		{
			return false;
		}

		Shape marker = new Ellipse2D.Double(point.getX() - 8, point.getY() - 8, 16, 16);
		renderShape(graphics, marker, border, fill, stroke, true);
		return true;
	}

	private static void renderShape(Graphics2D graphics, Shape shape, Color border, Color fill, BasicStroke stroke, boolean outline)
	{
		Color originalColor = graphics.getColor();
		java.awt.Stroke originalStroke = graphics.getStroke();

		graphics.setColor(fill);
		graphics.fill(shape);
		if (outline)
		{
			graphics.setColor(border);
			graphics.setStroke(stroke);
			graphics.draw(shape);
		}

		graphics.setStroke(originalStroke);
		graphics.setColor(originalColor);
	}

	private void renderDebug(Graphics2D graphics)
	{
		String line = plugin.getDebugLine();
		String configLine = "NPC Indicators config: names=" + plugin.getHighlights().size()
			+ " hull=" + config.highlightHull()
			+ " tile=" + config.highlightTile()
			+ " namesOnNpc=" + config.drawNames();
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

	private static LocalPoint southWestTile(LocalPoint localPoint, int size)
	{
		int offset = Math.max(0, size - 1) * Perspective.LOCAL_TILE_SIZE / 2;
		return new LocalPoint(localPoint.getX() - offset, localPoint.getY() - offset);
	}

	private String formatNpcName(GameClient.NpcInfo npc)
	{
		if (!config.drawCombatLevel() || npc.getCombatLevel() < 0)
		{
			return npc.getName();
		}

		return npc.getName() + " (level-" + npc.getCombatLevel() + ")";
	}

	private Color getNameColor(GameClient.NpcInfo npc, Color defaultColor)
	{
		if (!config.highlightColorRelativeToCombatLevel() || npc.getCombatLevel() < 0)
		{
			return defaultColor;
		}

		return combatLevelColor(client.getLocalPlayerCombatLevel(), npc.getCombatLevel());
	}

	private static Color combatLevelColor(int playerCombatLevel, int npcCombatLevel)
	{
		int difference = playerCombatLevel - npcCombatLevel;
		if (difference < -9)
		{
			return new Color(0xff0000);
		}
		if (difference < -6)
		{
			return new Color(0xff3000);
		}
		if (difference < -3)
		{
			return new Color(0xff7000);
		}
		if (difference < 0)
		{
			return new Color(0xffb000);
		}
		if (difference > 9)
		{
			return new Color(0x00ff00);
		}
		if (difference > 6)
		{
			return new Color(0x40ff00);
		}
		if (difference > 3)
		{
			return new Color(0x80ff00);
		}
		if (difference > 0)
		{
			return new Color(0xc0ff00);
		}
		return new Color(0xffff00);
	}
}
