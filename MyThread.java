public class MyThread extends Thread{
	int p;
	double [][] A;
	double [] b;
	int N;
	MyThread(int p, int N, double [][] A, double [] b){
		this.p = p;
		this.N = N;
		this.A = A;
		this.b = b;
	}
	@Override
	public void run(){
		int max = p;
		for (int i = p + 1; i < N; i++) {
			if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
				max = i;
			}
		}
		double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
		double   t    = b[p]; b[p] = b[max]; b[max] = t;

		if (Math.abs(A[p][p]) <= 1e-10) {
			System.out.println("NO");
			return;
		}

		for (int i = p + 1; i < N; i++) {
			double alpha = A[i][p] / A[p][p];
			b[i] -= alpha * b[p];
			for (int j = p; j < N; j++) {
				A[i][j] -= alpha * A[p][j];
			}
		}
	}
}