package br.edu.ifsuldeminas.mch.calc;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ifsuldeminas.mch.calc";
    //DECLARAÇÃO DE VARIÁVEIS
    private Button buttonIgual, buttonSubtracao, numeroTres, numeroDois,
            numeroCinco, numeroSeis, buttonMultiplicacao, numeroSete, numeroOito,
            numeroNove, buttonDivisao, buttonReset, buttonDelete, buttonPorcento, numeroQuatro, numeroUm,
            buttonSoma, buttonVirgula, numeroZero;


    private TextView textViewResultado;
    private TextView textViewUltimaExpressao;

    //ATRIBUIÇÃO DOS BOTOES NAS VARIÁVEIS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IniciarComponentes();
        numeroZero.setOnClickListener(this);
        numeroUm.setOnClickListener(this);
        numeroDois.setOnClickListener(this);
        numeroTres.setOnClickListener(this);
        numeroQuatro.setOnClickListener(this);
        numeroCinco.setOnClickListener(this);
        numeroSeis.setOnClickListener(this);
        numeroSete.setOnClickListener(this);
        numeroOito.setOnClickListener(this);
        numeroNove.setOnClickListener(this);
        buttonVirgula.setOnClickListener(this);
        buttonSoma.setOnClickListener(this);
        buttonSubtracao.setOnClickListener(this);
        buttonMultiplicacao.setOnClickListener(this);
        buttonDivisao.setOnClickListener(this);
        buttonPorcento.setOnClickListener(this);

        //FUNCIONAMENTO DO BOTAO RESET (BASICAMENTE LIMPA TUDO)
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewUltimaExpressao.setText("");
                textViewResultado.setText("");
            }
        });

        //FUNCIONAMENTO DO BOTAO DELETE (DELETA O ULTIMO CARACTERE)
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView expressao = findViewById(R.id.textViewResultadoID); //BUSCA A EXPRESSAO
                String string = expressao.getText().toString(); //TRANSFORMA EM STRING

                String txtExpressao = null;
                if (!string.isEmpty()) { //VERIFICA SE TA VAZIA (NAO DA PRA APAGAR STRING VAZIA)

                    byte var0 = 0; //DEFINE O INICIO DA STRING (PRA USAR NA FUNCAO SUBSTRING DPS) "BYTE" É DESNECESSÁRIO. + PONTOS POR ESTILO

                    int var1 = string.length() - 1; //DEFINE O FIM DA SUBSTRING - 1 (REMOVENDO O ULTIMA CARACTERE)
                    txtExpressao = string.substring(var0, var1); //CRIA UMA NOVA STRING - O ULTIMO CARACTERE OU SEJA
                    // NAO VAMOS "APAGAR" O ULTIMA NUMERO E SIM CRIAR UMA NOVA STRING SEM O ULTIMO NUMERO


                    expressao.setText(txtExpressao); //ATUALIZA O TEXTVIEW COM A NOVA EXPRESSAO
                }
                //textViewResultado.setText(""); //LIMPA O CAMPO DE RESULTADO
            }
        });

        //FUNCIONAMENTO BOTAO DE IGUAL, CONTEM TODA A LOGICA DE CONTA POR MEIO DA BIBLIOTECA exp4j
        buttonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String expressao = textViewResultado.getText().toString();

                    if (expressao.contains("%")) { //AQUI CONTEM A LOGICA DO BOTAO PORCENTAGEM QUE TEM Q SER FEITO NA MAO.
                        String[] partes = expressao.split("%"); //SEPARA A EXPRESSAO EM DUAS PARTES TENDO O SIMBULO DE % COMO MEIO

                        if (partes.length == 2) { //CONFIRMA QUE FOI DIVIDIDO EM 2 PARTES SO
                            double num1 = Double.parseDouble(partes[0].trim()); //PRIMEIRA PARTE
                            double num2 = Double.parseDouble(partes[1].trim()); //SEGUNDA PARTE

                            double resultado = (num1/100) * num2; // RESTO DA DIVISAO
                            textViewResultado.setText(String.valueOf(resultado));
                        } else {
                            textViewResultado.setText("Erro");
                        }

                    } else {
                        Calculable avaliadorExpressao = new ExpressionBuilder(expressao).build(); //CALCULO NORMAL DE QUALQUER EXPRESSAO USANDO O exp4j
                        Double resultado = avaliadorExpressao.calculate();
                        textViewUltimaExpressao.setText(expressao);
                        textViewResultado.setText(resultado.toString());
                    }

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    textViewResultado.setText("Erro");
                }
            }
        });
    }


    //FUNCAO PARA INICIALIZAR TODOS OS COMPONENTES CONFORME SEUS ID'S DOS BOTOES
    public void IniciarComponentes(){
        numeroZero = findViewById(R.id.buttonZeroID);
        numeroUm = findViewById(R.id.buttonUmID);
        numeroDois = findViewById(R.id.buttonDoisID);
        numeroTres = findViewById(R.id.buttonTresID);
        numeroQuatro = findViewById(R.id.buttonQuatroID);
        numeroCinco = findViewById(R.id.buttonCincoID);
        numeroSeis = findViewById(R.id.buttonSeisID);
        numeroSete = findViewById(R.id.buttonSeteID);
        numeroOito = findViewById(R.id.buttonOitoID);
        numeroNove = findViewById(R.id.buttonNoveID);

        buttonSoma = findViewById(R.id.buttonSomaID);
        buttonSubtracao = findViewById(R.id.buttonSubtracaoID);
        buttonMultiplicacao = findViewById(R.id.buttonMultiplicacaoID);
        buttonDivisao = findViewById(R.id.buttonDivisaoID);
        buttonPorcento = findViewById(R.id.buttonPorcentoID);
        buttonVirgula = findViewById(R.id.buttonVirgulaID);
        buttonIgual = findViewById(R.id.buttonIgualID);
        buttonReset = findViewById(R.id.buttonResetID);
        buttonDelete = findViewById(R.id.buttonDeleteID);
        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);
    }

    //MONTA TODAS AS EXPRESSOES
    public void AcrescentarUmaExpressao(String string) {
        String operadores = "+-*/%"; // SETA OS OPERADORES
        String expressaoAtual = textViewResultado.getText().toString(); // SETA O QUE TEM NA EXPRESSAO ATUAL

        if (expressaoAtual.isEmpty()) {
            if (operadores.contains(string) && !string.equals("-")) {
                return; // EVITA INICIAR COM OPERADORES INVALIDOS, EXCETO "-"
            }
            if (string.equals("-")) {
                textViewResultado.append(string); // PERMITE INICIAR COM "-" (NUMERO NEGATIVO)
                return;
            }
            if (string.equals(".")) {
                return; // EVITA INICIAR COM UM PONTO
            }
        }

        // IMPEDIR DOIS PONTOS CONSECUTIVOS
        if (string.equals(".") && expressaoAtual.endsWith(".")) {
            return; // IGNORA SE O ÚLTIMO CARACTERE JÁ FOR UM PONTO
        }

        if (operadores.contains(string)) {
            // INDICA QUE TENTAMOS ADICIONAR UM OPERADOR
            if (!expressaoAtual.isEmpty()) {
                char ultimoCaractere = expressaoAtual.charAt(expressaoAtual.length() - 1);
                if (operadores.contains(Character.toString(ultimoCaractere))) {
                    expressaoAtual = expressaoAtual.substring(0, expressaoAtual.length() - 1); // REMOVE O ULTIMO OPERADOR
                    textViewResultado.setText(expressaoAtual);
                }
            }
            textViewResultado.append(string); // ADICIONA O NOVO OPERADOR
        } else {
            textViewResultado.append(string); // SE FOR NUMERO OU PONTO VÁLIDO, SÓ ADICIONA
        }
    }




    //PERMITE ATRIBUIR O VALOR CORRETO Á TELA DE EXPRESSOES CASO CADA BOTAO SEJA PRESSIONADO
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buttonZeroID) {
            AcrescentarUmaExpressao("0");
        } else if (id == R.id.buttonUmID) {
            AcrescentarUmaExpressao("1");
        } else if (id == R.id.buttonDoisID) {
            AcrescentarUmaExpressao("2");
        } else if (id == R.id.buttonTresID) {
            AcrescentarUmaExpressao("3");
        } else if (id == R.id.buttonQuatroID) {
            AcrescentarUmaExpressao("4");
        } else if (id == R.id.buttonCincoID) {
            AcrescentarUmaExpressao("5");
        } else if (id == R.id.buttonSeisID) {
            AcrescentarUmaExpressao("6");
        } else if (id == R.id.buttonSeteID) {
            AcrescentarUmaExpressao("7");
        } else if (id == R.id.buttonOitoID) {
            AcrescentarUmaExpressao("8");
        } else if (id == R.id.buttonNoveID) {
            AcrescentarUmaExpressao("9");
        } else if (id == R.id.buttonVirgulaID) {
            AcrescentarUmaExpressao(".");
        } else if (id == R.id.buttonSomaID) {
            AcrescentarUmaExpressao("+");
        } else if (id == R.id.buttonSubtracaoID) {
            AcrescentarUmaExpressao("-");
        } else if (id == R.id.buttonMultiplicacaoID) {
            AcrescentarUmaExpressao("*");
        } else if (id == R.id.buttonDivisaoID) {
            AcrescentarUmaExpressao("/");
        } else if (id == R.id.buttonPorcentoID) {
            AcrescentarUmaExpressao("%");
        }
    }
}
//PEGADINHA DO 0 NO TEXTVIEW PADRAO, DE PROPÓSITO?



















