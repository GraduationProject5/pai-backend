package backend.util;

import backend.vo.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lienming on 2019/1/18.
 */
public class LocalFileHandler {

    private static String home = System.getProperty("user.home");
    private static String location = "Desktop" ;
    private static String rootDir = "users" ;

//    private static String userPrefix = "user";
    private static String projectDir = "projects";
    private static String projectPrefix = "project" ;
    private static String tablePrefix = "table" ;
    private static String dataDir = "datasources";

    private static String lineSeperator = System.getProperty("line.separator");

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
                + userID ;
    }

    public static String getUserProjectsPath(String userID){
        return home + File.separator
                + location + File.separator
                + rootDir + File.separator
                + userID + File.separator
                + projectDir ;
    }

    public static String getUserDatasourcesPath(String userID){
        return home + File.separator
                + location + File.separator
                + rootDir + File.separator
                + userID + File.separator
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

    public static boolean createProjectTable
            (String userID, String projectID, Table table){
        String targetPath = getUserProjectsPath(userID) +
                File.separator + projectID + table.tableName ;
        File file = new File(targetPath) ;
        if(file.exists())
            return false ;

        FileOutputStream fos = null ;
        PrintWriter pw = null ;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos) ;
            pw.write(table.toString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    public static boolean deleteDataFile(String userID,String fileName){
        String targetPath = getUserDatasourcesPath(userID) +
                File.separator + fileName ;
        File file = new File(targetPath) ;
        if(file.exists()&&file.isFile()){
            if(file.delete())
                return true;
            else return false;
        }
        return false ;
    }

    public static boolean deleteProjectFile(String userID,String projectID){
        String targetPath = getUserProjectsPath(userID) +
                File.separator + projectID;
//        if (!targetPath.endsWith(File.separator))
//            targetPath = targetPath + File.separator;
        File dirFile = new File(targetPath) ;

        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false ;
        }

        File[] files = dirFile.listFiles();
        boolean flag = true ;
        for(File f : files ) {
            if(f.exists()&&f.isFile()){
                flag = f.delete();
                if(!flag)
                    break;
            }
            else if(f.isDirectory()){
                //...
                flag = false ;
                break;
            }
        }
        return flag ;
    }

    public static boolean deleteTableFile
            (String userID,String projectID,String tableName){
        String targetPath = getUserProjectsPath(userID) +
                File.separator + projectID + File.separator + tableName;
        File file = new File(targetPath) ;
        if(file.exists()&&file.isFile()){
            if(file.delete())
                return true;
            else return false;
        }
        return false ;
    }



    public static void main(String[] args){

//        System.out.print(LocalFileHandler.createProject("123","1"));
//        System.out.print(LocalFileHandler.importDatasource("123","!","1"));
//       System.out.print(LocalFileHandler.createUser("123"));
//        System.out.print(LocalFileHandler.deleteDataFile("123","asd.csv"));
//        System.out.print(LocalFileHandler.deleteProjectFile("123","1"));
    }

}
