package gui.panels.dialogs;

import com.formdev.flatlaf.intellijthemes.*;
import gui.UICore;
import gui.frames.GUIFrame;
import utils.Callback;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AppearanceDialog extends JPanel {
    private GUIFrame<JDialog> ParentFrame = null;
    private JList<String> themeList = null;
    private JPanel themePanel = null;
    private JSlider opacity = null;

    public AppearanceDialog() {
        themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));

        List<String> ThemeNames = new ArrayList<>();
        {
            ThemeNames.add("Arc");
            ThemeNames.add("Arc Orange");
            ThemeNames.add("Arc Dark");
            ThemeNames.add("Arc Dark Orange");
            ThemeNames.add("Carbon");
            ThemeNames.add("Cobalt 2");
            ThemeNames.add("Cyan Light");
            ThemeNames.add("Dark Flat");
            ThemeNames.add("Dark Purple");
            ThemeNames.add("Dracula");
            ThemeNames.add("Gradianto Dark Fuchsia");
            ThemeNames.add("Gradianto Deep Ocean");
            ThemeNames.add("Gradianto Midnight Blue");
            ThemeNames.add("Gradianto Nature Green");
        }

        themeList = new JList(ThemeNames.toArray());
        themeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        themeList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        themeList.setVisibleRowCount(-1);

        themePanel.add(themeList);

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

        Callback callback = ()->{
            try {
                final String btnName = themeList.getSelectedValue();
                switch (btnName) {
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
            SwingUtilities.updateComponentTreeUI(this);
            ParentFrame.getAppFrame().dispose();
        };

        themeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    callback.call();
                }
            }
        });

    }

}
