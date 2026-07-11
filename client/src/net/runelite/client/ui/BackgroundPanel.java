/*
 * Copyright (c) 2026, Void
 * All rights reserved.
 */
package net.runelite.client.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class BackgroundPanel extends JPanel
{
	private final BufferedImage background;

	BackgroundPanel(BufferedImage background)
	{
		this.background = background;
		setOpaque(true);
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
	}

	BackgroundPanel(LayoutManager layout, BufferedImage background)
	{
		super(layout);
		this.background = background;
		setOpaque(true);
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
	}

	@Override
	protected void paintComponent(Graphics graphics)
	{
		if (background == null || getWidth() <= 0 || getHeight() <= 0)
		{
			super.paintComponent(graphics);
			return;
		}

		double scale = Math.max(
			(double) getWidth() / background.getWidth(),
			(double) getHeight() / background.getHeight());
		int width = Math.max(1, (int) Math.round(background.getWidth() * scale));
		int height = Math.max(1, (int) Math.round(background.getHeight() * scale));
		int x = (getWidth() - width) / 2;
		int y = (getHeight() - height) / 2;

		Graphics2D graphics2d = (Graphics2D) graphics.create();
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.drawImage(background, x, y, width, height, null);
		graphics2d.dispose();
	}
}
