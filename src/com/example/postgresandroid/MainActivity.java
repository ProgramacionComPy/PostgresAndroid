package com.example.postgresandroid;

/*
 * Utilizar PostgreSQL en Android con JDBC
 * www.programacion.com.py - Recursos y documentación para desarrolladores - By Rodrigo Paszniuk
 * PD: Para agregar una libreria .jar cualquiera al proyecto solamente se debe agregar a la carpeta libs del mismo.
 * Para actualizar cambios hacer click derecho al proyecto y luego  click izquierdo a Refresh.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);			
		//Desde la version 3 de android, no se permite abrir una conexión de red desde el thread principal.
		//Por lo tanto se debe crear uno nuevo.
		sqlThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  Thread sqlThread = new Thread() {                     
		    public void run() {                                    
				try { 
		            Class.forName("org.postgresql.Driver"); 
		            // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD"); 
		            Connection conn = DriverManager.getConnection( 
		                            "jdbc:postgresql://192.168.0.4:5432/fifa", "test", "test"); 
		               //En el stsql se puede agregar cualquier consulta SQL deseada.
		               // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
		               String stsql = "Select version()";
			           Statement st = conn.createStatement();
			           ResultSet rs = st.executeQuery(stsql);
		               rs.next();
		               System.out.println( rs.getString(1) );			            		  
		               conn.close(); 
		    } catch (SQLException se) { 
		            System.out.println("oops! No se puede conectar. Error: " + se.toString()); 
		    } catch (ClassNotFoundException e) { 
		    	System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage()); 
		    }
		    }    				
		};    
		
		  /*
		  new Thread(new Runnable() {
			    public void run() {
			    	
					try { 
			            Class.forName("org.postgresql.Driver"); 
			            Connection conn = DriverManager.getConnection( 
			                            "jdbc:postgresql://192.168.0.4:5432/fifa", "test", "test"); 
			             String stsql = "Select version()";
				           Statement st = conn.createStatement();
				           ResultSet rs = st.executeQuery(stsql);
			               rs.next();
			               System.out.println( rs.getString(1) );			            		  
			               conn.close(); 
			    } catch (SQLException se) { 
			            System.out.println("oops! can't connect. Error: " + se.toString()); 
			    } catch (ClassNotFoundException e) { 
			    	System.out.println("oops! Class not found. Error: " + e.getMessage()); 
			    }
					
					runOnUiThread(new Runnable() {
				        public void run() {
				            Toast.makeText(MainActivity.this, "Tarea finalizada!",
				                Toast.LENGTH_SHORT).show();
				        }
				    });	
			    				}
			}).start();
			
	  */
}
