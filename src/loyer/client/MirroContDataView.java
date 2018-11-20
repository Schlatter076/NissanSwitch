package loyer.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;

import loyer.db.UserTools;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

public class MirroContDataView {

  private JFrame frame;
  /** 获取当前屏幕宽度 */
  private static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  /** 获取当前屏幕高度 */
  private static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem openItem;
  private JMenuItem exitItem;
  private JMenu editMenu;
  private JMenuItem usartItem;
  private JMenuItem resultItem;
  private JMenuItem ngReportItem;
  private JToolBar toolBar;
  private JButton exitButt;
  private JButton usartButt;
  private JButton resultButt;
  private JButton ngReportButt;
  private JSplitPane splitPane;
  private JMenuItem contactItem;
  private JMenuItem aboutItem;
  private JToolBar comToolBar;
  private JToggleButton com1Butt;
  private JToggleButton com2Butt;
  /** 测试数据显示面板 */
  private JScrollPane dataPanel;
  private JSplitPane leftSplitPane;
  private JPanel rightPanel;
  private JProgressBar progressBar;
  private Timer timer1;
  private int progressValue = 0;
  private JTextField totalField;
  private JTextField okField;
  private JTextField ngField;
  private JTextField timeField;
  private JTextField statuField;
  private JTextField productField;
  /** 机种名 */
  private static final String PRODUCT_NAME = "MIRROR-CONT后视镜开关";
  private int totalCount = 0;
  private int okCount = 0;
  private int ngCount = 0;
  private int timeCount = 0;
  /** 饼图面板 */
  private ChartPanel chartPanel;
  private MyPieChart myPie;
  private PiePlot piePlot;
  /** 产品扫描区域 */
  private JTextField scanField;
  private JCheckBox nayinButt;
  private JCheckBox spotButt;
  /** 左下侧数据显示区域 */
  private JTextArea editArea;
  /** 格式化日期显示 */
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
  /** 换行符 */
  private static final String SEPARATOR = System.getProperty("line.separator");
  private JMenu helpMenu;
  /**保留页面*/
  private JScrollPane persistScroll;
  /**自定义绿色*/
  private static final Color GREEN = new Color(0, 204, 51);

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MirroContDataView window = new MirroContDataView();
          window.frame.setVisible(true);
          window.addListener();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public MirroContDataView() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    
    try {
      // 将界面风格设置成和系统一置
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    } // */

    frame = new JFrame("日产开关测试系统");
    Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/pic/Kyokuto.png"));
    frame.setIconImage(img);
    // 界面初始大小
    frame.setBounds(WIDTH / 9, HEIGHT / 10, WIDTH * 7 / 9, HEIGHT * 8 / 10);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    // 设置菜单
    menuBar = new JMenuBar();
    fileMenu = new JMenu("文件(F)");
    openItem = new JMenuItem("打开(O)");
    exitItem = new JMenuItem("退出(X)");
    editMenu = new JMenu("编辑(E)");
    usartItem = new JMenuItem("打开调试工具");
    usartItem.addActionListener(usartListener);
    resultItem = new JMenuItem("查看测试结果");
    ngReportItem = new JMenuItem("查看不良报告");
    helpMenu = new JMenu("帮助(H)");
    contactItem = new JMenuItem("联系我(Contact us)");
    aboutItem = new JMenuItem("关于(About)");
    helpMenu.add(contactItem);
    helpMenu.add(aboutItem);
    menuBar.add(fileMenu);
    fileMenu.add(openItem);
    fileMenu.add(exitItem);
    menuBar.add(editMenu);
    editMenu.add(usartItem);
    editMenu.add(resultItem);
    editMenu.add(ngReportItem);
    menuBar.add(helpMenu);    
    frame.setJMenuBar(menuBar);

    // 设置工具栏
    toolBar = new JToolBar("工具栏");
    exitButt = new JButton("退出系统(X)");
    exitButt.setFont(new Font("宋体", Font.PLAIN, 13));
    usartButt = new JButton("打开调试工具(UT)");
    usartButt.addActionListener(usartListener);
    usartButt.setFont(new Font("宋体", Font.PLAIN, 13));
    resultButt = new JButton("查看测试结果(RS)");
    resultButt.setFont(new Font("宋体", Font.PLAIN, 13));
    ngReportButt = new JButton("查看不良报告(RT)");
    ngReportButt.setFont(new Font("宋体", Font.PLAIN, 13));
    nayinButt = new JCheckBox("捺印");
    nayinButt.setFont(new Font("宋体", Font.PLAIN, 12));
    nayinButt.setSelected(true);
    spotButt = new JCheckBox("点测");
    spotButt.setFont(new Font("宋体", Font.PLAIN, 12));

