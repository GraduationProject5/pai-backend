package backend.util;

import java.io.*;

/**
 * Created by lienming on 2019/1/18.
 */
public class LocalFileHandler {

    private static String home = System.getProperty("user.home");
    private static String location = "Desktop" ;
    private static String rootDir = "users" ;
    private static String userPrefix = "user";
    private static String projectDir = "projects";
    private static String projectPrefix = "project" ;
    private static String tablePrefix = "table" ;
    private static String dataDir = "datasources";
    public static File users = new File(home + File.separator
            + location + File.separator
            + rootDir);

    public static void createUsersRootDir(){
        if(!users.exists())
            users.mkdir();
    }

    public static void init(){
        createUsersRootDir();
    }

    public static boolean createUser(String userID) {
        File user = new File(getUserPath(userID));
        if(user.exists())
            return false ;
        user.mkdir();
        File userProjects = new File(getUserProjectsPath(userID));
        userProjects.mkdir();
        File userDatasources = new File(getUserDatasourcesPath(userID));
        userDatasources.mkdir();
        return true ;
    }

    public static String getUserPath(String userID) {
        return home + File.separator
                + location + File.separator
                + rootDir + File.separator
                + userPrefix + userID ;
    }

    public static String getUserProjectsPath(String userID){
        return home + File.separator
                + location + File.separator
                + rootDir + File.separator
                + userPrefix + userID + File.separator
                + projectDir ;
    }

    public static String getUserDatasourcesPath(String userID){
        return home + File.separator
                + location + File.separator
                + rootDir + File.separator
                + userPrefix + userID + File.separator
                + dataDir ;
    }

    public static boolean importDatasource(String userID,
                                String fileName,String fileContent){
//        fileName = "asd.csv" ;
//        fileContent = "D:\\file\\";
        String targetPath = getUserDatasourcesPath(userID) +
                File.separator + fileName ;

        File file = new File(targetPath) ;
        if(file.exists())
            return false ;
        FileOutputStream fos = null ;
        PrintWriter pw = null ;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos) ;
            pw.write(fileContent);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(pw!=null)
                pw.close();
            if(fos!=null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        return true ;

    }

    public static boolean createProject(String userID,String projectID){
        String targetPath = getUserProjectsPath(userID) +
                File.separator + projectID ;

        File file = new File(targetPath) ;
        if(file.exists())
            return false ;
        file.mkdir();
        return true ;
    }

    public static boolean createProjectTable(){

        return false ;
    }

    public static boolean deleteFile(){
        return false ;
    }

    public static void main(String[] args){
//        System.out.print(LocalFileHandler.createProject("123","1"));
//        System.out.print(LocalFileHandler.importDatasource("123","!","1"));
//       System.out.print(LocalFileHandler.createUser("123"));
    }

}
