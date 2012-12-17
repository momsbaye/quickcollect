package enquete.quickcollect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;

public class ReponsesBDD {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "idev.db";
 
	private static final String TABLE_REPONSES = "table_reponses_fiches";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_ENQTEUR = "ID_ENQTEUR";
	private static final int NUM_COL_ENQTEUR = 1;
	private static final String COL_RUBR = "ID_RUBR";
	private static final int NUM_COL_RUBR = 2;
	private static final String COL_IDQUES = "IDQUESTION";
	private static final int NUM_COL_IDQUES = 3;
	private static final String COL_REP = "Reponse";
	private static final int NUM_COL_REP = 4;
	
	
	private SQLiteDatabase bdd;
	 
	private BaseReponsesFiches maBaseSQLite;
	public ReponsesBDD(Context context) {
		// TODO Auto-generated constructor stub
		maBaseSQLite = new BaseReponsesFiches(context, NOM_BDD, null, VERSION_BDD);

	}
	


		// ouverture en lecture ecriture
		public void open(){
			//on ouvre la BDD en �criture
			bdd = maBaseSQLite.getWritableDatabase();
		}
		
		// fermeture
		public void close(){
			//on ferme l'acc�s � la BDD
			bdd.close();
		}
		
		public SQLiteDatabase getBDD(){
			return bdd;
		}
		public long insertReponsesFiches(ReponsesFiches rf){
			//Cr�ation d'un ContentValues (fonctionne comme une HashMap)
			ContentValues values = new ContentValues();
			//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
			values.put(COL_ID, rf.getIdfiche());
			values.put(COL_ENQTEUR, rf.getIdenqueteur());
			values.put(COL_RUBR, rf.getIdrubrique());
			values.put(COL_IDQUES, rf.getIdquestion());
			values.put(COL_REP, rf.getReponse());
			//on ins�re l'objet dans la BDD via le ContentValues
			return bdd.insert(TABLE_REPONSES, null, values);
		}
		public ReponsesFiches getReponses(){
			//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
			Cursor c = bdd.query(TABLE_REPONSES, new String[] {COL_ID, COL_ENQTEUR, COL_RUBR, COL_IDQUES, COL_REP}, null, null, null, null, null);
			return cursorToReponsesFiches(c);
		}
	 
		//Cette m�thode permet de convertir un cursor en un livre
		private ReponsesFiches cursorToReponsesFiches(Cursor c){
			//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
			if (c.getCount() == 0)
				return null;
	 
			//Sinon on se place sur le premier �l�ment
			c.moveToFirst();
			//On cr�� un livre
			ReponsesFiches rep = new ReponsesFiches();
			//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
			rep.setIdfiche(c.getInt(NUM_COL_ID));
			rep.setIdquestion(c.getString(NUM_COL_IDQUES));
			rep.setReponse(c.getString(NUM_COL_REP));
			//On ferme le cursor
			c.close();
	 
			//On retourne le livre
			return rep;
		}
		

}
