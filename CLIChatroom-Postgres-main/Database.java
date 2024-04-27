
import java.sql.*;

import java.util.Scanner;

public class Database {

static Connection c;

static Statement stmt;

static int id;
static int totalMessages;
static int printFrom;
public static void main(String[] args){



try {

Class.forName("org.postgresql.Driver");

c = DriverManager.getConnection() 

//info removed for privacy 

}

catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);

}

initialPage();

}



public static void initialPage() {
System.out.println( "Welcome to ChatterMates.");

System.out.println( "Choose one of the following: (L)ogin, (R)egister, (Q)uit.");
System.out.println("---------------------------------------------------------");
Scanner input = new Scanner(System.in);

String userInput = input.nextLine().toLowerCase();
int state = 1;

if(userInput.equals("l")|| userInput.equals("login")) {
state = 0;
login();

}

if(userInput.equals("r")|| userInput.equals("register")) {
state = 0;
register();

}

if(userInput.equals("q") || userInput.equals("quit")) {
	
System.out.println("Goodbye.");

state = 0;

exit();

}

else {
if(state == 1) {
System.out.println("Sorry, input is not accepted. Please, try again.");
System.out.println("---------------------------------------------------------");
initialPage();
}
}

}

public static void login() {

try {

Scanner input = new Scanner(System.in);

String savedUser = "";

System.out.print("Username: ");

String usernameInput = input.next().toLowerCase();

stmt = c.createStatement();

ResultSet rs = stmt.executeQuery("select username from accounts;");
String savedUserFinal = " ";

while(rs.next()) {

savedUser = rs.getString("username").toLowerCase();

int userLength = usernameInput.length();

String savedUserTotal = savedUser.substring(0, userLength);

if(savedUserTotal.equals(usernameInput)) {
savedUserFinal = savedUserTotal;
}
}
if(savedUserFinal.equals(" ")) {
	
	System.out.println("Username does not exist. You can register an account with name by pressing 'R'");
	System.out.println("---------------------------------------------------------");
	initialPage();
}
stmt.close();
rs.close();

System.out.print("Password: ");

String passwordInput = input.next().toLowerCase();
String passwordUpper = passwordInput.toLowerCase();
String passCheckFinal =" ";
stmt = c.createStatement();

rs = stmt.executeQuery("select password from accounts where username = '" + usernameInput +"'");
while(rs.next()) {
passCheckFinal = rs.getString("password");
}

int passCheckLength = passwordUpper.length();
String passCheckSnip = passCheckFinal.substring(0,passCheckLength);

if(!passCheckSnip.equals(passwordUpper)) {

System.out.println("Incorrect Password, Please try again.");
System.out.println("---------------------------------------------------------");
System.out.print("Password: ");

passwordInput = input.next().toLowerCase();
passwordUpper = passwordInput.toLowerCase();
}

loggedIn(usernameInput);

}catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);

}

}

