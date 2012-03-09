/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;
import java.io.IOException;
import java.net.DatagramPacket; 
import java.net.DatagramSocket;
import java.net.InetAddress; 
import java.net.SocketException;
import java.awt.BorderLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

    
public class data_provider extends Jframe {

    private JTextField enterField; // mandar discografia
    private JTextArea displayArea; // exibir mensagem da discografia enviada
    private DatagramSocket socket; // socket (TCP/IP) para conectar ao Servidor
    
    // configura o datagram e a interface com o usuário
    public Provider ()
    {
        
      super ("Provider");
        
        enterField = new JTextField ("Informe a discografia");
        enterField.addActionListener(
                new ActionListener () 
                {
                
                    public void actionPerfomed (ActionEvent event)
                    {
                        try  // cria e envia o pacote  
                        {   
                            //obtem a mensagem no campos de texto
                            String message = event.getActionCommand();
                            displayArea.append("\nEnviando discografia:" + message + "\n");
                            
                            byte data[] = message.getBytes(); // converte a mensagem em bytes
                            
                            //cria sendPacket
                            DatagramPacket sendPacket = new DatagramPacket(data,data.length, InetAddress.getLocalHost(), 5000);
                    
                            socket.send(sendPacket); //envia o pacote
                            displayArea.append ("Discografia enviada\n"); 
                            displayArea.setCaretPosition(displayArea.getText().length());
                        } // fim do try
                        catch (IOException ioException)
                        {
                            displayMessage(ioException.toString() + "\n");
                            ioException.printStackTrace();
                        } // fim do catch
                    } // fim do método actionPerfomed
                } // fim da classe inner
            ); // fim da chamada para addActionListener
        
        add(enterField, BorderLayout.NORTH);
        displayArea = new JTextArea();
        setSize(400,300); // configura o tamanho da janela
        setVisible(true); // mostra a janela
        
        try // cria datagrama para envio e recebimento de pacote
        {
            socket = new DatagramSocket();
        } // fim do try
        catch (SocketException socketException)
        {
            socketException.printStackTrace();
            System.exit (1); 
        } // fim do catch
    }// fim do construtor Provider
    
    // espera que os pacotes cheguem ao servidor
    
    public void waitForPackets()
    {
        while (true)
        {
            try // recebe o pacote exibe o conteúdo 
            {
                byte data[] = new byte[100]; //configura o pacote
                DatagramPacket receivePacket = new DatagramPacket (data, data.length); 
                socket.receive(receivePacket); //espera o pacote
                
                //exibe o conteúdo do pacote
                
                displayMessage (
                        "\nPacote recebido:" + 
                        "\ndoHost:"+ receivePacket.getAddress()+
                        "\nPorta do Host:" + receivePacket.getPort() +
                        "\nTamanho:" + receivePacket.getLength() +
                        "\nContendo:\n\t" + new String (receivePacket.getData(),
                                0, receivePacket.getLength()));
            } // fim do try
        
            catch (IOException exception)
            {
                displayMessage(exception.toString() +  "\n");
                exception.printStackTrace();
            } // fim do catch
        } // fim do while
    }// fim do método waitforpackets

    //manipula a displayArea na thread de despacho de eventos
    private void displayMassage (final String  messageToDisplay)
    {
        SwingUtilities.invokeLater(
                new Runnable ()
                {
                  public void run () // atualiza displayArea
                  {
                      displayArea.append (messageToDisplay);
                  } // fim do método run
                } // fim da classe inner
                ); // fim da chamada  para SwingUtilities.invokeLater
    } // fim do método displayMessage
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
