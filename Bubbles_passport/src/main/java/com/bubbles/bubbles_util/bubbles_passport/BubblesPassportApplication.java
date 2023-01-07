package com.bubbles.bubbles_util.bubbles_passport;

import com.bubbles.bubbles_util.bubbles_passport.config.JwtConfig;
import com.bubbles.bubbles_util.bubbles_passport.service.PassportService;
import com.bubbles.bubbles_util.bubbles_passport.utils.ChatroomPassportUtils;
import com.bubbles.bubbles_util.bubbles_passport.utils.PassportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

@SpringBootApplication
public class BubblesPassportApplication implements CommandLineRunner {
    private final PassportService passportService;
    private final JwtConfig jwtConfig;

    @Autowired
    public BubblesPassportApplication(PassportService passportService, JwtConfig jwtConfig) {
        this.passportService = passportService;
        this.jwtConfig = jwtConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(BubblesPassportApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String path = "passport.txt";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to use Bubbles chatroom create passport generator!");
        int count = 1;
        int max_user = 10;
        try {
            System.out.println("Enter the passport count you want to create(default: 1):");
            String in = scanner.nextLine();
            count = in.isEmpty() ? 1 : Integer.valueOf(in);
            System.out.println("Enter the max user limit of the chatrooms created from passport(default: 10):");
            in = scanner.nextLine();
            max_user = in.isEmpty() ? 1 : Integer.valueOf(in);
            System.out.println(String.format("Generating %d passport with up to %d users ...", count, max_user));
            BufferedWriter o = new BufferedWriter(new FileWriter(path));
            for(int i = 0; i < count; i ++){
                String token = ChatroomPassportUtils.createChatroomPassport(max_user, jwtConfig);
                passportService.save(token, PassportType.CREATE_CHATROOM);
                o.write("ChatroomPassport: ");
                o.write(token);
                o.newLine();
            }
            o.close();
            System.out.println(String.format("Passport generated(output file: \"%s\").", path));
        }
        catch (Exception e){
            System.out.println("Invalid input. Terminating ...");
        }

    }
}
