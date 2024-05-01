import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Universidade {
    
    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        System.out.println("Digite o nome do arquivo: ");
        String fileName = in.nextLine();

        in.close();
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int numAlunosSem1 = Integer.parseInt(br.readLine().split(" : ")[1]);
            Map<String, Double> transicoes = lerEntrada(fileName, br);
            Map<String, Integer> semestres = mapSemestres(transicoes);
            semestres.put("Sem_1", numAlunosSem1);

            // Simulação da universidade
            int[] simulacao = simularUniversidade(transicoes, semestres);
            
            // Exibindo resultados
            System.out.println("Número de alunos diplomados: " + simulacao[0]);
            System.out.println("Número total de alunos na universidade: " + simulacao[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Map<String, Double> lerEntrada(String fileName, BufferedReader br) throws IOException{
        Map<String, Double> transicoes = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" -> ");
            String transicao = parts[0] + " -> " + parts[2];
            double probabilidade = Double.parseDouble(parts[1]);
            transicoes.put(transicao, probabilidade);
        }
        return transicoes;
    }

    public static HashMap<String, Integer> mapSemestres(Map<String, Double> transicoes){
        HashMap<String, Integer> semestres = new HashMap<String, Integer>();

        for(String transicao : transicoes.keySet()){
            String semestre = transicao.split(" -> ")[1];
            if(!semestres.containsKey(semestre)) semestres.put(semestre, 0);
        }

        return semestres;
    }

    public static int[] simularUniversidade(Map<String, Double> transicoes, Map<String, Integer> semestres){
        String semestreAtual = "Sem_1";
        int numAlunos = semestres.get(semestreAtual), i = 1;

        while (i < semestres.size()){          
            for (String transicao : transicoes.keySet()) {
                String[] partes = transicao.split(" -> ");
                if (partes[0].equals(semestreAtual)) {
                    double prob = transicoes.get(transicao);
                    semestres.put(partes[1], (int)(numAlunos * prob));
                }
            }
            i++;
            semestreAtual = "Sem_" + i;
            numAlunos = i == semestres.size() ? semestres.get("Diploma") : semestres.get(semestreAtual);
        }

        int totalAlunos = 0;

        for(String semestre : semestres.keySet()){
            if(semestre == "Diploma") continue;
            else totalAlunos += semestres.get(semestre);
        }

        int[] resultado = {numAlunos, totalAlunos};
        return resultado;
    }
    
}
