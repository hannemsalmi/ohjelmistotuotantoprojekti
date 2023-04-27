package controller;

import java.util.List;
import java.time.LocalDate;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
/**
 * Interface for the controller class of a budget/expense tracking application.
 * This interface defines the main methods required to manage expenses, users, and categories.
 */
public interface IKontrolleri {
	 /**
     * Retrieves a list of expenses for a specific user.
     * @param kayttajaid The user ID for which to retrieve expenses.
     * @return A list of expenses associated with the specified user.
     */
	public abstract List<Kulu> getKulut(int kayttajaid);
	 /**
     * Retrieves a single expense by its ID.
     * @param kuluid The ID of the expense to retrieve.
     * @return The expense associated with the specified ID.
     */
	public abstract Kulu getKulu(int kuluid);
	/**
     * Adds a new expense to the application.
     * @param nimi The name of the expense.
     * @param hinta The price of the expense.
     * @param paivamaara The date of the expense.
     * @param kategoria The category of the expense.
     * @param kayttaja The user associated with the expense.
     * @param kuvaus A description of the expense.
     */
	public abstract void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus);
	/**
     * Retrieves a user by their ID.
     * @param kayttajaid The ID of the user to retrieve.
     * @return The user associated with the specified ID.
     */
	public abstract Kayttaja getKayttaja(int kayttajaid);
	 /**
     * Adds a new user to the application.
     * @param nimi The name of the user.
     * @param budjetti The budget of the user.
     */
	public abstract void lisaaKayttaja(String nimi, double budjetti);
	/**
     * Retrieves a list of user names.
     * @return A list of user names.
     */
	public abstract List<String> getKayttajat();
	/**
     * Retrieves a list of category names for a specific user.
     * @param omistaja The owner of the categories.
     * @return A list of category names associated with the specified user.
     */
	public abstract List<String> getKategorianimet(String omistaja);
	/**
     * Retrieves a category by its name and owner.
     * @param nimi The name of the category.
     * @param omistaja The owner of the category.
     * @return The category associated with the specified name and owner.
     */
	public abstract Kategoria getKategoria(String nimi, String omistaja);
	/**
     * Updates the budget for a specific user.
     * @param kayttajaID The ID of the user whose budget will be updated.
     * @param budjetti The new budget value.
     */
	public abstract void paivitaBudjetti(int kayttajaID, double budjetti);
	 /**
     * Modifies an existing expense.
     * @param id The ID of the expense to modify.
     * @param summa The new amount for the expense.
     * @param nimi The new name for the expense.
     * @param kuvaus The new description for the expense.
     * @return true if the expense was successfully modified, false otherwise.
     */
	public abstract boolean muokkaaKulua(int id, Double summa, String nimi, String kuvaus);
	/**
     * Modifies an existing category.
     * @param id The ID of the category to modify.
     * @param nimi The new name for the category.
     * @return true if the category was successfully modified, false otherwise.
     */
	public abstract boolean muokkaaKategoriaa(int id, String nimi);
	 /**
     * Changes the category of an existing expense.
     * @param kuluId The ID of the expense whose category will be changed.
     * @param uusiKategoria The new category for the expense.
     * @return true if the category was successfully changed, false otherwise.
     */
	public abstract boolean muutaKulunKategoria(int kuluId, Kategoria uusiKategoria);
	/**
     * Deletes an expense by its ID.
     * @param id The ID of the expense to delete.
     * @return true if the expense was successfully deleted, false otherwise.
     */
	public abstract boolean poistaKulu(int id);
	/**
     * Deletes a category by its ID and user.
     * @param id The ID of the category to delete.
     * @param kayttaja The user associated with the category to delete.
     * @return true if the category was successfully deleted, false otherwise.
     */
	public abstract boolean poistaKategoria(int id, Kayttaja kayttaja);
	/**
     * Adds a new category to the application.
     * @param nimi The name of the category.
     * @param omistaja The owner of the category.
     */
	public abstract void lisaaKategoria(String nimi, String omistaja);
	/**
     * Retrieves a list of categories for a specific user.
     * @param omistaja The owner of the categories.
     * @return A list of categories associated with the specified user.
     */
	public abstract List<Kategoria> getKategoriat(String omistaja);
	 /**
     * Deletes all data associated with a specific user.
     * @param kayttajanID The ID of the user whose data will be deleted.
     */
	public abstract void poistaKayttajanTiedot(int kayttajanID);
	/**
     * Sends a request for a shopping list.
     * @return A string containing the shopping list data.
     * @throws Exception If there is an issue with the request.
     */
	public abstract String sendOstoslistaRequest()throws Exception;
	
}