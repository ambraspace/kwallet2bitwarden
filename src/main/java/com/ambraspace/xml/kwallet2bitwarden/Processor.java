package com.ambraspace.xml.kwallet2bitwarden;

import java.io.File;

import com.ambraspace.xml.kwallet2bitwarden.vault.BitwardenVault;
import com.ambraspace.xml.kwallet2bitwarden.vault.SecureNoteItem;
import com.ambraspace.xml.kwallet2bitwarden.xml.Folder;
import com.ambraspace.xml.kwallet2bitwarden.xml.Password;
import com.ambraspace.xml.kwallet2bitwarden.xml.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class Processor
{

    public static void main(String[] args)
    {

        if (args == null || args.length == 0)
        {
            System.out.println("No XML file provided!");
            return;
        }

        if (args.length > 1)
        {
            System.out.println("Too many arguments provided!");
            return;
        }

        File inputFile = new File(args[0]);

        if (!inputFile.exists())
        {
            System.out.println("Provided file doesn't exist!");
            return;
        }

        if (!inputFile.canRead())
        {
            System.out.println("Provided file is not readable!");
            return;
        }

        Wallet wallet = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class, Folder.class, Password.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            wallet = (Wallet) jaxbUnmarshaller.unmarshal(inputFile);
            makeBitwardenFile(wallet);
        } catch (Exception e) {
            System.out.println("Error processing file!");
            e.printStackTrace();
        }

    }

    private static void makeBitwardenFile(Wallet wallet)
    {

        BitwardenVault vault = new BitwardenVault();

        for (Folder folder:wallet.getFolders())
        {

            if (folder.getPasswords() == null || folder.getPasswords().isEmpty())
                continue;

            for (Password password:folder.getPasswords())
            {
                SecureNoteItem item = new SecureNoteItem();
                item.setType(2);
                item.setName(password.getName());
                item.setNotes(password.getPassword());
                vault.getItems().add(item);
            }

        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try
        {
            System.out.println(mapper.writeValueAsString(vault));
        } catch (Exception e)
        {
            System.err.println("Unable to serialize!");
            e.printStackTrace();
        }


    }

}
