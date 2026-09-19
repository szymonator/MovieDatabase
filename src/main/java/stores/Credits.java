package stores;

import structures.*;

import java.util.Comparator;

import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;

    private MyHashTable<CreditNode> creditsTable;
    private MyHashTable<PersonNode> castTable;
    private MyHashTable<PersonNode> crewTable;


    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        this.stores = stores;
        this.creditsTable = new MyHashTable<CreditNode>(1511);
        this.castTable = new MyHashTable<PersonNode>(10000); // underestimating amount of unique cast - resize can be triggered
        this.crewTable = new MyHashTable<PersonNode>(10000); // underestimating amount of unique crew - resize can be triggered
        
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        if (creditsTable.get(id) == null) {
            CreditNode credits = new CreditNode(id, cast, crew);
            creditsTable.put(credits);

            resolveCast(id, cast);
            resolveCrew(id, crew);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds each cast member to the table
     * 
     * <p>For every cast member in the provided array, if they don't yet exist in the table, create them
     * and add the film they worked on. Else, simply add the information to their current store.</p>
     * @param filmID the ID of the movie that was worked on
     * @param castList an array of all the cast members
     */
    private void resolveCast(int filmID, CastCredit[] castList) {
        Person person;
        CastCredit credit;
        for (int i=0; i<castList.length; i++) {
            credit = castList[i];
            if (castTable.get(credit.getID()) == null) {
                person = new Person(credit.getID(), credit.getName(), credit.getProfilePath());
                castTable.put(new PersonNode(person, filmID, credit.getOrder()));
            } else {
                castTable.get(credit.getID()).addFilm(filmID, credit.getOrder());
            }
        }
    }

    /**
     * Adds each crew member to the table
     * 
     * <p>For every crew member in the provided array, if they don't yet exist in the table, create them
     * and add the film they worked on. Else, simply add the information to their current store.</p>
     * @param filmID the ID of the movie that was worked on
     * @param crewList an array of all the crew members
     */
    private void resolveCrew(int filmID, CrewCredit[] crewlist) {
        Person person;
        CrewCredit credit;
        for (int i=0; i<crewlist.length; i++) {
            credit = crewlist[i];
            if (crewTable.get(credit.getID()) == null) {
                person = new Person(credit.getID(), credit.getName(), credit.getProfilePath());
                crewTable.put(new PersonNode(person, filmID, 99));
            } else {
                crewTable.get(credit.getID()).addFilm(filmID, 99);
            }
        }
    }


    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        return creditsTable.remove(id);
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film cannot be 
     *         found in Credits, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        CreditNode cn = creditsTable.get(filmID);
        if (cn != null) {
            CastCredit[] temp = cn.getCast();
            quickSort(temp, 0, temp.length-1,  (a, b) -> Integer.compare(a.getOrder(), b.getOrder()));
            return temp;
        } else {
            return new CastCredit[0];
        }
        
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in "id" order (not "elementID"). If there 
     *         is no crew members attached to a film, or the film cannot be found in Credits, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        CreditNode cn = creditsTable.get(filmID);
        if (cn != null) {
            CrewCredit[] temp = cn.getCrew();
            quickSort(temp, 0, temp.length-1,  (a, b) -> Integer.compare(a.getID(), b.getID()));
            return temp;
        } else {
            return new CrewCredit[0];
        }
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        CreditNode cn = creditsTable.get(filmID);
        if (cn != null) {
            return cn.getCastSize();
        } else {
            return -1;
        }
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCrew(int filmID) {
        CreditNode cn = creditsTable.get(filmID);
        if (cn != null) {
            return cn.getCrewSize();
        } else {
            return -1;
        }
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        long startTime = System.nanoTime();
        
        MyLinkedList<PersonNode> castLinkedList = castTable.getAllObjects();
        MyListElement<PersonNode> current = castLinkedList.getHead();
        Person[] arr = new Person[castLinkedList.getSize()];
        for (int i=castLinkedList.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal().getPerson();
            current = current.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getUniqueCast executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        long startTime = System.nanoTime();
        
        MyLinkedList<PersonNode> crewLinkedList = crewTable.getAllObjects();
        MyListElement<PersonNode> current = crewLinkedList.getHead();
        Person[] arr = new Person[crewLinkedList.getSize()];
        for (int i=crewLinkedList.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal().getPerson();
            current = current.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getUniqueCrew executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCast(String cast) {
        long startTime = System.nanoTime();
        
        String search = cast.toLowerCase();
        MyLinkedList<PersonNode> list = castTable.getAllObjects();
        MyLinkedList<PersonNode> people = new MyLinkedList<PersonNode>();
        MyListElement<PersonNode> current = list.getHead();
        PersonNode personNode;

        for (int i=0; i<list.getSize(); i++) {
            personNode = current.getVal();
            if (personNode.getPerson().getName().toLowerCase() != null && personNode.getPerson().getName().toLowerCase().contains(search)) {
                people.add(personNode);
            }
            current = current.getNext();
        }

        MyListElement<PersonNode> currentPerson = people.getHead();
        Person[] arr = new Person[people.getSize()];
        for (int i=0; i<people.getSize(); i++) {
            arr[i] = currentPerson.getVal().getPerson();
            currentPerson = currentPerson.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("findCast executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCrew(String crew) {
        long startTime = System.nanoTime();
        
        String search = crew.toLowerCase();
        MyLinkedList<PersonNode> list = crewTable.getAllObjects();
        MyLinkedList<PersonNode> people = new MyLinkedList<PersonNode>();
        MyListElement<PersonNode> current = list.getHead();
        PersonNode personNode;

        for (int i=0; i<list.getSize(); i++) {
            personNode = current.getVal();
            if (personNode.getPerson().getName().toLowerCase() != null && personNode.getPerson().getName().toLowerCase().contains(search)) {
                people.add(personNode);
            }
            current = current.getNext();
        }

        MyListElement<PersonNode> currentPerson = people.getHead();
        Person[] arr = new Person[people.getSize()];
        for (int i=0; i<people.getSize(); i++) {
            arr[i] = currentPerson.getVal().getPerson();
            currentPerson = currentPerson.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("findCrew executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        PersonNode pn = castTable.get(castID);
        if (pn != null) {
            return pn.getPerson();
        } else {
            return null;
        }
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID){
        PersonNode pn = crewTable.get(crewID);
        if (pn != null) {
            return pn.getPerson();
        } else {
            return null;
        }
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        PersonNode pn = castTable.get(castID);
        if (pn != null) {
            return pn.getFilmIds();
        } else {
            return new int[0];
        }
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        PersonNode pn = crewTable.get(crewID);
        if (pn != null) {
            return pn.getFilmIds();
        } else {
            return new int[0];
        }
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID){
        PersonNode pn = castTable.get(castID);
        if (pn != null) {
            return pn.getStarsInIds();
        } else {
            return new int[0];
        }
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest to lowest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        long startTime = System.nanoTime();
        
        MyLinkedList<PersonNode> castLinkedList = castTable.getAllObjects();
        MyListElement<PersonNode> current = castLinkedList.getHead();
        PersonNode[] tempArr = new PersonNode[castLinkedList.getSize()];
        for (int i=castLinkedList.getSize()-1; i>=0; i--) {
            tempArr[i] = current.getVal();
            current = current.getNext();
        }

        quickSort(tempArr, 0, tempArr.length-1, (a, b) -> Integer.compare(b.getCreditCount(), a.getCreditCount()));

        int cap = Math.min(numResults, tempArr.length);
        Person[] arr = new Person[cap];
        for (int i=0; i<cap; i++) {
            arr[i] = tempArr[i].getPerson();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getMostCastCredits executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        PersonNode pn = castTable.get(castID);
        if (pn != null) {
            return pn.getCreditCount();
        } else {
            return -1;
        }
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return creditsTable.getSize();
    }

    /**
     * A recursive method that implements the QuickSort algorithm
     * @param arr the array to be sorted
     * @param start the position of the i/start pointer 
     * @param end the position of the j/end pointer
     * @param comp a lambda function which determines if the sort occurs ascending/descending, and also implements the comparison method for the given object
     */
    private <T> void quickSort(T[] arr, int start, int end, Comparator<T> comp) {
        if (start < end) {
            int pivotIndex = partition(arr, start, end, comp);
            
            quickSort(arr, start, pivotIndex - 1, comp);
            quickSort(arr, pivotIndex + 1, end, comp);
        }
    }

    /**
     * An implementation of the Lomute-style partition algorithm
     * <p>ensures that for the chosen pivot, which is the middle value, every value below it is smaller,
     * and every value about it is larger. During this process, the pivot is kept at the end.</p>
     * 
     * @param arr the array to be sorted
     * @param start the position of the i/start pointer 
     * @param end the position of the j/end pointer
     * @param comp a lambda function which determines if the sort occurs ascending/descending, and also implements the comparison method for the given object
     * @return the value at the pivot at the end of the partition
     */
    private <T> int partition(T[] arr, int start, int end, Comparator<T> comp) {
        
        // follows structure from lecture notes
        int pivotIndex = getMedianPivotIndex(arr, start, end, comp);
        swap(arr, pivotIndex, end);

        T pivotValue = arr[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (comp.compare(arr[j], pivotValue) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, end);
        return i + 1;
    }

    /**
     * A helper method to swap 2 given values in the given array
     * 
     * @param arr the array in which the swap occurs
     * @param i the first index
     * @param j the second index
     */
    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * An efficient helper method that uses the median of 3 process to choose the pivot index
     * 
     * @param arr the array the quicksort is being performed on
     * @param low the position of the i/start index
     * @param high the position of the j/end index
     * @param comp a lambda function which determines if the sort occurs ascending/descending, and also implements the comparison method for the given object
     * @return the computed pivot index
     */
    private <T> int getMedianPivotIndex(T[] arr, int low, int high, Comparator<T> comp) {
        int mid = low + (high - low) / 2; 
        
        T a = arr[low];
        T b = arr[mid];
        T c = arr[high];

        if (comp.compare(a, b) < 0) {
            if (comp.compare(b, c) < 0) {
                return mid;
            } else if (comp.compare(a, c) < 0) {
                return high;
            } else {
                return low;
            }
        } else {
            if (comp.compare(a, c) < 0) {
                return low;
            } else if (comp.compare(b, c) < 0) {
                return high;
            } else {
                return mid;
            }
        }
    }
}
