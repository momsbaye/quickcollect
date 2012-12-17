package enquete.quickcollect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FichesBDD {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "idev.db";
 
	private static final String TABLE_FICHE = "table_fiche";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	
 
	private SQLiteDatabase bdd;
 
	private BaseFiche maBaseSQLite;
 
	public FichesBDD(Context context) {
		// TODO Auto-generated constructor stub
		maBaseSQLite = new BaseFiche(context, NOM_BDD, null, VERSION_BDD);
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
	
	
	// insert fiche
	public long insertFiche(){
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_ID, "");
		return bdd.insert(TABLE_FICHE, null, values);
	}
	
	public Fiche getFiche(){
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
		Cursor c = bdd.query(TABLE_FICHE, new String[] {COL_ID}, null, null, null, null, null);
		return cursorToFiche(c);
	}
	
	//Cette m�thode permet de convertir un cursor en un livre
		private Fiche cursorToFiche(Cursor c){
			//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
			if (c.getCount() == 0)
				return null;
	 
			//Sinon on se place sur le premier �l�ment
			c.moveToFirst();
			//On cr�� un livre
			Fiche fiche = new Fiche();
			//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
			fiche.setId(c.getInt(NUM_COL_ID));
			//On ferme le cursor
			c.close();
	 
			//On retourne le livre
			return fiche;
		}
 
	
	

}
