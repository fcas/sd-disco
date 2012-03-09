package sd.disco;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.SocketException;

public class SdDisco {

    public static void main(String args[]) {
        DatagramSocket s = null;
        try {
            s = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];
            System.out.println("\n\n*** Servidor aguardando request");
            DatagramPacket req = new DatagramPacket(buffer, buffer.length);
            s.receive(req);
            System.out.println("*** Request recebido de: "
                    + req.getAddress() + ":" + req.getPort());

            // envia resposta
            DatagramPacket resp = new DatagramPacket(req.getData(),
                    req.getLength(),
                    req.getAddress(), req.getPort());
            s.send(resp);
        } catch (SocketException e) {
            System.out.println("Erro de socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro envio/recepcao pacote: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}