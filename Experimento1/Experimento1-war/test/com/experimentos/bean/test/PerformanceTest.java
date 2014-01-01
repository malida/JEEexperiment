/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.experimentos.bean.test;

import com.experimentos.beans.MonitoreoBean;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Camilo
 */
public class PerformanceTest {
    
    private MonitoreoBean b;
    
    public PerformanceTest() {
        
    }
    
    @Before
    public void setUp() {
        
        b=new MonitoreoBean();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(timeout = 3000)
    public void testLatenciaPosicion() throws Exception
    {
       
        Socket s;
        DataOutputStream mensaje;
        
        Runnable r = new Runnable() {

            @Override
            public void run() {
               b.actualizarPos();
            }
        };
        new Thread(r, "actualizarPos-thread").start();
        
        
        
        s = new Socket("localhost",5002); /*conectar a un servidor en localhost con puerto 5000*/
        mensaje = new DataOutputStream(s.getOutputStream());

        mensaje.writeUTF(0+";"+0);
        mensaje.close();
        s.close();
            
        
    }
    
    @Test(timeout = 1500)
    public void testLatenciaEmergencia() throws Exception
    {   
        Socket s;
        DataOutputStream mensaje;
        
        Runnable r = new Runnable() {

            @Override
            public void run() {
               b.emergencia();
            }
        };
        new Thread(r, "actualizarPos-thread").start();
        
        
        s = new Socket("localhost",5002); /*conectar a un servidor en localhost con puerto 5000*/
        mensaje = new DataOutputStream(s.getOutputStream());
        mensaje.writeUTF("EMERGENCIA!");
        mensaje.close();
        s.close();
    }
}