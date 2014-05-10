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

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.*;
import javax.activation.*;

import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Exporter extends AsyncTask <Void, Void, Void>{

	/*
	 * 
	 * No functions defined yet!
	 * 
	 */
	
	/*public void exportToGmail(){
		try {   
            GMailSender sender = new GMailSender("username@gmail.com", "password");
            sender.sendMail("This is Subject",   
                    "This is Body",   
                    "user@gmail.com",   
                    "user@yahoo.com");   
        } catch (Exception e) {   
            Log.e("SendMail", e.getMessage(), e);   
        } 
	}*/
	private Activity A;
	private Session session;
	
	public Exporter(Activity a){
		A = a;
	}
	
	public void exportToEmail(Activity A){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"oscjo729@student.liu.se"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "Testing 1, 2\n testing testing");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
		    A.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(A, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void exportJavaMail() throws AddressException, MessagingException, UnsupportedEncodingException{
		/*String host = "smtp.gmail.com";
		String address = "jonas.gorling@gmail.com";
		
		String from = "noReply@timestamp.com";
		String pass = "password";
		String to="jonas.gorling@gmail.com";
		
		Multipart multiPart;
		String finalString="";
		

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Log.i("Check", "done pops");
		
		//Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		*/	
		//testar mer
		String emailID=new String();
		//Pattern emailPattern=Patterns.EMAIL_ADDRESS;
		Account []accounts=AccountManager.get(A).getAccountsByType("com.google");
		AccountManagerFuture<Bundle> acquiredToken = null;
		Account theAccount = null;
		for(Account account:accounts)
		{
			//need to implement multiple accounts handling..
			emailID=account.name;
			theAccount = account;
		}
		acquiredToken = AccountManager.get(A).getAuthToken(theAccount, "oauth2:https://mail.google.com/", null, A, null, null);
		String token = null;
		try{
			token = acquiredToken.getResult().getString(AccountManager.KEY_AUTHTOKEN);
			Log.d("Export", token);
			Log.d("Export", emailID);
			
			String title = "TimeStamp export";
			String body = "Hello this is an automaticaly generated email from the TimeStamp application \n"
						+ "Below you will see the attatched .csv file containing your worked time";
			sendMail(title, body, emailID, token, emailID);
		}
		catch(Exception e){
			Log.d("Export", e.getMessage());
		}
		//invalidate, if necessary?
		//AccountManager.get(A).invalidateAuthToken("com.google", token);
		//AccountManager.get(A).getAuthToken(account, "oauth2:https://mail.google.com/", null, A, new OnTokenAcquired(), null);
		
		//testar mer
		/*
		DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(),"text/plain" ));
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setDataHandler(handler);
		Log.i("Check", "done sessions");
		
		multiPart=new MimeMultipart();

		InternetAddress toAddress;
		toAddress = new InternetAddress(to);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		Log.i("Check", "added recipient");
		message.setSubject("Send Auto-Mail");
		message.setContent(multiPart);
		message.setText("Demo For Sending Mail in Android Automatically");
		
		
		
		Log.i("Check", "transport");
		Transport transport = session.getTransport("smtp");
		Log.i("Check", "connecting");
		transport.connect(host, address, pass);
		Log.i("Check", "wana send");
		//transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		*/
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
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

    public synchronized void sendMail(String subject, String body, String user,
            String oauthToken, String recipients) {
        try {

            SMTPTransport smtpTransport = connectToSmtp("smtp.gmail.com",
                    587, user, oauthToken, false);
            
            //Allow exception to be thrown upon success
            smtpTransport.setReportSuccess(true);
            
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));   
                    message.setSender(new InternetAddress(user));   
                    message.setSubject(subject);   
                    message.setDataHandler(handler);   
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
        }    
    }
}