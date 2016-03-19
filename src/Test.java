import java.io.*;
import java.util.Date;

class Account{
	private Double balance;
	private Integer accountNumber;
	private String status;
	private static int nextAccountNumber=16000;
	public static final double interest = 4.00;
	
	private Account(Double balance){
		this.balance=balance;
		accountNumber=nextAccountNumber++;
		if(balance>=0) status= "active";
	}
	
	public void showAccountDetails(){
		System.out.print("Account Number: ");
		System.out.print(this.getAccountNumber());
		System.out.print("Current Balance: ");
		System.out.println(this.getAccountBalance());
		System.out.print("Account Status: ");
		System.out.println(this.getStatus());
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
	
	public String getStatus(){
		return status;
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public Double getAccountBalance(){
		return balance;
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

	public User(String name, String userName, String password){
		this.name=name;
		this.userName=userName;
		this.password=password;
	}
}

class Admin extends User{
	Bank bank;
	public Admin(String name, String userName, String password){
		super(name,userName,password);
	}
}

class Customer extends User{
	private ArrayList<Account> accounts = new ArrayList<Account>();
	private ArrayList<Loan> loanList = new ArrayList<Loan>();
	public Customer(String name, String userName, String password){
		super(name,userName,password);
	}

	public void showAllLoanDetails(){
		System.out.print("Total Loans Issued are: ");
		System.out.println(loanList.size());
		for(Loan loan : loanList){
			if(loan.status=="active") loan.showLoanDetails();
		}
	}

	public double getTotalLoanAmount(){
		double temp=0;
		for(int i=0;i<loanCount;i++){
			temp+=loanList[i].amount;
		}
		return temp;
	}

	public void openNewAccount() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		Double amount;
		// Input Amount
		do{
			try{
				System.out.println("How much money would you like to deposit now? Minimum amount to open an account is Rs.1000");
				amount = Double.parseDouble(br.readLine());
				if(amount<1000) System.out.println("Sorry we cant open an account with amount " + amount.toString());
				else {
					System.out.println("Okay! You will have "+ amount.toString() + " as opening balance"); 
					break;
				}
			}
			catch(NumberFormatException e){
				System.out.println("Please enter a number as an Amount");
			}
		}while(true);
		Account temp = new Account(amount);
		if(account.add(temp)) {
			System.out.println("Account created.");
			temp.showAccountDetails();
		}
	}	

	public void issueNewLoan(){
		if (loanList.size()>10) {
			System.out.println("Sorry! You cannot have more than 10 loans");
			return;
		}
		
		String type;
		Integer amount;
		Double tenureInYears;
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		do{
			System.out.println("Enter Type of your loan:\nEnter \"HomeLoan\" for Home Loan\nEnter \"EducationLoan\" for Education Loan");
			type = new String(br.readLine());
			if(type.equals("HomeLoan") || type.equals("EducationLoan")) break;
			else{
				System.out.println("Please enter one of the two mentioned types.\n Note: Enter type without quotes.")
			}
		}while(true);
		do{
			System.out.println("Enter loan amount");
			amount = Integer.parseInt(br.readLine());
			if(this.getTotalLoanAmount()+amount>1000000){
			System.out.println("Sorry! We donot lend more than one million rupees per customer. Try a small amount.");
			}
			else{
				break;
			}
		}while(true);

		do{
			System.out.println("Enter loan duration in years");
			tenureInYears = Double.parseDouble(br.readLine());
			if(tenureInYears<=0){
			System.out.println("Sorry! duration cannot be 0");
			}
			else{
				break;
			}
		}while(true);


		if(type=="HomeLoan") {
			HomeLoan temp = new HomeLoan(amount,tenureInYears);
			if(loanList.add(temp)){
				System.out.println("Your loan is issued");
				temp.showLoanDetails();
				break;
			}
		}
		if(type=="EducationLoan") {
			EducationLoan temp = new EducationLoan(amount,tenureInYears);
			if(loanList.add(temp)){
				System.out.println("Your loan is issued");
				temp.showLoanDetails();
				break;
			}
		}
	}

//	public void payLoanDueAmount(Integer loanID){
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
}

class Bank{
	private Admin admin;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private Integer totalMoneyWithBank=10000000;
	private Integer totalMoneyleased=0;
	private boolean bankStarted=flase;
	public static Bank bank;
	public static int customerID=0;

	private Bank(){
		admin = new Admin("Gopal Goel","admin","admin");
		admin.bank=this;
		bank=this;
		bankStarted=false;
		customers = new Customer[1000];
	}
	public static Bank getBank(){
		if(bankStarted==false){
			return new Bank();
		}
		else return bank;
	}

	public void addCustomers(){
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		String name;
		String userName;
		String password;

		//input name
		do{
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

		// input username
		do{
			System.out.println("Choose a username containing only letters.");
			userName= new String(br.readLine());
			if(userName.matches("[a-zA-Z]+")){
				if(checkUsername(userName)){
					System.out.println("Username already exists. Please Choose another.");
				}
				else{
					System.out.println("Your username is: "+ name);
					break;
				}
			}
			else{
				System.out.println("Invalid username.");
			}
		}while(true);

		// input password
		do{
			System.out.println("Choose a password.");
			password= new String(br.readLine());
			System.out.println("Type your chosen password again.");
			String temp= new String(br.readLine());
			if(password.equals(temp)){
				System.out.println("Password chosen succesfully.");
				break;
			}
			else{
				System.out.println("Passwords didn't match.");
			}
		}while(true);

		Customer newCustomer = new Customer(name,userName,password);
		if(customers.add(newCustomer)) System.out.println("You are now our customer.");

	}

	private Customer customerLogin(String userName, String password){
		Customer temp=null;
		for(int i=0;i<customers.size();i++){
			if(customers[i].userName.equals(userName) && customers[i].password.equals(password)) temp = customers[i];
		}
		return temp;
	}

	private boolean checkUsername(String userName){
		for(Customer customer : customers){
			if customer.username.equals(userName) return true;
		}
		return false;
	}

	private boolean checkPassword(String password){
		for(Customer customer : customers){
			if customer.password.equals(userName) return true;
		}
		return false;
	}
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


































