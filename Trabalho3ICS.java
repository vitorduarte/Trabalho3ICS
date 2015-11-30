package trabalho3ics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import sintese.InstrumentoAditivo;
import sintese.Som;

public class Trabalho3ICS extends JPanel implements ActionListener {

    private  int largura = 390;
    private  int altura  = 280;
    private long inicio = 0;
    
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final double[] frequencias = {32.7032, 34.6478, 36.7081, 38.8909,
                                               41.2034, 43.6535, 46.2493, 48.9994, 
                                               51.9131, 55.000, 58.2705, 61.7354};
    

    JButton botaoAbrir, botaoMostrar;
    JTextField caminhoArq, Mostrador;
    JFileChooser pa;
    
    FileWriter arq = null;
            

    private Sequencer  sequenciador = null;
    private Sequence   sequencia;
    
    private Som som=null;
    private int andamento_atual = 100;
    private static final long serialVersionUID = 1L;
    InstrumentoAditivo Ins1;


            
    public Trabalho3ICS() {
        super(new BorderLayout());
 
        
        JPanel painelLog = new JPanel();
        caminhoArq = new JTextField();
        caminhoArq.setText(System.getProperty("user.dir"));
        caminhoArq.setEditable(false);
        painelLog.add(caminhoArq);


        pa = new JFileChooser();
        botaoAbrir = new JButton("Abrir");
        botaoMostrar = new JButton("Mostrar mensagens");
        

        

        botaoAbrir.addActionListener(this);
        botaoMostrar.addActionListener(this);
        
        botaoMostrar.setEnabled(false);
        
 
        
        JPanel botaoPainel = new JPanel(); 
        botaoPainel.add(botaoAbrir);
        botaoPainel.add(botaoMostrar);
  
        //adicionando o botao e log aos paineis painel
        add(painelLog, BorderLayout.BEFORE_FIRST_LINE);
        add(botaoPainel, BorderLayout.PAGE_END);
        
    }
    public void Abrir(){
        JFileChooser selecao = new JFileChooser(".");  
        selecao.setFileSelectionMode(JFileChooser.FILES_ONLY);              
        selecao.setFileFilter(new FileFilter() {
            public boolean accept(File f){
                if (!f.isFile()) 
                    return false;
                   
                String name = f.getName().toLowerCase();
                    
                return name.endsWith(".mid") || name.endsWith(".midi");
                    
            }

            public String getDescription(){

                return "Arquivo Midi (*.mid,*.midi)";
            }
        });

        selecao.showOpenDialog(this);  

        if(selecao.getSelectedFile() != null){
            caminhoArq.setText(selecao.getSelectedFile().toString());  
            File arqseqnovo = selecao.getSelectedFile();
            try { 
                if(sequenciador!=null && sequenciador.isRunning()) { 
                    sequenciador.stop();
                    sequenciador.close();
                    sequenciador = null;
                }
                Sequence sequencianova = MidiSystem.getSequence(arqseqnovo);
                sequencia = sequencianova;
                double duracao = sequencianova.getMicrosecondLength()/1000000.0d;
                 
                //botaoMOSTRADORarquivo.setText("Arquivo: \"" + arqseqnovo.getName() + "\"");                
                //botaoMOSTRADORduracao.setText("\nDura\u00e7\u00e3o:"+ formataInstante(duracao));                   
                  
                botaoMostrar.setEnabled(true);                                  
            }
            catch (Throwable e1) { 
                System.out.println("Erro em carregaArquivoMidi: "+ e1.toString());
            }
        }

    }
    
