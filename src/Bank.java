import java.io.*;
import java.util.Date;

class Account{
	private String name;
	private Integer balance;
	private Integer accountNumber;
	private String password;
	private String status;
	private Loan[] loanList;
	private static int loanCount=0;
	private static int nextAccountNumber=16000;
	public static final double interest = 4.00;
	
	private Account(String name, int balance, String password){
		this.name=name;
		this.balance=balance;
		accountNumber=nextAccountNumber++;
		this.password=password;
		if(balance>=0) status= "active";
		loanList= new Loan[10];

	}
	
	public void deposit(int amount){
		balance+=amount;
		System.out.println("Your updated balance is: " + balance.toString());
		if(balance>=0) status="active";
	}
	
	public void withdraw(int amount){
		if(amount<=balance+1000){
			balance+=amount;
			System.out.println("Your updated balance is: " + balance.toString());
			if(balance<0) status="frozen";
		}
		else{
			System.out.println("You donot have enough balance to withdraw");
		}
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public void showBalance(){
		System.out.println("Your Balance is" + balance.toString());
	}

	public void issueNewLoan(String type,int amount,byte tenureInYears){
		if (loanCount>9) {
			System.out.println("Sorry! You cannot have more than 10 loans");
			return;
		}
		else if(this.getTotalLoanAmount()+amount>1000000){
			System.out.println("Sorry! You cannot take loan more than one million rupees.");
			return;
		}
		else if(type=="HomeLoan") {
			loanList[loanCount++]= new HomeLoan(amount,tenureInYears);
			System.out.println("Your loan is issued");
			loanList[loanCount-1].showLoanDetails();
		}
		else if(type=="EducationLoan") {
			loanList[loanCount++]= new EducationLoan(amount,tenureInYears);
		}
	}
	public double getTotalLoanAmount(){
		float temp=0;
		for(int i=0;i<loanCount;i++){
			temp+=loanList[i].amount;
		}
		return temp;
	}
	public static Account openNewAccount() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		String name;
		Integer amount;
		String password;
		do{
			System.out.println("Lets open a new Account");
			System.out.println("Your full name please?");
			name= new String(br.readLine().toUpperCase());
			if(name.matches("[A-Z]+\\s[A-Z]+")){
				System.out.println("Welcome "+ name);
				break;
			}
			else{
				System.out.println("Sorry the format of name should be \"FirstName LastName\"");
			}
		}while(true);

		// Input Amount
		do{
			try{
				System.out.println("How much money would you like to deposit now? Minimum amount to open an account is Rs.10000");
				amount = Integer.parseInt(br.readLine());
				if(amount<10000) System.out.println("Sorry we cant open an amount with amount " + amount.toString());
				else {
					System.out.println("Okay! You will have "+ amount.toString() + " as opening balance"); 
					break;
				}
			}
			catch(NumberFormatException e){
				System.out.println("Please enter a number as an Amount");
			}
		}while(true);
		// input password
		System.out.println("Choose a password to access your account");
		password = new String(br.readLine());
		return new Account(name,amount,password);
	}	
}


class Accounts{
	private Account[] customers;
	public static boolean flag=false;
	private Accounts(){
		customers = new Account[1000];
	}

	public static Accounts createAccountsDirectry(){
		//if(flag==false)
			return new Accounts();
	} 

	public void newAccount() throws IOException{
		Account temp = Account.openNewAccount();
		customers[temp.getAccountNumber()-16000]=temp;
	}

}


abstract class Loan{
	protected double amount;
	protected Date startDate;
	protected Date endDate;
	protected byte tenureInYears;
	public String status;
	public Loan(int amount,byte tenureInYears){
		this.amount=amount;
		this.tenureInYears=tenureInYears;
		startDate = new Date();
		endDate = new Date();
		endDate.setYear(startDate.getYear()+tenureInYears);
		status="active";
	}
	abstract public double getDueAmount();
	abstract public void showLoanDetails();
	abstract public double getLoanRate();
}

class EducationLoan extends Loan{
	public static String loanName = "\"Bright Future Education Loan\"";
	public static double rate = 10.0;
	private double amountDue;

	public EducationLoan(int amount, byte tenureInYears){
		super(amount,tenureInYears);
	}
	
	public double getDueAmount(){
		amountDue=amount*tenureInYears*(100+rate)/100.0;
		return amountDue;
	}

	public void showLoanDetails(){
		System.out.println(loanName);
		System.out.print("Issue Date: ");
		System.out.println(startDate);
		System.out.print("End Date: ");
		System.out.println(endDate);
		System.out.print("Due amount on End Date is: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}
}

class HomeLoan extends Loan{
	public static String loanName = "Secure life Home loans";
	public static double rate = 15.0;
	private double amountDue;
	
	public HomeLoan(int amount, byte tenureInYears){
		super(amount,tenureInYears);
	}
	
	public double getDueAmount(){
		amountDue=amount*tenureInYears*(100+rate)/100;
		return amountDue;
	}

	public void showLoanDetails(){
		System.out.println(loanName);
		System.out.print("Issue Date: ");
		System.out.println(startDate);
		System.out.print("End Date: ");
		System.out.println(endDate);
		System.out.print("Due amount on End Date is: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}
}

	
public class Bank {
	public static void main (String[] args) throws IOException{
		System.out.println("===============================");
		System.out.println("Welcome to the Bank of Westeros");
		System.out.println("===============================");
		Accounts dir = Accounts.createAccountsDirectry();

		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		int input;
		do{
		showUserMenu();
		input=Integer.parseInt(br.readLine());
		}while(input!=8);
		
	}

	public static void showAdminMenu(){
		System.out.println();
		System.out.println("Press 1 to check total money in bank");
		System.out.println("Press 2 to check total amount of loans leased");
		System.out.println("Press 3 to show all frozen Accounts");
		System.out.println("Press 4 to grant interest to all Accounts");
		System.out.println("Press 5 to change interest rate");
		System.out.println("Press 6 to change loan rate");
		System.out.println("Press 7 for other activities");
		System.out.println("Press 8 to exit");
	}
	public static void showUserMenu(){
		System.out.println();
		System.out.println("Press 1 to deposit money");
		System.out.println("Press 2 to withdraw money");
		System.out.println("Press 3 to check account balance");
		System.out.println("Press 4 to open a new account");
		System.out.println("Press 5 to apply for a loan");
		System.out.println("Press 6 to make a fixed deposit");
		System.out.println("Press 7 for other activities");
		System.out.println("Press 8 to exit");
		
	}

}