public static void loggedIn(String username) {
	try {


Scanner input = new Scanner(System.in);


System.out.println("Welcome " + username + " to Chatter.");

System.out.println("Select one of the following commands: ");
System.out.println("---------------------------------------------------------");
System.out.println("/l to see a list of all available chatrooms, /j to join a chatroom, /c to create a chatroom,\n"
		+ "/a to access account settings, or /logout to return to the main menu.");

String userInput = input.nextLine();

String chatRoomName = " ";
if(userInput.equals("/l") || userInput.equals("/list")) {
	System.out.println("You can join any of the following by using /j");
	stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("select chatname from chatrooms;");
	while(rs.next()) {
		String chatCheck = rs.getString("chatname");
	System.out.println(chatCheck);
	}
	System.out.println("---------------------------------------------------------");
	System.out.println();
	loggedIn(username);
}

if(userInput.contains("/a")) {
	accountSettings(username);
}

if(userInput.contains("/account")) {
	accountSettings(username);
}

if(userInput.contains("/j ")){

chatRoomName = userInput.substring(3);


}

if(userInput.contains("/join ")) {

chatRoomName = userInput.substring(5);

}

if(userInput.contains("/c ")) {

chatRoomName = userInput.substring(3);

}

if(userInput.contains("/create ")){

chatRoomName = userInput.substring(8);

}

if(userInput.contains("/logout")) {

initialPage();

}


if(userInput.contains("/j ") || userInput.contains("/join ")){
	stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("select chatname from chatrooms");
	int checkLength = chatRoomName.length();
	int joinCheck = 1;
	while(rs.next()) {
		
		String chatRoomExists = rs.getString("chatname");
		String chatExistsCheck = chatRoomExists.substring(0, checkLength);
		String chatExistsCheckFinal = chatExistsCheck.toLowerCase();
		if(chatExistsCheckFinal.equals(chatRoomName)) {
			joinCheck = 0;
		
		System.out.println("Now connected to " + chatRoomName + ".");

		System.out.println("Welcome to " + chatRoomName);
		System.out.println("---------------------------------------------------------");
		System.out.println("You can use any of the following commands: /users to see who's connected, "

		+ "/history to see chat history, or /leave to disconnect");
		
		String usernameUpper = username.toLowerCase();
		String chatRoomUpper = chatRoomName.toLowerCase();
		stmt = c.createStatement();
		String sql = "INSERT INTO " + chatRoomUpper + "USERSIN(USERNAME) VALUES('" + usernameUpper + "');";
		String lowerSQL = sql.toLowerCase();
		stmt.execute(lowerSQL);

		chatRoom(username, chatRoomName);
	}
	}

if(joinCheck == 1) {
		System.out.println("Chat Room does not exist, please create it, or select another.");
		System.out.println();
		loggedIn(username);
}
}


if(userInput.contains("/c ") || userInput.contains("/create ")){
	if(userInput.length() > 20) {
		System.out.println("Chat name must be less than 20 characters. Please try again.");
		System.out.println("---------------------------------------------------------");
		loggedIn(username);
	}
	stmt = c.createStatement();
	int match = 0;
	int chatNameLength = chatRoomName.length();
	ResultSet rs = stmt.executeQuery("select chatname from chatrooms");
	while(rs.next()) {
		String chatNameCheck = rs.getString("chatname").substring(0, chatNameLength);
		String chatNameCheckFinal = chatNameCheck.toLowerCase();
		if(chatNameCheckFinal.equals(chatRoomName.toLowerCase())) {
			match = 1;
			break;
		}
		
	}

if(match == 0) {
		String sql = "INSERT INTO CHATROOMS(CHATNAME) " +

		"VALUES('" + chatRoomName + "');";

		stmt.executeLargeUpdate(sql);

System.out.println("Chat room created. Now connected to " + chatRoomName + ".");


System.out.println("Welcome to " + chatRoomName +".");
System.out.println("---------------------------------------------------------");
System.out.println("You can use any of the following commands: /users to see who's connected, \n"

+ "/history to see chat history, /help to see a list of commands or /leave to disconnect");
String chatRoomRegister = chatRoomName.toLowerCase();

sql = "CREATE TABLE " + chatRoomRegister + "HISTORY " +
"(USERNAME CHAR(20) PRIMARY KEY NOT NULL, CHATMESSAGE CHAR(50) NOT NULL)";

stmt.execute(sql);

sql = "CREATE TABLE " + chatRoomRegister + "USERSIN " +
"(USERNAME CHAR(20) PRIMARY KEY NOT NULL)";

stmt.execute(sql);

c.setAutoCommit(false);

stmt = c.createStatement();
sql = "INSERT INTO " + chatRoomName + "USERSIN(" +

"USERNAME) " +

"VALUES('" + username + "');";

stmt.execute(sql);

chatRoom(username, chatRoomName);

}


else {

System.out.println("Chat Room already exists, please try again.");
System.out.println("You can join that room by typing /j");
System.out.println("---------------------------------------------------------");
System.out.println();
loggedIn(username);

}
	}else {
		System.out.println("Invalid input. Please try again.");
		System.out.println("---------------------------------------------------------");
		System.out.println();
		loggedIn(username);
	}
	
	}catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);
	}

}




public static void chatRoom(String username, String chatName){
	
	try {

boolean connected = true;
Scanner input = new Scanner(System.in);

String userInput = input.nextLine().toLowerCase();


while(connected = true) {

if(userInput.equals("/leave")) {
stmt = c.createStatement();
String sql = "DELETE FROM " + chatName + "USERSIN WHERE USERNAME = '" + username + "';";
String sqlLow = sql.toLowerCase();
stmt.execute(sqlLow);
connected = false;
loggedIn(username);

}
if(userInput.contains("/help")) {
System.out.println("You can type any of the following commands: ");
System.out.println("/users to see who's connected");
System.out.println("/history to see chat history");
System.out.println("/leave to disconnect and return to the main menu");

break;
}

if(userInput.contains("/history")) {

	stmt = c.createStatement();
	
	ResultSet rs = stmt.executeQuery("select * from " + chatName + "history;");
	
	
	while(rs.next()) {
		String whoSaid = rs.getString("username");
		int userStopPoint = whoSaid.indexOf(" ");
		String userSaid = whoSaid.substring(0,userStopPoint -1);
		String chatMessage = rs.getString("chatmessage");
		
		System.out.println(userSaid + ": " + chatMessage);
	}
	
break;
}

if(userInput.contains("/users")){
stmt = c.createStatement();
String chatLower = chatName.toLowerCase();

ResultSet rs = stmt.executeQuery("SELECT USERNAME FROM " + chatLower + "USERSIN;");	
while(rs.next()) {
	String printUser = rs.getString("username");
	System.out.println(printUser);
}

break;
}

else {
	id ++;
	totalMessages++;
	
stmt = c.createStatement();

String storeChat = "INSERT INTO " + chatName +"HISTORY(USERNAME,CHATMESSAGE) " +
"VALUES('" + username + id + "', '" + userInput + "');";

String storeToPrint = "INSERT INTO CHATMESSAGES(ID,NAME,MESSAGE) VALUES("+ totalMessages + ", '" + username + "', '"
+ userInput +"');";


stmt.execute(storeToPrint.toLowerCase());
stmt.execute(storeChat.toLowerCase());
	}	


	stmt = c.createStatement();
	ResultSet chatPrint = stmt.executeQuery("select * from chatmessages");
	while(chatPrint.next()) {
		
	if(chatPrint.getInt("ID") > printFrom) {
	String printUser = chatPrint.getString("name");
	String printChat = chatPrint.getString("message");
	int userShort = username.length();
	String printUserShort = printUser.substring(0, userShort);
	System.out.print(printUserShort + ": " + printChat);
	printFrom = totalMessages;
	}
	
	}
	System.out.println();
	chatRoom(username, chatName);
	
break;
	
	}
	

chatRoom(username, chatName);
	}
catch(Exception e) {
e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);
	}
}
	

