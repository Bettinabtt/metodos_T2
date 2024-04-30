import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Universidade {

    public static void main(String[] args) {
        // Ler a entrada do arquivo
        String fileName = "casos-de-teste/caso1.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int numAlunosSem1 = Integer.parseInt(br.readLine().split(" : ")[1]);
            Map<String, Double> transicoes = lerEntrada(fileName, br);

            // Simulação da universidade
            int numAlunosDiplomados = simularUniversidade(numAlunosSem1, transicoes);
            int numAlunosTotal = calcularTotalAlunos(transicoes, numAlunosSem1, numAlunosDiplomados);

            // Exibindo resultados
            System.out.println("Número de alunos diplomados: " + numAlunosDiplomados);
            System.out.println("Número total de alunos na universidade: " + numAlunosTotal);
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

    public static int simularUniversidade(int numAlunosSem1, Map<String, Double> transicoes) {
    int numAlunosDiplomados = 0;
    int numAlunosSemestre = numAlunosSem1;
    String semestreAtual = "Sem_1";

    while (!semestreAtual.startsWith("Diploma")) {
        List<String> transicoesPossiveis = new ArrayList<>();
        for (String transicao : transicoes.keySet()) {
            String[] partes = transicao.split(" -> ");
            if (partes[0].equals(semestreAtual)) {
                transicoesPossiveis.add(transicao);
            }
        }

        // Selecionar uma transição possível com base nas probabilidades
        String proximoSemestre = selecionarProximoSemestre(transicoesPossiveis, transicoes);

        if (!proximoSemestre.startsWith("Diploma")) {
            int alunosNesteSemestre = (int) (numAlunosSemestre * transicoes.getOrDefault(proximoSemestre, 0.0));
            numAlunosSemestre -= alunosNesteSemestre;
        } else {
            numAlunosDiplomados += numAlunosSemestre;
            numAlunosSemestre = 0;
        }

        semestreAtual = proximoSemestre;
    }

    return numAlunosDiplomados;
}

private static String selecionarProximoSemestre(List<String> transicoesPossiveis, Map<String, Double> transicoes) {
    double valorAleatorio = Math.random();
    double acumulado = 0.0;

    for (String transicao : transicoesPossiveis) {
        acumulado += transicoes.get(transicao);
        if (valorAleatorio <= acumulado) {
            return transicao.split(" -> ")[1];
        }
    }

    // Se não for selecionado, retornar o último como padrão
    return transicoesPossiveis.get(transicoesPossiveis.size() - 1).split(" -> ")[1];
}

    

    public static int calcularTotalAlunos(Map<String, Double> transicoes, int numAlunosSem1, int numAlunosDiplomados) {
        int numAlunosTotal = numAlunosSem1;
        for (Map.Entry<String, Double> entry : transicoes.entrySet()) {
            String transicao = entry.getKey();
            double probabilidade = entry.getValue();

            String[] semestres = transicao.split(" -> ");
            String proximoSemestre = semestres[2];

            if (!proximoSemestre.equals("Diploma 1")) {
                numAlunosTotal += (int) (numAlunosTotal * probabilidade);
            }
        }
        numAlunosTotal -= numAlunosDiplomados; // Removendo alunos que se formaram
        return numAlunosTotal;
    }
}
