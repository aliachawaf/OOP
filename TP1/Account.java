
public class Account {

	private int accountNumber;
	private double balance;

	// contructors
	public Account(int accountNumber) {
		this.accountNumber = accountNumber;
		balance = 0.0;
	}

	public Account(int accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	// getters & setters
	public int getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	//methods
	public void credit(double amount){
		balance = balance + amount;
	}
	
	public void debit(double amount){
		
		if (balance >= amount){
			balance = balance - amount;
		}
		
		else{
			System.out.println("amount withdrawn exceeds the current balance ! ");
		}
		
	}
	
	
	public String toString(){
		return "A/C no:" + accountNumber + ", Balance = $" + balance; 
	}

}
