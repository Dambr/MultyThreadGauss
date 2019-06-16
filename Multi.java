import java.util.Scanner;

public class Multi{
	
	static double random(int min, int max){
		max -= min;
		return (int) (Math.random() * ++max) + min;
	}
	static void showMatrix(double [][] A, double [] B){
		for (int i = 0; i < A.length; i ++){
			System.out.println();
			for (int j = 0; j < B.length; j ++){
				System.out.print((int)A[i][j] + " ");
			}
			System.out.print("\t" + (int)B[i]);
		}
		System.out.println();
		System.out.println();
	}
	public static void main(String[] args) {
		
		/* Ввод данных */

		Scanner s = new Scanner(System.in);
		System.out.print("Enter number of stb: ");
		int n = s.nextInt();
		System.out.print("Enter number of str: ");
		int m = s.nextInt();
		double [][] A = new double[n][m];
		double [] b = new double[n];
		for (int i = 0; i < n; i++) {
			A[i] = new double[m];
			for (int j = 0; j < m; j++) {
				A[i][j] = random(1, 9);
			}
			b[i] = random(1, 9);
		}

		showMatrix(A, b);

		/* Метод Гаусса */
		int proc = Integer.parseInt(System.getenv("NUMBER_OF_PROCESSORS"));
		long start = System.currentTimeMillis();
		int N  = n;

		MyThread [] myThread = new MyThread [proc];
		for (int p = 0; p < N; p++) {
			myThread[p % proc] = new MyThread(p, N, A, b);
			myThread[p % proc].start(); 
		}

		// Обратный проход

		double[] x = new double[N];
		for (int i = N - 1; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < N; j++) {
				sum += A[i][j] * x[j];
			}
			x[i] = (b[i] - sum) / A[i][i];
		}

		long finish = System.currentTimeMillis();
		/* Вывод результатов */

		if (n < m) {
			System.out.print("INF");
		} else {
			System.out.println("YES");
			for (int i = 0; i < N; i++) {
				System.out.println("X" + (i + 1) + ": " + x[i]);
			}
		}
		System.out.println();
		System.out.println("Time: " + (finish - start) / 1000 + " sec");
		System.out.println("Processors: " + System.getenv("NUMBER_OF_PROCESSORS"));
	}
}
