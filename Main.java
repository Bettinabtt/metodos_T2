import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo de entrada: ");
        String fileName = in.nextLine(); 
        
        //ArrayList<String[]> infos = new ArrayList<String[]>();
        HashMap<String, ArrayList<String[]>> infos = new HashMap<>();;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String entrada = br.readLine();
            String line, semanaAtual, proxSemana, chance;

            while ((line = br.readLine()) != null) {
                semanaAtual = line.split(" -> ")[0];
                chance = line.split(" -> ")[1];
                proxSemana = line.split(" -> ")[2];

                if(!infos.keySet().contains(semanaAtual)){
                    infos.put(semanaAtual, new ArrayList<>());
                }

                infos.get(semanaAtual).add(new String[]{chance, proxSemana});                
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        
        System.out.println(infos);
        in.close();
    }
}
