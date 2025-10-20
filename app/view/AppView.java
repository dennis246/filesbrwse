package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import util.ContainerUtil;


public class AppView {

    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    // static File[] currentFiles;
    static String[] currentFiles;
    static File currentFile;
    static JFrame mainframe;
    // static JFrame[] frames;
    static JPanel panel;
    static int appStateCount = 0;
    static int threshold = 16;
    static int mainSeekPos = 0;
    static java.util.List<String> selectedPathHistory;
    static String resourcePath = "";
    static Font primaryHeaderFont = new Font("Arial", Font.BOLD, 18);
    static Font primaryContentFont = new Font("Arial", Font.PLAIN, 12);
    static Font secondaryContentFont = new Font("Arial", Font.PLAIN, 14);
    static Font tertiaryContentFont = new Font("Calibri", Font.BOLD, 18);

    static java.util.List<LinkedHashMap<String, Object>> componentsInfo;

    public AppView() {
        resourcePath = "";
        {
            Map<TextAttribute, Object> att = new HashMap<>();
            att.put(TextAttribute.WEIGHT, 800);
            att.put(TextAttribute.FAMILY, "Times New Roman");
            att.put(TextAttribute.SIZE, 16);
            att.put(TextAttribute.WIDTH, 10);
            secondaryContentFont = new Font(att);
        }

        {
            Map<TextAttribute, Object> att = new HashMap<>();
            att.put(TextAttribute.WEIGHT, 800);
            att.put(TextAttribute.FAMILY, "Consolas");
            att.put(TextAttribute.SIZE, 16);
            att.put(TextAttribute.WIDTH, 400);
            tertiaryContentFont = new Font(att);
        }

        componentsInfo = new ArrayList<>();
    }

    public void buildFullView() {

        try {

            mainframe = new JFrame();
            mainframe.setBackground(new Color(60, 60, 60));
            mainframe.setForeground(new Color(60, 60, 60));
            mainframe.add(emitMainPanel());
            mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainframe.setSize(viewPortWidth * 30 / 100, viewPortHeight * 30 / 100);
            mainframe.setVisible(true);
            mainframe.setAlwaysOnTop(true);

            InputStream is = getClass().getResourceAsStream("/view/resources/j343.png");
            Image image = ImageIO.read(is);
            mainframe.setIconImage(image);
            is.close();
            ++appStateCount;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Component[] queueComponetsForRender() {

        Component[] kcArr = new Component[3];
        kcArr[0] = emitToolbarView();
        kcArr[1] = emitFilePathView();
        kcArr[2] = emitListView();

        return kcArr;

    }

    private static Component emitFilePathView() {

        JPanel pathPanel = new JPanel();
        pathPanel.setPreferredSize(new Dimension(500, 40));

        String labelName = currentFile == null ? "Root" : currentFile.getPath();
        Label filePathLabel = new Label(labelName, 0);
        filePathLabel.setFont(tertiaryContentFont);
        filePathLabel.setName(labelName);
        pathPanel.add(filePathLabel);

        return pathPanel;

    }

    public static void updateView(Component currentComponent) {

        componentsInfo.size();
        var last = (Component) componentsInfo.get(componentsInfo.size() - 1).get("mainPanel");
        mainframe.remove(last);
        mainframe.add(emitMainPanel());
        mainframe.validate();
    }

    private static Component emitMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setName("mainPanel");

        // Insets insets = new Insets(20, 20, 20, 20);
        // Graphics graphics = Graphics

       /*  CompoundBorder cb = new CompoundBorder();
        cb.paintBorder(mainPanel, mainframe.getGraphics(), mainSeekPos, 100, 200, 100);
        mainPanel.setBorder(cb); */
        mainPanel.setPreferredSize(new Dimension(660, 100));

        for (var comp : queueComponetsForRender()) {
            mainPanel.add(comp);
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(mainPanel.getName(), mainPanel);
        componentsInfo.add(map);

        return mainPanel;

    }

    public static Component emitListView() { 

        List listView = new java.awt.List();
        listView.setName("filesList");
        listView.setPreferredSize(new Dimension(800, 200));
        listView.setSize(viewPortWidth * 30 / 100, viewPortHeight * 30 / 100);
        listView.setBackground(new Color(0, 180, 200));
        listView.setBounds(16, 12, 50, 10);
        listView.setForeground(new Color(20, 20, 20));
        //listView.setForeground(Color.white);

        listView.setFont(secondaryContentFont);
        listView.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                var x = e.getSource();
                String selectedFilePath = e.getActionCommand();
                explorePath(selectedFilePath);
            }

        });

