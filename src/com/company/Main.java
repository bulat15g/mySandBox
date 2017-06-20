package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    static String shellResult = "";

    public static void main(String[] args) {
        String test[];
        test = parseStringToArray("adb connect 192.168.1.46");

        runBashCommand(test);

        if ((shellResult.equals("connected to 192.168.1.46:5555"))
                || (shellResult.equals("already connected to 192.168.1.46:5555"))) {
            Integer choose = 1;
            System.out.println("Введите входную команду:");

            while (choose != 0) {
                System.out.println("0-выход 1- запуск приложения 2- убить приложение");
                choose = new Scanner(System.in).nextInt();

                switch (choose) {
                    case 0: {
                        System.out.println("goodbye");
                        System.exit(0);
                        break;
                    }
                    case 1: {
                        test = parseStringToArray(
                                "adb shell am start -n com.dev47apps.droidcamx/com.dev47apps.droidcamx.DroidCamX");
                        runBashCommand(test);
                        System.out.println("Try: http://192.168.1.46:4747/");
                        break;
                    }
                    case 2: {
                        test = parseStringToArray(
                                "adb shell am force-stop com.dev47apps.droidcamx");
                        runBashCommand(test);

                        break;
                    }
                }
            }
        }
        else {
            if (shellResult.equals("unable to connect to 192.168.1.46:5555")) {
                System.out.println("cannot connect");
            }
            else {
                System.out.println("unnamed exception");
            }
        }

    }

    public static String[] parseStringToArray(String string) {
        String ret[] = string.split(" ");
        return ret;
    }

    public static void showArray(String[] abc) {
        for (int i = 0; i < abc.length; i++) {
            System.out.println("command[" + i + "]:=" + abc[i]);
        }
    }

    public static void runBashCommand(final String[] RunCommand) {

        OutputStream out = null;
        InputStream in = null;

        try {
            Process shellProcess = Runtime.getRuntime().exec(RunCommand, null);
            out = shellProcess.getOutputStream();
            in = shellProcess.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            shellResult = "";

            while ((line = bufferedReader.readLine()) != null) {
                shellResult += line;
                System.out.println(line);
            }

//                    handleBashCommandsResult(shellResult);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}