package net.runelite.client.plugins.xptracker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.SkillColor;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.ProgressBarComponent;
import net.runelite.client.ui.overlay.components.SplitComponent;

class XpTrackerOverlay extends OverlayPanel
{
	private static final int BORDER_SIZE = 2;
	private static final int XP_AND_PROGRESS_BAR_GAP = 2;
	private static final int XP_AND_ICON_GAP = 4;
	private static final Rectangle XP_AND_ICON_COMPONENT_BORDER = new Rectangle(2, 1, 4, 0);

	private final XpTrackerPlugin plugin;
	private final XpTrackerConfig config;
	private final SkillIconManager skillIconManager;

	@Inject
	XpTrackerOverlay(XpTrackerPlugin plugin, XpTrackerConfig config, SkillIconManager skillIconManager)
	{
		super(plugin);
		this.plugin = plugin;
		this.config = config;
		this.skillIconManager = skillIconManager;
		setPosition(OverlayPosition.TOP_RIGHT);
		setLayer(OverlayLayer.UNDER_WIDGETS);
		setPriority(OverlayPriority.LOW);
		panelComponent.setBorder(new Rectangle(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		panelComponent.setGap(new Point(0, XP_AND_PROGRESS_BAR_GAP));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		graphics.setFont(FontManager.getRunescapeSmallFont());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("XP Tracker")
			.right(format(plugin.getTotalGained()) + " / " + format(plugin.getXpPerHour()) + " xp/hr")
			.leftColor(Color.WHITE)
			.rightColor(new Color(135, 220, 255))
			.build());

		int rows = 0;
		for (XpTrackerPlugin.XpState state : plugin.getSkills())
		{
			if (state.getGained() <= 0 || rows++ >= config.maxRows())
			{
				continue;
			}

			panelComponent.getChildren().add(skillPanel(state));
		}

		return super.render(graphics);
	}

	private PanelComponent skillPanel(XpTrackerPlugin.XpState state)
	{
		XpSnapshotSingle snapshot = new XpSnapshotSingle(state);
		PanelComponent skillPanel = new PanelComponent();
		PanelComponent iconXpSplitPanel = new PanelComponent();
		iconXpSplitPanel.setBorder(XP_AND_ICON_COMPONENT_BORDER);
		iconXpSplitPanel.setBackgroundColor(null);

		BufferedImage icon = skillIconManager.getSkillImage(state.getSkill(), true);
		ImageComponent imageComponent = new ImageComponent(icon);

		LineComponent topLine = LineComponent.builder()
			.left(config.onScreenDisplayMode().getKey() + ":")
			.right(config.onScreenDisplayMode().getValueFunc().apply(snapshot))
			.build();
		LineComponent bottomLine = LineComponent.builder()
			.left(config.onScreenDisplayModeBottom().getKey() + ":")
			.right(config.onScreenDisplayModeBottom().getValueFunc().apply(snapshot))
			.build();
		SplitComponent xpSplit = SplitComponent.builder()
			.first(topLine)
			.second(bottomLine)
			.orientation(ComponentOrientation.VERTICAL)
			.build();
		SplitComponent iconXpSplit = SplitComponent.builder()
			.first(imageComponent)
			.second(xpSplit)
			.orientation(ComponentOrientation.HORIZONTAL)
			.gap(new Point(XP_AND_ICON_GAP, 0))
			.build();

		ProgressBarComponent progressBarComponent = new ProgressBarComponent();
		progressBarComponent.setBackgroundColor(new Color(61, 56, 49));
		progressBarComponent.setForegroundColor(SkillColor.find(state.getSkill()).getColor());
		progressBarComponent.setLeftLabel(String.valueOf(snapshot.getStartLevel()));
		progressBarComponent.setRightLabel(String.valueOf(snapshot.getEndLevel()));
		progressBarComponent.setCost(snapshot.getSkillProgressToGoal());

		iconXpSplitPanel.getChildren().add(iconXpSplit);
		skillPanel.getChildren().add(iconXpSplitPanel);
		skillPanel.getChildren().add(progressBarComponent);
		return skillPanel;
	}

	private static String format(int value)
	{
		return String.format("%,d", value);
	}
}