        if (currentFiles == null) {
            String[] activeDrives = findActiveDrives();
            if (currentFiles == null) {
                currentFiles = new String[activeDrives.length];
            }

            int index = 0;
            for (var cd : activeDrives) {

                File aFile = new File(cd);
                if (aFile.exists()) {
                    currentFiles[index] = aFile.getPath();
                    // currentFiles = (File[]) ContainerUtil.add(currentFiles, aFile);
                }

                index++;

            }

            currentFile = new File(currentFiles[0]);
        }

        for (var currentFile : currentFiles) {
            listView.add(currentFile);
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(listView.getName(), listView);
        componentsInfo.add(map);

        return listView;
    }

    private static void explorePath(String selectedFilePath) {
        if (selectedPathHistory == null) {
            selectedPathHistory = new ArrayList<>();
            currentFile = new File(selectedFilePath);
        } else {

            String prevAgg = "";
            for (var row : selectedPathHistory) {
                prevAgg += row + "\\";
            }

            currentFile = new File(prevAgg + "\\" + selectedFilePath);

        }

        if (currentFile.isDirectory()) {
            selectedPathHistory.add(selectedFilePath);
            currentFiles = currentFile.list();
        } else {
            // openFile(currentFile);
        }

        if (appStateCount > 0) {
            updateView(findComponentFromMapByName("filesList"));
        }
    }

    private static Component findComponentFromMapByName(String componentName) {

        for (var crow : componentsInfo) {
            if (crow.get(componentName) != null) {
                return (Component) crow.get(componentName);
            }
        }

        return null;

    }

    protected static Component emitBackButton() {
        Button backBtn = new Button("Back");
        backBtn.setPreferredSize(new Dimension(40, 30));
        backBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // var x = e.getSource();

                if (selectedPathHistory == null || selectedPathHistory.size() == 0) {
                    return;
                }

                selectedPathHistory.remove(selectedPathHistory.size() - 1);

                String prevAgg = "";
                for (var row : selectedPathHistory) {
                    prevAgg += row + "\\";
                }

                currentFile = new File(prevAgg);
                currentFiles = currentFile.list();

                updateView(backBtn);

            }
        });

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(backBtn.getName(), backBtn);
        componentsInfo.add(map);

        return backBtn;
    }

    public static Component emitToolbarView() {

        JPanel tbPanel = new JPanel();
        tbPanel.setName("toolbarPanel");
        tbPanel.setPreferredSize(new Dimension(200, 50));

        tbPanel.add(emitBackButton());
        tbPanel.add(emitHomeButton());
        tbPanel.add(emitOpenButton());

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(tbPanel.getName(), tbPanel);
        componentsInfo.add(map);

        return tbPanel;

    }

    private static Component emitOpenButton() {
        Button openBtn = new Button("Open");
        openBtn.setPreferredSize(new Dimension(40, 30));
        openBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // explorePath(currentFile.getPath());
                byte[] bytes = openFile(currentFile);
                //WebUtil.post(bytes);
                System.exit(0);
            }

        });

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(openBtn.getName(), openBtn);
        componentsInfo.add(map);

        return openBtn;
    }

    private static byte[] openFile(File thatfile) {

        try {
            InputStream is = new FileInputStream(thatfile);
            byte[] fbytes = is.readAllBytes();
            is.close();
            return fbytes;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static Component emitHomeButton() {
        Button homeBtn = new Button("Home");
        homeBtn.setPreferredSize(new Dimension(40, 30));
        homeBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                var x = e.getSource();
                selectedPathHistory = null;
                currentFile = null;
                currentFiles = null;
                updateView(homeBtn);

            }
        });

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(homeBtn.getName(), homeBtn);
        componentsInfo.add(map);

        return homeBtn;
    }

    private static String[] findActiveDrives() {

        String[] activeDrives = new String[0];
        char[] dl = new char[0];
        for (int d = 65, pi = 0; d < 91; d++, pi++) {
            dl = ContainerUtil.addToCharArray(dl, (char) d);
        }

        for (int i = 0; i < dl.length; i++) {
            String cd = String.valueOf(dl[i]) + ":\\";

            File afile = new File(cd);
            if (afile.exists()) {
                activeDrives = ContainerUtil.addToStringArray(activeDrives, cd);
            }

        }

        return activeDrives;

    }

    private static Image emitImage(String iconpath) {
        try {
            // BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_3BYTE_BGR);
            File afile = new File(iconpath);
            InputStream is = new FileInputStream(afile);
            BufferedImage bi = ImageIO.read(is);
            return bi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

