package net.runelite.client.plugins.networklogger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

class NetworkLoggerPanel extends PluginPanel
{
	private static final int EVENT_BODY_HEIGHT = 120;

	private final NetworkLoggerPlugin plugin;
	private final JPanel tickContainer = new JPanel();
	private final JLabel statusLabel = new JLabel();
	private final Map<Integer, Boolean> expandedStates = new HashMap<>();
	private boolean refreshQueued;

	@Inject
	private NetworkLoggerPanel(NetworkLoggerPlugin plugin)
	{
		super(false);
		this.plugin = plugin;

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout(0, 6));
		top.setBackground(ColorScheme.DARK_GRAY_COLOR);
		top.setBorder(new EmptyBorder(8, 8, 8, 8));

		JPanel actions = new JPanel();
		actions.setBackground(ColorScheme.DARK_GRAY_COLOR);
		actions.setLayout(new BorderLayout(6, 0));

		JButton pauseButton = new JButton("Pause / Resume");
		pauseButton.addActionListener(e ->
		{
			plugin.togglePaused();
			refresh();
		});

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(e ->
		{
			plugin.clear();
			refresh();
		});

		actions.add(pauseButton, BorderLayout.WEST);
		actions.add(clearButton, BorderLayout.EAST);

		statusLabel.setForeground(Color.WHITE);
		top.add(statusLabel, BorderLayout.NORTH);
		top.add(actions, BorderLayout.SOUTH);

		tickContainer.setLayout(new BoxLayout(tickContainer, BoxLayout.Y_AXIS));
		tickContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JScrollPane scrollPane = new JScrollPane(tickContainer);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);

		add(top, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		refresh();
	}

	void refreshLater()
	{
		if (refreshQueued)
		{
			return;
		}

		refreshQueued = true;
		SwingUtilities.invokeLater(() ->
		{
			try
			{
				refresh();
			}
			finally
			{
				refreshQueued = false;
			}
		});
	}

	private void refresh()
	{
		List<NetworkLoggerPlugin.TickLog> logs = plugin.getTickLogs();
		statusLabel.setText((plugin.isPaused() ? "Paused" : "Recording") + " | ticks " + logs.size());

		tickContainer.removeAll();
		for (int i = 0; i < logs.size(); i++)
		{
			tickContainer.add(createTickPanel(logs.get(i), i >= 3));
		}

		tickContainer.revalidate();
		tickContainer.repaint();
	}

	private JPanel createTickPanel(NetworkLoggerPlugin.TickLog log, boolean collapsedByDefault)
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		panel.setBorder(new EmptyBorder(0, 0, 6, 0));

		boolean expanded = expandedStates.getOrDefault(log.getTick(), !collapsedByDefault);
		String summary = "Tick " + log.getTick() + " - " + log.getMarker() + " | " + log.getEventCount() + " events";

		JButton header = new JButton((expanded ? "[-] " : "[+] ") + summary);
		header.setHorizontalAlignment(JButton.LEFT);
		header.addActionListener(e ->
		{
			expandedStates.put(log.getTick(), !expandedStates.getOrDefault(log.getTick(), expanded));
			refresh();
		});

		JTextArea textArea = new JTextArea(String.join("\n", log.getEvents()));
		textArea.setEditable(false);
		textArea.setLineWrap(false);
		textArea.setWrapStyleWord(false);
		textArea.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		textArea.setForeground(Color.WHITE);
		textArea.setCaretPosition(0);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		textArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		textArea.setColumns(48);

		panel.add(header, BorderLayout.NORTH);
		if (expanded)
		{
			JScrollPane textScroll = new JScrollPane(textArea);
			textScroll.setBorder(null);
			textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			textScroll.setPreferredSize(new Dimension(0, EVENT_BODY_HEIGHT));
			panel.add(textScroll, BorderLayout.CENTER);
		}

		return panel;
	}
}
