import java.io.*;

class Account{
	private String name;
	private Integer balance;
	private Integer accountNumber;
	private String password;
	private String status;
	private static int nextAccountNumber=16000;
	
	private Account(String name, int balance, String password){
		this.name=name;
		this.balance=balance;
		accountNumber=nextAccountNumber++;
		this.password=password;
		if(balance>=0) status= "active";
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
	
	
	public void showBalance(){
		System.out.println("Your Balance is" + balance.toString());
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


	
public class Bank {
	public static void main (String[] args) throws IOException{
		System.out.println("===============================");
		System.out.println("Welcome to the Bank of Westeros");
		System.out.println("===============================");
		Account a1 = Account.openNewAccount();
/*
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in)); 
		do{
		showUserMenu();
		int input=Integer.parseInt(br.readLine());
		}while(input!=8);
*/		
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
