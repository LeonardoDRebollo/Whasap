package whasap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
/**
 *
 * @author crish
 */


public class HiloActivos extends Thread {
    private DefaultTableModel modeloUserConectados;
    private HashSet<String> elementosUnicos = new HashSet<>();

    public HiloActivos(DefaultTableModel modeloUsersConectados) {
        this.modeloUserConectados = modeloUsersConectados;
    }

    @Override
    public void run() {
        try (ServerSocket servidor = new ServerSocket(5213)) {
    while (true) {
        try (Socket conexion = servidor.accept();
             DataInputStream recibe = new DataInputStream(conexion.getInputStream())) {
            String texto = recibe.readUTF();
            String[] partes = texto.split(",");
            if (partes.length > 1) {
                // Verifica y elimina la duplicidad de tablas en la segunda columna
                for (int fila = 0; fila < modeloUserConectados.getRowCount(); fila++) {
                    Object valorColumnaDos = modeloUserConectados.getValueAt(fila, 0);
                    if (valorColumnaDos != null && valorColumnaDos.toString().equals(partes[0])) {
                        modeloUserConectados.removeRow(fila);
                        break;
                    }
                }
            }
                 else{                            
                       if (!elementosUnicos.contains(texto)) {
                       // Agregar la fila y el elemento al conjunto
                       elementosUnicos.add(texto);
                       modeloUserConectados.addRow(new Object[]{partes[0],partes[1]});
                     }
                   }
               }
           }
        } catch (IOException e) {  
             e.printStackTrace();
        }
    }
}


