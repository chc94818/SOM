import java.util.ArrayList;

public class SOM {
	double[][] vT;
	double learn;
	double times;
	int near;
	double[][] dataTrain;
	double[][] dataTest;
	double[] weight;
	double maxTemp;
	double minTemp;
	double reguTemp;
	double reguNu;
	double[][] data;
	double rmse;
	double side;
	Perceptron p[][];

	public double[][] initialData(ArrayList<double[]> temp) {
		System.out.println("ini !");
		data = new double[temp.size()][temp.get(0).length];

		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < temp.get(0).length; j++) {
				data[i][j] = temp.get(i)[j];
			}
		}
		return data;
	}

	public void randomData() {
		double[] exchange = new double[data[0].length];

		for (int i = 0; i < data.length * 3; i++) {
			int index1 = (int) (Math.random() * data.length);
			int index2 = (int) (Math.random() * data.length);
			exchange = data[index1];
			data[index1] = data[index2];
			data[index2] = exchange;
		}
	}

	public Perceptron[][] cal(double learnTemp, double timesTemp,
			int nearTemp, Perceptron temp[][], double scale) {

//		for (int i = 0; i < data.length; i++) {
//			for (int j = 0; j < data[0].length; j++) {
//				System.out.print(data[i][j] + " ");
//			}
//			System.out.println();
//		}

		p = temp;
		learn = learnTemp;
		times = timesTemp;
		near = nearTemp;
		side = scale * 1.05;
		double pGap = 2 * side / (p.length - 1);

		// ªì©l¤Ænode
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p.length; j++) {

				p[i][j].randomWeight(data[0].length);
				p[i][j].setWeight(-side + i * pGap, -side + j * pGap);
			}
		}

		for (int i = 0; i < times; i++) {
			
			randomData();
			train(i);
			if(near>0){
				near--;
			}
		
		}
		test();
		return p;
	}

	public void train(int time) {
		double valueTemp = 0;
		for (int i = 0; i < data.length; i++) {
			int minIdR = 0;
			int minIdC = 0;
			double minValue = 10000000;

			for (int j = 0; j < p.length; j++) {
				for (int k = 0; k < p.length; k++) {
					valueTemp = p[j][k].getDistance(data[i]);
					if (valueTemp < minValue) {
						minValue = valueTemp;
						minIdR = j;
						minIdC = k;
					}
				}
			}
			for(int j = minIdR-near ; j <= minIdR+near ; j++){
				for(int k = minIdC-near ; k <= minIdC+near ; k++){
					if(j>=0 && j < p.length &&k>=0 && k < p.length){
						p[j][k].winnerLearn(learn*(1-time/times), data[i]);
					}
				}
			}
			
		}
	}

	public void test() {
		double valueTemp = 0;
		
		for (int j = 0; j < p.length; j++) {
			for (int k = 0; k < p.length; k++) {
				
				p[j][k].initialWin();
			}
		}	
		
		for (int i = 0; i < data.length; i++) {
			int minIdR = 0;
			int minIdC = 0;
			double minValue = 10000000;

			for (int j = 0; j < p.length; j++) {
				for (int k = 0; k < p.length; k++) {
					valueTemp = p[j][k].getDistance(data[i]);
					if (valueTemp < minValue) {
						minValue = valueTemp;
						minIdR = j;
						minIdC = k;
					}
				}
			}
			
						p[minIdR][minIdC].win();
		
			
		}
	}

}
