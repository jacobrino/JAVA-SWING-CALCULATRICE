import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
public class Calculer
{
	private String valeurCalculer;//Atribut pour le construceur.
/**********************************************************************************************/
	public Calculer()//Constructeur vide.
	{	
		valeurCalculer="Aucune valeur entree .";
	}
	public Calculer(String entrer)//Surcharge du Constructeur.
	{
		valeurCalculer=CalculeAFaire(entrer);
	}
	public String toString()
	{
		return valeurCalculer;
	}
/*************Les détailes nécessaires utilies pour gérer les erreurs.***************/
	private String retourneErreur(String entrer)
	{
		//Ne pas autoriser les caractères autres que :
		// '*' , '-' , '+' , '/', '.' , 'chiffres' , '(' , ')'
		if(siExisteCaractereNonAutoriseDansLentrer(entrer))
		return "404ErrorCaractereNonAutorise.";
		else if(siLaFinEstOperandeNonAutorise(entrer))
		return "404ErrorLafinEstOperandeNonAutorise.";
		else if(siEntrerEstVide(entrer))
		return "Aucune valeur entree.";
		else if(siLeDebutEstOperandeNonAutorise(entrer))
		return "404ErrorLeDebutEstOperandeNonAutorise. ";
		else if(siExisteDeuxCaractereSuccessifOpperandeNonAutorise(entrer))
		return "404ErrorDeuxCaractereSuccessifOpperandeNonAutorise.";	
		else if(siExisteDivisionParZero(entrer))
			return "404ErrorDivisionParZero.";
		else if(siExisteParentheseVideInterieure(entrer))
			return "404ErrorParentheseVideInterieure.";
		else if(siExisteParentheseNonFerme(entrer))
			return "404ErrorParentheseNonFerme.";
		else if(siExisteParentheseIncorrecte(entrer))
			return "404ErrorParentheseIncorrecte.";
		else if(siExisteParentheseMalPlace(entrer))
			return "404ErrorParentheseMalPlace.";
		else if(siExisteDeuxPointSuccessif(entrer))
			return "404ErrorDeuxPointSuccessif.";
		return "";
	}
	private Boolean testerSiExisteErreur(String entrer)
	{
		if(siExisteCaractereNonAutoriseDansLentrer(entrer)||
		siLaFinEstOperandeNonAutorise(entrer)||
		siEntrerEstVide(entrer)||
		siLeDebutEstOperandeNonAutorise(entrer)||
		siExisteDeuxCaractereSuccessifOpperandeNonAutorise(entrer)||
		siExisteDivisionParZero(entrer)||
		siExisteParentheseVideInterieure(entrer)||
		siExisteParentheseNonFerme(entrer)||
		siExisteParentheseIncorrecte(entrer)||
		siExisteParentheseMalPlace(entrer)||
		siExisteDeuxPointSuccessif(entrer))
			return true;
		else
			return false;
	}
	private Boolean siEntrerEstVide(String entrer)
	{
		return entrer.length()==0;
	}
	private Boolean siExisteCaractereNonAutoriseDansLentrer(String entrer)
	{
		Boolean a=false;
		for(int i=0;i<entrer.length();i++)
		{
			if(siCaractereParmisChiffre(entrer.charAt(i))||siCaractereParmisOperandeEtParenthese(entrer.charAt(i))||entrer.charAt(i)=='.')
			{
			a=false;
			}
			else
			{
				a=true;
				break;
			}
		}
		return a;
	}
	//Dans le cas ou une parenthèse est manquante.
	private Boolean siExisteParentheseNonFerme(String entrer)
	{
		int nombre=0;
		for(int i=0;i<entrer.length();i++)
		{
			if(entrer.charAt(i)=='('||entrer.charAt(i)==')')
				nombre++;
		}
		return nombre%2!=0;
	}
	//Dans le cas ou les deux parenthèses doit être égaux, lorsqu'on ouvre,
	//on doit fermer.
	private Boolean siExisteParentheseIncorrecte(String entrer)
	{
		int compteurOuvert=0;
		int compteurFerme=0;
		for(int i=0;i<entrer.length();i++)
		{
			if(entrer.charAt(i)=='(')
				compteurOuvert++;
			else if(entrer.charAt(i)==')')
				compteurFerme++;
			else
				continue;
		}
		return compteurOuvert!=compteurFerme;
	}
	//Dans le cas ou les parenthès sont mals placés.
	private Boolean siExisteParentheseMalPlace(String entrer)
	{
		String recupererValeurEntreParenthese="";
		String recupereValeurSorti=entrer;
		Boolean autorisation=true,resultat_Sorti=true;
		
		if(nombreTotalParenthese(entrer)==0)
			resultat_Sorti=false;
		else if(nombreTotalParenthese(entrer)==1)
			resultat_Sorti=true;
		else
		{
				for(int i=0;i<entrer.length();i++)
				{
						if(entrer.charAt(i)==')'&&autorisation==true)
						return true;
						else if(entrer.charAt(i)=='(')
						autorisation=false;

						if(i==dernierPositionIndiceParentheseOuverte(entrer))
						{
						autorisation=false;
						if(siExisteParentheseFermeApresIndice(entrer,i))
						{
							for(int j=i+1;j<prochaineIndicePositionAPartirIndiceParentheseFerme(entrer,i);j++)
							{
								recupererValeurEntreParenthese=recupererValeurEntreParenthese+entrer.charAt(j);
							}
						}
						else
							{
								resultat_Sorti=true;
								break;
							}
						recupereValeurSorti=prendreResteCaractere(entrer,true,i)+recupererValeurEntreParenthese+prendreResteCaractere(entrer,false,prochaineIndicePositionAPartirIndiceParentheseFerme(entrer,i+1));
						return siExisteParentheseMalPlace(recupereValeurSorti);
						}
				}
				
		}
		return resultat_Sorti;
	}
	private Boolean siExisteDeuxPointSuccessif(String entrer)
	{
		Boolean sorti=false;
		for(int i=0;i<entrer.length()-1;i++)
		{
			if(entrer.charAt(i)=='.'&&entrer.charAt(i+1)=='.')
			{
				sorti=true;
				break;
			}
		}
		return sorti;
	}

