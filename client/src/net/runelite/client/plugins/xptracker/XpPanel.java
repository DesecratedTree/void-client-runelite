package net.runelite.client.plugins.xptracker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.DragAndDropReorderPane;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ImageUtil;

class XpPanel extends PluginPanel
{
	private final Map<Skill, XpInfoBox> infoBoxes = new EnumMap<>(Skill.class);
	private final Set<Skill> activeSkills = new HashSet<>();
	private final JLabel overallExpGained = new JLabel(XpInfoBox.htmlLabel("Gained: ", 0));
	private final JLabel overallExpHour = new JLabel(XpInfoBox.htmlLabel("Per hour: ", 0));
	private final JPanel overallPanel = new JPanel();
	private final PluginErrorPanel errorPanel = new PluginErrorPanel();
	private final DragAndDropReorderPane infoBoxPanel = new DragAndDropReorderPane();

	@Inject
	XpPanel(XpTrackerPlugin xpTrackerPlugin, XpTrackerConfig xpTrackerConfig, SkillIconManager iconManager)
	{
		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setLayout(new BorderLayout());

		JPanel layoutPanel = new JPanel();
		layoutPanel.setOpaque(false);
		layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
		add(layoutPanel, BorderLayout.NORTH);

		overallPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		overallPanel.setLayout(new BorderLayout());
		overallPanel.setVisible(false);

		JMenuItem reset = new JMenuItem("Reset All");
		reset.addActionListener(e -> xpTrackerPlugin.reset());

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(reset);
		overallPanel.setComponentPopupMenu(popupMenu);

		JLabel overallIcon = new JLabel(new ImageIcon(ImageUtil.loadImageResource(getClass(), "/skill_icons/overall.png")));

		JPanel overallInfo = new JPanel();
		overallInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		overallInfo.setLayout(new GridLayout(2, 1));
		overallInfo.setBorder(new EmptyBorder(0, 10, 0, 0));

		overallExpGained.setFont(FontManager.getRunescapeSmallFont());
		overallExpHour.setFont(FontManager.getRunescapeSmallFont());
		overallInfo.add(overallExpGained);
		overallInfo.add(overallExpHour);

		overallPanel.add(overallIcon, BorderLayout.WEST);
		overallPanel.add(overallInfo, BorderLayout.CENTER);

		layoutPanel.add(overallPanel);
		layoutPanel.add(infoBoxPanel);

		for (Skill skill : Skill.values())
		{
			if (skill != Skill.OVERALL)
			{
				infoBoxes.put(skill, new XpInfoBox(xpTrackerPlugin, xpTrackerConfig, infoBoxPanel, skill, iconManager));
			}
		}

		errorPanel.setContent("Exp trackers", "You have not gained experience yet.");
		add(errorPanel, BorderLayout.CENTER);
	}

	void rebuild(Collection<XpTrackerPlugin.XpState> skills, int totalGained, int xpPerHour)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			rebuildAsync(skills, totalGained, xpPerHour);
		}
		else
		{
			SwingUtilities.invokeLater(() -> rebuildAsync(skills, totalGained, xpPerHour));
		}
	}

	private void rebuildAsync(Collection<XpTrackerPlugin.XpState> skills, int totalGained, int xpPerHour)
	{
		boolean layoutChanged = false;
		if (totalGained > 0 && !overallPanel.isVisible())
		{
			overallPanel.setVisible(true);
			remove(errorPanel);
			layoutChanged = true;
		}
		else if (totalGained == 0 && overallPanel.isVisible())
		{
			overallPanel.setVisible(false);
			add(errorPanel, BorderLayout.CENTER);
			layoutChanged = true;
		}

		overallExpGained.setText(XpInfoBox.htmlLabel("Gained: ", totalGained));
		overallExpHour.setText(XpInfoBox.htmlLabel("Per hour: ", xpPerHour));

		Set<Skill> nextActiveSkills = new HashSet<>();
		for (XpTrackerPlugin.XpState state : skills)
		{
			XpInfoBox xpInfoBox = infoBoxes.get(state.getSkill());
			if (xpInfoBox != null)
			{
				nextActiveSkills.add(state.getSkill());
				layoutChanged |= xpInfoBox.ensureVisible();
				xpInfoBox.update(true, false, new XpSnapshotSingle(state));
			}
		}

		for (Skill skill : new HashSet<>(activeSkills))
		{
			if (!nextActiveSkills.contains(skill))
			{
				XpInfoBox xpInfoBox = infoBoxes.get(skill);
				if (xpInfoBox != null)
				{
					layoutChanged |= xpInfoBox.removeFromPanel();
				}
			}
		}
		activeSkills.clear();
		activeSkills.addAll(nextActiveSkills);

		if (layoutChanged)
		{
			infoBoxPanel.revalidate();
			revalidate();
		}
		repaint();
	}

	void resetAllInfoBoxes()
	{
		boolean layoutChanged = false;
		for (XpInfoBox xpInfoBox : infoBoxes.values())
		{
			layoutChanged |= xpInfoBox.removeFromPanel();
		}
		activeSkills.clear();
		if (layoutChanged)
		{
			infoBoxPanel.revalidate();
			revalidate();
		}
	}
}
