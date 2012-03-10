package ufrn.engsoft.pd;

/* @author eng.soft */ 
 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerUDP {

    public static void main(String args[]) throws SocketException {

        /*Classe DatagramSocket: envia e recebe pacotes de datagramas*/

        DatagramSocket s = null;

        /*Constrói o DatagramSocket ligado a porta 6789.*/

        s = new DatagramSocket(6789);
        System.out.println("Servidor: aguardando pedido na porta 6789");

        /*Looping*/

        while (true) {

            try {

                /*Os bytes são armazenados em buffer.*/

                byte[] buffer = new byte[1000];

                /** DatagramPacket classe utilizada para o serviço de entrega de pacotes, 
                 * não orientado a conexão.
                 */
                DatagramPacket req = new DatagramPacket(buffer, buffer.length);

                /* s.receive Requere datagrama ao cliente.*/

                s.receive(req);
                System.out.println("Servidor: recebeu: " + req.getAddress() + ":" + req.getPort() + " " + new String(req.getData()));

                /** DatagramPacket: Envia resposta ao cliente : 
                 * - Método getData: retorna o buffer de dados.
                 * - Método getLength: retorna o comprimeto dos dados a serem.
                 *   enviados/recebidos. 
                 * - Método getAddress: Retorna o ip da máquina origem/destino. 
                 * - Método getPort: Define o número da porta.
                 */
                DatagramPacket resp = new DatagramPacket(req.getData(), req.getLength(),
                        req.getAddress(), req.getPort());
                s.send(resp);

            } catch (SocketException e) {
                System.out.println("Erro de socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro envio/recepcao pacote: " + e.getMessage());
            } finally {
            }
        }
    }
}