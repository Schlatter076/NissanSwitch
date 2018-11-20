package loyer.client;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class MyPieChart {
  private ChartPanel frame1;
  private DefaultPieDataset data;
  private JFreeChart chart;
  private PiePlot piePlot;
  
  public MyPieChart(int ok, int ng) {
    data = getDataSet(ok, ng);
    chart = ChartFactory.createPieChart3D(null, data, true, true, false); //第一个参数为标题，这里我不要了
    /////////////////////////////////
    chart.setTextAntiAlias(false);
    chart.setBackgroundPaint(null);
    ////////////////////////////////
    // 设置百分比
    piePlot = (PiePlot) chart.getPlot();// 获取图表区域对象
    DecimalFormat df = new DecimalFormat("0.00%");// 获得一个DecimalFormat对象，主要是设置小数问题
    NumberFormat nf = NumberFormat.getNumberInstance();// 获得一个NumberFormat对象
    StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);// 获得StandardPieSectionLabelGenerator对象
    piePlot.setLabelGenerator(sp1);// 设置饼图显示百分比

    // 没有数据的时候显示的内容
    piePlot.setNoDataMessage("暂无数据显示");
    piePlot.setCircular(true);  
    piePlot.setLabelGap(0.02D);
    //piePlot.setDataset(dataset);

    piePlot.setIgnoreNullValues(true);// 设置不显示空值
    piePlot.setIgnoreZeroValues(true);// 设置不显示负值
    frame1 = new ChartPanel(chart, true);
    
    //chart.getTitle().setFont(new Font("等线", Font.PLAIN, 24));// 设置标题字体
    //chart.getTitle().setBackgroundPaint(Color.RED);
    
    //背景色　透明度     
    piePlot.setBackgroundAlpha(0.0f);  
    //piePlot.setBackgroundPaint(null);
    //前景色　透明度     
    piePlot.setForegroundAlpha(1.0f);
    //piePlot.setLabelLinksVisible(true);
    
    //piePlot.setBackgroundImage(Toolkit.getDefaultToolkit().getImage("src/run.jpg"));  //替换背景
    
    piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 15));// 解决乱码
    piePlot.setLabelBackgroundPaint(Color.ORANGE);
    piePlot.setLabelOutlinePaint(null);
    chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 14));
    //chart.getLegend().setBackgroundPaint(null); //图最下面的标识设置透明
  }
  public static DefaultPieDataset getDataSet(int ok, int ng) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("不良", ng);
    dataset.setValue("良品", ok);
    return dataset;
  }
  public ChartPanel getChartPanel() {
    return frame1;
  }
  public JFreeChart getJFreeChart() {
    return chart;
  }
  public PiePlot getPiePlot() {
    return piePlot;
  }

}
