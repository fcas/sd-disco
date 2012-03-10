package ufrn.engsoft.pd;

/* @author eng.soft */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DataUDP implements Runnable {

    private int port;
    private InetAddress server;
    private DatagramSocket socket;
    private String name;

    /* Construtor do Data provider */
    public DataUDP(String name, InetAddress server, int port) {
        this.name = name;
        this.server = server;
        this.port = port;
        printMessage(this.name, "iniciando.");
    }

    /** Método para imprimir o nome do ator e sua ação,
     *  neste caso, o ator será Dataprovider */
    private void printMessage(String who, String action) {
        System.out.printf("%s:>%s\n.", who, action);
    }

    @Override
    public void run() {
        while (true) {
            try {

                byte[] buf = new byte[1024];

                /** DatagramPacket classe utilizada para o serviço de entrega de pacotes, 
                 * não orientado a conexão.
                 */
                DatagramPacket datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);

                String msg = new String(datagram.getData(), 0, datagram.getLength());
                printMessage(this.name, msg);
            } catch (IOException x) {
            }
        }
    }

    void doIt() {

        try {

            /* Abre socket para iniciar a comunicação com o servidor */
            socket = new DatagramSocket();

            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();

            Scanner in = new Scanner(System.in);

            String request;

            do {

                request = in.nextLine();

                byte[] requestData = request.getBytes();
                DatagramPacket req = new DatagramPacket(requestData, requestData.length, server, port);

                /* Envia mensagem para o servidor */

                try {
                    socket.send(req);
                    printMessage(this.name, "enviando " + request);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (!request.equals("!fim"));

            /* Fecha conexão */
            socket.close();


        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        /*Verifica se a porta está em uso */
        if (args.length != 2) {
            System.err.println("usage: java DataUDP ip_servidor porta_servidor.");
            System.exit(0);
        }

        /* Seta o servidor como nulo */
        int port = Integer.parseInt(args[1]);
        InetAddress server = null;

        /* getByName: Determina o endereço IP de um host, neste caso o servidor */
        try {
            server = InetAddress.getByName(args[0]);
        } /* Informa que o endereço IP não foi determinado */ catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /* Cria Data Provider */
        new DataUDP("data", server, port).doIt();

    }
}