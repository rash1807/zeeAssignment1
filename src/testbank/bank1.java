package testbank;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class bank1 {
	
	public static void createAcc(String user,String password)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter account number");
		String accnt=sc.next();
		System.out.println("Enter balance");
		int balance=sc.nextInt();
		
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
			PreparedStatement ps1 = con.prepareStatement("update register set accnt=? where username=?");
			ps1.setString(1,accnt );
			ps1.setString(2, user);
			ps1.executeUpdate();
			
			PreparedStatement ps2 = con.prepareStatement("update register set balance=? where username=?");
			ps2.setInt(1, balance);
			ps2.setString(2, user);
      		ps2.executeUpdate();
      		
      		
			System.out.println("Data updated");
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		
	}
	public static void displayDetails()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter account number: ");
		String acc=sc.next();
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
			PreparedStatement ps=con.prepareStatement("select * from register where accnt=?");
			ps.setString(1, acc);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString(1)+":"+rs.getInt(2)+":"+ rs.getInt(3)+":"+rs.getInt(4)+":"+rs.getString(5));
			}
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	public static void transferMoney() {
		
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter sender's account no");
		String send1=sc.next();
		
		System.out.println("Enter receiver's account no");
		String rece1=sc.next();
		
		System.out.println("Enter amount to transfer");
		int amtt=sc.nextInt();
		
		int x=0,x1=0;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
		PreparedStatement ps = con.prepareStatement("select * from register where accnt=?");
		
		ps.setString(1, send1);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			x=rs.getInt(5);
		
		//System.out.println(x);
		
		PreparedStatement ps2 = con.prepareStatement("select * from register where accnt=?");
		ps2.setString(1, rece1);
		ResultSet rs1=ps2.executeQuery();
		while(rs1.next())
		x1=rs1.getInt(5);
		
		if(x>=amtt)                         //"update bankAcc set money=? where name=name1"
		{
			x=x-amtt;
			PreparedStatement ps1 = con.prepareStatement("update register set balance=? where accnt=?");
			ps1.setInt(1, x);
			ps1.setString(2, send1);
			ps1.execute();
			
			
			
			PreparedStatement ps3 = con.prepareStatement("update register set balance=? where accnt=?");
			ps3.setInt(1, x1+amtt );
			ps3.setString(2, rece1);
			ps3.execute();
			System.out.println("Updated");
			
		}
		//Scanner sc = new Scanner(System.in);
		else
		{
			System.out.println("not enough balance");
		}
		
			con.close();
		
		}
		catch(Exception e)
		{
		System.out.println(e);
		}
		finally
		{
		System.out.println("donee");
		}
	}
	public static void login()
	{
		int found=0,ch1;
		String usrname;
		String phoneno;
		String pass;
		Connection con=null;
		Scanner sc1=new Scanner(System.in);
		System.out.println("Enter username: ");
		usrname=sc1.next();
		
		System.out.println("Enter password: ");
		String pass1=sc1.next();
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
			Statement smt=con.createStatement();
			ResultSet rs=smt.executeQuery("select * from  register");
			while(rs.next())
			{
				if(rs.getString(1).equals(usrname) && rs.getString(2).equals(pass1))
				{
					System.out.println("Logged in successfully");
					found=1;
					break;
				}
				
			}
			
		}
			catch(Exception e)
			{
			System.out.println(e);
			}
			if(found==0)
			{
				System.out.println("user not registered!");
				register();
			}
			
			
			do{
				System.out.println("--------------------------------");
				System.out.println("1.create");
				System.out.println("2.Display details");
				System.out.println("3. transfer money");
				System.out.println("4.Exit");
				System.out.println("--------------------------------");
				System.out.println("Enter your Choice: ");
				//Scanner sc1=new Scanner(System.in);
				ch1=sc1.nextInt();

					if(ch1==1){
					createAcc(usrname,pass1);
					}
					
				     if(ch1==2)
				     displayDetails();
				     
				     if(ch1==3)
				      transferMoney();
				
			
	}while(ch1!=4);
	}
	
	public static void register()
	{
		Connection con=null;
		String usrname;
		String phoneno;
		String pass;
		Scanner sc1=new Scanner(System.in);
		System.out.println("Enter name: ");
		usrname=sc1.next();
		System.out.println("Enter password: ");
		pass=sc1.next();
		System.out.println("Enter phone ");
		phoneno=sc1.next();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
			PreparedStatement ps = con.prepareStatement("insert into register values(?,?,?)");
			//Scanner sc = new Scanner(System.in);
			ps.setString(1,usrname);
			ps.setString(2,pass);
			ps.setString(3,phoneno);
			// //1ps.setString(6,pan);
			ps.execute();
			System.out.println("registration successful! ");
			login();
			}
			catch(Exception e)
			{
			System.out.println(e);
			}
	}

	
	public static void main(String[] args) throws SQLException {

		Scanner sc=new Scanner(System.in);
		System.out.println("1)Login\t2)Register");
		int ch1=sc.nextInt();
		int found=0;

		if(ch1==1)
		{
		login();
		}
		if(ch1==2) {
		register();
		}
	}
}