public static void exit() {

System.exit(0);

}

public static void accountSettings(String username) {
	try {
	Scanner input = new Scanner(System.in);
	
	System.out.println("Select one of the following: /username to change usernames,  /password to change passwords,\n "
			+ "/delete to delete your account, or /back to return to the main menu.");
	System.out.println("---------------------------------------------------------");
	
	String userInput = input.next().toLowerCase();
	
	if(userInput.contains("/password")) {
		System.out.print("Please enter your new password: ");
		String newPass = input.next().toLowerCase();
		
	stmt = c.createStatement();
	stmt.execute("delete from accounts where username = '" + username + "';");
	String newUser = username.toLowerCase();
	
		System.out.println("Password Changed.");
	
	createAccount(newUser, newPass);
		
	}
	
	if(userInput.contains("/username")) {
		
		String passwordSave = " ";
		stmt = c.createStatement();

		ResultSet rs = stmt.executeQuery("select password from accounts where username = '" + username +"'");
		while(rs.next()) {
		passwordSave = rs.getString("password");
		}
		
		System.out.println("Please select a new Username: ");
		String newUser = input.next();
		stmt = c.createStatement();
		stmt.execute("delete from accounts where username = '" + username + "';");
		 newUser = newUser.toLowerCase();
		createAccount(newUser, passwordSave);
			initialPage();
	}
	
	if(userInput.contains("/delete")) {
		System.out.println("This cannot be undone, Are you sure? [Y]es [N]o");
		userInput = input.next().toLowerCase();
		if(userInput.contains("y") || userInput.contains("yes")){
			stmt = c.createStatement();
			stmt.execute("delete from accounts where username = '" + username + "';");
			stmt.execute("delete from chatmessages where name = '" + username + "';");
			initialPage();
		}
		if(userInput.equals("n") || userInput.equals("no")) {
			accountSettings(username);
		}
		
	}
	
	if(userInput.contains("/back")) {
		loggedIn(username);
	}else {
		System.out.println("Invalid Input. Please try again.");
		accountSettings(username);
	}
	}catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);
	}

}

public static void register() {

try {
	

Scanner input = new Scanner(System.in);


System.out.println("Username: ");
String usernameInput = input.next().toLowerCase();
if(usernameInput.length() > 20) {
	System.out.println("Username cannot be longer than 20 characters, please try again.");
	register();
}

stmt = c.createStatement();

ResultSet rs2 = stmt.executeQuery("select username from accounts;");

while(rs2.next()) {
int checkLength = usernameInput.length();
String userList = rs2.getString("username").toLowerCase();
String userCheck = userList.substring(0,checkLength);

if(userCheck.equals(usernameInput)) {

System.out.println("Username already exists, please select another.");

register();

}

}

System.out.println("Username Available, please select a password");

System.out.print("Password: ");

String passwordInput = input.next().toLowerCase();

System.out.println("Congratulations, you're account has been registered.");
createAccount(usernameInput, passwordInput);




}catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);

}
}

public static void createAccount(String usernameInput, String passwordInput){
	try {
c.setAutoCommit(true);

stmt = c.createStatement();
String sql = "INSERT INTO ACCOUNTS(USERNAME,PASSWORD,CURRENT_ROOM) VALUES('" + usernameInput + "', '" + passwordInput + "', 'NONE');";
stmt.executeUpdate(sql);
stmt.close();

initialPage();
		
	}catch(Exception e) {

e.printStackTrace();

System.err.println(e.getClass().getName()+": " + e.getMessage());

System.exit(0);

}

}

}

