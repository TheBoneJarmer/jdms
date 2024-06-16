package be.labruyere.jdms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static Path cwd;
    private static Path filename;

    private static void runInit() {
        if (Files.exists(filename)) {
            err("There is already an existing jdpms file in this location.");
            exit(1);
        }

        write("");
    }

    private static void run(String command, String... params) {
        if (command.equals("init")) runInit();
    }

    private static void init() {
        cwd = Paths.get("").toAbsolutePath();
        filename = Path.of(cwd + "/dependencies.jdpms");
    }

    public static void main(String[] args) {
        if (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help"))) {
            // Print help
            exit(0);
        }

        if (args.length < 1) {
            err("Not enough parameters provided");
            exit(1);
        }

        var command = args[0];
        var params = new String[args.length - 1];

        for (var i = 1; i < args.length; i++) {
            params[i - 1] = args[i];
        }

        init();
		run(command, params);
    }

    /* HELPER METHODS */
    private static void out(String input) {
        System.out.println(input);
    }

    private static void err(String input) {
        System.err.println(input);
    }

    private static void exit(int status) {
        System.exit(status);
    }

    private static void write(String input) {
        var file = filename.toFile();

        try {
            var writer = new FileWriter(file, true);
            writer.write(input);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] read() {
        var file = filename.toFile();
        var result = new ArrayList<String>();

        try {
            var scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }

            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result.toArray(String[]::new);
    }
}
