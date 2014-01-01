/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package updates;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author MALIDA
 */
public class ControlUPDATE extends javax.swing.JFrame {

    PrivateKey priv;
    PublicKey pub;
    private Double[] latListA = {4.665399,4.681823,4.699959};
    private Double[] longListA = {-74.060951,-74.057862,-74.055115};
    Signature dsa;   
    int jA=0;
    

    final String HOST = "localhost";
    final int PUERTO=5002;
    Socket sc;
    Socket scw;
    Socket scp;
    DataOutputStream mensaje;
    DataOutputStream publica;
    
    Random rnd = new Random();

    public ControlUPDATE() {
        try {
            initComponents();
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);KeyPair pair = keyGen.generateKeyPair();
            priv = pair.getPrivate();
            pub = pair.getPublic();
            dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(priv);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException ex) {
            Logger.getLogger(ControlUPDATE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarPub(){
        try{
            System.out.println(DatatypeConverter.printHexBinary(pub.getEncoded()));
            System.out.println(pub.getEncoded());
            System.out.println(pub.getFormat()+pub.getAlgorithm());
            scp = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(pub.getEncoded().length);
            scp.getOutputStream().write(bb.array());
            scp.getOutputStream().write(pub.getEncoded());
            scp.getOutputStream().flush();
            scp.close();
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    public void initClientPos() /*ejecuta este metodo para correr el cliente */{
        try{
            double longitud= -74.15 + Math.random()*(0.09);
            double latitud= 4.6 + Math.random()*(0.1);
            int  n1 = rnd.nextInt(5) + 1;
            int  n2 = rnd.nextInt(5) + 1;
            int  n3 = rnd.nextInt(10) + 1;

            String str1 = ";"+n1+";"+n2+";"+n3+";"+latListA[jA]+";"+longListA[jA];
            byte[] a1 = str1.getBytes();
            int hash =str1.hashCode();
            String str= hash+"";
            byte[] a = str.getBytes();
            dsa.update(a);
            byte[] b;
            do{
                b = dsa.sign();
            }while(b.length!=46);
            byte[] c = new byte[a.length + b.length+a1.length];

            System.arraycopy(b, 0, c, 0, b.length);
            System.arraycopy(a, 0, c, b.length, a.length);
            System.arraycopy(a1, 0, c, b.length+a.length, a1.length);
            
            byte[] firma = new byte[46];
            System.arraycopy(c, 0, firma, 0, 46);

            int len = c.length;
            if (len > 0 && b.length==46) {
                sc = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/
                OutputStream out = sc.getOutputStream(); 
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeInt(len);
                dos.write(c, 0, len);
                System.out.println("Firma: "+new String(b));
                System.out.println("Hash: "+new String(a));
                System.out.println("Mensaje: "+new String(a1));
                sc.close();
            }else{
                System.out.println("Tamaño de firma incorrecto!");                
            }
        }catch(SignatureException | IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        jA=(jA+1)%3;
    }

    public void initClientW() /*ejecuta este metodo para correr el cliente */{
        try{
            int  n1 = rnd.nextInt(4) + 1;
            int  n2 = rnd.nextInt(5) + 1;
            String str1 =";"+n1+""+n2+"EMERGENCIA!";
            int hash =str1.hashCode();
            byte[] a1 = str1.getBytes();
            String str= hash+"";
            byte[] a = str.getBytes();
            dsa.update(a);
            byte[] b;
            do{
                b = dsa.sign();
            }while(b.length!=46);
            byte[] c = new byte[a.length + b.length+a1.length];

            System.arraycopy(b, 0, c, 0, b.length);
            System.arraycopy(a, 0, c, b.length, a.length);
            System.arraycopy(a1, 0, c, b.length+a.length, a1.length);

            int len = c.length;
            if (len > 0 && b.length==46) {
                scw = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/
                OutputStream out = scw.getOutputStream(); 
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeInt(len);
                dos.write(c, 0, len);
                System.out.println("Firma: "+new String(b));
                System.out.println("Hash: "+new String(a));
                System.out.println("Mensaje: "+new String(a1));
                scw.close();
            }else{
                System.out.println("Tamaño de firma incorrecto!");                
            }
        }catch(SignatureException | IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("UPDATE POSITION");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("EMERGENCY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("PK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        initClientPos();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        initClientW();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        enviarPub();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlUPDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlUPDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlUPDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlUPDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ControlUPDATE asd = new ControlUPDATE();
                        asd.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}
