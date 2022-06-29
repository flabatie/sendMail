package fr.ans.qm;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

public class SendAnEmail {
	
/*	 private static List<String> resultSetArray=new ArrayList<>();
	 private static String username ="root";     //Enter DB Username
	 private static String password = "root";    //Enter DB password
	 private static  String url = "localhost";         //Enter DB URL

	
	 
	 private static void fetchDataFromDatabase(String selectQuery,Connection connection) throws  Exception{
         try {


             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery);
             int numCols = rs.getMetaData().getColumnCount();

             while(rs.next()) {
                 StringBuilder sb = new StringBuilder();

                 for (int i = 1; i <= numCols; i++) {
                     sb.append(String.format(String.valueOf(rs.getString(i))) + " ");

                 }
                 resultSetArray.add(sb.toString());

             }

         } catch (SQLException e) {
             System.out.println("Sql exception " + e.getMessage());
         }

     }
	 
	 public static void printToCsv(List<String> resultArray) throws Exception{

	        File csvOutputFile = new File("C:\\temp\\Export.csv");
	        FileWriter fileWriter = new FileWriter(csvOutputFile, false);


	        for(String mapping : resultArray) {
	            fileWriter.write(mapping + "\n");
	         }

	        fileWriter.close();

	    }

	 public static void main(String args[]) throws Exception{
		    Connection connection=DriverManager.getConnection(url,username,password);   
	        fetchDataFromDatabase("SQL queries", connection);
	        printToCsv(resultSetArray);                

	 }
}*/
	 
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws AddressException, MessagingException {
	      
	      
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
     // Get a Properties object
        Properties props = System.getProperties();
		
	    props.setProperty("proxySet","true");
	    //props.setProperty("socksProxyHost","10.0.49.163");
	    //props.setProperty("socksProxyPort","3128");
	    props.setProperty("mail.smtp.proxy.port","3128"); // ok sur vm windows & poste si samu 
	    //props.setProperty("mail.smtp.proxy.host","10.0.49.163"); // ok  sur vm windows
	    props.setProperty("mail.smtp.proxy.host","10.43.49.200");       // poste si samu 
        props.setProperty("mail.smtp.host", "smtp.office365.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.socketFactory.port", "587");
        props.setProperty("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "tnra.sisamu@esante.si-samu.fr";
        final String password = "TestS0t0&22";
        Session session = Session.getDefaultInstance(props,new Authenticator(){
                                protected PasswordAuthentication getPasswordAuthentication() {
                                   return new PasswordAuthentication(username, password);
                                }});
     
      // -- Create a new message --
        Message msg = new MimeMessage(session);
        
     // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("tnra.sisamu@esante.si-samu.fr"));
        msg.setRecipients(Message.RecipientType.TO, 
                         InternetAddress.parse("fanny.labatie@gmail.com",false));
        msg.setSubject("Hello");
        msg.setText("How are you");
        msg.setSentDate(new Date());
        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        String file = "C:\\temp\\Export.csv"; // Correspond au chemin du fichier que je renseigne dans ma form
        String fileName = "Export.csv";
        FileDataSource source = new FileDataSource(file);      
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("Message sent.");
     }
}

