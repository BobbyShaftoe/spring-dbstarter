package com.nick.db.starter.dbstarter.service;

import com.nick.db.starter.dbstarter.model.Users;
import com.nick.db.starter.dbstarter.repository.UsersRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DataLoaderService {

    private final UsersRepository usersRepository;

    public DataLoaderService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ArrayList<Users> loadUsersFromCSV(String csvFile) {

        ArrayList<Users> usersArrayList = new ArrayList<>();

        Path csvFilePath = Paths.get(".", "data" + "/" + csvFile);
        System.out.println("Absolute CSV file path: " + csvFilePath.toAbsolutePath());

        if (Files.exists(csvFilePath)) {
            try {
                Reader csvFileReader = new FileReader(String.valueOf(csvFilePath));
                Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvFileReader);
                for (CSVRecord record : records) {
                    System.out.println(record.toString());
                    usersArrayList.add(
                            new Users(
                                    record.get("firstName"),
                                    record.get("lastName"),
                                    record.get("email"),
                                    Integer.parseInt(record.get("age"))
                            )
                    );
                }
            } catch (IOException e) {
                System.out.println("Unable to read CSV file: " + e);
            }
        } else {
            System.out.println("File not found: " + csvFilePath);
        }
        return usersArrayList;
    }

    //    @Modifying
//    @Transactional
    public HashMap<String, ArrayList<Users>> saveUsersToDB(ArrayList<Users> usersArrayList) {

        HashMap<String, ArrayList<Users>> saveUsersToDBHashMap = new HashMap<>();

        ArrayList<Users> usersToAdd = new ArrayList<>();
        ArrayList<Users> usersAlreadyExist = new ArrayList<>();

        if (usersArrayList.isEmpty()) {
            saveUsersToDBHashMap.put("usersToAdd", usersToAdd);
            saveUsersToDBHashMap.put("usersAlreadyExist", usersAlreadyExist);
            return saveUsersToDBHashMap;
        }

        for (Users user : usersArrayList) {
            if (usersRepository.existsByEmail(user.getEmail())) {
                System.out.println("User already exists: " + user.getEmail());
                usersAlreadyExist.add(user);
                continue;
            }
            usersToAdd.add(user);
        }
        usersRepository.saveAll(usersToAdd);

        saveUsersToDBHashMap.put("usersToAdd", usersToAdd);
        saveUsersToDBHashMap.put("usersAlreadyExist", usersAlreadyExist);
        return saveUsersToDBHashMap;
    }
}





