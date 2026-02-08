package GUI.Component.Chart.CurveChart;

import GUI.Component.Chart.CurveChart.BlankChart.BlankPlotChart;
import GUI.Component.Chart.CurveChart.BlankChart.BlankPlotChatRender;
import GUI.Component.Chart.CurveChart.BlankChart.SeriesSize;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class CurveChart extends javax.swing.JPanel {

    DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<ModelLegend2> legends = new ArrayList<>();
    private List<ModelChart2> model = new ArrayList<>();

    // ===== ANIMATION =====
    private Timer timer;
    private float animate = 0f;
    private final int ANIMATION_DURATION = 800; // ms
    private final int FPS = 60;

    public CurveChart() {
        initComponents();
        setBackground(Color.WHITE);

        blankPlotChart.getNiceScale().setMaxTicks(5);
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public int getMaxLegend() {
                return legends.size();
            }

            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index, List<Path2D.Double> gra) {
                for (int i = 0; i < legends.size(); i++) {
                    double ys;
                    double xs;
                    double x = size.getX() + size.getWidth() / 2f;
                    if (index == 0) {
                        ys = chart.getSeriesValuesOf(0, size.getHeight()) * animate;
                        ys = size.getY() + size.getHeight() - ys;
                        xs = x - size.getWidth() / 2;
                    } else {
                        ys = chart.getSeriesValuesOf(model.get(index - 1).getValues()[i], size.getHeight()) * animate;
                        ys = size.getY() + size.getHeight() - ys;
                        xs = x - size.getWidth();
                    }
                    double s = xs + size.getWidth() / 4;
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                    double yy = size.getY() + size.getHeight() - seriesValues;
                    gra.get(i).append(
                            new CubicCurve2D.Double(xs, ys, s, ys, s, yy, x, yy),
                            true
                    );
                    if (index == chart.getLabelCount() - 1) {
                        xs = x;
                        ys = yy;
                        s = xs + size.getWidth() / 4;
                        seriesValues = chart.getSeriesValuesOf(0, size.getHeight()) * animate;
                        yy = size.getY() + size.getHeight() - seriesValues;
                        gra.get(i).append(
                                new CubicCurve2D.Double(xs, ys, s, ys, s, yy, x + size.getWidth() / 2, yy),
                                true
                        );
                    }
                }
            }

            @Override
            public void renderGraphics(Graphics2D g2, List<Path2D.Double> gra) {
                g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
                for (int i = 0; i < gra.size(); i++) {
                    Color c = legends.get(i).getColorLight();
                    g2.setPaint(new GradientPaint(
                            0, 0, legends.get(i).getColor(),
                            0, getHeight(),
                            new Color(c.getRed(), c.getGreen(), c.getBlue(), 0)
                    ));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fill(gra.get(i));

                    g2.setPaint(new GradientPaint(
                            0, 0, legends.get(i).getColor(),
                            getWidth(), 0, legends.get(i).getColorLight()
                    ));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    g2.draw(gra.get(i));
                }
            }

            @Override
            public boolean mouseMoving(BlankPlotChart chart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
                return false;
            }
        });
    }

    // ===== PUBLIC API =====
    public void addLegend(String name, Color color, Color color1) {
        ModelLegend2 data = new ModelLegend2(name, color, color1);
        legends.add(data);
        panelLegend.add(new LegendItem2(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }

    public void addData(ModelChart2 data) {
        model.add(data);
        blankPlotChart.setLabelCount(model.size());
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }

    public void clear() {
        stop();
        animate = 0;
        blankPlotChart.setLabelCount(0);
        model.clear();
        repaint();
    }

    public void start() {
        stop();
        animate = 0f;
        int delay = 1000 / FPS;
        float step = (float) delay / ANIMATION_DURATION;

        timer = new Timer(delay, e -> {
            animate += step;
            if (animate >= 1f) {
                animate = 1f;
                stop();
            }
            repaint();
        });
        timer.start();
    }

    private void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        blankPlotChart = new GUI.Component.Chart.CurveChart.BlankChart.BlankPlotChart();
        panelLegend = new javax.swing.JPanel();

        panelLegend.setOpaque(false);
        panelLegend.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                                        .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>

    // Variables declaration
    private GUI.Component.Chart.CurveChart.BlankChart.BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    // End of variables declaration
}
