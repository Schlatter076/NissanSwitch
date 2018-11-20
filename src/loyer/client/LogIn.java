package loyer.client;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import loyer.db.ProductData;
import loyer.db.ProductTools;

public class LogIn {

  private JFrame frame;
  /**获取当前屏幕宽度*/
  private static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  /**获取当前屏幕高度*/
  private static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  private JPanel panel1;
  private JPanel panel2;
  private JPanel panel4;
  private JLabel pksLabel;
  private JButton exitButt;
  private JButton logInButt;
  private JButton chooseButt;
  private JTextField textField;
  /**产品型号*/
  private ProductData data;
  private int typeCount = 1;
  private boolean isDataView = false;
  

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          LogIn window = new LogIn();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public LogIn() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    
    try {
      //将界面风格设置成和系统一置
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }//*/
    
    frame = new JFrame();
    Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/pic/Kyokuto.png"));
    frame.setIconImage(img);
    frame.getContentPane().setBackground(new Color(255, 255, 255));
    frame.setBounds(WIDTH / 3, HEIGHT / 3, WIDTH / 3, HEIGHT / 5);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setUndecorated(true);  //去掉窗口装饰
    frame.getContentPane().setLayout(new GridLayout(3, 1));
    
    //添加全局enter键监听
    Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
      
      @Override
      public void eventDispatched(AWTEvent event) {
        //如果有键按下，且按键是enter键，点击登录按钮
        if(((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED && ((KeyEvent)event).getKeyChar() == KeyEvent.VK_ENTER) {
          logInButt.doClick();
        }
      }
    }, AWTEvent.KEY_EVENT_MASK);
    
    panel1 = new JPanel();
    panel1.setBorder(new LineBorder(new Color(255, 255, 255), 5));
    panel1.setBackground(new Color(220, 220, 220));
    panel1.setOpaque(false);
    frame.getContentPane().add(panel1);
    panel1.setLayout(new BorderLayout(0, 0));
    
    pksLabel = new JLabel("广州番禺旭东阪田电子有限公司(PKS)");
    pksLabel.setHorizontalAlignment(SwingConstants.LEFT);
    pksLabel.setForeground(new Color(0, 0, 0));
    pksLabel.setBackground(new Color(220, 220, 220));
    pksLabel.setFont(new Font("楷体", Font.BOLD | Font.ITALIC, 16));
    pksLabel.setIcon(new ImageIcon(img));  //this.getClass().getResource("/pic/Kyokuto.png")
    panel1.add(pksLabel, BorderLayout.CENTER);
    
    panel2 = new JPanel();
    panel2.setOpaque(false);
    panel2.setBackground(new Color(220, 220, 220));
    panel2.setBorder(new LineBorder(new Color(245, 245, 245), 5, true));
    frame.getContentPane().add(panel2);
    panel2.setLayout(new BorderLayout(7, 0));
    
    chooseButt = new JButton("选择机种");
    chooseButt.addActionListener(e -> {
      data = ProductTools.getByNum(typeCount);
      if(data == null) {
        data = ProductTools.getByNum(1);
        typeCount = 1;
      }
      textField.setText(data.getProductName());
      typeCount++;
    });
    chooseButt.setFont(new Font("宋体", Font.PLAIN, 14));
    chooseButt.setForeground(Color.BLACK);
    chooseButt.setBackground(new Color(255, 255, 255));
    panel2.add(chooseButt, BorderLayout.EAST);
    
    textField = new JTextField();
    textField.setEditable(false);
    textField.setHorizontalAlignment(SwingConstants.CENTER);
    textField.setFont(new Font("楷体", Font.BOLD, 22));
    //设置默认显示机种
    textField.setText(ProductTools.getByNum(typeCount).getProductName());
    textField.setForeground(new Color(255, 0, 0));
    textField.setBackground(new Color(0, 0, 0));
    panel2.add(textField, BorderLayout.CENTER);
    textField.setColumns(10);
    
    panel4 = new JPanel();
    panel4.setBackground(new Color(245, 245, 245));
    panel4.setLayout(new GridLayout(1, 2, 20, 5));
    panel4.setOpaque(false);
    TitledBorder tb = new TitledBorder(new EtchedBorder(), "先选择机种，然后点击登录按钮", TitledBorder.CENTER, 
        TitledBorder.TOP, new Font("等线", Font.ITALIC, 13), Color.BLUE);
    panel4.setBorder(tb);
    frame.getContentPane().add(panel4);
    
    exitButt = new JButton("退出(Exit)");
    exitButt.setFont(new Font("宋体", Font.PLAIN, 13));
    exitButt.addActionListener(e -> System.exit(0));  //退出系统
    exitButt.setBackground(new Color(255, 255, 255));
    exitButt.setForeground(new Color(0, 0, 0));
    panel4.add(exitButt);
    
    logInButt = new JButton("登录(LogIn)");
    //登录事件
    logInButt.addActionListener(e -> logIn());
    logInButt.setForeground(new Color(0, 0, 0));
    logInButt.setFont(new Font("宋体", Font.PLAIN, 16));
    logInButt.setBackground(new Color(255, 255, 255));
    panel4.add(logInButt);    
  }
  /**
   * 登录事件
   */
  public void logIn() {
    if(!isDataView) {
      String productName = textField.getText();
      switch(productName) {
      case "VDC开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "VDC开关测试...");
        break;
      case "MIRROR-CONT后视镜开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "MIRROR-CONT后视镜开关...");
        break;
      case "HAZARD告警灯开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "HAZARD告警灯开关...");
        break;
      case "ILLUMINATION开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "ILLUMINATION开关...");
        break;
      case "RETRACTOR大灯调节开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "RETRACTOR大灯调节开关...");
        break;
      case "STRG方向盘低配开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "STRG方向盘低配开关...");
        break;
      case "STRG方向盘中配开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "STRG方向盘中配开关...");
        break;
      case "TRUK-OPENER后备箱开关" :
        isDataView = true;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "TRUK-OPENER后备箱开关...");
        break;
      }
    }
  }

}
