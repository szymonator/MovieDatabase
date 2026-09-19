package stores;

import interfaces.Identifiable;

public class CreditNode implements Identifiable {
    
    private int id;
    private CastCredit[] cast;
    private CrewCredit[] crew;

   /**
    * A contructor for the CreditNode class
    * 
    * @param id the ID to be stored
    * @param cast the array of cast members to be stored
    * @param crew the arry of crew members to be stored
    */
    public CreditNode(int id, CastCredit[] cast, CrewCredit[] crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    /**
     * Retrieves the ID of the credits
     * 
     * @return the ID of the credits
     */
    public int getID() {
        return this.id;
    }

    /**
     * Retrieves the array of cast members stored
     * 
     * @return the array of cast members stored
     */
    public CastCredit[] getCast() {
        return cast;
    }

    /**
     * Retrieves the array of crew members stored
     * 
     * @return the array of crew members stored
     */
    public CrewCredit[] getCrew() {
        return crew;
    }

    /**
     * Retrieves the amount of cast credited
     * 
     * @return the amount of cast credited
     */
    public int getCastSize() {
        return cast.length;
    }

    /**
     * Retrieves the amount of crew credited
     * 
     * @return the amount of crew credited
     */
    public int getCrewSize() {
        return crew.length;
    }
     
}
