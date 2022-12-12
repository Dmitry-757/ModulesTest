package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.BaseModelsMethsI;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 *
 * attempt to make single Repository-class instead of SkillRepository, SpecialtyRepository and DeveloperRepository ))
 * extends BaseModelsMethsI - чтобы JVM знал что у T есть соответствующие методы
 */
public class MyGenericRepositoryImpl<T extends BaseModelsMethsI> implements GenericRepository<T, Long> {
    final Class<T> typeParameterClass;
    private final String fileName;
    private final String tmpFileName;
    private final Path file;
    private final Path tmpFile;
    private final Path lastIdfile;

    private static final Gson gson = new GsonBuilder()
//            .setPrettyPrinting() //formats json-file to well done form
            .create();

    public MyGenericRepositoryImpl(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.fileName = typeParameterClass.getSimpleName().toLowerCase() + ".json";
        this.tmpFileName = typeParameterClass.getSimpleName().toLowerCase() + ".tmp";
        this.file = Paths.get(fileName);
        this.tmpFile = Path.of(tmpFileName);
        this.lastIdfile = Path.of(typeParameterClass.getSimpleName().toLowerCase() + ".lastId");

    }


    @Override
    public List<T> getAll(Status status) {
        List<T> itemList = new LinkedList<>();
        if (!Files.exists(file)) {
            System.out.println("file "+fileName+" not exist!");
            return itemList;
        }

        try {
            List<String> lines = Files.readAllLines(file);
            for (String jsonStr : lines) {
                T item = new Gson().fromJson(jsonStr, typeParameterClass);
                if (status == null) {
                    itemList.add(item);
                } else if (item.getStatus() == status) {
                    itemList.add(item);
                }

            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("some io exception in module MyGenericRepositoryImpl in meth getAll: "+e.getMessage());
        }
        return itemList;
    }

    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<T> getAll() {
        return getAll(null);
    }


    @Override
    public T getById(Long id) {
        if (!Files.exists(file)) {
            System.out.println("The db-file "+file.getFileName()+" is absent!");
            return null;
        }
        String jsonStr;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                jsonStr = sc.nextLine();
                T item = new Gson().fromJson(jsonStr, typeParameterClass);
                if (item.getId() == id) {
                    return item;
                }
            }
        } catch (IOException e) {
            System.out.println("some io exception in module MyGenericRepositoryImpl in meth getById: "+e.getMessage());
        }

        return null;
    }

    @Override
    public void addOrUpdate(T item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        }else {
            //*** update ***
            update(item);
        }
    }


    public void add(T item){
        try {
            if (Files.exists(file)) {
                Files.write(file, List.of(gson.toJson(item)), StandardOpenOption.APPEND);
            } else {
                Files.write(file, List.of(gson.toJson(item)), StandardOpenOption.CREATE);
            }
            //записываем lastId
            long lastId = item.getStaticLastId();
            Files.writeString(lastIdfile, "" + lastId, Charset.defaultCharset(), StandardOpenOption.CREATE);

        } catch (IOException e) {
            System.out.println("some io exception in module MyGenericRepositoryImpl in meth add: "+e.getMessage());
        }
    }

    public void update(T item){
        if (!Files.exists(file)) {
            System.out.println("file "+fileName+" not exist!");
            return;
        }

        try(
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                BufferedWriter out = new BufferedWriter(new FileWriter(tmpFileName))
                )
        {
            String jsonStr;
            Specialty updatingItem;
            while((jsonStr=in.readLine())!=null)  {
                updatingItem = new Gson().fromJson(jsonStr, Specialty.class);

                if (updatingItem.getId() == item.getId()) {
                    jsonStr = gson.toJson(item);
                }

                out.write(jsonStr);
                out.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("oops! File "+fileName+" not found in module MyGenericRepositoryImpl in meth update: "+e.getMessage());
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("some io exception in module MyGenericRepositoryImpl in meth update: "+e.getMessage());
        }
        try {
            Files.move(tmpFile, file, REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("some io exception in module MyGenericRepositoryImpl in meth update: "+e.getMessage());
        }

    }

    @Override
    public void delete(T item) {
        item.setDeleted();
        update(item);
    }

    @Override
    public void unDelete(T item) {
        item.setUnDeleted();
        update(item);
    }

}
