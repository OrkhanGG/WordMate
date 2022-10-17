package gui.panels.dialogs;

import com.formdev.flatlaf.intellijthemes.*;
import gui.UICore;
import gui.frames.GUIFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Hashtable;

public class AppearanceDialog extends JPanel {
    private GUIFrame<JDialog> ParentFrame = null;
    private JList<String> themeList = null;
    private JPanel themePanel = null;
    private JSlider opacity = null;
    private JScrollPane themePanelScrollPane;

    public AppearanceDialog() {
        // TODO: Increase the size of this dialog
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));

        String[] ThemeNames =
        {
            "Arc",
            "Arc Orange",
            "Arc Dark",
            "Arc Dark Orange",
            "Carbon",
            "Cobalt 2",
            "Cyan Light",
            "Dark Flat",
            "Dark Purple",
            "Dracula",
            "Gradianto Dark Fuchsia",
            "Gradianto Deep Ocean",
            "Gradianto Midnight Blue",
            "Gradianto Nature Green"
        };

        themeList = new JList(ThemeNames);
        themeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        themeList.setLayoutOrientation(JList.VERTICAL);
        themeList.setVisibleRowCount(7);

        themePanelScrollPane = new JScrollPane();
        themePanelScrollPane.setViewportView(themeList);

        themePanel.add(themePanelScrollPane);

        opacity = new JSlider(JSlider.HORIZONTAL, 30, 100, 100);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(30, new JLabel("Transparent"));
        labelTable.put(100, new JLabel("Opaque"));
        opacity.setLabelTable(labelTable);
        opacity.setPaintLabels(true);

        setLayout(new BorderLayout());
        add(themePanel, BorderLayout.PAGE_START);
        add(opacity, BorderLayout.PAGE_END);
        setSize(new Dimension(300, 200));

        addListeners();

        ParentFrame = new GUIFrame<>(JDialog.class, this);
        ParentFrame.setFrameName("Themes");
        ParentFrame.Initialize();
    }

    private void addListeners() {
        opacity.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                final float op = opacity.getValue() / 100.f;

                ((GUIFrame<JFrame>) UICore.getInstance().getFrame("Main")).getAppFrame().setOpacity(op);
                ParentFrame.getAppFrame().setOpacity(op);
            }
        });

        themeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                    try {
                        final String selectedTheme = themeList.getSelectedValue();
                        switch (selectedTheme) {
                            case "Arc" -> UIManager.setLookAndFeel(new FlatArcIJTheme());
                            case "Arc Orange" -> UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
                            case "Arc Dark" -> UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
                            case "Arc Dark Orange" -> UIManager.setLookAndFeel(new FlatArcDarkOrangeIJTheme());
                            case "Carbon" -> UIManager.setLookAndFeel(new FlatCarbonIJTheme());
                            case "Cobalt 2" -> UIManager.setLookAndFeel(new FlatCobalt2IJTheme());
                            case "Cyan Light" -> UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
                            case "Dark Flat" -> UIManager.setLookAndFeel(new FlatDarkFlatIJTheme());
                            case "Dark Purple" -> UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
                            case "Dracula" -> UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                            case "Gradianto Dark Fuchsia" ->
                                    UIManager.setLookAndFeel(new FlatGradiantoDarkFuchsiaIJTheme());
                            case "Gradianto Deep Ocean" -> UIManager.setLookAndFeel(new FlatGradiantoDeepOceanIJTheme());
                            case "Gradianto Midnight Blue" ->
                                    UIManager.setLookAndFeel(new FlatGradiantoMidnightBlueIJTheme());
                            case "Gradianto Nature Green" ->
                                    UIManager.setLookAndFeel(new FlatGradiantoNatureGreenIJTheme());
                        }
                    } catch (UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }

                    SwingUtilities.updateComponentTreeUI(((GUIFrame<JFrame>) UICore.getInstance().getFrame("Main")).getAppFrame());
                    SwingUtilities.updateComponentTreeUI(((JDialog)ParentFrame.getAppFrame()));
                }
        });

    }

}
