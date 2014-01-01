/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.experimentos.bean.test;

import com.experimentos.beans.MonitoreoBean;
import java.io.DataOutputStream;
import java.net.Socket;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author Camilo
 */
public class ConcurrencyPosicionTest {
    
    private MonitoreoBean b;
    private boolean continuarThread;
    
    @Rule
    public ContiPerfRule i = new ContiPerfRule();
    
    public ConcurrencyPosicionTest() {
        continuarThread=true;
        b=new MonitoreoBean();
    }
    
    @Before
    public void setUp() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                
                while(continuarThread)
                {
                    b.actualizarPos();
                }
                
            }
        };
        new Thread(r, "actualizarPos-thread").start();
    }
    
    @After
    public void tearDown() {
        continuarThread=false;
    }
    
    
    @Test
    @Required(totalTime = 4000)
    @PerfTest(invocations = 600, threads=600, duration = 30000)
    public void loadTestPosicion() throws Exception
    {
        Socket s;
        DataOutputStream mensaje;
        
        
        
        
        
        s = new Socket("localhost",5002); /*conectar a un servidor en localhost con puerto 5000*/
        mensaje = new DataOutputStream(s.getOutputStream());

        mensaje.writeUTF(0+";"+0);
        mensaje.close();
        s.close();
        
    }
}