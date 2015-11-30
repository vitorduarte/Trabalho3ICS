
package trabalho3ics;



import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.filechooser.FileFilter;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;

import javax.sound.midi.InvalidMidiDataException;

import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;

public class Trabalho3ICS extends JPanel implements ActionListener {

    private  int largura = 390;
    private  int altura  = 280;
    private long inicio = 0;
    
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    

    JButton botaoAbrir, botaoMostrar;
    JTextField caminhoArq, Mostrador;
    JFileChooser pa;
            

    private Sequencer  sequenciador = null;
    private Sequence   sequencia;

            
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
            File arqmidi = new File(caminho);
            
            sequencia    = MidiSystem.getSequence(arqmidi);
            
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
        }  
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

    public void getInformacoes(Sequence sequencia)
    {
        Track[] trilha = sequencia.getTracks();
        int i = 0;
        while(trilha[i] != null){
            System.out.println("");
            System.out.println("Trilha: " + i );
            System.out.println("");
            getDuracao(trilha[i]);
            i++;
        }
    }
    public void getDuracao(Track trilha)
    {
        int j = 0;
        int val = 0;
        float[] inicio = new float[5000];
        float[] duracao = new float[5000];
        float[] key = new float[5000];
        
        while(j < trilha.size()){
            MidiMessage mensagem = trilha.get(j).getMessage();
            
            
            
            if(mensagem instanceof ShortMessage)
            {
                ShortMessage mensagemNota = (ShortMessage)mensagem;

                
                if(mensagemNota.getCommand() == NOTE_ON)
                {
                    inicio[val] = trilha.get(j).getTick();
                    key[val] = (float)mensagemNota.getData1();
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
                            System.out.println("Tempo" + inicio[p] + " Nota:" + p + " Duracao: " + duracao[p]);

                        }
                        p++;
                    }
                    

                }
                    
                }
            j++;
            }
            
        }
        
    }
