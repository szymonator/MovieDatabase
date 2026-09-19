package stores;

import java.time.LocalDate;

import interfaces.Identifiable;
import structures.MyLinkedList;
import structures.MyListElement;

public class MovieNode implements Identifiable {

    
    private int id;
    private String title;
    private String originalTitle;
    private String overview;
    private String tagline;
    private String status;
    private Genre[] genres;
    private LocalDate release;
    private long budget;
    private long revenue;
    private String[] languages;
    private String originalLanguage;
    private double runtime;
    private String homepage;
    private boolean adult;
    private boolean video;
    private String poster;

    private int collectionID;
    private String collectionName;
    private String collectionPosterPath;
    private String collectionBackdropPath;
    private String imdbID;
    private double popularity;
    private double voteAverage;
    private int voteCount;

    private MyLinkedList<Company> productionCompanies;
    private MyLinkedList<String> productionCountries;

    /**
     * A constructor for the MovieNode object
     * 
     * @param id
     * @param title
     * @param originalTitle
     * @param overview
     * @param tagline
     * @param status
     * @param genres
     * @param release
     * @param budget
     * @param revenue
     * @param languages
     * @param originalLanguage
     * @param runtime
     * @param homepage
     * @param adult
     * @param video
     * @param poster
     */
    public MovieNode(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline;
        this.status = status;
        this.genres = genres;
        this.release = release;
        this.budget = budget;
        this.revenue = revenue;
        this.languages = languages;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.homepage = homepage;
        this.adult = adult;
        this.video = video;
        this.poster = poster;

        this.productionCompanies = new MyLinkedList<Company>();
        this.productionCountries = new MyLinkedList<String>();
    }

    // Getters and Setters
    /**
     * Gets the id.
     * @return the id
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Gets the title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the original title.
     * @return the original title
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Gets the overview.
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Gets the tagline.
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * Gets the status.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the genres.
     * @return the genres
     */
    public Genre[] getGenres() {
        return genres;
    }

    /**
     * Gets the release.
     * @return the release
     */
    public LocalDate getRelease() {
        return release;
    }

    /**
     * Gets the budget.
     * @return the budget
     */
    public long getBudget() {
        return budget;
    }

    /**
     * Gets the revenue.
     * @return the revenue
     */
    public long getRevenue() {
        return revenue;
    }

    /**
     * Gets the languages.
     * @return the languages
     */
    public String[] getLanguages() {
        return languages;
    }

    /**
     * Gets the original language.
     * @return the original language
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Gets the runtime.
     * @return the runtime
     */
    public double getRuntime() {
        return runtime;
    }

    /**
     * Gets the homepage.
     * @return the homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Gets the adult.
     * @return the adult
     */
    public boolean getAdult() {
        return adult;
    }

    /**
     * Gets the video.
     * @return the video
     */
    public boolean getVideo() {
        return video;
    }

    /**
     * Gets the poster.
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Gets the collection ID.
     * @return the collection ID
     */
    public int getCollectionID() {
        return collectionID;
    }

    /**
     * Sets the collection ID.
     * @param collectionID the collection ID to set
     */
    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    /**
     * Gets the collection name.
     * @return the collection name
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Sets the collection name.
     * @param collectionName the collection name to set
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * Gets the collection poster path.
     * @return the collection poster path
     */
    public String getCollectionPosterPath() {
        return collectionPosterPath;
    }

    /**
     * Sets the collection poster path.
     * @param collectionPosterPath the collection poster path to set
     */
    public void setCollectionPosterPath(String collectionPosterPath) {
        this.collectionPosterPath = collectionPosterPath;
    }

    /**
     * Gets the collection backdrop path.
     * @return the collection backdrop path
     */
    public String getCollectionBackdropPath() {
        return collectionBackdropPath;
    }

    /**
     * Sets the collection backdrop path.
     * @param collectionBackdropPath the collection backdrop path to set
     */
    public void setCollectionBackdropPath(String collectionBackdropPath) {
        this.collectionBackdropPath = collectionBackdropPath;
    }

    /**
     * Gets the imdb ID.
     * @return the imdb ID
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     * Sets the imdb ID.
     * @param imdbID the imdb ID to set
     */
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     * Gets the popularity.
     * @return the popularity
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * Sets the popularity.
     * @param popularity the popularity to set
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Gets the vote average.
     * @return the vote average
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Sets the vote average.
     * @param voteAverage the vote average to set
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * Gets the vote count.
     * @return the vote count
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets the vote count.
     * @param voteCount the vote count to set
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Gets the production companies.
     * @return the production companies
     */
    public Company[] getProductionCompanies() {

        Company[] arr = new Company[productionCompanies.getSize()];
        MyListElement<Company> current = productionCompanies.getHead();
        for (int i=productionCompanies.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }

        return arr;
    }

    /**
     * Gets the production countries.
     * @return the production countries
     */
    public String[] getProductionCountries() {

        String[] arr = new String[productionCountries.getSize()];
        MyListElement<String> current = productionCountries.getHead();
        for (int i=productionCountries.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }

        return arr;
    }

    /**
     * Adds a production company.
     * @param c the company to add
     */
    public void addProductionCompany(Company c) {
        productionCompanies.add(c);
    }

    /**
     * Adds production countries.
     * @param s the country to add
     */
    public void addProductionCountries(String s) {
        productionCountries.add(s);
    }

    public int compareTo(MovieNode other) {
        return Integer.compare(this.getID(), other.getID());
    }
}
