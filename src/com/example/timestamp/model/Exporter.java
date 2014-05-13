/**  Copyright (c) 2014, Group D in course TNM082
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the {organization} nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

package com.example.timestamp.model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;
import com.example.timestamp.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Exporter extends AsyncTask <Void, Void, Void>{
	
	String csvFile = "mycsv.csv";
	BufferedReader br;
	
	Context context;
	private Activity A;
	
	private Session session;
	private boolean isStatic;
	private boolean isConnected;
	private boolean CC;
	
	private ArrayList<TimePost> exportList;
	private CheckBox ccBox;
	private EditText emailTo;
	private String sendTo;
	
	public Exporter(String message, ArrayList<TimePost> list, Activity a){
		A = a;
		context = a;
		exportList = list;
		
		//Checks for internet connection
		ConnectivityManager cm = (ConnectivityManager)A.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
		
		
		//Setup dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(A);
		
		builder.setTitle(message);
		
		//create custom view to alert dialog
		View view = (View) A.getLayoutInflater().inflate(R.layout.export_alert_dialog, null);
		builder.setView(view);
		
		//get the items we want to extract information from;
		ccBox = (CheckBox) view.findViewById(R.id.alertExportCC);
		ccBox.setChecked(SettingsManager.getExportToggleCC(A));
		emailTo = (EditText) view.findViewById(R.id.alertReceiverEmail);
		emailTo.setText(SettingsManager.getExportEmailAddress(A));
		
		builder.setPositiveButton("Send with token", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // Skicka in rapport (tas till redigera vyn?)
	        	   
	    		if(!isConnected){
		    		AlertDialog.Builder builder = new AlertDialog.Builder(A);
					builder.setTitle("Cannot send report, check for internet connection");
					
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				           }
				    });
						
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
	        	}
	        	else{
	        		isStatic = false;
	        		CC = ccBox.isChecked();
	        		if(CC != SettingsManager.getExportToggleCC(A))
	        			SettingsManager.setExportToggleCC(CC, A);
	        		sendTo = emailTo.getText().toString();
	        		if(!sendTo.equals(SettingsManager.getExportEmailAddress(A)));
	        			SettingsManager.setExportEmailAddress(sendTo, A);
	        		execute();
	        	}
	        }
	           
		})
		.setNeutralButton("Send with static", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   
	        	if(!isConnected){
		    		AlertDialog.Builder builder = new AlertDialog.Builder(A);
					builder.setTitle("Cannot send report, check for internet connection");
					
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				           }
				    });
						
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
	        	}
	        	else{
	        		isStatic = true;
	        		CC = ccBox.isChecked();
	        		execute();
	        	}
	           }
	    })
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub	
			}
		});
		AlertDialog alertDialog = builder.create();
		
		alertDialog.show();
	}
	
	public void createCSV(Context c, ArrayList<TimePost> tplist){
		try{

			DB db = new DB(c);
			
			FileOutputStream fOut = c.openFileOutput(csvFile, Context.MODE_WORLD_WRITEABLE);
		    OutputStreamWriter writer = new OutputStreamWriter(fOut); 			
			String output="";		
			
			//CSV header 
			output += "Project";
			output += ',';
			output += "Start time";
			output += ',';
			output += "End time";
			output += ',';
			output += "Comment";
			output += '\n';
			
			
			//Write all timepost to csv output string.
			for (TimePost temp : tplist){
				output += db.getProject(temp.projectId).getName();
				output += ',';
				output += temp.getStartTime();
				output += ',';
				output += temp.getEndTime();
				output += ',';
				output += temp.getComment();
				output += '\n';
				
			}
			
			writer.write(output);
		    writer.close();
		    
			
		}catch(IOException e){
			e.printStackTrace();
			Log.d("hej","CSVcreate: write fail");
		}
		
		
	}

	//Function for debugging CSV file.
	public void readCSV(Context c){
		try{
			FileInputStream in = c.openFileInput(csvFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));


			String line;
			StringBuilder out = new StringBuilder();
			
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        } 
			
			reader.close();
		}
		catch(IOException e){
			Log.d("hej", "readCSV: fail");
			e.printStackTrace();
		}
	}

//Jonas part
	
	@Deprecated
	public void exportToEmail(Activity A){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"timestampnoreply@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "Testing 1, 2\n testing testing");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
		    A.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException e) {
		    Toast.makeText(A, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void exportJavaMail() throws AddressException, MessagingException, UnsupportedEncodingException{
		String emailID=new String();
		//Pattern emailPattern=Patterns.EMAIL_ADDRESS;
		Account []accounts=AccountManager.get(A).getAccountsByType("com.google");
		Account theAccount = null;
		
		for(Account account:accounts)
		{
			//need to implement multiple accounts handling..
			emailID=account.name;
			theAccount = account;
		}
		
		AccountManagerFuture<Bundle> acquiredToken = AccountManager.get(A).getAuthToken(theAccount, "oauth2:https://mail.google.com/", null, A, null, null);
		
		try{
			String token = acquiredToken.getResult().getString(AccountManager.KEY_AUTHTOKEN);
			Log.d("Export", token);
			Log.d("Export", emailID);
			
			String title = "TimeStamp export week " + new GregorianCalendar().get(Calendar.WEEK_OF_YEAR)+":";
			String body = "This is an automaticaly generated email from the TimeStamp application \n"
						+ "Below you will see the attatched .csv file containing your worked time";
			//adding csv file
			File dir = context.getFilesDir();
			File file = new File( dir.getAbsolutePath() + "/mycsv.csv");
			sendMail(title, body, file, emailID, token, sendTo, CC);
			
		}catch(IOException e){
			Log.d("Export", "Send mail: file not found");
		}catch(Exception e){
			Log.d("Export", e.getMessage());
		}
		//invalidate, if necessary?
		//AccountManager.get(A).invalidateAuthToken("com.google", token);
		//AccountManager.get(A).getAuthToken(account, "oauth2:https://mail.google.com/", null, A, new OnTokenAcquired(), null);
	}
	
	
	private void exportJavaMailStatic() throws AddressException, MessagingException, UnsupportedEncodingException{
		String host = "smtp.gmail.com";
		String address = "timestampnoreply@gmail.com";
		
		String from = "timestampnoreply@gmail.com";
		String pass = "timestamp123";
		String to="timestampnoreply@gmail.com";
		

		String finalString="";
		
		// Initiate setup for mail.
		Properties props = System.getProperties();
		
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "465");
		props.put("mail.debug", "false");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(props, null);
		
		//Build the mail structure
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		
		MimeBodyPart messageBodyPart = new MimeBodyPart(); 
		String bodyPart = "This is an automaticaly generated email from the TimeStamp application \n"
						+ "Below you will see the attatched .csv file containing your worked time";
		messageBodyPart.setText(bodyPart);
		
		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart(); 
               
		//adding csv file
		try{
			File dir = context.getFilesDir();
			File file = new File( dir.getAbsolutePath() + "/mycsv.csv");
			
			messageBodyPart.attachFile(file);
			messageBodyPart.setFileName("Tidsrapport.csv");
		    multiPart.addBodyPart(messageBodyPart);
		    
		}catch(IOException e){
			Log.d("Export", "Send mail: file not found");
		}
		
		
		InternetAddress toAddress = new InternetAddress(to);
				
		message.setSubject("Tidsrapport för vecka " + new GregorianCalendar().get(Calendar.WEEK_OF_YEAR)+":");
		message.setContent(multiPart);
		//message.setText("Demo For Sending Mail in Android Automatically");
		message.addRecipient(Message.RecipientType.TO, toAddress);
		
		Log.i("Check", "transport");
		Transport transport = session.getTransport("smtp");
		transport.connect(host, address, pass);

		Log.i("Check", "wana send");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
		Log.i("Check", "sent");
	}
	
	
	

	@Override
	protected Void doInBackground(Void... params) {
		try {
			createCSV(context, exportList);
			if(isStatic)
				exportJavaMailStatic();
			else
				exportJavaMail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}





    //... senast test
    public SMTPTransport connectToSmtp(String host, int port, String userEmail,
            String oauthToken, boolean debug) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.sasl.enable", "false");
        props.put("mail.debug", debug);
        session = Session.getInstance(props);
        session.setDebug(debug);


        final URLName unusedUrlName = null;
        SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
        // If the password is non-null, SMTP tries to do AUTH LOGIN.
        final String emptyPassword = null;
        transport.connect(host, port, userEmail, emptyPassword);

                byte[] response = String.format("user=%s\1auth=Bearer %s\1\1", userEmail,
                oauthToken).getBytes();
        response = BASE64EncoderStream.encode(response);

        transport.issueCommand("AUTH XOAUTH2 " + new String(response), 235);

        return transport;
    }

    public synchronized void sendMail(String subject, String body, File attatchedFile, 
    		String user, String oauthToken, String recipients, boolean ccToUser) {
        try {

            SMTPTransport smtpTransport = connectToSmtp("smtp.gmail.com",
                    587, user, oauthToken, false);
            
            //Allow exception to be thrown upon success
            smtpTransport.setReportSuccess(true);
            
            //Create message
            MimeMessage message = new MimeMessage(session);
            		message.setSender(new InternetAddress(user));
            		message.setSubject(subject); 
            //Message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            		messageBodyPart.setText(body);
            //Multipart container to construct message content
    		Multipart multiPart = new MimeMultipart();
    				multiPart.addBodyPart(messageBodyPart);
    		//Message attachment
    		messageBodyPart = new MimeBodyPart();		
    				messageBodyPart.attachFile(attatchedFile);
    				messageBodyPart.setFileName("Tidsrapport.csv");
    				
    				multiPart.addBodyPart(messageBodyPart);
    				message.setContent(multiPart);
    		if(ccToUser)
    			message.setRecipient(Message.RecipientType.BCC, new InternetAddress(user));		    
        		    
            if (recipients.indexOf(',') > 0)   
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));   
            else  
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));   
            smtpTransport.sendMessage(message, message.getAllRecipients());   


        } 
        //Was the mail delivered?
        catch (SMTPSendFailedException e) {
            //Log.d("Export", e.getMessage());
            //Log.d("Export", e.getNextException().getClass().toString());
            
        	//is it a succeed exception?
        	if(e.getNextException().getClass() == SMTPAddressSucceededException.class){
	            A.runOnUiThread(new Runnable() {
	                public void run() {
	                    Toast.makeText(A, "Email sent", Toast.LENGTH_SHORT).show();
	                    Log.i("Export", "Sent");
	                }
	            });
            }
        }
        //Other exceptions
        catch (MessagingException e) {
            Log.d("Export", e.getMessage());
        } catch (IOException e) {
			e.printStackTrace();
		}    
    }
}
