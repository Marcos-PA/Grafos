package Codigo;
public class App {
    public static void main(String[] args) throws Exception {
        LeitorCsv csv = new LeitorCsv("Codigo/cidades.csv");
        System.out.println(csv.headers());
        while(csv.hasNextLine()){
            System.out.print(csv.lineCount() + " ");
            System.out.println(csv.nextLine().get("city"));    
        }
    }
}
