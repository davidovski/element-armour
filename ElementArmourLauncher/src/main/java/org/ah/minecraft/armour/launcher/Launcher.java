package org.ah.minecraft.armour.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JOptionPane;

public class Launcher {
    public static final String ELEMENTARMOURDIR = "elementarmour";
    public static final String SERVERDIR = "server";

    public static void main(String[] args) throws IOException, InterruptedException {

        String[] yesno = new String[2];
        yesno[0] = "Yes";
        yesno[1] = "No";

        String[] options = new String[4];
        options[0] = "Goodbye!";
        options[1] = "Stop Server";
        options[2] = "Terminate Server";
        options[3] = "I'm good!";

        File home = new File(System.getProperty("user.home"));
        File dir = new File(home, ELEMENTARMOURDIR);
        if (!dir.mkdirs()) {
            System.err.println("Cannot create " + dir.getAbsolutePath());
        }

        System.out.println("Server dir: " + SERVERDIR);
        File serverDir = new File(dir, SERVERDIR);
        if (serverDir.exists() && serverDir.isDirectory()) {
            System.out.println("Already found server, updating...");

        } else {
            int action = JOptionPane.showOptionDialog(null, "Would you like to create a new element armour server\n" + "In " + home.toPath() + "/ElementArmour", "action",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesno, yesno[0]);

            if (action == 0) {
                System.out.println("Installing files...");

                File zip = new File(dir, "server.zip");
                downloadServer(zip);
                try {
                    unZipIt(zip, serverDir);
                } finally {
                    zip.delete();
                }
            } else {
                System.exit(0);
                return;
            }
        }
        int update = JOptionPane.showOptionDialog(null, "Would you like to update Element Armour?", "action", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesno,
                yesno[0]);

        if (update == 0) {
            File spigot = new File(serverDir, "spigot.jar");
            downloadSpigot(spigot);

            System.out.println("Updating Plugins...");

            File pluginsDir = new File(serverDir, "plugins");
            File pluginZip = new File(pluginsDir, "all.zip");

            pluginsDir.mkdirs();

            downloadPlugins(pluginZip);

            unZipIt(pluginZip, pluginsDir);
        }
        String params = "-Xmx1024M -XX:MaxPermSize=128m -jar";
        String jarFile = new File(serverDir, "spigot.jar").getAbsolutePath();
        String cmd = "java " + params + " " + jarFile + " -o true";
        System.out.println("Starting server...");

        Process proc = Runtime.getRuntime().exec(cmd, null, serverDir);
        System.out.println("Server running");
        System.out.println("connect to it at:");
        System.out.println("    localhost:25565");

        while (true) {
            int action = JOptionPane.showOptionDialog(null,
                    "[localhost:25565]\n\nThe server is running at: localhost:25565\n" + "It does take a while to boot up, so be patient\n\n"
                            + "Select terminate if you wish to stop it (Forcibly)\n" + "Press close to close the launcher. (Will keep the server running)\n"
                            + "If you do close the launcher, stop the server with the [/stop] command ingame.",
                    "Element Armour Launcher", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
            if (action == 0) {
                System.exit(0);

                return;
            } else if (action == 1) {
                proc.destroy();
                System.exit(0);

                return;
            } else if (action == 2) {
                proc.destroy();
                System.exit(0);

                return;

            }
        }

    }

    private static void downloadServer(File zipFile) throws MalformedURLException, IOException, ProtocolException, FileNotFoundException {
        URL url = new URL("http://mouldycheerio.com/ElementArmour/server.zip");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try {
            InputStream in = connection.getInputStream();
            try {
                FileOutputStream out = new FileOutputStream(zipFile);
                try {
                    copy(in, out);
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } finally {
            connection.disconnect();
        }
    }

    private static void downloadSpigot(File zipFile) throws MalformedURLException, IOException, ProtocolException, FileNotFoundException {
        URL url = new URL("http://mouldycheerio.com/ElementArmour/spigot.jar");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try {
            InputStream in = connection.getInputStream();
            try {
                FileOutputStream out = new FileOutputStream(zipFile);
                try {
                    copy(in, out);
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } finally {
            connection.disconnect();
        }
    }

    private static void downloadPlugins(File plugin) throws MalformedURLException, IOException, ProtocolException, FileNotFoundException {
        URL url = new URL("http://mouldycheerio.com/ElementArmour/plugins/all.zip");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try {
            InputStream in = connection.getInputStream();
            try {
                FileOutputStream out = new FileOutputStream(plugin);
                try {
                    copy(in, out);
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } finally {
            connection.disconnect();
        }
    }

    private static byte[] buffer = new byte[10240];

    public static void copy(InputStream input, OutputStream output) throws IOException {
        System.out.println("Copying...");
        int n = input.read(buffer);
        while (n >= 0) {
            output.write(buffer, 0, n);
            n = input.read(buffer);
        }
        output.flush();
        System.out.print(" Copied!");

        // int len;
        // while ((len = zis.read(buffer)) > 0) {
        // fos.write(buffer, 0, len);
        // }
    }

    public static File unZipIt(File zipFile, File folder) {
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }

            ZipFile zif = new ZipFile(zipFile);
            try {
                Enumeration<? extends ZipEntry> zipEntries = zif.entries();
                while (zipEntries.hasMoreElements()) {
                    ZipEntry ze = zipEntries.nextElement();

                    String fileName = ze.getName();
                    if (fileName.contains(".DS_Store")) {

                    } else {
                        File newFile = new File(folder, fileName);
                        if (ze.isDirectory()) {
                            if (!newFile.mkdirs()) {
                                System.err.println("Could not create dir " + newFile.getAbsolutePath());
                            }
                        } else {
                            System.out.println("file unzip : " + newFile.getAbsoluteFile());

                            if (!newFile.getParentFile().mkdirs()) {
                                System.err.println("Could not create dir " + newFile.getParentFile().getAbsolutePath());
                            }

                            FileOutputStream fos = new FileOutputStream(newFile);
                            try {
                                copy(zif.getInputStream(ze), fos);
                            } finally {
                                fos.close();
                            }
                        }
                    }
                }

                System.out.println("Done");
            } finally {
                zif.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return folder;
    }
}
