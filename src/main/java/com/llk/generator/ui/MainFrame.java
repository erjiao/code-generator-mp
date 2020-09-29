package com.llk.generator.ui;

import com.llk.generator.bean.DriverType;
import com.llk.generator.bean.GenConfig;
import com.llk.generator.utils.Configure;
import com.llk.generator.utils.GenUtils;
import com.llk.generator.utils.JdbcUrls;
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
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;


/**
 * @author llk
 * @date 2019-10-01 20:59
 */
public class MainFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private static final String PG_JDBC_URL_TEMPLATE = "jdbc:postgresql://host:port/database?currentSchema=public";

    private static final String MYSQL_JDBC_URL_TEMPLATE = "jdbc:mysql://host:port/database?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

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


        JLabel dsTypeLabel = createRegularLabel("数据库类型: ");
        panel.add(dsTypeLabel);
        JComboBox<String> dsType =
                createRegularComboBox(DriverType.getTypes());
        panel.add(dsType);

        JLabel urlLabel = createRegularLabel("jdbcUrl：");
        panel.add(urlLabel);
        JTextField url = createRegularTextField("url");
        panel.add(url);

        dsType.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String type = e.getItem().toString();
                String urlText = url.getText().trim();
                log.info("select ds type is {}, jdbcUrl: {}", type, urlText);
                if (DriverType.getByType(type) == DriverType.MYSQL) {
                    urlLabel.setToolTipText(MYSQL_JDBC_URL_TEMPLATE);
                } else if (DriverType.getByType(type) == DriverType.POSTGRE_SQL) {
                    urlLabel.setToolTipText(PG_JDBC_URL_TEMPLATE);
                }
//                url.setCaretPosition(0);
            }
        });

        JLabel userLabel = createRegularLabel("用户名：");
        panel.add(userLabel);
        JTextField user = createRegularTextField("user");
        panel.add(user);

        JLabel passwordLabel = createRegularLabel("密码：");
        panel.add(passwordLabel);
        JTextField password = createRegularTextField("password");
        panel.add(password);

        JLabel tableLabel = createRegularLabel("表名(多个逗号分隔)：");
        tableLabel.setToolTipText("多个逗号分隔, 留空生成全部");
        panel.add(tableLabel);
        JTextField table = createRegularTextField("table");
        panel.add(table);

        JLabel prefixLabel = createRegularLabel("表名前缀：");
        prefixLabel.setToolTipText("生成的类会去去掉表名前缀");
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

        JLabel projectDirLabel = createRegularLabel("项目路径：");
        panel.add(projectDirLabel);
        JTextField projectDir = createRegularTextField("projectDir");
        panel.add(projectDir);

//        JLabel javaPathLabel = createRegularLabel("Java文件输出路径：");
//        panel.add(javaPathLabel);
//        JTextField javaPath = createRegularTextField("javaPath");
//        javaPath.setSize(javaPath.getWidth() - 80, javaPath.getHeight());
//        panel.add(javaPath);
//
//        JButton javaPathButton = new JButton("浏览");
//        javaPathButton.setLocation(javaPath.getX() + javaPath.getWidth(), javaPath.getY());
//        javaPathButton.setSize(80, 30);
//        panel.add(javaPathButton);
//        javaPathButton.addActionListener(new ScannerActionListener(javaPath));
//
//
//        JLabel xmlPathLabel = createRegularLabel("xml文件输出路径：");
//        panel.add(xmlPathLabel);
//        JTextField xmlPath = createRegularTextField("xmlPath");
//        xmlPath.setSize(xmlPath.getWidth() - 80, xmlPath.getHeight());
//        panel.add(xmlPath);
//
//        JButton xmlPathButton = new JButton("浏览");
//        xmlPathButton.setLocation(xmlPath.getX() + xmlPath.getWidth(), xmlPath.getY());
//        xmlPathButton.setSize(80, 30);
//        panel.add(xmlPathButton);
//        xmlPathButton.addActionListener(new ScannerActionListener(xmlPath));


        JButton button = new JButton("生成代码");
        button.setLocation(projectDirLabel.getX() + 200, projectDirLabel.getY() + GAP_LOCATION_Y + 20);
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
            config.setJavaOutputDir(projectDir.getText().trim() + "/src/main/java");
            config.setPackageName(_package.getText().trim());
            config.setProjectDir(projectDir.getText().trim());
            config.setXmlOutputDir(projectDir.getText().trim() + "/src/main/resources/mapper");
            config.setAuthor(author.getText().trim());
            config.setSchema(JdbcUrls.getParameter(url.getText().trim()).get("currentSchema"));
            log.info("{}", config);

            try {
                GenUtils.generatorCode(config);
            } catch (Throwable ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                JOptionPane.showMessageDialog(null, sw.toString(), "错误", JOptionPane.ERROR_MESSAGE);
                log.error("生成代码失败：", ex);
            }
        });

        // 获取配置, 添加默认值
        url.setText(Configure.value("jdbcUrl"));
        user.setText(Configure.value("username"));
        password.setText(Configure.value("password"));
        prefix.setText(Configure.value("tablePrefix"));
        _package.setText(Configure.value("packageName"));
        author.setText(Configure.value("author"));
        projectDir.setText(Configure.value("projectDir"));
        dsType.setSelectedItem(Configure.value("dataSourceType"));

//        javaPath.setText(Configure.value("javaOutputDir"));
//        xmlPath.setText(Configure.value("xmlOutputDir"));

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
