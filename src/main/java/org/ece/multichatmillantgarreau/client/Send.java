/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.client;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author egarreau
 */
public class Send implements Runnable{

    private PrintWriter out;

    private String  txt = null;

    private Scanner sc = null;

    public Send(PrintWriter out) {

        this.out = out;

    }

    public void run() {

        sc = new Scanner(System.in);

        while (true) {

            System.out.println("Votre message :");

            txt = sc.nextLine();

            out.println(txt);

            out.flush();

        }

    }
}
