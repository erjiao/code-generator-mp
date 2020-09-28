package com.llk.generator.ui;

import com.llk.generator.bean.GenConfig;
import com.llk.generator.utils.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @author llk
 * @date 2019-10-01 20:59
 */
public class MainFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    // 已经加入的label的数量
    private int labelCount = 0;

    // 已经加入的fields
    private int fieldCount = 0;

    public MainFrame() throws HeadlessException {
        setTitle("mybatis-plus 代码生成器");
        setSize(600, 800);
        setLocationRelativeTo(null); // 居中
        setLayout(new BorderLayout()); // 边界布局
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        int gap = 20;

        JPanel panel = new JPanel();
        panel.setName("panel");

        panel.setLayout(null);
        this.add(Box.createHorizontalStrut(gap), BorderLayout.EAST);
        this.add(Box.createHorizontalStrut(gap), BorderLayout.WEST);
        this.add(Box.createVerticalStrut(gap), BorderLayout.NORTH);
        this.add(Box.createVerticalStrut(gap), BorderLayout.SOUTH);
        this.add(panel, BorderLayout.CENTER);


        JLabel dsTypeLabel = createRegularLabel("数据库驱动: ");
        panel.add(dsTypeLabel);
        JComboBox<String> dsType =
                createRegularComboBox(Arrays.asList("com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.Driver"));
        panel.add(dsType);


        JLabel urlLabel = createRegularLabel("数据库url：");
        panel.add(urlLabel);
        JTextField url = createRegularTextField("url");
        panel.add(url);

        JLabel userLabel = createRegularLabel("用户名：");
        panel.add(userLabel);
        JTextField user = createRegularTextField("user");
        panel.add(user);

        JLabel passwordLabel = createRegularLabel("密码：");
        panel.add(passwordLabel);
        JTextField password = createRegularTextField("password");
        panel.add(password);

        JLabel tableLabel = createRegularLabel("表名(多个逗号分隔)：");
        panel.add(tableLabel);
        JTextField table = createRegularTextField("table");
        panel.add(table);

        JLabel prefixLabel = createRegularLabel("表名前缀：");
        panel.add(prefixLabel);
        JTextField prefix = createRegularTextField("prefix");
        panel.add(prefix);

        JLabel packageLabel = createRegularLabel("包名：");
        panel.add(packageLabel);
        JTextField _package = createRegularTextField("package");
        panel.add(_package);

        JLabel authorLabel = createRegularLabel("代码author：");
        panel.add(authorLabel);
        JTextField author = createRegularTextField("author");
        panel.add(author);

        JLabel javaPathLabel = createRegularLabel("Java文件输出路径：");
        panel.add(javaPathLabel);
        JTextField javaPath = createRegularTextField("javaPath");
        javaPath.setSize(javaPath.getWidth() - 80, javaPath.getHeight());
        panel.add(javaPath);

        JButton javaPathButton = new JButton("浏览");
        javaPathButton.setLocation(javaPath.getX() + javaPath.getWidth(), javaPath.getY());
        javaPathButton.setSize(80, 30);
        panel.add(javaPathButton);
        javaPathButton.addActionListener(new ScannerActionListener(javaPath));


        JLabel xmlPathLabel = createRegularLabel("xml文件输出路径：");
        panel.add(xmlPathLabel);
        JTextField xmlPath = createRegularTextField("xmlPath");
        xmlPath.setSize(xmlPath.getWidth() - 80, xmlPath.getHeight());
        panel.add(xmlPath);

        JButton xmlPathButton = new JButton("浏览");
        xmlPathButton.setLocation(xmlPath.getX() + xmlPath.getWidth(), xmlPath.getY());
        xmlPathButton.setSize(80, 30);
        panel.add(xmlPathButton);
        xmlPathButton.addActionListener(new ScannerActionListener(xmlPath));


        JButton button = new JButton("生成代码");
        button.setLocation(xmlPathLabel.getX() + 200, xmlPathLabel.getY() + GAP_LOCATION_Y + 20);
        button.setSize(150, 40);
        button.setFont(new Font("楷体", Font.BOLD, 16));
        button.addActionListener(e -> {
            GenConfig config = new GenConfig();
            config.setDsType(Objects.requireNonNull(dsType.getSelectedItem()).toString());
            config.setUrl(url.getText().trim());
            config.setUsername(user.getText().trim());
            config.setPassword(password.getText().trim());
            config.setTable(table.getText().trim());
            config.setTablePrefix(prefix.getText().trim());
            config.setJavaOutputDir(javaPath.getText().trim());
            config.setPackageName(_package.getText().trim());
            config.setXmlOutputDir(xmlPath.getText().trim());
            config.setAuthor(author.getText().trim());
            log.info("{}", config);

            try {
                GenUtils.generatorCode(config);
            } catch (Throwable ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                JOptionPane.showMessageDialog(null, sw.toString(), "错误", JOptionPane.ERROR_MESSAGE);
                log.error("生成失败：", ex);
            }
        });
        panel.add(button);
        setVisible(true);

    }

    class ScannerActionListener implements ActionListener {
        private JTextField textField;

        private ScannerActionListener(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "文件夹";
                }
            });
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File _javaPath = fc.getSelectedFile();
                textField.setText(_javaPath.getAbsolutePath());
            }
        }
    }

    private static final int LABEL_INIT_LOCATION_X = 10;
    private static final int LABEL_INIT_LOCATION_Y = 20;


    private static final int FIELD_INIT_LOCATION_X = 180;
    private static final int FIELD_INIT_LOCATION_Y = 20;

    private static final int GAP_LOCATION_Y = 40;

    private JLabel createRegularLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(new Font("宋体",Font.PLAIN, 14));
        label.setLocation(LABEL_INIT_LOCATION_X, LABEL_INIT_LOCATION_Y + labelCount * GAP_LOCATION_Y);
        label.setSize(160, 30);
        labelCount ++;
        return label;
    }

    private JTextField createRegularTextField(String name) {
        JTextField textField = new JTextField();
        textField.setName(name);
        textField.setLocation(FIELD_INIT_LOCATION_X, FIELD_INIT_LOCATION_Y + fieldCount * GAP_LOCATION_Y);
        textField.setSize(300, 30);
        fieldCount ++;
        return textField;
    }

    private JComboBox<String> createRegularComboBox(List<String> asList) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setLocation(FIELD_INIT_LOCATION_X, FIELD_INIT_LOCATION_Y + fieldCount * GAP_LOCATION_Y);
        comboBox.setSize(250, 30);
        for (String item : asList) {
            comboBox.addItem(item);
        }
        fieldCount ++;

        return comboBox;
    }


}
