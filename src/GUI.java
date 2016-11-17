import javax.swing.*;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.soap.Node;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class GUI implements ActionListener {
	coodPaint canvas = new coodPaint();
	double scale;
	static ArrayList<double[]> data;
	static double[][] reguData;
	static ArrayList<Double> kind;
	int outputNum;
	Perceptron[][] Nodes;
	JFrame frame;

	JButton btnSelect;
	JButton btnCal;
	JButton btnDV;
	JButton btnLN;
	JButton btnWin;

	JPanel panelTOP;
	JPanel[] panelLayer;
	JPanel panelBOT;
	JPanel jp;

	JLabel labelFile;
	JLabel labelNear;
	JLabel labelLearn;
	JLabel labalTimes;
	JLabel labalNum;
	JLabel[][] nodeLabel;

	JTextField inputNear;
	JTextField inputLearn;
	JTextField inputTimes;
	JTextField inputNum;

	JFileChooser fileChooser;
	File file;

	SOM som = new SOM();

	// Perceptron perceptron = new Perceptron();

	GUI() {;
		panelLayer = new JPanel[0];
		// frame
		frame = new JFrame();
		frame.setTitle("SOM");
		frame.setSize(645, 820);
		frame.setLayout(new BorderLayout());

		// btn
		btnSelect = new JButton("選擇檔案");
		btnSelect.addActionListener(this);
		btnCal = new JButton("開始測試");
		btnCal.addActionListener(this);
		btnDV = new JButton("(顯示/不顯示)DATA");
		btnDV.addActionListener(this);
		btnLN = new JButton("(顯示/不顯示)連結線");
		btnLN.addActionListener(this);
		btnWin = new JButton("(顯示/不顯示)得勝次數");
		btnWin.addActionListener(this);
		// label
		labelFile = new JLabel();
		labelNear = new JLabel("鄰域函數: ");
		labelLearn = new JLabel("學習率 : ");
		labalTimes = new JLabel("疊代次數 : ");
		labalNum = new JLabel("神經元數目(邊長) : ");
	
		// file
		fileChooser = new JFileChooser();

		// text
		inputNear = new JTextField(5);
		inputNear.setText("2");

		inputTimes = new JTextField(5);
		inputTimes.setText("500");

		inputLearn = new JTextField(5);
		inputLearn.setText("0.05");
		
		inputNum = new JTextField(5);
		inputNum.setText("10");

		// panel
		panelBOT = new JPanel();
		panelBOT.add(labelLearn);
		panelBOT.add(inputLearn);
		panelBOT.add(labelNear);
		panelBOT.add(inputNear);
		panelBOT.add(labalTimes);
		panelBOT.add(inputTimes);
		panelBOT.add(labalNum);
		panelBOT.add(inputNum);
		panelBOT.add(btnSelect);
		panelBOT.add(btnCal);
		panelBOT.add(btnDV);
		panelBOT.add(btnLN);
		panelBOT.add(btnWin);

		panelBOT.setBorder(BorderFactory.createTitledBorder("設定"));
		panelBOT.setPreferredSize(new Dimension(580, 140));

		
		
	

		// frame

		frame.setResizable(false);
		frame.addWindowListener(new WindowHandler());
		frame.add(labelFile, BorderLayout.NORTH);
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(panelBOT, BorderLayout.SOUTH);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}


	public void actionPerformed(ActionEvent e) {

		int result;
		if (e.getActionCommand().equals("(顯示/不顯示)DATA")&& file != null){
			canvas.setDVisible();
		}
		if (e.getActionCommand().equals("(顯示/不顯示)連結線")&& file != null){
			canvas.setLVisible();
		}
		if (e.getActionCommand().equals("(顯示/不顯示)得勝次數")&& file != null){
			canvas.setWVisible();
		}
		if (e.getActionCommand().equals("開始測試") && file != null) {
			Nodes = new Perceptron[Integer.valueOf(inputNum.getText())][Integer.valueOf(inputNum.getText())];
		
			for(int i = 0 ; i < Nodes.length ; i++){
				for(int j = 0 ; j < Nodes.length ; j++){
					Nodes[i][j] = new Perceptron();
				}
			}
			Nodes = som.cal(Double.valueOf(inputLearn.getText()),Double.valueOf(inputTimes.getText()),Integer.valueOf(inputNear.getText()),Nodes,scale);
			canvas.drawNode(Nodes);
		}

		if (e.getActionCommand().equals("選擇檔案")) {
			fileChooser.setDialogTitle("選擇檔案");
			result = fileChooser.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				labelFile.setText("檔案名稱：" + file.getName());
				kind = new ArrayList<>();
				try {
					data = new ArrayList();
					Scanner input = new Scanner(file);

					while (input.hasNext()) {

						String tempInput = input.nextLine();
						String[] tempStr = tempInput.split("\\s+");
						double[] temp = null;

						// System.out.println("000:\""+tempInput+"\"");
						if (tempStr[0].equals("")) {
							int space = 1;
							// System.out.print("tempStr:\""+tempStr[space]+"\"");
							while (tempStr[space].equals("")) {
								// System.out.print("tempStr:\""+tempStr[space]+"\"");
								space++;
							}

							temp = new double[tempStr.length - space];
							for (int i = space; i < tempStr.length; i++) {
								// System.out.println("I:\""+i+"\"");
								// System.out.println("space:\""+space+"\"");
								temp[i - space] = Double.valueOf(tempStr[i]);
								// System.out.println("tempStr[i]:\""+i+"="+tempStr[i]+"\"");
							}
						} else if (!tempStr[0].equals("")) {
							temp = new double[tempStr.length];
							for (int i = 0; i < tempStr.length; i++) {
								// System.out.println("tempStr[i]:\""+i+"="+tempStr[i]+"\"");
								temp[i] = Double.valueOf(tempStr[i]);
							}
						}
						if (kind.isEmpty()) {
							kind.add(temp[temp.length - 1]);
						}
						for (int i = 0; i < kind.size(); i++) {
							if (temp[temp.length - 1] == kind.get(i)) {
								break;
							}
							if (i == kind.size() - 1) {
								kind.add(temp[temp.length - 1]);
							}
						}

						data.add(temp);

					}
					outputNum=1;
					int temp = 2;
					while(temp<kind.size()){
						outputNum++;
						temp*=2;
					}
					
					// perceptron.setData(data);
					// canvas.drawData(data);

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			reguData = som.initialData(data);
			scale = canvas.drawData(reguData);
			
			
		}
	}

}

class WindowHandler extends WindowAdapter { // 次類別
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}