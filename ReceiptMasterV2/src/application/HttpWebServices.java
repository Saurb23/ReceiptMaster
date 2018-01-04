package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpWebServices {
//	static String License_Key="";
//	static String Hardware_Key="595839058ksfjskfshfskjhfksjfxfsfjckfskf";
//	static long id=1;
//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//	static Date Fromdate = new Date();
//	static Date Todate = new Date();
//	static String Fromdate1="2017-09-11";
//	static String Todate1="2017-09-11";
//	static String status="1";
	private final String USER_AGENT = "Mozilla/5.0";
	InputStream is=null;
	 JSONArray jsonArray=new JSONArray();
	 JSONObject json=new JSONObject();
	 JSONArray jsonArray1=new JSONArray();
	 JSONObject json1=new JSONObject();
	
//	// HTTP GET request
		public String sendGet(String Licens) throws Exception {
			 json.put("License_Key1", Licens);
		     jsonArray.put(json);
		     
			String url = "https://java4us.000webhostapp.com/webServise2.php?order="+jsonArray;

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//print result
			System.out.println(response.toString());
			
			
			return response.toString();
			
		}

		// HTTP POST request
		public String sendPost(String License_Key, String Hardware_Key, String Fromdate1, String Todate1,String status) throws Exception {
			
			json1.put("Hardware_Key",Hardware_Key);
	        json1.put("from_date", URLEncoder.encode(Fromdate1,"UTF-8"));
	        json1.put("to_date", URLEncoder.encode(Todate1,"UTF-8"));
	        json1.put("License_Key", License_Key);
	        json1.put("status", status);
	        jsonArray1.put(json1);

			String url = "https://java4us.000webhostapp.com/webservise.php?order="+jsonArray1;
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "";
			
			//Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			//int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
            
			//print result
			System.out.println("response"+response.toString());
			if(response.toString().equals("1")) {
//				System.out.println("here ");
				String homeDir=System.getProperty("user.home");
				String filePath=homeDir+File.separator+"bl-lk/";
				File dir= new File(filePath);
				dir.mkdirs();
				File file= new File(filePath+"lic.prop");
				file.createNewFile();
				
				FileWriter fileWriter=new FileWriter(file);
				PrintWriter printWriter= new PrintWriter(fileWriter);
				printWriter.println(License_Key);
				printWriter.println(Hardware_Key);
				printWriter.println(Fromdate1);
				printWriter.println(Todate1);
				printWriter.println(status);
//				printWriter.flush();
//				fileWriter.flush();
				printWriter.close();
				Path path = FileSystems.getDefault().getPath(filePath);
				Files.setAttribute(path, "dos:hidden", true);
//				fileWriter.close();
				SplashScreen.popup.close();
				SplashScreen.mainStage.show();
			}
			return response.toString();
		}

	}

