package whasap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author djclu
 */
public class HiloMensajes extends Thread {

    private DefaultTableModel mensajeModel;

    public HiloMensajes(DefaultTableModel mensajeModel) {
        this.mensajeModel = mensajeModel;
    }

    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(5211);
            while (true) {
                Socket conexion = servidor.accept();
                DataInputStream recibe = new DataInputStream(conexion.getInputStream());
                String texto;
                texto = recibe.readUTF();
                mensajeModel.addRow(new Object[]{texto});
                conexion.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
