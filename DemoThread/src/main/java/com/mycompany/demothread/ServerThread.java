/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demothread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ServerThread{

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(3333);
            Socket s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String str = "", str2 = "";
            while (!str.equals("/stop")) {                
                str = din.readUTF();
                System.out.println("Client: " + str);
                str2 = br.readLine();
                dout.writeUTF("Server: " + str2);
                dout.flush();
            }
            din.close();
            s.close();
            ss.close();
        } catch (Exception e) {
        }
        
    }
}