    public void Mostrar(String caminho){
        
        
        try{
            //File arqmidi = new File(caminho);
            

            File file = new File("./Teste.java");
            arq = new FileWriter(file);
            arq.write("import java.awt.EventQueue;\n");
            arq.write("import java.awt.Font;\n");
            arq.write("import java.awt.event.ActionEvent;\n");
            arq.write("import java.awt.event.ActionListener;\n");
            arq.write("import java.util.Hashtable;\n");
            arq.write("import java.util.LinkedList;\n");

            arq.write("import javax.swing.JButton;\n");
            arq.write("import javax.swing.JFrame;\n");
            arq.write("import javax.swing.JLabel;\n");
            arq.write("import javax.swing.JPanel;\n");
            arq.write("import javax.swing.JSlider;\n");
            arq.write("import javax.swing.JTextField;\n");
            arq.write("import javax.swing.border.EmptyBorder;\n");
            arq.write("import javax.swing.event.ChangeEvent;\n");
        	arq.write("import javax.swing.event.ChangeListener;\n");

        	arq.write("import sintese.Curva;\n");
        	arq.write("import sintese.Envoltoria;\n");
        	arq.write("import sintese.InstrumentoAditivo;\n");
        	arq.write("import sintese.Melodia;\n");
        	arq.write("import sintese.Nota;\n");
        	arq.write("import sintese.Oscilador;\n");
        	arq.write("import sintese.Polifonia;\n");
        	arq.write("import sintese.Som;\n");
        	arq.write("import sintese.UnidadeH;\n");
        	arq.write("import sintese.Voz;\n");
        	arq.write("import javax.swing.ImageIcon;\n");
        	
            arq.write("public class Teste{\n");
            arq.write("\tInstrumentoAditivo Ins1;\n");
            
            arq.write("\tpublic Teste(){\n");
            arq.write("\t\tCurva c_sustain = new Curva(512);\n");
            arq.write("\t\tc_sustain.addPonto(0, 1750);\n");
            arq.write("\t\tc_sustain.addPonto(512, 1750);\n");
            arq.write("\t\tEnvoltoria sustain = new Envoltoria(c_sustain);\n");
            arq.write("\t\tUnidadeH uH1 = new UnidadeH();\n");
            arq.write("\t\tuH1.setEnvoltoria(sustain);\n");
            arq.write("\t\tuH1.setLambda(0.5f);\n");
            arq.write("\t\tOscilador osc = new Oscilador();\n");
            arq.write("\t\tosc.setFrequencia(700f);\n");
            arq.write("\t\tosc.setFase(1.2f);\n");
            arq.write("\t\tuH1.setOscilador(osc);\n");
            arq.write("\t\tIns1 = new InstrumentoAditivo();\n");
            arq.write("\t\tIns1.addUnidade(uH1);\n");
            
            Track[] tracks = sequencia.getTracks();
            int j=3;//Trilha escolhida
                arq.write("\t\t Melodia mel"+j+"= new Melodia();");
                arq.write("\n" + getInformacoes(sequencia, j));
                arq.write("\t\t Som som"+ j +"= mel"+ j +".getSom(Ins1);\n");
                arq.write("\t\t som"+ j +".salvawave();\n");
                arq.write("\t\t som"+ j +".visualiza();\n");

              
            arq.write("\t}\n");
            
            arq.write("\tpublic static void main(String[] args) {\n");
            arq.write("\t\tTeste teste = new Teste();\n");
            arq.write("\t}\n");
            arq.write("}\n");
            
            arq.close();

			System.out.println("Done");
        } catch (IOException e) {
			e.printStackTrace();
		}
            
            
            /*sequencia    = MidiSystem.getSequence(arqmidi);
            
            sequenciador = MidiSystem.getSequencer();  
            sequenciador.setSequence(sequencia); 
            sequenciador.open();  
            Thread.sleep(500);
            sequenciador.start();
            getInformacoes(sequencia);
  
                
                
            
        }
        catch(InvalidMidiDataException e2) { 
            System.out.println(e2+" : Erro nos dados midi."); 
        }
        catch(IOException e3) { 
            System.out.println(e3+" : O arquivo midi nao foi encontrado.");   
        }
        catch(Exception e){  
            System.out.println(e.toString());  
        }  */
    }




    public void actionPerformed(ActionEvent e) {
 
        if (e.getSource() == botaoAbrir) {
            Abrir();
        }
        else if(e.getSource() == botaoMostrar){
            Mostrar(caminhoArq.getText());

        }

    }


    private static void createAndShowGUI() {
        //Cria a janela
        JFrame janela = new JFrame("Tocador de mid");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adciona o busca arquivo na janela
        janela.add(new Trabalho3ICS());

        //Mostra a janela
        janela.pack();
        janela.setVisible(true);
    }
    public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    UIManager.put("swing.boldMetal", Boolean.FALSE); 
                    createAndShowGUI();
                }
            });
        }

    public String getInformacoes(Sequence sequencia, int n_trilha)
    {
        Track[] trilhas = sequencia.getTracks();
        Track trilha = trilhas[n_trilha];
        String saida = "";
        String saida2;

        int j = 0;
        int val = 0;

        float[] inicio = new float[5000];
        float[] duracao = new float[5000];
        float[] key = new float[5000];
        float[] ampl = new float[5000];
        double[] freq = new double[5000];
        
        while(j < trilha.size()){
            MidiMessage mensagem = trilha.get(j).getMessage();
            if(mensagem instanceof ShortMessage)
            {
                ShortMessage mensagemNota = (ShortMessage)mensagem;

                if(mensagemNota.getCommand() == NOTE_ON)
                {
                    inicio[val] = trilha.get(j).getTick();
                    key[val] = (float)mensagemNota.getData1();
                    int indiceFreq = (int)key[val]/12;
                    int oitava = (int)(key[val]/12)-1;
                    freq[val] = frequencias[indiceFreq]*oitava;
                    ampl[val] = mensagemNota.getData2();
                    val++;
                    
                    
                }
                if (mensagemNota.getCommand() == NOTE_OFF)
                {
                    int p = 0;
                    while(p < val)
                    {
                        if(duracao[p]!= 0){

                        }
                        else if(key[p] == mensagemNota.getData1())
                        {
                            float fim = trilha.get(j).getTick();
                            duracao[p] = fim - inicio[p];
                            saida2 = ("\t\tNota n" + p + " = new Nota(" + duracao[p]/1000 + ", " + freq[p] + ", " + ampl[p]/5+");\n" +
                            "\t\tmel" + n_trilha + ".addNota(n" + p + ");\n");
                            saida = saida.concat(saida2);
                        }
                        p++;
                    }
                    

                }
                    
                }
            j++;
            }
            return saida;
        }   
    }