    toolBar.add(exitButt);
    toolBar.addSeparator();
    toolBar.add(usartButt);
    toolBar.addSeparator();
    toolBar.add(resultButt);
    toolBar.addSeparator();
    toolBar.add(ngReportButt);
    toolBar.addSeparator();
    toolBar.add(nayinButt);
    toolBar.addSeparator();
    toolBar.add(spotButt);
    toolBar.setBackground(Color.GRAY);
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    toolBar.add(new JSeparator());
    comToolBar = new JToolBar("窗口工具栏");
    toolBar.add(comToolBar);
    com1Butt = new JToggleButton("COM1");
    com1Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com1Butt);
    comToolBar.addSeparator();
    com2Butt = new JToggleButton("COM2");
    com2Butt.setFont(new Font("宋体", Font.PLAIN, 12));
    comToolBar.add(com2Butt);

    dataPanel = new JScrollPane();
    dataPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "测试数据:", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    dataPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    dataPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    totalField = new JTextField(totalCount + "");
    totalField.setEditable(false);
    totalField.setHorizontalAlignment(SwingConstants.CENTER);
    totalField.setFont(new Font("宋体", Font.PLAIN, 30));
    totalField.setBackground(new Color(245, 245, 245));
    okField = new JTextField(okCount + "");
    okField.setEditable(false);
    okField.setHorizontalAlignment(SwingConstants.CENTER);
    okField.setFont(new Font("宋体", Font.PLAIN, 30));
    okField.setForeground(GREEN);
    okField.setBackground(new Color(245, 245, 245));
    ngField = new JTextField(ngCount + "");
    ngField.setEditable(false);
    ngField.setHorizontalAlignment(SwingConstants.CENTER);
    ngField.setFont(new Font("宋体", Font.PLAIN, 30));
    ngField.setForeground(Color.RED);
    ngField.setBackground(new Color(245, 245, 245));
    timeField = new JTextField(timeCount + "");
    timeField.setEditable(false);
    timeField.setBackground(Color.BLACK);
    timeField.setForeground(Color.RED);
    timeField.setHorizontalAlignment(SwingConstants.CENTER);
    timeField.setFont(new Font("宋体", Font.PLAIN, 30));
    statuField = new JTextField();
    statuField.setEditable(false);
    statuField.setText("STOP");
    statuField.setBackground(Color.ORANGE);
    statuField.setHorizontalAlignment(SwingConstants.CENTER);
    statuField.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 30));

    MyPanel statuPanel = new MyPanel("运行状态", statuField);
    MyPanel totalPanel = new MyPanel("测试总数", totalField);
    MyPanel okPanel = new MyPanel("良品数", okField);
    MyPanel ngPanel = new MyPanel("NG数", ngField);
    MyPanel timePanel = new MyPanel("测试时间(S)", timeField);
    JPanel panel = new JPanel();

    panel.setBorder(new LineBorder(Color.GRAY, 3));
    panel.setLayout(new GridLayout(5, 1, 5, 5));
    panel.add(statuPanel);
    panel.add(totalPanel);
    panel.add(okPanel);
    panel.add(ngPanel);
    panel.add(timePanel);
    // 饼图添加面板
    JPanel jp = new JPanel();
    myPie = new MyPieChart(okCount, ngCount);
    chartPanel = myPie.getChartPanel();
    chartPanel.setPreferredSize(new Dimension(120, 400));
    piePlot = myPie.getPiePlot();
    piePlot.setSectionPaint("良品", GREEN);
    piePlot.setSectionPaint("不良", Color.RED);
    jp.setLayout(new BorderLayout());
    jp.setBorder(new TitledBorder(new EtchedBorder(), "不良对照", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLUE));

    jp.add(chartPanel, BorderLayout.CENTER);

    JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, jp);
    pane.setResizeWeight(0.1D);

    rightPanel = new JPanel();
    rightPanel.setBorder(new TitledBorder(new EtchedBorder(), "测试结果页面", TitledBorder.CENTER, TitledBorder.BOTTOM,
        new Font("楷体", Font.PLAIN, 16), Color.BLUE));
    rightPanel.setLayout(new BorderLayout());
    rightPanel.add(pane, BorderLayout.CENTER);

    progressBar = new JProgressBar(JProgressBar.VERTICAL);
    progressBar.setForeground(GREEN);

    scanField = new JTextField("产品编号");
    scanField.setBackground(Color.BLACK);
    scanField.setHorizontalAlignment(SwingConstants.CENTER);
    scanField.setForeground(Color.RED);
    scanField.setFont(new Font("宋体", Font.BOLD, 30));
    scanField.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 1), "扫描结果:", TitledBorder.CENTER, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.WHITE));

    productField = new JTextField();
    productField.setBackground(new Color(240, 240, 240));
    productField.setForeground(Color.BLUE);
    productField.setFont(new Font("宋体", Font.BOLD, 40));
    productField.setText(PRODUCT_NAME);
    productField.setHorizontalAlignment(SwingConstants.CENTER);
    productField.setEditable(false);
    productField.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 2), "当前测试机种:", TitledBorder.LEFT,
        TitledBorder.TOP, new Font("楷体", Font.PLAIN, 16), Color.BLACK));

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 3), "测试步骤及内容", TitledBorder.CENTER,
        TitledBorder.TOP, new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    
    try {
      persistScroll = new JScrollPane(new ImageComponent(ImageIO.read(this.getClass().getResource("/pic/frame.jpg"))));
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    persistScroll.setPreferredSize(new Dimension(100, 200));
    persistScroll.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), " ", TitledBorder.LEFT, TitledBorder.TOP,
        new Font("楷体", Font.PLAIN, 16), Color.BLACK));
    //persistScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    persistScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    tablePanel.add(dataPanel, BorderLayout.CENTER);
    tablePanel.add(progressBar, BorderLayout.EAST);
    tablePanel.add(scanField, BorderLayout.SOUTH);
    tablePanel.add(productField, BorderLayout.NORTH);
    tablePanel.add(persistScroll, BorderLayout.WEST);

    editArea = new JTextArea();
    editArea.setFont(new Font("楷体", Font.PLAIN, 13));
