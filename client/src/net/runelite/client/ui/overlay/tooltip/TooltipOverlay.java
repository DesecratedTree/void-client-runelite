/*
 * Copyright (c) 2017, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.ui.overlay.tooltip;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.GameClient;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.config.TooltipPositionType;
import net.runelite.client.plugins.mousehighlight.MouseHighlightConfig;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TooltipComponent;

@Singleton
public class TooltipOverlay extends Overlay
{
	private static final int UNDER_OFFSET = 24;
	private static final int PADDING = 2;
	private final TooltipManager tooltipManager;
	private final GameClient client;
	private final RuneLiteConfig runeLiteConfig;
	private final MouseHighlightConfig mouseHighlightConfig;

	@Inject
	private TooltipOverlay(GameClient client, TooltipManager tooltipManager, final RuneLiteConfig runeLiteConfig, final ConfigManager configManager)
	{
		this.client = client;
		this.tooltipManager = tooltipManager;
		this.runeLiteConfig = runeLiteConfig;
		this.mouseHighlightConfig = configManager.getConfig(MouseHighlightConfig.class);
		setPosition(OverlayPosition.TOOLTIP);
		setPriority(OverlayPriority.HIGHEST);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		// additionally allow tooltips above the full screen world map and welcome screen
//		drawAfterInterface(WidgetID.FULLSCREEN_CONTAINER_TLI);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final List<Tooltip> tooltips = tooltipManager.getTooltips();

		if (tooltips.isEmpty())
		{
			return null;
		}

		try
		{
			return renderTooltips(graphics, tooltips);
		}
		finally
		{
			// Tooltips must always be cleared each frame
			tooltipManager.clear();
		}
	}

	private Dimension renderTooltips(Graphics2D graphics, List<Tooltip> tooltips)
	{
		final int canvasWidth = client.getCanvasWidth();
		final int canvasHeight = client.getCanvasHeight();
		final net.runelite.api.Point mouseCanvasPosition = client.getMouseCanvasPosition();
		final Rectangle prevBounds = getBounds();

		final int tooltipX = clamp(mouseCanvasPosition.getX() + mouseHighlightConfig.tooltipOffsetX(), 0, canvasWidth - prevBounds.width);
		final int baseTooltipY = runeLiteConfig.tooltipPosition() == TooltipPositionType.ABOVE_CURSOR
				? mouseCanvasPosition.getY() - prevBounds.height
				: mouseCanvasPosition.getY() + UNDER_OFFSET;
		final int tooltipY = clamp(baseTooltipY + mouseHighlightConfig.tooltipOffsetY(), 0, canvasHeight - prevBounds.height);

		final Rectangle newBounds = new Rectangle(tooltipX, tooltipY, 0, 0);

		for (Tooltip tooltip : tooltips)
		{
			final LayoutableRenderableEntity entity;

			if (tooltip.getComponent() != null)
			{
				entity = tooltip.getComponent();
				if (entity instanceof PanelComponent)
				{
					((PanelComponent) entity).setBackgroundColor(runeLiteConfig.overlayBackgroundColor());
				}
			}
			else
			{
				final TooltipComponent tooltipComponent = new TooltipComponent();
				tooltipComponent.setText(tooltip.getText());
				tooltipComponent.setBackgroundColor(runeLiteConfig.overlayBackgroundColor());
				entity = tooltipComponent;
			}

			entity.setPreferredLocation(new Point(tooltipX, tooltipY + newBounds.height));
			final Dimension dimension = entity.render(graphics);

			// Create incremental tooltip newBounds
			newBounds.height += dimension.height + PADDING;
			newBounds.width = Math.max(newBounds.width, dimension.width);
		}

		return newBounds.getSize();
	}

	private static int clamp(int value, int min, int max)
	{
		if (max < min)
		{
			return min;
		}

		return Math.max(min, Math.min(max, value));
	}
}
