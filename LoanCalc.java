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
		// Calculate monthly interest rate
		double monthlyRate = rate / 100 / 12;
		double balance = loan;
		
		// Loop over all periods to compute the remaining balance
		for (int i = 0; i < n; i++) {
			balance = balance * (1 + monthlyRate) - payment;
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
    	double guess = loan / n;  // Initial guess (loan / periods) is a better starting point
    	double balance = endBalance(loan, rate, n, guess);
    	double stepSize = 0.1;  // Start with a reasonable step size to adjust payments

    	// Brute force loop to find the correct periodical payment
   		while (Math.abs(balance) > epsilon && iterationCounter < 10000) {
        	guess += stepSize;  // Try to fine-tune the guess incrementally
        	balance = endBalance(loan, rate, n, guess);
        	iterationCounter++;  // Count the number of iterations
        	// Dynamically adjust step size based on how close we are to the solution
        	if (Math.abs(balance) < 100) {
            	stepSize = 0.01;  // Reduce step size to refine the estimate
        	}
    	}

    	// If the loop finishes because of max iterations, print a warning
    	if (iterationCounter >= 10000) {
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
        double guess = (low + high) / 2;  // Mid-point guess
        double balance = endBalance(loan, rate, n, guess);

        // Loop until the difference between high and low is less than the epsilon tolerance
        while (high - low > epsilon) {
            balance = endBalance(loan, rate, n, guess);
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