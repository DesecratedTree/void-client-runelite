package net.runelite.client.plugins.xptracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.SkillColor;
import net.runelite.client.ui.components.MouseDragEventForwarder;
import net.runelite.client.ui.components.ProgressBar;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.QuantityFormatter;

class XpInfoBox extends JPanel
{
	static final DecimalFormat TWO_DECIMAL_FORMAT = new DecimalFormat("0.00");

	static
	{
		TWO_DECIMAL_FORMAT.setRoundingMode(RoundingMode.DOWN);
	}

	private static final String HTML_LABEL_TEMPLATE =
		"<html><body style='color:%s'>%s<span style='color:white'>%s</span></body></html>";
	private static final EmptyBorder DEFAULT_PROGRESS_WRAPPER_BORDER = new EmptyBorder(0, 7, 7, 7);
	private static final EmptyBorder COMPACT_PROGRESS_WRAPPER_BORDER = new EmptyBorder(5, 1, 5, 5);

	private final JComponent panel;

	@Getter
	private final Skill skill;

	private final JPanel container = new JPanel();
	private final JPanel headerPanel = new JPanel();
	private final JPanel statsPanel = new JPanel();
	private final JPanel progressWrapper = new JPanel();
	private final JLabel compactSkillIcon;
	private final ProgressBar progressBar = new ProgressBar();
	private final JLabel topLeftStat = new JLabel();
	private final JLabel bottomLeftStat = new JLabel();
	private final JLabel topRightStat = new JLabel();
	private final JLabel bottomRightStat = new JLabel();
	private final XpTrackerConfig xpTrackerConfig;

