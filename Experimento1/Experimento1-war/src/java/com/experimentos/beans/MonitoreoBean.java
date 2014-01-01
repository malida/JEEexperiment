package com.experimentos.beans;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.WebServiceRef;
import pojos.Accidente;
import pojos.Conductor;
import pojos.Tiempo;
import pojos.Trayecto;
import pojos.Trouble;
import servicios.LocationBeanLocal;
import servicios.ServicioPersistencia;
import ws.EmergencyWS_Service;

/**
 *
 * @author MALIDA
 */
public final class MonitoreoBean {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Police-war/EmergencyWS.wsdl")
    private EmergencyWS_Service service;
    
    @EJB
    private LocationBeanLocal servicioLocation;
    

    final int PUERTO=5002;
    
    ServerSocket sc; //posicion
    Socket so;
    
    ServerSocket scw; //warnings
    Socket sow;
    
    ServerSocket scp; //publica
    Socket sop;
    
    String mensajeRecibido;
    Signature sig; //para verificar
    
    private Double latA=0.0;
    private Double longA=0.0;
    
    byte[] firma = new byte[92];
    byte[] mensaje = new byte[200];
    byte[] pub;
    PublicKey pub1;
    ServicioPersistencia sp;
    
    public MonitoreoBean() {
        sp = new ServicioPersistencia();
        try {
            sig = Signature.getInstance("SHA1withDSA", "SUN");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MonitoreoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MonitoreoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initServerPublica(){
        try{
            scp = new ServerSocket(PUERTO);
            sop=new Socket();
            System.out.println("Esperando una conexión:");
            sop = scp.accept();
            System.out.println("Un cliente se ha conectado.");
            try{
                byte[] lenb = new byte[4];
                sop.getInputStream().read(lenb,0,4);
                ByteBuffer bb = ByteBuffer.wrap(lenb);
                int len = bb.getInt();
                byte[] servPubKeyBytes = new byte[len];
                sop.getInputStream().read(servPubKeyBytes);
                X509EncodedKeySpec ks = new X509EncodedKeySpec(servPubKeyBytes);
                KeyFactory kf = KeyFactory.getInstance("DSA", "SUN");
                pub1 = kf.generatePublic(ks);
                System.out.println(DatatypeConverter.printHexBinary(pub1.getEncoded()));
                System.out.println(pub1.getEncoded());
                System.out.println("Confirmando conexion al cliente....");
                System.out.println("Cerrando conexión...");
                scp.close();    
            } catch (IOException e) {
                System.out.println("Error obtaining server public key 1.");
                System.exit(0);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error obtaining server public key 2.");
                System.exit(0);
            } catch (InvalidKeySpecException e) {
                System.out.println("Error obtaining server public key 3.");
                System.exit(0);
            }   
//            try{
//                InputStream in = sop.getInputStream();
//                DataInputStream dis = new DataInputStream(in);
//
//                int len = dis.readInt();
//                pub = new byte[len];
//                if (len > 0) {
//                    dis.readFully(pub);
//                }
//                System.out.println("Confirmando conexion al cliente....");
//                System.out.println("Cerrando conexión...");
//                scp.close();    
//            }catch(Exception e ){
//                System.out.println("Error: "+e.getMessage());
//            }
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private boolean confirmar(byte[] mensaje, byte[] firma){
        try {
            sig.initVerify(pub1);
            sig.update(mensaje); 
            return sig.verify(firma);
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }
    
    private void initServerPos(){
        try{       
            sc=new ServerSocket(PUERTO);
            try{
                so=new Socket();
                System.out.println("Esperando una conexión:");
                so = sc.accept();
                System.out.println("Un cliente se ha conectado.");
                
                InputStream in = so.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                int len = dis.readInt();                
                byte[] data = new byte[len];
                dis.readFully(data);                
                System.out.println("Confirmando conexion al cliente....");    
                System.arraycopy(data, 0, firma, 0, 46);
                System.arraycopy(data, 46, mensaje, 0, data.length-46);
                
                mensajeRecibido = new String(mensaje);
                String[] asd = mensajeRecibido.split(";");
                String men = ";"+Integer.parseInt(asd[1])+";"+Integer.parseInt(asd[2])+";"+Integer.parseInt(asd[3])+";"+Double.parseDouble(asd[4])+";"+Double.parseDouble(asd[5]);
                int hash = Integer.parseInt(asd[0]);
                boolean on = confirmar(asd[0].getBytes(),firma);
                System.out.println(on); 
                if(on == true){
                    System.out.println("Autentificacion de datos valida....");
                    if(hash == men.hashCode()){
                        System.out.println("Confirmando datos validos e integros....");
                        latA=Double.parseDouble(asd[1]);
                        longA=Double.parseDouble(asd[2]);
                        
                        servicioLocation.setCurrentLat(latA);
                        servicioLocation.setCurrentLong(longA);

                        System.out.println("Trayecto: "+asd[1]+" Conductor: "+asd[2]+" Tiempo: "+asd[3]);                        
                        Trayecto trayecto = sp.findByIdTry(new Long(asd[1]));
                        Conductor conductor = sp.findByIdCon(new Long(asd[2]));
                        Tiempo tp = new Tiempo(trayecto, conductor, Integer.parseInt(asd[3]));
                        System.out.println(trayecto);
                        System.out.println(conductor);
                        System.out.println(asd[3]);
                        System.out.println(tp);
                        sp.create(tp);
                        System.out.println("(log guardado en la base de datos)");

                    }else{
                        System.out.println("Datos no validos o integros....");                    
                    }
                }
                System.out.println("Cerrando conexión...");
                sc.close();    
            }catch(Exception e){
                System.out.println("Error: "+e.getMessage());
                sc.close();    
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void initServerWarnings(){
        try{
            scw=new ServerSocket(PUERTO);
            try{
                sow=new Socket();
                System.out.println("Esperando una conexión:");
                sow = scw.accept();
                System.out.println("Un cliente se ha conectado.");
                
                InputStream in = sow.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                int len = dis.readInt();                
                byte[] data = new byte[len];
                dis.readFully(data);                
                
                System.out.println("Confirmando conexion al cliente....");    
                System.arraycopy(data, 0, firma, 0, 46);
                System.arraycopy(data, 46, mensaje, 0, data.length-46);
                mensajeRecibido = new String(mensaje);
                String[] asd = mensajeRecibido.split(";");

                
                String men = ";"+asd[1].substring(0, 13);
                String acc = men.substring(1, 2);
                String tray = men.substring(2, 3);
                int hash = Integer.parseInt(asd[0]);
                boolean on = confirmar(asd[0].getBytes(),firma);
                System.out.println(on); 
                if(on==true){
                    System.out.println("Autentificacion de datos validos....");
                    if(hash == men.hashCode()){
                        System.out.println("Confirmando datos validos e integros....");
                        men= men.substring(3, 13);
                        
                        System.out.println("Accidente: "+acc+" Trayecto: "+tray);
                        Accidente accidente = sp.findByIdAcc(new Long(acc));
                        Trayecto trayecto = sp.findByIdTry(new Long(tray));
                        Trouble tr = new Trouble(trayecto, accidente);
                        System.out.println(accidente);
                        System.out.println(trayecto);
                        System.out.println(tr);
                        sp.create(tr);

                        System.out.println(men+"(log guardado en la base de datos)");
                        addEmergency("EMERGENCIA  -  " + new java.util.Date());
                    }else{
                        System.out.println("Datos no validos o integros....");                    
                    }
                }
                System.out.println("Cerrando conexión...");
                scw.close();    
            }catch(Exception e){
                System.out.println("Error: "+e.getMessage());
                scw.close();    
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    private void conmasefi1(){
        //List<Tiempo> findAlltmp = sp.findAlltmp();
        int[] conductores;
        conductores = new int[6];
        
        for (Tiempo v : sp.findAlltmp()){
            int asd = v.getConductor_id().getId().intValue();
            conductores[asd]=conductores[asd]+v.getTiempo_segundos();
        }
        int j=0;
        for(int i=1;i<conductores.length;i++){
            if(conductores[i] < conductores[1]){
                conductores[1] = conductores[i];
                j=i;
            }
        }
        System.out.println("El conductor mas eficiente es el numero: "+j+" con un tiempo (minutos) record de: "+conductores[1]);
    }

    private void conmenefi1(){
        List<Tiempo> findAlltmp = sp.findAlltmp();
        int[] conductores;
        conductores = new int[6];
        
        for (Tiempo v : findAlltmp){
            int asd = v.getConductor_id().getId().intValue();
            conductores[asd]=conductores[asd]+v.getTiempo_segundos();
        }
        int j=0;
        for(int i=1;i<conductores.length;i++){
            if(conductores[i] > conductores[1]){
                conductores[1] = conductores[i];
                j=i;
            }
        }
        System.out.println("El conductor menos eficiente es el numero: "+j+" con un tiempo (minutos) maximo de: "+conductores[1]);
    }
    
    private void accmascom1(){
        List<Trouble> findAlltrb = sp.findAlltrb();
        int[] accidentes;
        accidentes = new int[5];
        
        for (Trouble v : findAlltrb){
            int asd = v.getAccidente_id().getId().intValue();
            accidentes[asd]=accidentes[asd]+1;
        }
        int j=0;
        for(int i=1;i<accidentes.length;i++){
            if(accidentes[i] > accidentes[1]){
                accidentes[1] = accidentes[i];
                j=i;
            }
        }
        System.out.println("El accidente mas comun es el numero: "+j+" con un numero total de ocurrencias: "+accidentes[1]);
    }
    
    private void tmppro1(){
        List<Tiempo> findAlltmp = sp.findAlltmp();
        int tiempo=0;
        
        for (Tiempo v : findAlltmp){
            tiempo=tiempo+v.getTiempo_segundos();
        }
        System.out.println("El tiempo promedio (minutos) en completar una ruta es: "+tiempo/findAlltmp.size());
    }
    
    private void trypro1(){
        List<Trouble> findAlltrb = sp.findAlltrb();
        int[] trayecto;
        trayecto = new int[6];
        
        for (Trouble v : findAlltrb){
            int asd = v.getTrayecto_id().getId().intValue();
            trayecto[asd]=trayecto[asd]+1;
        }
        
        int j=0;
        for(int i=1;i<trayecto.length;i++){
            if(trayecto[i] > trayecto[1]){
                trayecto[1] = trayecto[i];
                j=i;
            }
        }
        System.out.println("El trayecto mas problematico es el numero: "+j+" con un numero total de accidentes: "+trayecto[1]);
    }

    private void all(){
        List<Conductor> findAllcon = sp.findAllCon();
        List<Trayecto> findAlltry = sp.findAllTry();
        List<Accidente> findAllacc = sp.findAllAcc();
        System.out.println("Conductores!");
        for (Conductor v : findAllcon){
            System.out.println("ID: "+v.getId().toString()+" Nombre: "+v.getNombre());
        }
        System.out.println("Trayectos!");
        for (Trayecto v : findAlltry){
            System.out.println("ID: "+v.getId().toString()+" Nombre: "+v.getNombre());
        }
        System.out.println("Accidentes!");
        for (Accidente v : findAllacc){
            System.out.println("ID: "+v.getId().toString()+" Descripcion: "+v.getDescripcion());
        }
    }
    
    //maneja el serversocket para recibir clave publica
    public synchronized void publica(){
        initServerPublica();
    }
    
    //maneja el serversocket para emergencias, muestra el valor por consola(por mejorar!)
    public synchronized void emergencia(){
        initServerWarnings();
    }

    //maneja el serversocket para actualizar pos.    
    public synchronized void actualizarPos(){
        initServerPos();
    }

    public synchronized void conmasefi(){
        conmasefi1();
    }

    public synchronized void conmenefi(){
        conmenefi1();
    }
    
    public synchronized void accmascom(){
        accmascom1();
    }

    public synchronized void trypro(){
        trypro1();
    }

    public synchronized void tmppro(){
        tmppro1();
    }
    
    public synchronized void referencias(){
        all();
    }

    public Double getLatA() {
        return latA;
    }

    public void setLatA(Double latA) {
        this.latA = latA;
    }
    public Double getLongA() {
        return longA;
    }
    public void setLongA(Double longA) {
        this.longA = longA;
    }

    private void addEmergency(java.lang.String msg) {
        ws.EmergencyWS port = service.getEmergencyWSPort();
        port.addEmergency(msg);
    }
}

