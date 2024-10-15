import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Objects;

public final class MainFrame {
    private final JFrame mainFrame;
    private final File fontFile = new File(Objects.requireNonNull(Main.class.getResource("/JetBrainsMono-Regular.ttf")).getPath());
    private final JideTabbedPane tabbedPane = new JideTabbedPane();

    public MainFrame() {
        FlatMacDarkLaf.setup();
        mainFrame = new JFrame();
        initFrame();
        initMenuBar();
        mainFrame.setVisible(true);
    }

    private void initFrame() {
        mainFrame.setMinimumSize(FrameConstants.MINIMUM_FRAME_DIMENSION);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initTabbedPane();
        initBottomBar();
    }

    private void initTabbedPane() {
        tabbedPane.setFocusable(false);
        tabbedPane.requestFocus(false);
        tabbedPane.setShowCloseButtonOnTab(true);
        tabbedPane.setShowIconsOnTab(true);
        JTextArea area1 = new JTextArea();
        area1.setText("\t\t\t\t\tWelcome to Notes Nest , an open source text editor written purely in Java Swing.\n\n");
        area1.setText(area1.getText() + "\nSpecial Thanks To:-\n");
        area1.setText(area1.getText() + "\n1.For User Interface Themes: Flat Laf Platform \n2.For TabbedPane: JideSoft\n3.For Images: Intellij Idea Icons\n\n");
        area1.setText(area1.getText() + "\nShortcuts:-\n");
        area1.setText(area1.getText() + "\n1.New File: Ctrl + N \n2.New Window: Ctrl + T\n3.Quit: Ctrl + Q");
        area1.setEditable(false);
        File fontFile = new File(Objects.requireNonNull(Main.class.getResource("/JetBrainsMono-Regular.ttf")).getPath());
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(15.0F);
            area1.setFont(font);
            tabbedPane.setFont(Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(10.0F));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        tabbedPane.setShowIconsOnTab(true);
        area1.requestFocusInWindow();
        tabbedPane.add("About Notes Nest", area1);
        for (int index = 0; index < tabbedPane.getTabCount(); index++)
            tabbedPane.setIconAt(0, new FlatSVGIcon(getClass().getResource("/csv_dark.svg")));
        mainFrame.add(tabbedPane);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu view = new JMenu("View");
        menuBar.add(file);
        initFile(file);
        menuBar.add(edit);
        initEdit(edit);
        menuBar.add(view);
        initView(view);
        mainFrame.setJMenuBar(menuBar);
        menuBar.add(Box.createHorizontalGlue());
        JButton settingsButton = new JButton(new FlatSVGIcon(Main.class.getResource("/settings_dark.svg")));
        settingsButton.putClientProperty("JButton.buttonType", "toolBarButton");
        settingsButton.setFocusable(false);
        settingsButton.setBorderPainted(false);
        menuBar.add(settingsButton);
        initSettingsButton(settingsButton);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            menuBar.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initSettingsButton(JButton settingsButton) {
        settingsButton.addActionListener(event -> {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem darkTheme = setDarkTheme(settingsButton);
            JMenuItem lightTheme = setLightTheme(settingsButton);
            popupMenu.add(darkTheme);
            popupMenu.add(lightTheme);
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
                darkTheme.setFont(font);
                lightTheme.setFont(font);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
            popupMenu.show(settingsButton, popupMenu.getX(), popupMenu.getY() + 30);
        });
    }

    private JMenuItem setDarkTheme(JButton settingsButton) {
        JMenuItem darkTheme = new JMenuItem("Dark Theme");
        darkTheme.addActionListener(e -> {
            settingsButton.setIcon(new FlatSVGIcon(Main.class.getResource("/settings_dark.svg")));
            for (int index = 0; index < tabbedPane.getTabCount(); index++)
                tabbedPane.setIconAt(0, new FlatSVGIcon(getClass().getResource("/csv_dark.svg")));
            try {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                SwingUtilities.updateComponentTreeUI(mainFrame);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return darkTheme;
    }

    private JMenuItem setLightTheme(JButton settingsButton) {
        JMenuItem lightTheme = new JMenuItem("Light Theme");
        lightTheme.addActionListener(e -> {
            settingsButton.setIcon(new FlatSVGIcon(Main.class.getResource("/settings.svg")));
            for (int index = 0; index < tabbedPane.getTabCount(); index++)
                tabbedPane.setIconAt(0, new FlatSVGIcon(getClass().getResource("/csv.svg")));
            try {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
                SwingUtilities.updateComponentTreeUI(mainFrame);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return lightTheme;
    }

    private void initView(JMenu view) {
        JMenuItem wordWrap = new JMenuItem("Word Wrap");
        view.add(wordWrap);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            wordWrap.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initFile(JMenu file) {
        JMenuItem newFile = new JMenuItem("New File");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newFile.addActionListener(e->{
            JTextArea textArea = new JTextArea();
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(15.0F);
                textArea.setFont(font);
            } catch (FontFormatException | IOException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setBorder(null);
            JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(null);
            tabbedPane.addTab("New File",new FlatSVGIcon(getClass().getResource("/csv_dark.svg")),scrollPane);
        });
        JMenuItem newWindow = new JMenuItem("New Window");
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,KeyEvent.CTRL_DOWN_MASK));
        newWindow.addActionListener(e->new MainFrame());
        JMenuItem openFile = new JMenuItem("Open File");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        openFile.addActionListener(actionEvent->{
            JFileChooser fileChooser=new JFileChooser();
            int selection=fileChooser.showOpenDialog( null);
            FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("","txt");
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            if(selection==JFileChooser.APPROVE_OPTION){
                String fileName=fileChooser.getSelectedFile().getAbsolutePath();
                try{
                    BufferedReader reader=new BufferedReader(new FileReader(fileName));
                    JTextArea textArea = new JTextArea();
                    try {
                        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(15.0F);
                        textArea.setFont(font);
                    } catch (FontFormatException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea.setBorder(null);
                    textArea.read(reader,null);
                    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setBorder(null);
                    tabbedPane.addTab("New File",new FlatSVGIcon(getClass().getResource("/csv_dark.svg")),scrollPane);
                }
                catch(Exception ignored){}
            }
        });
        JMenuItem saveFile = new JMenuItem("Save File");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(actionEvent->{
            JFileChooser fileChooser=new JFileChooser();
            FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("Text files","txt");
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int selection=fileChooser.showSaveDialog(null);
            if(selection==JFileChooser.APPROVE_OPTION){
                String fileName=fileChooser.getSelectedFile().getAbsolutePath();
                if(!fileName.contains(".txt"))
                    fileName=fileName+".txt";
                try{
                    BufferedWriter writer=new BufferedWriter(new FileWriter(fileName));
                    JTextArea textArea = new JTextArea();
                    try {
                        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(15.0F);
                        textArea.setFont(font);
                    } catch (FontFormatException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea.setBorder(null);
                    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setBorder(null);
                    tabbedPane.addTab(fileName,new FlatSVGIcon(getClass().getResource("/csv_dark.svg")),scrollPane);
                    textArea.write(writer);
                }
                catch(Exception ignored){}
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
        exit.addActionListener(e -> System.exit(0));
        file.add(newFile);
        file.add(newWindow);
        file.add(openFile);
        file.add(saveFile);
        file.add(exit);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            newFile.setFont(font);
            newWindow.setFont(font);
            openFile.setFont(font);
            saveFile.setFont(font);
            exit.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initEdit(JMenu edit) {
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem dateTime = new JMenuItem("Date Time");
        JMenu frameFont = new JMenu("Font");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(dateTime);
        edit.add(frameFont);
        initFrameFont(frameFont);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            cut.setFont(font);
            copy.setFont(font);
            paste.setFont(font);
            dateTime.setFont(font);
            frameFont.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initFrameFont(JMenu frameFont) {
        JMenuItem font1 = new JMenuItem("Font - Regular");
        JMenuItem font2 = new JMenuItem("Font - Medium");
        JMenuItem font3 = new JMenuItem("Font - Italic");
        frameFont.add(font1);
        frameFont.add(font2);
        frameFont.add(font3);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            font1.setFont(font);
            font2.setFont(font);
            font3.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initBottomBar() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Created By : Devansh Saxena On 06-10-2024");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12.0F);
            label.setFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        bottomPanel.add(label);
        mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
    }
}