import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D.Float;
import java.util.ArrayList;

public class coodPaint extends Canvas {
	double[][] data;
	Perceptron[][] nodes;
	int dotSize = 6;
	int enlarge = 100;
	boolean N_CAN_DRAW = false;
	boolean D_CAN_DRAW = true;
	boolean L_CAN_DRAW = true;
	boolean W_CAN_DRAW = false;
	double[] weight = new double[1];

	public coodPaint() {

		data = new double[1][1];
	}

	public void setDVisible() {
		if (D_CAN_DRAW) {
			D_CAN_DRAW = false;
		} else {
			D_CAN_DRAW = true;
		}
		repaint();
	}

	public void setLVisible() {
		if (L_CAN_DRAW) {
			L_CAN_DRAW = false;
		} else {
			L_CAN_DRAW = true;
		}
		repaint();
	}

	public void setWVisible() {
		if (W_CAN_DRAW) {
			W_CAN_DRAW = false;
		} else {
			W_CAN_DRAW = true;
		}
		repaint();
	}

	public void paint(Graphics g) {
		// 畫一條直線,從座標(100,100)到(150,150)
		g.setColor(Color.black);
		g.fillRect(0, 0, 640, 640);
		g.setColor(Color.white);
		g.drawLine(0, 320, 640, 320);
		g.drawLine(320, 0, 320, 640);
		if (data[0].length == 3) {
			if (D_CAN_DRAW) {
				for (int i = 0; i < data.length; i++) {
					int[] temp = null;

					g.setColor(Color.red);

					temp = correctPos(data[i][0], data[i][1]);
					// System.out.print(temp[0]+" "+temp[1]);

					g.fillRect(temp[0], temp[1], dotSize, dotSize);

				}
			}

			if (N_CAN_DRAW) {
				Graphics2D g2 = (Graphics2D) this.getGraphics();
				g2.setStroke(new BasicStroke(2));

				// node

				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < nodes.length; j++) {
						int[] temp = null;

						temp = correctPos(nodes[i][j].weight[1],
								nodes[i][j].weight[2]);
						nodes[i][j].setPosition(temp[0] + dotSize / 2, temp[1]
								+ dotSize / 2);
						if (L_CAN_DRAW) {
							g.setColor(Color.MAGENTA);
							if (i > 0) {
								g2.drawLine(nodes[i][j].positionX,
										nodes[i][j].positionY,
										nodes[i - 1][j].positionX,
										nodes[i - 1][j].positionY);
							}
							if (j > 0) {
								g2.drawLine(nodes[i][j].positionX,
										nodes[i][j].positionY,
										nodes[i][j - 1].positionX,
										nodes[i][j - 1].positionY);
							}
						}
					
					}

				}
				for (int i = 0; i < nodes.length; i++) {
					for (int j = 0; j < nodes.length; j++) {
						g.setColor(Color.GREEN);
						if (W_CAN_DRAW) {

							g.drawString(nodes[i][j].winTimes + "", nodes[i][j].positionX-dotSize/2,
									nodes[i][j].positionY+dotSize/2);
						} else {
							g.fillRect(nodes[i][j].positionX-dotSize/2, nodes[i][j].positionY-dotSize/2, dotSize, dotSize);
						}
						
					}
				}

			}

		}

	}

	public int[] correctPos(double x, double y) {
		int[] temp = new int[2];
		temp[0] = (int) Math.floor(x * enlarge) + 320 - dotSize / 2;
		temp[1] = -(int) Math.floor(y * enlarge) + 320 - dotSize / 2;
		return temp;
	}

	public double drawData(double[][] temp) {
		N_CAN_DRAW = false;
		double max = 0;
		for (int i = 0; i < temp.length; i++) {
			if (Math.abs(temp[i][0]) > max) {
				max = Math.abs(temp[i][0]);
			}
			if (Math.abs(temp[i][1]) > max) {
				max = Math.abs(temp[i][1]);
			}
		}
		enlarge = (int) (300 / max);
		// enlarge=40;
		data = temp;
		repaint();
		return max;
	}

	public void drawNode(Perceptron[][] temp) {
		nodes = temp;
		N_CAN_DRAW = true;
		repaint();
	}

	public int[] getLine(double[] temp) {

		int ret[] = new int[4];
		double x = -1000;
		double y = (temp[0] - x * temp[1]) / temp[2];
		ret[0] = (int) Math.floor(x * enlarge) + 320;
		ret[1] = -(int) Math.floor(y * enlarge) + 320;
		System.out.println(x + "   ,   " + y);

		double ex = 1000;
		double ey = (temp[0] - ex * temp[1]) / temp[2];

		ret[2] = (int) Math.floor(ex * enlarge) + 320;
		ret[3] = -(int) Math.floor(ey * enlarge) + 320;
		System.out.println(ex + "   ,   " + ey);
		return ret;
	}
}