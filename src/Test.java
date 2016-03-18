import java.io.*;
import java.util.Date;

class Account{
	private String name;
	private Double balance;
	private Integer accountNumber;
	private String password;
	private String status;
	private Loan[] loanList;
	private static int loanCount=0;
	private static int nextAccountNumber=16000;
	public static final double interest = 4.00;
	
	private Account(String name, Double balance, String password){
		this.name=name;
		this.balance=balance;
		accountNumber=nextAccountNumber++;
		this.password=password;
		if(balance>=0) status= "active";
		loanList= new Loan[10];

	}
	
	public void showAccountDetails(){
		System.out.print("Name: ");
		System.out.println(this.getName());
		System.out.print("Account Number: ");
		System.out.print(this.getAccountNumber());
		System.out.print("Current Balance: ");
		System.out.println(this.getAccountBalance());
		System.out.print("Account Status: ");
		System.out.println(this.getStatus());
		showAllLoanDetails();
	}

	public void showAllLoanDetails(){
		System.out.print("Total Loans Issued are: ");
		System.out.println(loanList.length);
		for(Loan loan : loanList){
			if(loan.status=="active") loan.showLoanDetails();
		}
	}

	public void payLoanDueAmount(Integer loanID){
		for(Loan loan : loanList){
			if ( loan.getLoanID() == loanID ){
				if(loan.getDueAmount()<balance){
					System.out.println("Sorry! Not enough balance in your account");
				}
				else {
					balance-=loan.getDueAmount();
					loan.status="inactive";
				}
			}
		}
	}

	public void deposit(int amount){
		double temp=balance;
		balance+=amount;
		if(temp<1000) {
			System.out.println("Rs. 100 is deducted as your account balance was below \"Minimum Balance\" i.e Rs. 1000, before this transaction.");
			balance-=100;
		}
		System.out.println("Your updated balance is: " + balance.toString());
		if(balance>=0) status="active";
		else status="frozen";
	}
	
	public void withdraw(int amount){
		if(amount<=balance+1000){
			balance-=amount;
			System.out.println("Your updated balance is: " + balance.toString());
			if(balance<0) status="frozen";
		}
		else{
			System.out.println("You donot have enough balance to withdraw");
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatus(){
		return status;
	}
	
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public Double getAccountBalance(){
		return balance;
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
		Double amount;
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
				System.out.println("How much money would you like to deposit now? Minimum amount to open an account is Rs.1000");
				amount = Double.parseDouble(br.readLine());
				if(amount<1000) System.out.println("Sorry we cant open an amount with amount " + amount.toString());
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
	private int loanID;
	private static int loanIssueNumber=1;
	public Loan(int amount,byte tenureInYears){
		this.amount=amount;
		this.tenureInYears=tenureInYears;
		startDate = new Date();
		endDate = new Date();
		endDate.setYear(startDate.getYear()+tenureInYears);
		status="active";
		loanID=loanIssueNumber++;
	}
	abstract public double getDueAmount();
	abstract public void showLoanDetails();
	abstract public double getLoanRate();

	public double getLoanAmount(){
		return amount;
	}

	public Date getStartDate(){
		return startDate;
	}

	public Date getEndDate(){
		return endDate;
	}
	
	public int getLoanID(){
		return loanID;
	}
}

class EducationLoan extends Loan{
	public static String loanName = "\"Bright Future Education Loan\"";
	public static double rate = 10.0;
	private double amountDue;

	public EducationLoan(int amount, byte tenureInYears){
		super(amount,tenureInYears);
		amountDue=amount*tenureInYears*(100+rate)/100.0;
	}
	
	public void showLoanDetails(){
		System.out.print("Loan Name: ");
		System.out.println(getLoanName());
		System.out.print("Loan ID: ");
		System.out.println(getLoanID());
		System.out.print("Loan Amount: ");
		System.out.println(getLoanAmount());
		System.out.print("Loan Start Date: ");
		System.out.println(getStartDate());
		System.out.print("End Date: ");
		System.out.println(getEndDate());
		System.out.print("Due Amount: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}

	public String getLoanName(){
		return loanName;
	}

	public double getDueAmount(){
		return amountDue;
	}
}

class HomeLoan extends Loan{
	public static String loanName = "Secure life Home loans";
	public static double rate = 15.0;
	private double amountDue;
	
	public HomeLoan(int amount, byte tenureInYears){
		super(amount,tenureInYears);
		amountDue=amount*tenureInYears*(100+rate)/100;
	}

	public void showLoanDetails(){
		System.out.print("Loan Name: ");
		System.out.println(getLoanName());
		System.out.print("Loan ID: ");
		System.out.println(getLoanID());
		System.out.print("Loan Amount: ");
		System.out.println(getLoanAmount());
		System.out.print("Loan Start Date: ");
		System.out.println(getStartDate());
		System.out.print("End Date: ");
		System.out.println(getEndDate());
		System.out.print("Due Amount: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}

	public String getLoanName(){
		return loanName;
	}

	public double getDueAmount(){
		return amountDue;
	}
}

class User{
	public String name;
	public String userName;
	public String password;
}

class Admin extends User{

}

class Customer extends User{

}

class Bank{
	
}
	
public class Test {
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
