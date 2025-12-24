import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;

public class Programme extends JFrame implements ActionListener,KeyListener
{
    public static String valeurStaticDansJtextField="";
    public static String valeurStaticCalculeDeJtexField="" ;
    JLabel afficheur;
    JButton[] boutons=new JButton[20];
    String touches[]= {"7","8","9","+","4","5","6","-","1","2","3","*","(",")","0","/",".","C","S","="};

    public Programme(String titreApplication) 
    {
        // création de la fenêtre
        super(titreApplication);

        ImageIcon image = new ImageIcon("tete.jpg");
        //Permet de charger l'image.
        setIconImage(image.getImage());
        //Permet de définir l'image comme icone de l'application initiale.
        afficheur=new JLabel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Autoriser la fermeture de l'App via le boutton rouge x.

        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran du bureau.
        setResizable(false); //On interdit la redimensionnement de la fenêtre

        setSize(385,400);
        Container contenu=getContentPane();

        contenu.setLayout(null);
        contenu.setBackground(Color.DARK_GRAY);    
         afficheur.setOpaque(true);//pour permettre d'afficher le background sur JLabel.
         afficheur.setBackground(Color.white);//Spécifier couleur Fond.
         
         afficheur.setHorizontalAlignment(SwingConstants.CENTER);
        //On centre les textes dans le Jlabel.
        
        contenu.add(afficheur);
        //Permet d'ajouter au conteneur l'afficheur JLabel.

        afficheur.setBounds(10,10,365,50);
        // réglage des dimensions de la fenêtre du Jlabel.       

        JPanel panneau=new JPanel();
        //Sont les panneau où se places les bouttons.

        panneau.setBackground(Color.DARK_GRAY);
        
        contenu.add(panneau);
        //C'est dans le Container qu'on place les panneaux.

        panneau.setBounds(10,70,365,280);

        panneau.setLayout(new GridLayout(5,4,10,10));
        //GridLayout aligne automatiquement les contenues dans son Layout.
        //(x,y,z,h)
        //x nombre de ligne.
        //y nombre de colonne, si x et y sont spécifié, alors ça équivaut à (x,0)
        //z espace entre l'horizontale.
        //h espace entre la verticalité.
        for(int i=0;i<20;i++)
        {
            boutons[i]=new JButton(touches[i]);
            boutons[i].setBackground(Color.white); 
            panneau.add(boutons[i]);
            //C'est dans le panneau qu'on place les bouttons.

            boutons[i].addActionListener(this);
            //On met un écouteur de click sur le boutton.
           boutons[i].addKeyListener(this);
           //On met un écouteur d'appuie du clavier sur le boutton.
        }

    }
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==boutons[19])//Boutons égal.
        {
            valeurStaticCalculeDeJtexField=resultat_Calcul(valeurStaticDansJtextField);
            valeurStaticDansJtextField=valeurStaticCalculeDeJtexField;
            afficheur.setText(valeurStaticCalculeDeJtexField);
        }
        else if(e.getSource()==boutons[17])//Boutton C(Supprimer tout).
        {
            afficheur.setText("");
            valeurStaticDansJtextField="";
            valeurStaticCalculeDeJtexField="";
        }
        else if(e.getSource()==boutons[18])//Boutton S(Supprimer).
        {
            valeurStaticDansJtextField=enleverUneCaractereParDerriere(valeurStaticDansJtextField);
            afficheur.setText(valeurStaticDansJtextField);
        }
        else
        {
                valeurStaticDansJtextField+=e.getActionCommand();
                afficheur.setText(valeurStaticDansJtextField);
        }
    }

    public void keyPressed(KeyEvent e) //Recuperation touche clavier appuyé.
    {
        if(e.getKeyCode()==8)//Boutons "backSpace(retour)".
        {
        valeurStaticDansJtextField=enleverUneCaractereParDerriere(valeurStaticDansJtextField);
        afficheur.setText(valeurStaticDansJtextField);            
        }
        else if(e.getKeyCode()==10)//Boutons "entré"
        {
            valeurStaticCalculeDeJtexField=resultat_Calcul(valeurStaticDansJtextField);
            valeurStaticDansJtextField=valeurStaticCalculeDeJtexField;
            afficheur.setText(valeurStaticCalculeDeJtexField);
        }
        else if(e.getKeyCode()==127)//Boutons "suppr"
        {
            afficheur.setText("");
            valeurStaticDansJtextField="";
            valeurStaticCalculeDeJtexField="";
        }
        else
        {
            if(siValeurEstAutorise(e.getKeyChar()))
            {
                valeurStaticDansJtextField+=e.getKeyChar();
                afficheur.setText(valeurStaticDansJtextField);
            }
        }

    }
    public void keyReleased(KeyEvent e) //Recuperation touche clavier relaché.
    {}
 
    public void keyTyped(KeyEvent e) 
    {}

    public String resultat_Calcul(String entre)//La méthode permettant de calculer les textes dans JLabel.
    {
        Calculer valeur1=new Calculer(entre);
        return ""+valeur1;
    }
    public String enleverUneCaractereParDerriere(String entre)
    {
        if(entre.length()==0)
            return entre;
        else
        return entre.substring(0,entre.length()-1);
    }
    Boolean siValeurEstAutorise(char entrer)
    {
        Boolean sorti=true;
            if(entrer=='-'||
                entrer=='+'||
                entrer=='*'||
                entrer=='/'||
                entrer=='0'||
                entrer=='1'||
                entrer=='2'||
                entrer=='3'||
                entrer=='4'||
                entrer=='5'||
                entrer=='6'||
                entrer=='7'||
                entrer=='8'||
                entrer=='9'||
                entrer=='('||
                entrer==')'||
                entrer=='.')
                sorti=true;
                else 
                    sorti=false; 
        return sorti;
    }
}