	//Gerer l'erreur si dans une parenthese est vide.
	private Boolean siExisteParentheseVideInterieure(String entrer)
	{
		Boolean a=false;
		for(int i=0;i<entrer.length()-1;i++)
		{
				if(entrer.charAt(i)=='('&&entrer.charAt(i+1)==')')
				{
					a=true;
					break;
				}
		}
		return a;
	}
	//Gerer l'erreur si on divise par zéro.
	private Boolean siExisteDivisionParZero(String entrer)
	{
		boolean a=false;
		for(int i=0;i<entrer.length();i++)
		{
			if(entrer.charAt(i)=='/'&&siResteApresVautZero(entrer,'/',i,determinerIndiceFinCaractere(entrer,'/',i)))
				a=true;
		}
		return a;
	}
	private Boolean siLeDebutEstOperandeNonAutorise(String entrer)
	{
		return (entrer.startsWith("*")||entrer.startsWith("/")||entrer.startsWith("."));
	}
	private Boolean siLaFinEstOperandeNonAutorise(String entrer)
	{
		return (entrer.endsWith("*")||entrer.endsWith("+")||entrer.endsWith("-")||entrer.endsWith("/")||entrer.endsWith("."));
	}	
	private static Boolean siExisteDeuxCaractereSuccessifOpperandeNonAutorise(String entrer)
	{
		Boolean a=false;
		for(int i=0;i<entrer.length();i++)
		{
			if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='*')
			{
				a=true;
				break;
			}			
			else if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='*')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='/')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='/')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='*')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='/')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='/')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='*')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='+')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='-')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='-')
			{
				a=true;
				break;
			}	
			else if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='+')
			{
				a=true;
				break;
			}	
			else
			continue;	
		}
		return a;
	}
