/*
 * Copyright (C) 2013 Daniel Medina <http://danielme.com>
 * 
 * This file is part of "Android WebView UI Demo".
 * 
 * "Android WebView UI Demo" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * "Android WebView UI Demo" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 3
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl-3.0.html/>
 */
package com.danielme.demo.webviewui;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
 


/**
 *  
 * @author danielme.com
 *
 */
public class MainActivity extends Activity
{
	
	private WebView webView;  
 
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavascriptManager(), "MainActivity");
		webView.loadUrl("file:///android_asset/ui.xhtml");	
	}	
	
	class JavascriptManager
	{
		@JavascriptInterface
		public void getFromWebView(String value)
		{ 
			Builder builder = new Builder(MainActivity.this);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setCancelable(false);
			builder.setNeutralButton(R.string.ok, null);
			builder.setTitle(R.string.info);
			builder.setCancelable(false);
			builder.setMessage(value);
			builder.create().show();
		}
		
		@JavascriptInterface
		public String getContacts()
		{
			Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			StringBuffer data = new StringBuffer();
			while(contacts.moveToNext()) 
			{ 
			   int nameFieldColumnIndex = contacts.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			   data.append(contacts.getString(nameFieldColumnIndex)+ "<br/>");
			}
 
			contacts.close();
			return data.toString();
		}
		
		@JavascriptInterface
		public String getString(String key)
		{
			String text = "";
			int id = getResources().getIdentifier(key, "string", getPackageName());
			if (id > 0)
			{
				text =  MainActivity.this.getString(id);
			}
			return text;
		}
	}

}