//    editArea.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "软件日志:", TitledBorder.LEFT, TitledBorder.TOP,
//        new Font("宋体", Font.PLAIN, 13), Color.BLACK));
    
    JScrollPane editScroll = new JScrollPane(editArea);
    editScroll.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "软件日志:", TitledBorder.LEFT, TitledBorder.TOP,
        new Font("宋体", Font.PLAIN, 13), Color.BLACK));

    leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, editScroll);
    leftSplitPane.setResizeWeight(0.85D);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane, rightPanel);
    splitPane.setResizeWeight(0.75D);
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);

    JLabel pksLabel = new JLabel(" *版权所有：广州番禺旭东阪田电子有限公司(Kyokuto)");
    pksLabel.setForeground(Color.GRAY);
    pksLabel.setFont(new Font("楷体", Font.PLAIN, 13));
    pksLabel.setHorizontalAlignment(SwingConstants.LEFT);
    frame.getContentPane().add(pksLabel, BorderLayout.SOUTH);

    timer1 = new Timer(20, e -> {
      if (statuField.getText().equals("STOP"))
        statuField.setText("正在测试");
      progressBar.setValue(progressValue);
      progressValue += 1;
      if (progressValue > 100)
        progressValue = 0;
      timeCount += 20;
      timeField.setText(calculate(timeCount));
      setPieChart(progressValue, 10);

    });
    timer1.start();

  }

  /**
   * 添加运行时日志
   * 
   * @param info
   */
  public void logBySelf(String info) {
    editArea.append(dateFormat.format(new Date()) + "::" + info + SEPARATOR);
  }
  /**
   * 存储到本地文本文件
   */
  public void log2txt() {
    String infos = editArea.getText();
    FileWriter writer = null;
    File path = new File("C://日产log");
    if (!path.isDirectory()) {
      path.mkdirs();
    }
    File file = new File(path, PRODUCT_NAME + System.currentTimeMillis() + ".log");
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      writer = new FileWriter(file);
      writer.write(infos);
      writer.flush();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "日志文件创建失败：" + e.getLocalizedMessage());
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "日志文件创建失败：" + e.getLocalizedMessage());
      }
    }
  }

  /**
   * 计算测试时间显示
   * 
   * @param num
   */
  public String calculate(int num) {

    int thousand = (num / 1000);
    int hundred = (num % 1000 / 100);
    int ten = (num % 100 / 10);

    return "" + thousand + "." + hundred + ten;
  }

  /**
   * 刷新饼图的方法
   * 
   * @param ok
   * @param ng
   */
  public void setPieChart(int ok, int ng) {
    piePlot.setDataset(MyPieChart.getDataSet(ok, ng));
  }

  /**
   * 验证管理员密码是否正确
   * 
   * @param command
   *          日志信息
   * @return 布尔值
   */
  public boolean pwdIsPassed(String command) {
    String pwd = JOptionPane.showInputDialog(null, "请输入管理员密码");
    if (pwd != null && pwd.length() != 0) {
      if (pwd.equals(UserTools.getUserByID(2).getPassword())) {
        logBySelf(command + "::输入了密码:" + pwd);
        return true;
      } else {
        JOptionPane.showMessageDialog(null, "密码错误，请联系系统管理员获取密码！");
        return false;
      }
    }
    return false;
  }

  /** 打开调试工具事件 */
  ActionListener usartListener = e -> {
    if (pwdIsPassed(e.getActionCommand())) {

    }
  };
  /** 查看测试结果事件 */
  ActionListener resultListener = e -> {
    if (pwdIsPassed(e.getActionCommand())) {

    }
  };
  /** 查看测试报告事件 */
  ActionListener reportListener = e -> {
    if (pwdIsPassed(e.getActionCommand())) {
      
    }
  };
  
  /**
   * 给各组件安装监听器
   */
  public void addListener() {
    // 窗口"X"关闭事件
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        close();
      }
    });
    // 退出菜单事件
    exitItem.addActionListener(e -> close());
    // 退出系统按钮事件
    exitButt.addActionListener(e -> close());
    // 捺印事件
    nayinButt.addActionListener(e -> {
      String pwd = JOptionPane.showInputDialog(null, "请输入捺印密码");
      if (pwd != null && pwd.length() != 0) {
        if (pwd.equals(UserTools.getUserByID(3).getPassword())) {
          if (nayinButt.isSelected()) {
            nayinButt.setSelected(true);
            logBySelf("申请捺印");
          } else {
            nayinButt.setSelected(false);
            logBySelf("取消捺印");
          }
        } else {
          JOptionPane.showMessageDialog(null, "密码错误，请联系系统管理员获取密码！");
          if (nayinButt.isSelected())
            nayinButt.setSelected(false);
          else
            nayinButt.setSelected(true);
        }
      } else {
        if (nayinButt.isSelected())
          nayinButt.setSelected(false);
        else
          nayinButt.setSelected(true);
      }
    });
    usartButt.addActionListener(usartListener);
    usartItem.addActionListener(usartListener);
    resultButt.addActionListener(resultListener);
    resultItem.addActionListener(resultListener);
    ngReportButt.addActionListener(reportListener);
    ngReportItem.addActionListener(reportListener);
    //打开菜单事件
    openItem.addActionListener(e -> {
      logBySelf(e.getActionCommand());
      FileDialog openDialog = new FileDialog(frame, "打开文件", FileDialog.LOAD);
      openDialog.setDirectory(".");
      openDialog.setVisible(true);
    });
  }

  /**
   * 系统退出
   */
  public void close() {
    int num = JOptionPane.showConfirmDialog(null, "确认退出系统？", "提示", JOptionPane.OK_CANCEL_OPTION);
    // 如果确认键按下
    if (num == JOptionPane.OK_OPTION) {
      logBySelf("退出系统");
      log2txt();
      System.exit(0);
    }
  }

  /**
   * 测试数据及测试时间显示面板
   * 
   * @author hw076
   *
   */
  class MyPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public MyPanel(String title, JTextField field) {
      TitledBorder tb = new TitledBorder(new EtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP,
          new Font("等线", Font.ITALIC, 13), Color.BLUE);
      setBorder(tb);
      setLayout(new BorderLayout());
      add(field, BorderLayout.CENTER);
    }
  }

}