/**********************************************************************************************/
	//Methode utilises pour la partie logique du calculatrice et gérant les erreurs.
	private int nombreTotalParenthese(String entrer)
	{
		int nombre=0;
			for(int i=0;i<entrer.length();i++)
			{
				if(entrer.charAt(i)=='('||entrer.charAt(i)==')')
					nombre++;
			}
		return nombre;
	}
	private int dernierPositionIndiceParentheseOuverte(String entrer)//Premiere étape.
	{
		int dernierePosition=0;
		for(int i=0;i<entrer.length();i++)
		{
			if(entrer.charAt(i)=='(')
				dernierePosition=i;
		}
		return dernierePosition;
	}
	private int prochaineIndicePositionAPartirIndiceParentheseFerme(String entrer, int indice)//Deuxième étape.
	{
		int prochainePosition=0;
		for(int i=indice+1;i<entrer.length();i++)
		{
			if(entrer.charAt(i)==')')
			{
				prochainePosition=i;
				break;
			}
		}
		return prochainePosition;
	}
	private Boolean siExisteParentheseFermeApresIndice(String entrer, int indice)
	{
		Boolean a =false;
		for(int i=indice+1;i<entrer.length();i++)
		{
				if(entrer.charAt(i)==')')
				{
					a=true;
					break;
				}
		}
		return a;
	}
	private Boolean siCaractereParmisOperande(char caractere)
	{
		return (caractere=='*'||caractere=='+'||caractere=='-'||caractere=='/');
	}
	private Boolean siCaractereParmisChiffre(char caractere)
	{
		return (caractere=='0'||caractere=='1'||caractere=='2'||caractere=='3'||caractere=='4'||caractere=='5'||caractere=='6'||caractere=='7'||caractere=='8'||caractere=='9');
	}
	private String enleverExponentielleEtForcerSortiPointsurDouble(double a)
	{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    	symbols.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat();
		f.setDecimalFormatSymbols(symbols);//forcer à afficher point pour le décimal.
		f.setMaximumFractionDigits(20);//limiter à 20 les chiffres apres la virgule.
		f.setGroupingUsed(false);//pour désactiver l'espace pour les chiffres milliers.
		return (f.format(a)).toString();
	}
	private int determinerIndiceFinCaractere(String entrer, char caractere,int debut)
	{
		int sortie=0;
			for(int i=debut;i<entrer.length();i++)
			{
					if(entrer.charAt(i)==caractere)
					{
						for(int z=i+1;z<entrer.length();z++)
						{
							if(siCaractereParmisOperande(entrer.charAt(z)))
							{
								sortie=z;
								return sortie;
							}
								if(z==entrer.length()-1)
								{
									sortie=z;
									break;
								}
						}
					}
			}
			return sortie;
	}
	//Si le reste à partir de debut vaut zéro.
	private Boolean siResteApresVautZero(String conversionDouble, char oper,int debut, int fin)
	{
		boolean a=true;
		for(int i=debut;i<fin;i++)
		{
			if(conversionDouble.charAt(i)==oper)
			{
					for(int j=i+1;j<=fin;j++)
					{
						if(conversionDouble.charAt(j)!='0')
						{
							a=false;
							break;
						}
					}
			}
		}
		return a;
	}
	//Inverser caractère à caracte les nombres.
	private String inverseNombre(String entrer)
	{
		String resultat_Sorti="";
			for(int i=entrer.length()-1;i>=0;i--)
			{
				resultat_Sorti=resultat_Sorti+entrer.charAt(i);
			}
		return resultat_Sorti;
	}
		//Recuperer Nombre de versAvant ou de versAprès avec "debut" comme entré pour la Multiplication et Division .
	private String retourneValeurNombrePourMultiplicationEtDivision(String entrer, boolean versAvant, int debut)
	{
	String operande_Recuperer="";	
		if(versAvant)
		{
		operande_Recuperer="";
			for(int i=debut-1;i>=0;i--)
			{
				if(i==0&&(entrer.charAt(i)=='+'||entrer.charAt(i)=='-'))
				{						 			
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	
				}
				else if(siCaractereParmisChiffre(entrer.charAt(i))||entrer.charAt(i)=='.')
				{
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	
				}
				else
					break;
			}
			return inverseNombre(operande_Recuperer);
			//au début le nombre est inversé..donc il faudrait l'inverser à nouveau.
		}
		else
		{
			operande_Recuperer="";
			while(siCaractereParmisChiffre(entrer.charAt(debut+1))||entrer.charAt(debut+1)=='.')
			{
				operande_Recuperer=operande_Recuperer+entrer.charAt(debut+1);
				if(debut==entrer.length()-2)
					break;
				else
				debut++;
			}
			return operande_Recuperer;
		}
	}
	private String retourneValeurNombrePourAdditionEtSoustraction(String entrer, boolean versAvant, int debut)
	{
	String operande_Recuperer="";	
		if(versAvant)
		{
		operande_Recuperer="";
			for(int i=debut-1;i>=0;i--)
			{
				if(i==0&&(entrer.charAt(i)=='+'||entrer.charAt(i)=='-'))
				{	
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	//Utilise pour prendre signe au début index 0.
				}
				else if(siCaractereParmisChiffre(entrer.charAt(i))||entrer.charAt(i)=='.')
				{
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	
				}
				else if(entrer.charAt(i)=='+'||entrer.charAt(i)=='-')
				{
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	
					break;
				}
				else
					break;
			//au début le nombre est inversé..donc il faudrait l'inverser à nouveau pour reconstutier la chaine initiale.
			}
			return inverseNombre(operande_Recuperer);
		}
		else
		{
			operande_Recuperer="";
			while(siCaractereParmisChiffre(entrer.charAt(debut+1))||entrer.charAt(debut+1)=='.')
			{
				operande_Recuperer=operande_Recuperer+entrer.charAt(debut+1);
				if(debut==entrer.length()-2)
					break;
				else
				debut++;
			}
			return operande_Recuperer;
		}
	}
	//Recuper le reste de la chaine...
	private String prendreResteCaractere(String entrer, boolean versAvant,int debut)
	{
		String operande_Recuperer="";
		if(versAvant)
		{
			while(debut>0)
			{
				operande_Recuperer=operande_Recuperer+entrer.charAt(debut-1);
				debut--;
			}
			return inverseNombre(operande_Recuperer);
			//au début le nombre est inversé..donc il faudrait l'inverser à nouveau.
		}
		else
		{
			operande_Recuperer="";
			while(debut<entrer.length()-1)
			{
				operande_Recuperer=operande_Recuperer+entrer.charAt(debut+1);
				debut++;
			}
			return operande_Recuperer;
		}
	}
	public String forcerAffichagePlusEtMoinsAuDebutNombre(String entrer)
	{
		if(siCaractereParmisChiffre(entrer.charAt(0)))
			return "+"+entrer;
		else 
			return entrer;
	}
	public String enleverPlusEtMoinsAuDebutNombre(String entrer)
	{
		if(entrer.charAt(0)=='+')
			return entrer.substring(1,entrer.length());
		else 
			return entrer;
	}

