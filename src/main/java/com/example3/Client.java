package com.example3;
import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket mySocket;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    int tentativoDiIndovinare;


    public Socket connetti(){
        System.out.println("2 CLIENT partito in esecuzione");
        
        try{
            //per l'input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            
            //creazione socket
            mySocket = new Socket(nomeServer, portaServer); //possibile utilizzare anche 'InetAddress.getLocalHost' al posto di 'nomeServer'

            //creazione degli stream
            outVersoServer = new DataOutputStream(mySocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
       
        }catch(UnknownHostException e){
            System.out.println("Host sconosciuto");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);;
        }

        return mySocket;
    }

    public void comunica(){
        for(;;){

            try{
                System.out.println("utente inserisci la stringa da trasmettere al server:");
                try{
                     tentativoDiIndovinare = Integer.parseInt(tastiera.readLine());
                }catch(Exception e){
                    System.out.println("Non è stato inserito un valore numerico");
                }
                
                

                //spedisco al server
                System.out.println("invio il numero ed attendo...");
                outVersoServer.writeBytes(tentativoDiIndovinare +"\n");

                //leggo dal server
                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("risposta del server : " +"\n" + stringaRicevutaDalServer);

                //controllo se ho indvinato o meno
                if(stringaRicevutaDalServer.equals("hai indovinato")){
                    mySocket.close();
                    break;
                    //nel caso il numero venga indovinato viene chiusa la connessione da parte del client
                }
                else{
                    System.out.println("riprova , ci sei quasi");
                }
                
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connesione");
                System.exit(1);
            }
        }
    }

}
