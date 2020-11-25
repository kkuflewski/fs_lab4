import java.sql.*;
import java.util.Scanner;
import java.lang.*; 
 
public class Main {
   
   public static void main(String[] args) throws SQLException, ClassNotFoundException {
      String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      String DB_URL = "jdbc:mysql://10.0.10.3:3306/kkuflewski?autoReconnect=true&useSSL=false";
      String DB_USER = "KKuflewski";
      String DB_PASS = "KKuflewski";
 
      Statement stmt = null;
      Connection connection = null;
 
      
      try {
	  Class.forName(JDBC_DRIVER);
	  connection = (Connection) DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
          if(connection != null)
          {
              System.out.println("Polaczono!");
              createDB(connection);
              Menu(); System.out.println("Wybierz opcje: ");
	      String input = new Scanner(System.in).next();
	      while(true)
	      {
                switch(input)
                {
                  case "1":
                  {
                    System.out.println("Wprowadz tytul");
                    String TITLE = new Scanner(System.in).next();
                    System.out.println("Wprowadz artyste");
                    String ARTIST = new Scanner(System.in).next();
                    insertAlbum(connection, TITLE, ARTIST);
                    break;
                  }
                    
                  
                  case "2":
                  {
                    showAlbums(connection);
                    break;
                  }
                    
                  
                  case "3":
                  {
                    System.out.println("Wprowadz identyfikator rekordu do usuniecia");
                    String ID = new Scanner(System.in).next();
                    int intId=Integer.parseInt(ID);
                    deleteAlbum(connection, intId);
                    break; 
                  }
                      
                  
                  case "4":
                  {
                      System.out.println("Wprowadz identyfikator rekordu do modyfikacji");
                      String ID = new Scanner(System.in).next();
                      int intId=Integer.parseInt(ID);
                      
                      System.out.println("Wprowadz tytul");
                      String TITLE = new Scanner(System.in).next();
                      
                      System.out.println("Wprowadz artyste");
                      String ARTIST = new Scanner(System.in).next();
                      
                      updateAlbum(connection, intId, TITLE, ARTIST);
                      
                      break;
                  }
                      
                  
                  case "5":
                  {
                      System.exit(0);
                  }
                      
                  
                  default:
                    System.out.println("Wybierz opcje: ");
                    Menu();
                    break;       
                }
 
                Menu();
                System.out.println("Wybierz opcje: ");
                input = new Scanner(System.in).next();
              }
          }
      }
      catch (SQLException ex)
      {
          System.out.println("Nie mozna polaczyc z baza danych");
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
      try {
        Thread.sleep(10000);
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
      
   }
   public static void createDB(Connection conn) throws SQLException
   {
       String sql = "CREATE TABLE Album (ID INT NOT NULL, TITLE VARCHAR(40) NOT NULL, ARTIST VARCHAR(25) NOT NULL, PRIMARY KEY(ID));";
 
       PreparedStatement statement = conn.prepareStatement(sql);
 
       statement.execute(sql);
       System.out.println("Tabela Albumy zostala utworzona");
 
   }
 
      public static void insertAlbum(Connection conn, String title,String artist) throws SQLException
   {
 
       String sql = "INSERT INTO Album (ID, TITLE, ARTIST) VALUES(null, ?, ?)";
 
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setString(1, title);
       statement.setString(2, artist);
 
       int rowsInserted = statement.executeUpdate();
       if(rowsInserted > 0)
       {
           System.out.println("Nowy album zostal dodany");
       }
 
   }
   public static void showAlbums(Connection conn) throws SQLException
   {
       String sql = "SELECT * FROM Album;";
       Statement statement = conn.createStatement();
       ResultSet rs = statement.executeQuery(sql);
 
       while(rs.next())
       {
           int id = rs.getInt("ID");
           String title = rs.getString("TITLE");
           String artist = rs.getString("ARTIST");
 
           System.out.println("ID: "+id+", TITLE: "+title+", ARTIST: "+artist);
       }
       rs.close();
   }
 
   public static void deleteAlbum(Connection conn, int id) throws SQLException
   {
       String sql = "DELETE FROM Album WHERE ID = ?";
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setInt(1, id);
 
       int rowsDeleted = statement.executeUpdate();
       if(rowsDeleted > 0)
       {
           System.out.println("Album o identyfikatorze= "+id+" zostal usuniety.");
       }
   }
 
   public static void updateAlbum(Connection conn, int albumId, String title, String artist) throws SQLException
   {
       String sql = "UPDATE User SET TITLE = ?, ARTIST = ? WHERE ID = ?";
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setString(1, title);
       statement.setString(2, artist);
       statement.setInt(3, albumId);
 
       int rowsUpdated = statement.executeUpdate();
       if(rowsUpdated > 0)
       {
           System.out.println("Album o identyfikatorze= "+albumId+" zostal zmodyfikowany.");
       }
       statement.close();
   }
 
   public static void Menu()
   {
        System.out.println();
        System.out.println("1. Dodaj album");
        System.out.println("2. Pokaz albumy");
        System.out.println("3. Usun album");
        System.out.println("4. Modyfikuj album");
        System.out.println("5. Wyjdz");	
        System.out.println();		
   }
 
 
}