/**********************************************************************************************/
	//Enlever les calculs d'operations '*' '/' '+' '-'
	private String enleverOperation(String entrer, char operateur)
	{
		String apresResultat=entrer;
		int debut=0;
		if(operateur=='*')
		{
				for(int i=0;i<entrer.length();i++)
				{
					if(entrer.charAt(i)=='*')
					{
						double c=Double.parseDouble(retourneValeurNombrePourMultiplicationEtDivision(entrer,true,i))*Double.parseDouble(retourneValeurNombrePourMultiplicationEtDivision(entrer,false,i));
						String finalAfficher=(enleverExponentielleEtForcerSortiPointsurDouble(c));
						apresResultat=prendreResteCaractere(entrer,true,i-retourneValeurNombrePourMultiplicationEtDivision(entrer,true,i).length())+finalAfficher+prendreResteCaractere(entrer,false,i+retourneValeurNombrePourMultiplicationEtDivision(entrer,false,i).length());
						return enleverOperation(apresResultat,'*');
					}		
				}
		}
		else if(operateur=='/')
		{
				for(int i=0;i<entrer.length();i++)
				{
					if(entrer.charAt(i)=='/')
					{
						double c=Double.parseDouble(retourneValeurNombrePourMultiplicationEtDivision(entrer,true,i))/Double.parseDouble(retourneValeurNombrePourMultiplicationEtDivision(entrer,false,i));
						String finalAfficher=(enleverExponentielleEtForcerSortiPointsurDouble(c));
						apresResultat=prendreResteCaractere(entrer,true,i-retourneValeurNombrePourMultiplicationEtDivision(entrer,true,i).length())+finalAfficher+prendreResteCaractere(entrer,false,i+retourneValeurNombrePourMultiplicationEtDivision(entrer,false,i).length());
						return enleverOperation(apresResultat,'/');
					}	
				}
		}
		else if(operateur=='+')
		{
				if(entrer.charAt(0)=='+'||entrer.charAt(0)=='-')
			debut=1;
			else
			debut=0;
				for(int i=debut;i<entrer.length();i++)
				{
					if(entrer.charAt(i)=='+')
					{
						double c=Double.parseDouble(retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i))+Double.parseDouble(retourneValeurNombrePourAdditionEtSoustraction(entrer,false,i));
						String finalAfficher=(enleverExponentielleEtForcerSortiPointsurDouble(c));
						apresResultat=prendreResteCaractere(entrer,true,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length())+forcerAffichagePlusEtMoinsAuDebutNombre(finalAfficher)+prendreResteCaractere(entrer,false,i+retourneValeurNombrePourAdditionEtSoustraction(entrer,false,i).length());
						return enleverOperation(apresResultat,'+');
					}		
				}
		}
		else
		{
			//L'opérateur est donc '-'.
				if(entrer.charAt(0)=='+'||entrer.charAt(0)=='-')
			debut=1;
			else
			debut=0;
				for(int i=debut;i<entrer.length();i++)
				{
					if(entrer.charAt(i)=='-')
					{
						double c=Double.parseDouble(retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i))-Double.parseDouble(retourneValeurNombrePourAdditionEtSoustraction(entrer,false,i));
						String finalAfficher=(enleverExponentielleEtForcerSortiPointsurDouble(c));
						apresResultat=prendreResteCaractere(entrer,true,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length())+forcerAffichagePlusEtMoinsAuDebutNombre(finalAfficher)+prendreResteCaractere(entrer,false,i+retourneValeurNombrePourAdditionEtSoustraction(entrer,false,i).length());
						return enleverOperation(apresResultat,'-');
					}		
				}
		}
		return apresResultat;
	}