	XpInfoBox(XpTrackerPlugin xpTrackerPlugin, XpTrackerConfig xpTrackerConfig, JComponent panel, Skill skill, SkillIconManager iconManager)
	{
		this.xpTrackerConfig = xpTrackerConfig;
		this.panel = panel;
		this.skill = skill;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 0, 0, 0));

		container.setLayout(new BorderLayout());
		container.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(e -> xpTrackerPlugin.resetSkillState(skill));

		JMenuItem resetOthers = new JMenuItem("Reset others");
		resetOthers.addActionListener(e -> xpTrackerPlugin.resetOtherSkillState(skill));

		JMenuItem resetPerHour = new JMenuItem("Reset/hr");
		resetPerHour.addActionListener(e -> xpTrackerPlugin.resetSkillPerHourState(skill));

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(reset);
		popupMenu.add(resetOthers);
		popupMenu.add(resetPerHour);

		headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		headerPanel.setLayout(new BorderLayout());

		statsPanel.setLayout(new DynamicGridLayout(2, 2));
		statsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		statsPanel.setBorder(new EmptyBorder(9, 2, 9, 2));

		topLeftStat.setFont(FontManager.getRunescapeSmallFont());
		bottomLeftStat.setFont(FontManager.getRunescapeSmallFont());
		topRightStat.setFont(FontManager.getRunescapeSmallFont());
		bottomRightStat.setFont(FontManager.getRunescapeSmallFont());

		statsPanel.add(topLeftStat);
		statsPanel.add(topRightStat);
		statsPanel.add(bottomLeftStat);
		statsPanel.add(bottomRightStat);

		headerPanel.add(getSkillIcon(iconManager, skill, 35, 35, false), BorderLayout.WEST);
		headerPanel.add(statsPanel, BorderLayout.CENTER);

		progressWrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		progressWrapper.setLayout(new BorderLayout());
		progressWrapper.setBorder(DEFAULT_PROGRESS_WRAPPER_BORDER);

		progressBar.setMaximumValue(100);
		progressBar.setBackground(new Color(61, 56, 49));
		progressBar.setForeground(SkillColor.find(skill).getColor());
		progressBar.setDimmedText("Paused");

		compactSkillIcon = getSkillIcon(iconManager, skill, 25, 16, true);
		compactSkillIcon.setVisible(false);

		progressWrapper.add(compactSkillIcon, BorderLayout.WEST);
		progressWrapper.add(progressBar, BorderLayout.CENTER);

		container.add(headerPanel, BorderLayout.NORTH);
		container.add(progressWrapper, BorderLayout.SOUTH);
		container.setComponentPopupMenu(popupMenu);
		progressBar.setComponentPopupMenu(popupMenu);

		MouseDragEventForwarder mouseDragEventForwarder = new MouseDragEventForwarder(panel);
		container.addMouseListener(mouseDragEventForwarder);
		container.addMouseMotionListener(mouseDragEventForwarder);
		progressBar.addMouseListener(mouseDragEventForwarder);
		progressBar.addMouseMotionListener(mouseDragEventForwarder);

		MouseAdapter clickToggleCompact = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					setCompactView(headerPanel.isVisible());
				}
			}
		};
		container.addMouseListener(clickToggleCompact);
		progressBar.addMouseListener(clickToggleCompact);

		add(container, BorderLayout.NORTH);
	}

	void reset()
	{
		if (removeFromPanel())
		{
			panel.revalidate();
			panel.repaint();
		}
	}

	void update(boolean updated, boolean paused, XpSnapshotSingle xpSnapshotSingle)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			rebuildAsync(updated, paused, xpSnapshotSingle);
		}
		else
		{
			SwingUtilities.invokeLater(() -> rebuildAsync(updated, paused, xpSnapshotSingle));
		}
	}

	boolean ensureVisible()
	{
		if (getParent() == panel)
		{
			return false;
		}

		panel.add(this);
		return true;
	}

	boolean removeFromPanel()
	{
		setCompactView(false);
		if (getParent() != panel)
		{
			return false;
		}

		panel.remove(this);
		return true;
	}

	private void setCompactView(boolean compact)
	{
		progressWrapper.setBorder(compact ? COMPACT_PROGRESS_WRAPPER_BORDER : DEFAULT_PROGRESS_WRAPPER_BORDER);
		headerPanel.setVisible(!compact);
		compactSkillIcon.setVisible(compact);
	}

	private static JLabel getSkillIcon(SkillIconManager iconManager, Skill skill, int width, int height, boolean small)
	{
		JLabel skillIcon = new JLabel();
		skillIcon.setIcon(new ImageIcon(iconManager.getSkillImage(skill, small)));
		skillIcon.setPreferredSize(new Dimension(width, height));
		skillIcon.setHorizontalAlignment(SwingConstants.CENTER);
		skillIcon.setVerticalAlignment(SwingConstants.CENTER);
		return skillIcon;
	}

	private void rebuildAsync(boolean updated, boolean paused, XpSnapshotSingle xpSnapshotSingle)
	{
		if (updated)
		{
			progressBar.setCost((int) xpSnapshotSingle.getSkillProgressToGoal());
			progressBar.setCenterLabel(xpTrackerConfig.progressBarLabel().getValueFunc().apply(xpSnapshotSingle));
			progressBar.setLeftLabel("Lvl. " + xpSnapshotSingle.getStartLevel());
			progressBar.setRightLabel("Lvl. " + xpSnapshotSingle.getEndLevel());
			progressBar.setToolTipText(xpTrackerConfig.progressBarTooltipLabel().getValueFunc().apply(xpSnapshotSingle) + " till goal lvl");
			progressBar.setDimmed(paused);
		}

		topLeftStat.setText(htmlLabel(xpTrackerConfig.xpPanelLabel1(), xpSnapshotSingle));
		topRightStat.setText(htmlLabel(xpTrackerConfig.xpPanelLabel2(), xpSnapshotSingle));
		bottomLeftStat.setText(htmlLabel(xpTrackerConfig.xpPanelLabel3(), xpSnapshotSingle));
		bottomRightStat.setText(htmlLabel(xpTrackerConfig.xpPanelLabel4(), xpSnapshotSingle));
	}

	private String htmlLabel(XpPanelLabel panelLabel, XpSnapshotSingle xpSnapshotSingle)
	{
		if (isActions(panelLabel) && isCombatSkill(skill))
		{
			return "";
		}

		return htmlLabel(panelLabel.getKey() + ": ", panelLabel.getValueFunc().apply(xpSnapshotSingle));
	}

	private static boolean isActions(XpPanelLabel panelLabel)
	{
		return panelLabel == XpPanelLabel.ACTIONS_LEFT || panelLabel == XpPanelLabel.ACTIONS_HOUR || panelLabel == XpPanelLabel.ACTIONS_DONE;
	}

	private static boolean isCombatSkill(Skill skill)
	{
		return skill == Skill.ATTACK || skill == Skill.STRENGTH || skill == Skill.DEFENCE || skill == Skill.HITPOINTS || skill == Skill.RANGED;
	}

	static String htmlLabel(String key, int value)
	{
		return htmlLabel(key, QuantityFormatter.quantityToRSDecimalStack(value, true));
	}

	static String htmlLabel(String key, String valueStr)
	{
		return String.format(HTML_LABEL_TEMPLATE, ColorUtil.toHexColor(ColorScheme.LIGHT_GRAY_COLOR), key, valueStr);
	}
}
