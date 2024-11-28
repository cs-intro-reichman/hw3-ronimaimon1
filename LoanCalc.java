// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
	
	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations 
	
	// Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
	public static void main(String[] args) {		
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {	
		double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = (balance - payment) * (1 + rate);  // Subtract payment, apply interest
        }
        return balance;
	}
	
	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;
        double guess = loan / n;  // Initial guess for the payment (no interest considered)
        double tolerance = 0.001;  // More precise tolerance for the ending balance
        double balance = endBalance(loan, rate, n, guess);
        
        // Set a max iteration limit to avoid too long execution time
        int maxIterations = 10000;

        while (Math.abs(balance) > tolerance && iterationCounter < maxIterations) {
            guess += 10;  // Increase the increment for faster convergence
            balance = endBalance(loan, rate, n, guess);
            iterationCounter++;  // Count the number of iterations
        }

        // If the loop finishes because of maxIterations, print a warning
        if (iterationCounter >= maxIterations) {
            System.out.println("Warning: Brute force search hit max iterations");
        }

        return guess;  // Return the estimated payment
    }
    
    // Uses bisection search to compute an approximation of the periodical payment 
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {  
		iterationCounter = 0;
        double low = loan / n;  // Start with low estimate
        double high = loan;     // Start with high estimate
        double tolerance = 0.001;  // More precise tolerance for the ending balance
        double guess = (low + high) / 2;  // Mid-point guess

        // Loop until the difference between high and low is less than the tolerance
        while (high - low > tolerance) {
            double balance = endBalance(loan, rate, n, guess);
            if (balance > 0) {
                low = guess;  // If the balance is positive, the payment is too low
            } else {
                high = guess;  // If the balance is negative, the payment is too high
            }
            guess = (low + high) / 2;  // Recalculate the mid-point guess
            iterationCounter++;  // Count the number of iterations
        }

        return guess;  // Return the estimated payment
	}
}