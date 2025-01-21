package org.example;

import com.jcraft.jsch.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String userName = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        String password = args[3];

        String remoteDirectory = "/SFTPTest";
        String remoteFileName = "addresses.json";
        String remoteFilePath = remoteDirectory + "/" + remoteFileName;
        String localOriginalFilePath = args[4];
        String localTempFileCopy = "tempAddresses.json";

        JSch jSch = new JSch();

        Session session = null;
        ChannelSftp channel = null;

        try{
            session = jSch.getSession(userName, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking","no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            try{
                channel.cd(remoteDirectory);
                System.out.println("Remote directory already exists");
            } catch (SftpException e) {
                System.out.println("Remote directory does not exist, need to create it");
                channel.mkdir(remoteDirectory);
                channel.cd(remoteDirectory);
            }
            boolean ifRemoteFileExist = true;
            try{
                channel.lstat(remoteFilePath);
                System.out.println("Remote file already exists");
            } catch (SftpException e) {
                System.out.println("Remote file does not exist, need to put if from local machine");
                ifRemoteFileExist = false;
            }
            if(!ifRemoteFileExist){
                if(!Files.exists(Paths.get(localOriginalFilePath))){
                    System.err.println("local file has not been found");
                    return;
                }
                channel.put(localOriginalFilePath,remoteFilePath);
                System.out.println("File uploaded to remoteDirectory");
            }
            channel.get(remoteFilePath,localTempFileCopy);
            System.out.println("local copy created");

            Menu.menu(localTempFileCopy);

            channel.put(localTempFileCopy,remoteFilePath);
            System.out.println("Filecopy uploaded to remoteDirectory");

            File tempFile = new File(localTempFileCopy);
            if(tempFile.exists()){
                if(tempFile.delete()){
                    System.out.println("temporary file deleted");
                } else  {
                    System.err.println("failed to delete");

                }
            }
            System.out.println("Program completed successfully");
            System.exit(0);

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

    }
}