/**********************************************************************************************/
		//code pour enlever les parenthèses mais pas pour calculer.
	private String enleverOperationParenthese(String entrer)
	{
		entrer=supprimerUneChiffreAvecParentheseOuverte(entrer);
		entrer=supprimerUneParentheseFermeAvecUneOuverte(entrer);//Attribuer donc l'operation de multiplication.
		String recupereValeurSorti=entrer,recupererValeurEntreParenthese="";
		
		if(nombreTotalParenthese(recupereValeurSorti)==0)
		{
			return sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(recupereValeurSorti);
		}	
		else
		{
				for(int i=0;i<entrer.length();i++)
				{
						if(i==dernierPositionIndiceParentheseOuverte(entrer))
						{
							for(int j=i+1;j<prochaineIndicePositionAPartirIndiceParentheseFerme(entrer,i);j++)
							{
								recupererValeurEntreParenthese=recupererValeurEntreParenthese+entrer.charAt(j);
							}
								if(testerSiExisteErreur(recupererValeurEntreParenthese))
								return retourneErreur(recupererValeurEntreParenthese);
								else
								{
								recupererValeurEntreParenthese=enleverTouteOperation(sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(recupererValeurEntreParenthese));
								recupereValeurSorti=prendreResteCaractere(entrer,true,i)+recupererValeurEntreParenthese+prendreResteCaractere(entrer,false,prochaineIndicePositionAPartirIndiceParentheseFerme(entrer,i+1));
								recupereValeurSorti=(enleverOperationParenthese(recupereValeurSorti));
								return recupereValeurSorti;
								}
						//Il est possible que la valeur entre parenthèse est de signe négatif et si, le signe operande avant la parenthese est aussi '-'
						//On se trouve dans un cas où il y a deux '-' successif d'où l'utilité de la méthode supprimerDeuxOperandeSuccessifDifferent devenat donc '+'.
						//enleverPlusEtMoinsAuDebutNombre joue un role si on croise un nombre '+7' on le traduit en '7', '-7' en '-7'
						//supprimerDeuxOperandeSuccessifDifferent joue un role si on croise deux signe différents, '+' au début, et '-', deviendrait donc '-'
						}
						else
						continue;	
				}
		}
				return sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(recupereValeurSorti);
	}
/**********************************************************************************************/
	//Effectuer tous calculs.
	private String enleverTouteOperation(String entrer)
	{
		//L'operation doit être d'abbord passer par la suppression des parenthèses(ci-dessous).
		entrer=sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(entrer);
		//La calculatrice suit les règles de priorisations sur les opérations.
		String calculFinalSortie=enleverOperation(entrer,'/');
		calculFinalSortie=enleverOperation(calculFinalSortie,'*');
		calculFinalSortie=enleverOperation(calculFinalSortie,'+');
		calculFinalSortie=enleverOperation(calculFinalSortie,'-');
		return enleverPlusEtMoinsAuDebutNombre(calculFinalSortie);
	}
	private int totalOperrandePlusEtMoins(String entrer)
	{
		int nombre=0;
			for(int i=0;i<entrer.length();i++)
			{
					if(entrer.charAt(i)=='+'||entrer.charAt(i)=='-')
						nombre++;
			}
			return nombre;
	}	
	private String supprimerDeuxOperandeSuccessif(String entrer)
	{
	//'-'*'-' donne '+'
	//On a pas spécifié le cas ou '+'*'+' donne '+' car on a forcé lors de
	// l'affichage du nombre que '+9' devient 9 donc, à la logique, ++9 devient 
	//directement +9 seulement.
	
		String resultat_Sorti="";
		for(int v=0;v<=totalOperrandePlusEtMoins(resultat_Sorti)+1;v++)
		{
			resultat_Sorti="";
			for(int i=0;i<entrer.length();i++)
			{
			if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='-')
			{
				resultat_Sorti=resultat_Sorti+'+';
				i++;
			}
			else if(entrer.charAt(i)=='-'&&entrer.charAt(i+1)=='+')
			{
				resultat_Sorti=resultat_Sorti+'-';
				i++;
			}
			else if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='+')
			{
				resultat_Sorti=resultat_Sorti+'+';
				i++;
				
			}
			else if(entrer.charAt(i)=='+'&&entrer.charAt(i+1)=='-')
			{
				resultat_Sorti=resultat_Sorti+'-';
				i++;
			}
			else
			resultat_Sorti=resultat_Sorti+entrer.charAt(i);
			}
			entrer=resultat_Sorti;		
		}
	return resultat_Sorti;
	}
	private String OperationDividanteEtMultiplicationAvecAdditionEtSoustraction(String entrer, int debut)
	{
		//Pour calculer des valeurs comme 2*-6........
		String sb = entrer;
		
				for(int i=debut;i<entrer.length();i++)
				{
					if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='-')
					{
						sb=deplacerCaractereParIndice(sb, false,i+1,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length());
						return OperationDividanteEtMultiplicationAvecAdditionEtSoustraction((sb),i+retourneValeurNombreAvecSigneApresIndice(entrer,i).length());
					}
					else if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='+')
					{
						sb=deplacerCaractereParIndice(sb, false,i+1,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length());
						return OperationDividanteEtMultiplicationAvecAdditionEtSoustraction((sb),i+retourneValeurNombreAvecSigneApresIndice(entrer,i).length());
					}
					else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='-')
					{
						sb=deplacerCaractereParIndice(sb, false,i+1,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length());
						return OperationDividanteEtMultiplicationAvecAdditionEtSoustraction((sb),i+retourneValeurNombreAvecSigneApresIndice(entrer,i).length());
					}
					else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='+')
					{
						sb=deplacerCaractereParIndice(sb, false,i+1,i-retourneValeurNombrePourAdditionEtSoustraction(entrer,true,i).length());
						return OperationDividanteEtMultiplicationAvecAdditionEtSoustraction((sb),i+retourneValeurNombreAvecSigneApresIndice(entrer,i).length());
					}				
				}
		return sb.toString();		
	}
	private Boolean siExisteOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(String entrer)
	{
		Boolean a=false;
		for (int i=0;i<entrer.length();i++) 
		{
					if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='-')
					{
						a=true;
						break;
					}
					else if(entrer.charAt(i)=='*'&&entrer.charAt(i+1)=='+')
					{
						a=true;
						break;
					}
					else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='-')
					{
						a=true;
						break;
					}
					else if(entrer.charAt(i)=='/'&&entrer.charAt(i+1)=='+')
					{
						a=true;
						break;
					}
					else
						a=false;
		}
		return a;
	}
	//La méthode final pour la sortie d'operation comme exemple: 2*-8, 2/-8, 2/+6
	private String sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(String entrer)
	{
		String sortie=entrer;	
			while(siExisteOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(entrer))
			{
			sortie=OperationDividanteEtMultiplicationAvecAdditionEtSoustraction(sortie,0);
			return sortieFinalOperationDividanteEtMultiplicationAvecAdditionEtSoustraction(sortie);
			}	
		return supprimerDeuxOperandeSuccessif(sortie);
	}
	private String retourneValeurNombreAvecSigneApresIndice(String entrer,int debut)
	{
		String operande_Recuperer="";
		for(int i=debut+1;i<entrer.length();i++)
			{
				if(i==debut+1&&(entrer.charAt(i)=='+'||entrer.charAt(i)=='-'))
				{	
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	//Utilise pour prendre signe au début index debut+1.
				}
				else if(siCaractereParmisChiffre(entrer.charAt(i))||entrer.charAt(i)=='.')
				{
					operande_Recuperer=operande_Recuperer+entrer.charAt(i);	
				}
				else if(siCaractereParmisOperandeEtParenthese(entrer.charAt(i)))
					break;
			//au début le nombre est inversé..donc il faudrait l'inverser à nouveau pour reconstutier la chaine initiale.
			}
		return operande_Recuperer;	
	}
	private Boolean siCaractereParmisOperandeEtParenthese(char caractere)
	{
		return (caractere=='*'||caractere=='+'||caractere=='-'||caractere=='/'||caractere=='('||caractere==')');
	}
	private String supprimerCaractereParIndice(String entrer, int indice)
	{
		String sortie="";
		for (int i=0;i<entrer.length();i++) 
		{
			if(indice==0)
				return entrer.substring(1,entrer.length());
			else if(i==indice)
				continue;
			else
				sortie=sortie+entrer.charAt(i);
		}
		return sortie;
	}
	private String deplacerCaractereParIndice(String entrer, Boolean versArriere, int indiceActuel,int nouveauIndice)
	{
		if(versArriere)
		{
		StringBuilder resultat=new StringBuilder(entrer);
		String resultat_Sorti_Final="";
		for(int i=0;i<entrer.length();i++)
		{
			if(i==indiceActuel)
			{
			resultat.insert(nouveauIndice+1,entrer.charAt(i));
			resultat_Sorti_Final=supprimerCaractereParIndice(resultat.toString(),indiceActuel);
			break;
			}
		}
		return resultat_Sorti_Final;
		}
		else
		{
			StringBuilder resultat=new StringBuilder(entrer);
			String resultat_Sorti_Final="";
			for(int i=0;i<entrer.length();i++)
			{
				if(i==indiceActuel)
				{
				resultat.insert(nouveauIndice,entrer.charAt(i));
				resultat_Sorti_Final=supprimerCaractereParIndice(resultat.toString(),indiceActuel+1);
				break;
				}
			}
		return resultat_Sorti_Final;
		}
	}
	private String supprimerUneParentheseFermeAvecUneOuverte(String entrer)
	//'(x)(y)' donne '*'
	{
		String resultat_Sorti="";
		for(int i=0;i<entrer.length()-1;i++)
		{
			if(entrer.charAt(i)==')'&&entrer.charAt(i+1)=='(')
			{
				resultat_Sorti=resultat_Sorti+')'+'*';
			}
			else
			resultat_Sorti=resultat_Sorti+entrer.charAt(i);
		}
		return resultat_Sorti+entrer.charAt(entrer.length()-1);
	}
	private String supprimerUneChiffreAvecParentheseOuverte(String entrer)
	//'2(y)' donne '2*y'
	{
		String resultat_Sorti="";
		for(int i=0;i<entrer.length()-1;i++)
		{
			if(siCaractereParmisChiffre(entrer.charAt(i))&&entrer.charAt(i+1)=='(')
			resultat_Sorti=resultat_Sorti+entrer.charAt(i)+'*';
			else
			resultat_Sorti=resultat_Sorti+entrer.charAt(i);
		}
		return resultat_Sorti+entrer.charAt(entrer.length()-1);
	}
/**********************************************************************************************/
	//Code logique du calculatrice 
		private String CalculeAFaire(String entrer)
	{
		entrer=entrer.replace(" ","");//Obligatoire à faire pour supprimer les caractères blancs.
		if(testerSiExisteErreur(entrer))
			return retourneErreur(entrer);
		else
			return enleverPlusEtMoinsAuDebutNombre(enleverTouteOperation((enleverOperationParenthese(entrer))));
	}
}